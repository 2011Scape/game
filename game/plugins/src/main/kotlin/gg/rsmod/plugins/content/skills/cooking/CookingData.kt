package gg.rsmod.plugins.content.skills.cooking

import gg.rsmod.plugins.api.cfg.Items

enum class CookingData(val raw: Int, val cooked: Int, val burnt: Int, val levelRequirement: Int, val experience: Double, val lowChance: Int, val highChance: Int) {

    SHRIMPS(raw = Items.RAW_SHRIMPS, cooked = Items.SHRIMPS, burnt = Items.BURNT_SHRIMP, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    ANCHOVIES(raw = Items.RAW_ANCHOVIES, cooked = Items.ANCHOVIES, burnt = Items.BURNT_FISH, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    CRAYFISH(raw = Items.RAW_CRAYFISH, cooked = Items.CRAYFISH, burnt = Items.BURNT_CRAYFISH, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    SARDINE(raw = Items.RAW_SARDINE, cooked = Items.SARDINE, burnt = Items.BURNT_FISH_369, levelRequirement = 1, experience = 40.0, lowChance = 118, highChance = 492),
    HERRING(raw = Items.RAW_HERRING, cooked = Items.HERRING, burnt = Items.BURNT_FISH_357, levelRequirement = 5, experience = 50.0, lowChance = 108, highChance = 472),
    MACKEREL(raw = Items.RAW_MACKEREL, cooked = Items.MACKEREL, burnt = Items.BURNT_FISH_357, levelRequirement = 10, experience = 60.0, lowChance = 98, highChance = 452),
    TROUT(raw = Items.RAW_TROUT, cooked = Items.TROUT, burnt = Items.BURNT_FISH_343, levelRequirement = 15, experience = 70.0, lowChance = 88, highChance = 432),
    COD(raw = Items.RAW_COD, cooked = Items.COD, burnt = Items.BURNT_FISH_343, levelRequirement = 18, experience = 75.0, lowChance = 88, highChance = 432),
    PIKE(raw = Items.RAW_PIKE, cooked = Items.PIKE, burnt = Items.BURNT_FISH_343, levelRequirement = 20, experience = 80.0, lowChance = 78, highChance = 412),
    SALMON(raw = Items.RAW_SALMON, cooked = Items.SALMON, burnt = Items.BURNT_FISH_343, levelRequirement = 25, experience = 90.0, lowChance = 68, highChance = 392),
    TUNA(raw = Items.RAW_TUNA, cooked = Items.TUNA, burnt = Items.BURNT_FISH_367, levelRequirement = 30, experience = 100.0, lowChance = 58, highChance = 372),
    LOBSTER(raw = Items.RAW_LOBSTER, cooked = Items.LOBSTER, burnt = Items.BURNT_LOBSTER, levelRequirement = 40, experience = 120.0, lowChance = 38, highChance = 332),
    BASS(raw = Items.RAW_BASS, cooked = Items.BASS, burnt = Items.BURNT_FISH_367, levelRequirement = 43, experience = 130.0, lowChance = 33, highChance = 312),
    SWORDFISH(raw = Items.RAW_SWORDFISH, cooked = Items.SWORDFISH, burnt = Items.BURNT_SWORDFISH, levelRequirement = 45, experience = 140.0, lowChance = 18, highChance = 292),
    MONKFISH(raw = Items.RAW_MONKFISH, cooked = Items.MONKFISH, burnt = Items.BURNT_MONKFISH, levelRequirement = 62, experience = 150.0, lowChance = 13, highChance = 280),
    SHARK(raw = Items.RAW_SHARK, cooked = Items.SHARK, burnt = Items.BURNT_SHARK, levelRequirement = 80, experience = 210.0, lowChance = 1, highChance = 232),
    SEA_TURTLE(raw = Items.RAW_SEA_TURTLE, cooked = Items.SEA_TURTLE, burnt = Items.BURNT_SEA_TURTLE, levelRequirement = 82, experience = 211.3, lowChance = 1, highChance = 222),
    MANTA_RAY(raw = Items.RAW_MANTA_RAY, cooked = Items.MANTA_RAY, burnt = Items.BURNT_MANTA_RAY, levelRequirement = 91, experience = 216.3, lowChance = 1, highChance = 222),

    BREAD(raw = Items.BREAD_DOUGH, cooked = Items.BREAD, burnt = Items.BURNT_BREAD, levelRequirement = 1, experience = 40.0, lowChance = 118, highChance = 492),
    PITTA_BREAD(raw = Items.PITTA_DOUGH, cooked = Items.PITTA_BREAD, burnt = Items.BURNT_PITTA_BREAD, levelRequirement = 58, experience = 40.0, lowChance = 118, highChance = 492),

    REDBERRY_PIE(raw = Items.UNCOOKED_BERRY_PIE, cooked = Items.REDBERRY_PIE, burnt = Items.BURNT_PIE, levelRequirement = 10, experience = 78.0, lowChance = 98, highChance = 452),
    MEAT_PIE(raw = Items.UNCOOKED_MEAT_PIE, cooked = Items.MEAT_PIE, burnt = Items.BURNT_PIE, levelRequirement = 20, experience = 110.0, lowChance = 78, highChance = 412),
    MUD_PIE(raw = Items.RAW_MUD_PIE, cooked = Items.MUD_PIE, burnt = Items.BURNT_PIE, levelRequirement = 29, experience = 128.0, lowChance = 58, highChance = 372),
    APPLE_PIE(raw = Items.UNCOOKED_APPLE_PIE, cooked = Items.APPLE_PIE, burnt = Items.BURNT_PIE, levelRequirement = 30, experience = 130.0, lowChance = 58, highChance = 372),
    GARDEN_PIE(raw = Items.RAW_GARDEN_PIE, cooked = Items.GARDEN_PIE, burnt = Items.BURNT_PIE, levelRequirement = 34, experience = 138.0, lowChance = 48, highChance = 352),
    FISH_PIE(raw = Items.RAW_FISH_PIE, cooked = Items.FISH_PIE, burnt = Items.BURNT_PIE, levelRequirement = 47, experience = 164.0, lowChance = 38, highChance = 332),
    ADMIRAL_PIE(raw = Items.RAW_ADMIRAL_PIE, cooked = Items.ADMIRAL_PIE, burnt = Items.BURNT_PIE, levelRequirement = 70, experience = 210.0, lowChance = 15, highChance = 270),
    WILD_PIE(raw = Items.RAW_WILD_PIE, cooked = Items.WILD_PIE, burnt = Items.BURNT_PIE, levelRequirement = 85, experience = 240.0, lowChance = 1, highChance = 222),
    SUMMER_PIE(raw = Items.RAW_SUMMER_PIE, cooked = Items.SUMMER_PIE, burnt = Items.BURNT_PIE, levelRequirement = 95, experience = 260.0, lowChance = 1, highChance = 212),

    STEW(raw = Items.UNCOOKED_STEW, cooked = Items.STEW, burnt = Items.BURNT_STEW, levelRequirement = 25, experience = 117.0, lowChance = 68, highChance = 392),
    CURRY(raw = Items.UNCOOKED_CURRY, cooked = Items.CURRY, burnt = Items.BURNT_CURRY, levelRequirement = 25, experience = 117.0, lowChance = 68, highChance = 392),

    PLAIN_PIZZA(raw = Items.UNCOOKED_PIZZA, cooked = Items.PLAIN_PIZZA, burnt = Items.BURNT_PIZZA, levelRequirement = 35, experience = 143.0, lowChance = 48, highChance = 352),

    BAKED_POTATO(raw = Items.POTATO, cooked = Items.BAKED_POTATO, burnt = Items.BURNT_POTATO, levelRequirement = 7, experience = 15.0, lowChance = 108, highChance = 472),
    SCRAMBLED_EGG(raw = Items.UNCOOKED_EGG, cooked = Items.SCRAMBLED_EGG, burnt = Items.BURNT_EGG, levelRequirement = 13, experience = 50.0, lowChance = 90, highChance = 438),
    COOKED_SWEETCORN(raw = Items.SWEETCORN, cooked = Items.COOKED_SWEETCORN, burnt = Items.BURNT_SWEETCORN, levelRequirement = 28, experience = 104.0, lowChance = 78, highChance = 412),
    FRIED_ONIONS(raw = Items.CHOPPED_ONION, cooked = Items.FRIED_ONIONS, burnt = Items.BURNT_ONION, levelRequirement = 42, experience = 60.0, lowChance = 36, highChance = 322),
    FRIED_MUSHROOMS(raw = Items.SLICED_MUSHROOMS, cooked = Items.FRIED_MUSHROOMS, burnt = Items.BURNT_MUSHROOM, levelRequirement = 46, experience = 60.0, lowChance = 16, highChance = 282),

    RABBIT(raw = Items.RAW_RABBIT, cooked = Items.COOKED_RABBIT, burnt = Items.BURNT_RABBIT, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    CHICKEN(raw = Items.RAW_CHICKEN, cooked = Items.COOKED_CHICKEN, burnt = Items.BURNT_CHICKEN, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    BEEF(raw = Items.RAW_BEEF, cooked = Items.COOKED_MEAT, burnt = Items.BURNT_MEAT, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    BEAR(raw = Items.RAW_BEAR_MEAT, cooked = Items.COOKED_MEAT, burnt = Items.BURNT_MEAT, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    RAT(raw = Items.RAW_RAT_MEAT, cooked = Items.COOKED_MEAT, burnt = Items.BURNT_MEAT, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    UGTHANKI(raw = Items.RAW_UGTHANKI_MEAT, cooked = Items.UGTHANKI_MEAT, burnt = Items.BURNT_MEAT, levelRequirement = 1, experience = 40.0, lowChance = 30, highChance = 253),
    YAK(raw = Items.RAW_YAK_MEAT, cooked = Items.COOKED_MEAT, burnt = Items.BURNT_MEAT, levelRequirement = 1, experience = 30.0, lowChance = 128, highChance = 512),
    ;


    companion object {
        val values = enumValues<CookingData>()
        val cookingDefinitions = values.associateBy { it.raw }
    }

}