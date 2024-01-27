package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Npcs

// TODO: make fishing spots move
enum class FishingSpot(
    val objectIds: List<Int>,
    val tools: List<FishingTool>
) {
    CRAYFISH_CAGE(
        objectIds = listOf(Npcs.FISHING_SPOT_CRAYFISH_CAGE),
        tools = listOf(FishingTool.CRAYFISH_CAGE)
    ),
    NET_AND_BAIT(
        objectIds = listOf(Npcs.FISHING_SPOT_NET_BAIT, Npcs.FISHING_SPOT_327),
        tools = listOf(FishingTool.SMALL_FISHING_NET, FishingTool.FISHING_ROD_SEA)
    ),
    LURE_AND_BAIT(
        objectIds = listOf(Npcs.FISHING_SPOT_LURE_BAIT, Npcs.FISHING_SPOT_328),
        tools = listOf(FishingTool.FISHING_ROD_RIVER, FishingTool.FLY_FISHING_ROD)
    ),
    NET_HARPOON(
        objectIds = listOf(Npcs.FISHING_SPOT_NET_HARPOON),
        tools = listOf(FishingTool.BIG_FISHING_NET, FishingTool.HARPOON_SHARK)
    ),
    CAGE_AND_HARPOON(
        objectIds = listOf(Npcs.FISHING_SPOT_CAGE_HARPOON),
        tools = listOf(FishingTool.LOBSTER_POT, FishingTool.HARPOON_NON_SHARK)
    ),
    FISHING_ROD_CAVEFISH(
        objectIds = listOf(Npcs.CAVEFISH_SHOAL),
        tools = listOf(FishingTool.FISHING_ROD_CAVEFISH)
    ),
    FISHING_ROD_ROCKTAIL(
        objectIds = listOf(Npcs.ROCKTAIL_SHOAL),
        tools = listOf(FishingTool.FISHING_ROD_ROCKTAIL)
    ),

    SMALL_FISHING_NET_MONKFISH(
        objectIds = listOf(Npcs.FISHING_SPOT_952),
        tools = listOf(FishingTool.MONKFISH_NET)
    ),

    KARAMBWAN(
        objectIds = listOf(Npcs.FISHING_SPOT_1176, Npcs.FISHING_SPOT_1177),
        tools = listOf(FishingTool.KARAMBWAN_VESSEL)
    ),

    BARBARIAN_ROD(
        objectIds = listOf(Npcs.BARBARIAN_FISHING_SPOT),
        tools = listOf(FishingTool.BARBARIAN_ROD)
    ),
}
