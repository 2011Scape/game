package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Npcs

// TODO: make fishing spots move
enum class FishingSpot(
    val objectIds: List<Int>,
    val tools: List<FishingTool>,
) {
    CRAYFISH_CAGE(
        objectIds = listOf(Npcs.FISHING_SPOT_CRAYFISH_CAGE, Npcs.FISHING_SPOT_7863),
        tools = listOf(FishingTool.CRAYFISH_CAGE),
    ),
    NET_AND_BAIT(
        objectIds =
            listOf(
                Npcs.FISHING_SPOT_NET_BAIT,
                Npcs.FISHING_SPOT_316,
                Npcs.FISHING_SPOT_327,
                Npcs.FISHING_SPOT_1331,
                Npcs.FISHING_SPOT_7045,
            ),
        tools = listOf(FishingTool.SMALL_FISHING_NET, FishingTool.FISHING_ROD_SEA),
    ),
    LURE_AND_BAIT(
        objectIds =
            listOf(
                Npcs.FISHING_SPOT_LURE_BAIT,
                Npcs.FISHING_SPOT_311,
                Npcs.FISHING_SPOT_328,
                Npcs.FISHING_SPOT_315,
                Npcs.FISHING_SPOT_1189,
                Npcs.FISHING_SPOT_927,
                Npcs.FISHING_SPOT_309,
                Npcs.FISHING_SPOT_317,
            ),
        tools = listOf(FishingTool.FISHING_ROD_RIVER, FishingTool.FLY_FISHING_ROD),
    ),
    NET_HARPOON(
        objectIds =
            listOf(
                Npcs.FISHING_SPOT_NET_HARPOON,
                Npcs.FISHING_SPOT_1333,
                Npcs.FISHING_SPOT_7044,
                Npcs.FISHING_SPOT_5471,
                Npcs.FISHING_SPOT_1405,
                Npcs.FISHING_SPOT_1406,
            ),
        tools = listOf(FishingTool.BIG_FISHING_NET, FishingTool.HARPOON_SHARK),
    ),
    CAGE_AND_HARPOON(
        objectIds =
            listOf(
                Npcs.FISHING_SPOT_CAGE_HARPOON,
                Npcs.FISHING_SPOT_1332,
                Npcs.FISHING_SPOT_7046,
                Npcs.FISHING_SPOT_3804,
                Npcs.FISHING_SPOT_5470,
            ),
        tools = listOf(FishingTool.LOBSTER_POT, FishingTool.HARPOON_NON_SHARK),
    ),
    FISHING_ROD_CAVEFISH(
        objectIds = listOf(Npcs.CAVEFISH_SHOAL),
        tools = listOf(FishingTool.FISHING_ROD_CAVEFISH),
    ),
    FISHING_ROD_ROCKTAIL(
        objectIds = listOf(Npcs.ROCKTAIL_SHOAL),
        tools = listOf(FishingTool.FISHING_ROD_ROCKTAIL),
    ),

    SMALL_FISHING_NET_MONKFISH(
        objectIds = listOf(Npcs.FISHING_SPOT_952, Npcs.FISHING_SPOT_3848),
        tools = listOf(FishingTool.MONKFISH_NET),
    ),

    HAPOON_FISHING(
        objectIds = listOf(Npcs.FISHING_SPOT_3848),
        tools = listOf(FishingTool.HARPOON_NON_SHARK),
    ),

    KARAMBWAN(
        objectIds = listOf(Npcs.FISHING_SPOT_1176, Npcs.FISHING_SPOT_1177),
        tools = listOf(FishingTool.KARAMBWAN_VESSEL),
    ),

    MORTMYRE_ROD(
        objectIds = listOf(Npcs.FISHING_SPOT_1238),
        tools = listOf(FishingTool.MORTMYRE_ROD),
    ),

    BARBARIAN_ROD(
        objectIds = listOf(Npcs.BARBARIAN_FISHING_SPOT),
        tools = listOf(FishingTool.BARBARIAN_ROD),
    ),
}
