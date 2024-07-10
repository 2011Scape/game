package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Objs

enum class Patch(
    val id: Int,
    val patchName: String,
    vararg val seedTypes: SeedType,
) {
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
    EntranaHops(Objs.HOPS_PATCH_8174, "hops patch", SeedType.Hops),
    LumbridgeHops(Objs.HOPS_PATCH_8175, "hops patch", SeedType.Hops),
    SeersHops(Objs.HOPS_PATCH_8176, "hops patch", SeedType.Hops),

    /**
     * Bushes
     */
    VarrockBush(Objs.BUSH_PATCH_7577, "bush patch", SeedType.Bush),
    RimmingtonBush(Objs.BUSH_PATCH_7578, "bush patch", SeedType.Bush),
    EtceteriaBush(Objs.BUSH_PATCH_7579, "bush patch", SeedType.Bush),
    ArdougneBush(Objs.BUSH_PATCH_7580, "bush patch", SeedType.Bush),

    /**
     * Fruit trees
     */
    GnomeStrongholdFruitTree(Objs.FRUIT_TREE_PATCH_7962, "fruit tree patch", SeedType.FruitTree),
    GnomeVillageFruitTree(Objs.FRUIT_TREE_PATCH_7963, "fruit tree patch", SeedType.FruitTree),
    KaramjaFruitTree(Objs.FRUIT_TREE_PATCH_7964, "fruit tree patch", SeedType.FruitTree),
    CatherbyFruitTree(Objs.FRUIT_TREE_PATCH_7965, "fruit tree patch", SeedType.FruitTree),

    /**
     * Trees
     */
    TaverleyTree(Objs.TREE_PATCH_8388, "tree patch", SeedType.Tree),
    FaladorTree(Objs.TREE_PATCH_8389, "tree patch", SeedType.Tree),
    VarrockTree(Objs.TREE_PATCH_8390, "tree patch", SeedType.Tree),
    LumbridgeTree(Objs.TREE_PATCH_8391, "tree patch", SeedType.Tree),
    GnomeTree(Objs.TREE_PATCH_19147, "tree patch", SeedType.Tree),

    /**
     * Special
     */
    Calquat(Objs.CALQUAT_TREE_7807, "calquat tree patch", SeedType.Calquat),
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
