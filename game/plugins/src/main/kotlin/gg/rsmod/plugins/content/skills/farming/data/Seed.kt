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
        val startingVarbitValue: Int
) {
    Guam(seedId = Items.GUAM_SEED, produceId = Items.GRIMY_GUAM, seedType = SeedType.Herb, level = 9, plantXp = 11.0, harvestXp = 12.5, startingVarbitValue = 4);

    val amountToPlant = seedType.amountToPlant // TODO: Jute is the exception here

    lateinit var seedName: String
        private set

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
