package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Items

enum class FishingTool(
    val id: Int?,
    val animation: Int,
    val baitId: Int?,
    val option: String,
    val fish: List<Fish>,
    val identifier: String
) {
    SMALL_NET(Items.SMALL_FISHING_NET, 621, null, "net", listOf(Fish.ANCHOVIES, Fish.SHRIMP), "Small fishing net"),
    FISHING_ROD_SEA(Items.FISHING_ROD, 622, Items.FISHING_BAIT, "bait", listOf(Fish.HERRING, Fish.SARDINE), "Fishing rod"),
    FISHING_ROD_RIVER(Items.FISHING_ROD, 622, Items.FISHING_BAIT, "bait", listOf(Fish.PIKE), "Fishing rod"),
    FLY_FISHING_ROD(Items.FISHING_ROD, 622, Items.FEATHER, "lure", listOf(Fish.SALMON, Fish.TROUT), "Fly fishing rod");

    val level = fish.map { it.level }.min() ?: 1

    fun relevantFish(level: Int) = fish.filter { it.level <= level }
}
