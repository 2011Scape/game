package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Items

/**
 * The order of the elements in the `fish` list is important. Starting with the first fish in the list,
 * for each fish its catch is rolled. If successful, the other fishes are ignored. The order of this list is
 * taken from the oldschool RuneScape wiki (e.g., https://oldschool.runescape.wiki/w/Raw_shrimps)
 */
enum class FishingTool(
    val id: Int?,
    val animation: Int,
    val baitId: Int?,
    val option: String,
    val fish: List<Fish>,
    val identifier: String
) {
    SMALL_FISHING_NET(
        id = Items.SMALL_FISHING_NET,
        animation = 621,
        baitId = null,
        option = "net",
        fish = listOf(Fish.ANCHOVIES, Fish.SHRIMP),
        identifier = "Small fishing net"),
    FISHING_ROD_SEA(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = Items.FISHING_BAIT,
        option = "bait",
        fish = listOf(Fish.HERRING, Fish.SARDINE),
        identifier = "Fishing rod"),
    FISHING_ROD_RIVER(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = Items.FISHING_BAIT,
        option = "bait",
        fish = listOf(Fish.PIKE),
        identifier = "Fishing rod"),
    FLY_FISHING_ROD(
        id = Items.FLY_FISHING_ROD,
        animation = 622,
        baitId = Items.FEATHER,
        option = "lure",
        fish = listOf(Fish.SALMON, Fish.TROUT),
        identifier = "Fly fishing rod");

    val level = fish.map { it.level }.min() ?: 1

    fun relevantFish(level: Int) = fish.filter { it.level <= level }
}
