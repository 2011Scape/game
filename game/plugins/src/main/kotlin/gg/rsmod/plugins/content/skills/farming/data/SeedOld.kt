package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items

enum class SeedOld(
        val seedId: Int,
        val produce: Item,
        val seedType: SeedType,
        val level: Int,
        val plantXp: Double,
        val harvestXp: Double,
        val growthStages: Int,
        val plantedVarbitValue: Int,
        val canDisease: Boolean = true,
        val diseaseSlots: Int,
        val minLiveSaveBaseSlots: Int? = null,
        val maxLiveSaveBaseSlots: Int? = null,
        val lives: Int = 3,
) {
    Guam(seedId = Items.GUAM_SEED, produce = Item(Items.GRIMY_GUAM), seedType = SeedType.Herb, level = 9, plantXp = 11.0, harvestXp = 12.5, growthStages = 4, plantedVarbitValue = 4, diseaseSlots = 27, minLiveSaveBaseSlots = 24, maxLiveSaveBaseSlots = 80),

    Marigold(seedId = Items.MARIGOLD_SEED, produce = Item(Items.MARIGOLDS), seedType = SeedType.Flower, level = 2, plantXp = 5.2, harvestXp = 47.0, growthStages = 4, plantedVarbitValue = 8, diseaseSlots = 30, lives = 1),

    Potato(seedId = Items.POTATO_SEED, produce = Item(Items.POTATO), seedType = SeedType.Allotment, level = 1, plantXp = 8.0, harvestXp = 9.0, growthStages = 4, plantedVarbitValue = 6, diseaseSlots = 30, minLiveSaveBaseSlots = 130, maxLiveSaveBaseSlots = 200),
    ;

    private val plantedVarbits = plantedVarbitValue..(plantedVarbitValue + growthStages)
    private val diseasedVarbits = (seedType.growth.diseasedOffset + plantedVarbitValue + (if (seedType.growth.canDiseaseOnFirstStage) 0 else 1)) until (seedType.growth.diseasedOffset + plantedVarbitValue + growthStages)
    private val diedVarbits = (seedType.growth.diedOffset + plantedVarbitValue + (if (seedType.growth.canDiseaseOnFirstStage) 0 else 1)) until (seedType.growth.diedOffset + plantedVarbitValue + growthStages)
    private val wateredVarbits = seedType.growth.wateredOffset?.let {
        (it + plantedVarbitValue + (if (seedType.growth.canDiseaseOnFirstStage) 0 else 1)) until (it + plantedVarbitValue + growthStages)
    } ?: listOf()

    val amountToPlant = seedType.plant.amountToPlant // TODO: Jute is the exception here

    lateinit var seedName: String
        private set

    fun isPlanted(patch: Patch, varbit: Int): Boolean {
        if (seedType !in patch.seedTypes) {
            return false
        }

        return isHealthy(varbit) || isDiseased(varbit) || isDead(varbit) || isWatered(varbit)
    }

    fun isHealthy(varbit: Int) = varbit in plantedVarbits
    fun isDiseased(varbit: Int) = varbit in diseasedVarbits
    fun isDead(varbit: Int) = varbit in diedVarbits
    fun isWatered(varbit: Int) = seedType.growth.wateredOffset != null && varbit in wateredVarbits
    fun growthStage(varbit: Int): Int {
        return when {
            isHealthy(varbit) -> varbit - plantedVarbitValue
            isDiseased(varbit) -> varbit - plantedVarbitValue - seedType.growth.diseasedOffset
            isDead(varbit) -> varbit - plantedVarbitValue - seedType.growth.diedOffset
            isWatered(varbit) -> varbit - plantedVarbitValue - seedType.growth.wateredOffset!!
            else -> throw IllegalStateException()
        }
    }

    companion object {
        /**
         * Initializes the names for all seeds. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            SeedOld.values().forEach {
                it.seedName = world.definitions.get(ItemDef::class.java, it.seedId).name.lowercase()
            }
        }
    }
}
