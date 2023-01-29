package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Npcs

// TODO: make fishing spots move
enum class FishingSpot(
    val objectIds: List<Int>,
    val tools: List<FishingTool>
) {
    NET_AND_BAIT(listOf(Npcs.FISHING_SPOT_NET_BAIT), listOf(FishingTool.SMALL_NET, FishingTool.FISHING_ROD)),
}
