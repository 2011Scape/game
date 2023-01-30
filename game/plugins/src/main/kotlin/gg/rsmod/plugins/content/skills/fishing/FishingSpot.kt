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
        objectIds = listOf(Npcs.FISHING_SPOT_NET_BAIT),
        tools = listOf(FishingTool.SMALL_FISHING_NET, FishingTool.FISHING_ROD_SEA)
    ),
    LURE_AND_BAIT(
        objectIds = listOf(Npcs.FISHING_SPOT_LURE_BAIT),
        tools = listOf(FishingTool.FISHING_ROD_RIVER, FishingTool.FLY_FISHING_ROD)
    ),
}
