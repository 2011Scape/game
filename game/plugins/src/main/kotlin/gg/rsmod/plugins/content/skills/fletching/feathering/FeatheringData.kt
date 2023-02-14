package gg.rsmod.plugins.content.skills.fletching.feathering

import gg.rsmod.plugins.api.cfg.Items

enum class FeatheringData(val product: Int, val raw: Int, val amount: Int = 10, val feathersRequired: Int = 1, val levelRequirement: Int, val experience: Double) {

    HEADLESS_ARROW(product = Items.HEADLESS_ARROW, raw = Items.ARROW_SHAFT, amount = 15, levelRequirement = 1, experience = 1.0),
    FLIGHTED_OGRE_ARROW(product = Items.FLIGHTED_OGRE_ARROW, raw = Items.OGRE_ARROW_SHAFT, amount = 6, feathersRequired = 4, levelRequirement = 1, experience = 0.9),


    BRONZE_BOLTS(product = Items.BRONZE_BOLTS, raw = Items.BRONZE_BOLTS_UNF, levelRequirement = 9, experience = 0.5),
    BLURITE_BOLTS(product = Items.BLURITE_BOLTS, raw = Items.BLURITE_BOLTS_UNF, levelRequirement = 24, experience = 1.0),
    IRON_BOLTS(product = Items.IRON_BOLTS, raw = Items.IRON_BOLTS_UNF, levelRequirement = 39, experience = 1.5),
    SILVER_BOLTS(product = Items.SILVER_BOLTS, raw = Items.SILVER_BOLTS_UNF, levelRequirement = 43, experience = 2.5),
    STEEL_BOLTS(product = Items.STEEL_BOLTS, raw = Items.STEEL_BOLTS_UNF, levelRequirement = 46, experience = 3.5),
    MITHRIL_BOLTS(product = Items.MITHRIL_BOLTS, raw = Items.MITHRIL_BOLTS_UNF, levelRequirement = 54, experience = 5.0),
    BROAD_BOLTS(product = Items.BROADTIPPED_BOLTS, raw = Items.UNFINISHED_BROAD_BOLTS, levelRequirement = 55, experience = 3.0),
    ADAMANT_BOLTS(product = Items.ADAMANT_BOLTS, raw = Items.ADAMANT_BOLTS_UNF, levelRequirement = 61, experience = 7.0),
    RUNITE_BOLTS(product = Items.RUNITE_BOLTS, raw = Items.RUNITE_BOLTS_UNF, levelRequirement = 69, experience = 10.0),


    BRONZE_DART(product = Items.BRONZE_DART, raw = Items.BRONZE_DART_TIP, levelRequirement = 1, experience = 1.8),
    IRON_DART(product = Items.IRON_DART, raw = Items.IRON_DART_TIP, levelRequirement = 22, experience = 3.8),
    STEEL_DART(product = Items.STEEL_DART, raw = Items.STEEL_DART_TIP, levelRequirement = 37, experience = 7.5),
    MITHRIL_DART(product = Items.MITHRIL_DART, raw = Items.MITHRIL_DART_TIP, levelRequirement = 52, experience = 11.2),
    ADAMANT_DART(product = Items.ADAMANT_DART, raw = Items.ADAMANT_DART_TIP, levelRequirement = 67, experience = 15.0),
    RUNE_DART(product = Items.RUNE_DART, raw = Items.RUNE_DART_TIP, levelRequirement = 81, experience = 18.8),
    DRAGON_DART(product = Items.DRAGON_DART, raw = Items.DRAGON_DART_TIP, levelRequirement = 95, experience = 25.0);


    companion object {
        val featheringDefinitions = values().associate { it.product to it}
        val possibleFeathers = setOf(
            Items.FEATHER,
            Items.RED_FEATHER,
            Items.ORANGE_FEATHER,
            Items.YELLOW_FEATHER,
            Items.BLUE_FEATHER,
            Items.STRIPY_FEATHER
        )
    }
}