package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Items

enum class Seed(
        val seedId: Int,
        val produceId: Int,
        val seedType: SeedType,
        val level: Int,
        val plantXp: Double,
        val harvestXp: Double,
        val growthStages: Int,
        val plantedVarbitValue: Int,
        val diseasedVarbitValue: Int,
        val diedVarbitValue: Int,
) {
    Guam(seedId = Items.GUAM_SEED, produceId = Items.GRIMY_GUAM, seedType = SeedType.Herb, level = 9, plantXp = 11.0, harvestXp = 12.5, growthStages = 4, plantedVarbitValue = 4, diseasedVarbitValue = 128, diedVarbitValue = 170);

    private val plantedVarbits = plantedVarbitValue..(plantedVarbitValue + growthStages)
    private val diseasedVarbits = diseasedVarbitValue until (diseasedVarbitValue + growthStages - 1)
    private val diedVarbits = diedVarbitValue until (diedVarbitValue + growthStages - 1)

    val amountToPlant = seedType.amountToPlant // TODO: Jute is the exception here

    lateinit var seedName: String
        private set

    fun isPlanted(patch: Patch, varbit: Int): Boolean {
        if (seedType !in patch.seedTypes) {
            return false
        }

        return isAlive(varbit) || isDiseased(varbit) || isDead(varbit)
    }

    fun isAlive(varbit: Int) = varbit in plantedVarbits
    fun isDiseased(varbit: Int) = varbit in diseasedVarbits
    fun isDead(varbit: Int) = varbit in diedVarbits
    fun isFullyGrown(varbit: Int) = growthStage(varbit) == growthStages
    fun growthStage(varbit: Int): Int {
        return when {
            isAlive(varbit) -> varbit - plantedVarbitValue
            isDiseased(varbit) -> varbit - diseasedVarbitValue + 1
            isDead(varbit) -> varbit - diedVarbitValue + 1
            else -> throw IllegalStateException()
        }
    }

    companion object {
        /**
         * Initializes the names for all seeds. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            Seed.values().forEach {
                it.seedName = world.definitions.get(ItemDef::class.java, it.seedId).name.lowercase()
            }
        }
    }
}
