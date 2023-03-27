package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Objs

enum class Patch(val id: Int, val patchName: String, vararg val seedTypes: SeedType) {
    /**
     * Herb
     */
    FaladorHerb(Objs.HERB_PATCH_8150, "herb patch", SeedType.Herb),
    CatherbyHerb(Objs.HERB_PATCH_8151, "herb patch", SeedType.Herb),
    ArdougneHerb(Objs.HERB_PATCH_8152, "herb patch", SeedType.Herb),
    MorytaniaHerb(Objs.HERB_PATCH_8153, "herb patch", SeedType.Herb),

    /**
     * Flower
     */
    FaladorFlower(Objs.FLOWER_PATCH_7847, "flower patch", SeedType.Flower),
    CatherbyFlower(Objs.FLOWER_PATCH_7848, "flower patch", SeedType.Flower),
    ArdougneFlower(Objs.FLOWER_PATCH_7849, "flower patch", SeedType.Flower),
    MorytaniaFlower(Objs.FLOWER_PATCH_7850, "flower patch", SeedType.Flower),

    /**
     * Allotment
     */
    FaladorAllotmentNorth(Objs.ALLOTMENT_8550, "allotment", SeedType.Allotment),
    FaladorAllotmentSouth(Objs.ALLOTMENT_8551, "allotment", SeedType.Allotment),
    CatherbyAllotmentNorth(Objs.ALLOTMENT_8552, "allotment", SeedType.Allotment),
    CatherbyAllotmentSouth(Objs.ALLOTMENT_8553, "allotment", SeedType.Allotment),
    ArdougneAllotmentNorth(Objs.ALLOTMENT_8554, "allotment", SeedType.Allotment),
    ArdougneAllotmentSouth(Objs.ALLOTMENT_8555, "allotment", SeedType.Allotment),
    MorytaniaAllotmentNorth(Objs.ALLOTMENT_8556, "allotment", SeedType.Allotment),
    MorytaniaAllotmentSouth(Objs.ALLOTMENT_8557, "allotment", SeedType.Allotment),

    /**
     * Hops
     */
    YanilleHops(Objs.HOPS_PATCH_8173, "hops patch", SeedType.Hops),
    ;

    var varbit: Int = -1
        private set

    val persistenceId = id.toString()

    companion object {
        /**
         * Initializes the definitions and varbits for all patches. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            values().forEach {
                it.varbit = world.definitions.get(ObjectDef::class.java, it.id).varbit
            }
        }

        fun byPatchId(patchId: Int) = values().firstOrNull { it.id == patchId }
    }
}
