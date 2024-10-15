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
    val baitId: List<Int>?,
    val option: String,
    val fish: List<Fish>,
    val identifier: String,
) {
    CRAYFISH_CAGE(
        id = Items.CRAYFISH_CAGE,
        animation = 10009,
        baitId = null,
        option = "cage",
        fish = listOf(Fish.CRAYFISH),
        identifier = "Crayfish cage",
    ),
    SMALL_FISHING_NET(
        id = Items.SMALL_FISHING_NET,
        animation = 621,
        baitId = null,
        option = "net",
        fish = listOf(Fish.ANCHOVIES, Fish.SHRIMP),
        identifier = "Small fishing net",
    ),
    BIG_FISHING_NET(
        id = Items.BIG_FISHING_NET,
        animation = 620,
        baitId = null,
        option = "net",
        fish = listOf(Fish.MACKEREL, Fish.COD, Fish.BASS),
        identifier = "Big fishing net",
    ),
    FISHING_ROD_SEA(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.SARDINE, Fish.HERRING),
        identifier = "Fishing rod",
    ),
    FISHING_ROD_RIVER(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.PIKE),
        identifier = "Fishing rod",
    ),
    FISHING_ROD_CAVEFISH(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.CAVEFISH),
        identifier = "Fishing rod",
    ),
    LUMBDSWAMP_ROD(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.SLIMY_EEL, Fish.FROG_SPAWN),
        identifier = "Fishing rod",
    ),

    MORTMYRE_ROD(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.SLIMY_EEL),
        identifier = "Fishing rod",
    ),

    FISHING_ROD_ROCKTAIL(
        id = Items.FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.LIVING_MINERALS),
        option = "bait",
        fish = listOf(Fish.ROCKTAIL),
        identifier = "Fishing rod",
    ),
    FLY_FISHING_ROD(
        id = Items.FLY_FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FEATHER),
        option = "lure",
        fish = listOf(Fish.SALMON, Fish.TROUT),
        identifier = "Fly fishing rod",
    ),

    KBWANJI_NET(
        id = Items.SMALL_FISHING_NET,
        animation = 621,
        baitId = null,
        option = "net",
        fish = listOf(Fish.KARAMBWANJI),
        identifier = "Small fishing net",
    ),

    FLY_FISHING_ROD_RAINBOW_FISH(
        id = Items.FLY_FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.STRIPY_FEATHER),
        option = "lure",
        fish = listOf(Fish.RAINBOW_FISH),
        identifier = "Fly fishing rod",
    ),

    LOBSTER_POT(
        id = Items.LOBSTER_POT,
        animation = 619,
        baitId = null,
        option = "cage",
        fish = listOf(Fish.LOBSTER),
        identifier = "Lobster pot",
    ),
    HARPOON_NON_SHARK(
        id = Items.HARPOON,
        animation = 618,
        baitId = null,
        option = "harpoon",
        fish = listOf(Fish.TUNA, Fish.SWORDFISH),
        identifier = "Harpoon",
    ),
    BARBARIAN_ROD(
        id = Items.BARBARIAN_ROD,
        animation = 622,
        baitId = listOf(Items.FEATHER, Items.FISHING_BAIT, Items.FISH_OFFCUTS, Items.ROE, Items.CAVIAR),
        option = "Use-rod",
        fish = listOf(Fish.LEAPING_TROUT, Fish.LEAPING_SALMON, Fish.LEAPING_STURGEON),
        identifier = "Barbarian rod",
    ),

    BARB_HARPOON(
        id = Items.BARBTAIL_HARPOON,
        animation = 618,
        baitId = null,
        option = "harpoon",
        fish = listOf(Fish.TUNA, Fish.SWORDFISH),
        identifier = "Barb Tail Harpoon",
    ),

    MONKFISH_NET(
        id = Items.SMALL_FISHING_NET,
        animation = 621,
        baitId = null,
        option = "net",
        fish = listOf(Fish.MONKFISH),
        identifier = "Small fishing net",
    ),

    KARAMBWAN_VESSEL(
        id = Items.KARAMBWAN_VESSEL,
        animation = 1193,
        baitId = null,
        option = "fish",
        fish = listOf(Fish.RAW_KARAMBWAN),
        identifier = "Fish",
    ),

    OILY_FISHING_ROD(
        id = Items.OILY_FISHING_ROD,
        animation = 622,
        baitId = listOf(Items.FISHING_BAIT),
        option = "bait",
        fish = listOf(Fish.LAVA_EEL),
        identifier = "Fishing rod",
    ),

    HARPOON_SHARK(
        id = Items.HARPOON,
        animation = 618,
        baitId = null,
        option = "harpoon",
        fish = listOf(Fish.SHARK),
        identifier = "Harpoon",
    ),

    ;

    val level = fish.minOfOrNull { it.level } ?: 1

    fun relevantFish(
        level: Int,
        strengthLevel: Int,
        agilityLevel: Int,
    ) = fish.filter {
        it.level <= level &&
            (it.strengthLevel == null || it.strengthLevel <= strengthLevel) &&
            (it.agilityLevel == null || it.agilityLevel <= agilityLevel)
    }
}
