package gg.rsmod.plugins.content.skills.herblore.mixing

import gg.rsmod.plugins.api.cfg.Items

enum class PotionData(
    val primary: Int, val secondary: Int, val product: Int, val levelRequirement: Int, val experience: Double
) {

    ATTACK_POTION(
        primary = Items.GUAM_POTION_UNF,
        secondary = Items.EYE_OF_NEWT,
        product = Items.ATTACK_POTION_3,
        levelRequirement = 3,
        experience = 25.0
    ),

    ANTIPOISON(
        primary = Items.MARRENTILL_POTION_UNF,
        secondary = Items.UNICORN_HORN_DUST,
        product = Items.ANTIPOISON_3,
        levelRequirement = 5,
        experience = 37.5
    ),

    STRENGTH_POTION(
        primary = Items.TARROMIN_POTION_UNF,
        secondary = Items.LIMPWURT_ROOT,
        product = Items.STRENGTH_POTION_3,
        levelRequirement = 12,
        experience = 50.0
    ),

    SERUM_207_POTION(
        primary = Items.ASH_POTION_UNF,
        secondary = Items.CLEAN_TARROMIN,
        product = Items.SERUM_207_3,
        levelRequirement = 15,
        experience = 50.0
    ),

    GUTHIX_BALANCE_POTION(
        primary = Items.GUTHIX_BALANCE_UNF_7654,
        secondary = Items.SILVER_DUST,
        product = Items.GUTHIX_BALANCE_3,
        levelRequirement = 22,
        experience = 62.5
    ),

    RESTORE_POTION(
        primary = Items.HARRALANDER_POTION_UNF,
        secondary = Items.RED_SPIDERS_EGGS,
        product = Items.RESTORE_POTION_3,
        levelRequirement = 22,
        experience = 62.5
    ),

    ENERGY_POTION(
        primary = Items.HARRALANDER_POTION_UNF,
        secondary = Items.CHOCOLATE_DUST,
        product = Items.ENERGY_POTION_3,
        levelRequirement = 26,
        experience = 67.5
    ),

    DEFENCE_POTION(
        primary = Items.RANARR_POTION_UNF,
        secondary = Items.WHITE_BERRIES,
        product = Items.DEFENCE_POTION_3,
        levelRequirement = 30,
        experience = 75.0
    ),

    AGILITY_POTION(
        primary = Items.TOADFLAX_POTION_UNF,
        secondary = Items.TOADS_LEGS,
        product = Items.AGILITY_POTION_3,
        levelRequirement = 34,
        experience = 80.0
    ),

    COMBAT_POTION(
        primary = Items.HARRALANDER_POTION_UNF,
        secondary = Items.GOAT_HORN_DUST,
        product = Items.COMBAT_POTION_3,
        levelRequirement = 36,
        experience = 84.0
    ),

    PRAYER_POTION(
        primary = Items.RANARR_POTION_UNF,
        secondary = Items.SNAPE_GRASS,
        product = Items.PRAYER_POTION_3,
        levelRequirement = 38,
        experience = 87.5
    ),

    SUMMONING_POTION(
        primary = Items.SPIRIT_WEED_POTION_UNF,
        secondary = Items.COCKATRICE_EGG,
        product = Items.SUMMONING_POTION_3,
        levelRequirement = 40,
        experience = 92.0
    ),

    CRAFTING_POTION(
        primary = Items.WERGALI_POTION_UNF,
        secondary = Items.FROG_SPAWN,
        product = Items.CRAFTING_POTION_3,
        levelRequirement = 42,
        experience = 95.0
    ),

    SUPER_ATTACK(
        primary = Items.IRIT_POTION_UNF,
        secondary = Items.EYE_OF_NEWT,
        product = Items.SUPER_ATTACK_3,
        levelRequirement = 45,
        experience = 100.0
    ),

    SUPER_ANTIPOISON(
        primary = Items.IRIT_POTION_UNF,
        secondary = Items.UNICORN_HORN_DUST,
        product = Items.SUPER_ANTIPOISON_3,
        levelRequirement = 48,
        experience = 106.3
    ),

    FISHING_POTION(
        primary = Items.AVANTOE_POTION_UNF,
        secondary = Items.SNAPE_GRASS,
        product = Items.FISHING_POTION_3,
        levelRequirement = 50,
        experience = 112.5
    ),

    SUPER_ENERGY(
        primary = Items.AVANTOE_POTION_UNF,
        secondary = Items.MORT_MYRE_FUNGUS,
        product = Items.SUPER_ENERGY_3,
        levelRequirement = 52,
        experience = 117.5
    ),

    HUNTER_POTION(
        primary = Items.AVANTOE_POTION_UNF,
        secondary = Items.KEBBIT_TEETH_DUST,
        product = Items.HUNTER_POTION_3,
        levelRequirement = 53,
        experience = 120.0
    ),

    SUPER_STRENGTH(
        primary = Items.KWUARM_POTION_UNF,
        secondary = Items.LIMPWURT_ROOT,
        product = Items.SUPER_STRENGTH_3,
        levelRequirement = 55,
        experience = 125.0
    ),

    FLETCHING_POTION(
        primary = Items.WERGALI_POTION_UNF,
        secondary = Items.WIMPY_FEATHER,
        product = Items.FLETCHING_POTION_3,
        levelRequirement = 58,
        experience = 132.0
    ),

    WEAPON_POISON(
        primary = Items.KWUARM_POTION_UNF,
        secondary = Items.DRAGON_SCALE_DUST,
        product = Items.WEAPON_POISON,
        levelRequirement = 60,
        experience = 137.5
    ),

    SUPER_RESTORE(
        primary = Items.SNAPDRAGON_POTION_UNF,
        secondary = Items.RED_SPIDERS_EGGS,
        product = Items.SUPER_RESTORE_3,
        levelRequirement = 63,
        experience = 142.5
    ),

    SUPER_DEFENCE(
        primary = Items.CADANTINE_POTION_UNF,
        secondary = Items.WHITE_BERRIES,
        product = Items.SUPER_DEFENCE_3,
        levelRequirement = 66,
        experience = 150.0
    ),

    ANTIFIRE(
        primary = Items.LANTADYME_POTION_UNF,
        secondary = Items.DRAGON_SCALE_DUST,
        product = Items.ANTIFIRE_3,
        levelRequirement = 69,
        experience = 157.5
    ),

    RANGING_POTION(
        primary = Items.DWARF_WEED_POTION_UNF,
        secondary = Items.WINE_OF_ZAMORAK,
        product = Items.RANGING_POTION_3,
        levelRequirement = 72,
        experience = 162.5
    ),

    MAGIC_POTION(
        primary = Items.LANTADYME_POTION_UNF,
        secondary = Items.POTATO_CACTUS,
        product = Items.MAGIC_POTION_3,
        levelRequirement = 76,
        experience = 172.5
    ),

    ZAMORAK_BREW(
        primary = Items.TORSTOL_POTION_UNF,
        secondary = Items.JANGERBERRIES,
        product = Items.ZAMORAK_BREW_3,
        levelRequirement = 78,
        experience = 175.0
    ),

    SARADOMIN_BREW(
        primary = Items.TOADFLAX_POTION_UNF,
        secondary = Items.CRUSHED_NEST,
        product = Items.SARADOMIN_BREW_3,
        levelRequirement = 81,
        experience = 180.0
    ),

    RECOVER_SPECIAL(
        primary = Items.SUPER_ENERGY_3,
        secondary = Items.PAPAYA_FRUIT,
        product = Items.RECOVER_SPECIAL_3,
        levelRequirement = 84,
        experience = 200.0
    ),

    SUPER_ANTIFIRE(
        primary = Items.ANTIFIRE_3,
        secondary = Items.PHOENIX_FEATHER,
        product = Items.SUPER_ANTIFIRE_3,
        levelRequirement = 85,
        experience = 210.0
    ),

    EXTREME_ATTACK(
        primary = Items.SUPER_ATTACK_3,
        secondary = Items.CLEAN_AVANTOE,
        product = Items.EXTREME_ATTACK_3,
        levelRequirement = 88,
        experience = 220.0
    ),

    EXTREME_STRENGTH(
        primary = Items.SUPER_STRENGTH_3,
        secondary = Items.CLEAN_DWARF_WEED,
        product = Items.EXTREME_STRENGTH_3,
        levelRequirement = 89,
        experience = 230.0
    ),

    EXTREME_DEFENCE(
        primary = Items.SUPER_DEFENCE_3,
        secondary = Items.CLEAN_LANTADYME,
        product = Items.EXTREME_DEFENCE_3,
        levelRequirement = 90,
        experience = 240.0
    ),

    EXTREME_MAGIC(
        primary = Items.MAGIC_POTION_3,
        secondary = Items.GROUND_MUD_RUNES,
        product = Items.EXTREME_MAGIC_3,
        levelRequirement = 91,
        experience = 250.0
    ),

    EXTREME_RANGING(
        primary = Items.RANGING_POTION_3,
        secondary = Items.GRENWALL_SPIKES,
        product = Items.EXTREME_RANGING_3,
        levelRequirement = 92,
        experience = 260.0
    ),

    SUPER_PRAYER(
        primary = Items.PRAYER_POTION_3,
        secondary = Items.WYVERN_BONEMEAL,
        product = Items.SUPER_PRAYER_3,
        levelRequirement = 94,
        experience = 270.0
    ),

    PRAYER_RENEWAL(
        primary = Items.FELLSTALK_POTION_UNF,
        secondary = Items.MORCHELLA_MUSHROOM,
        product = Items.PRAYER_RENEWAL_3,
        levelRequirement = 94,
        experience = 190.0
    ),

    //TODO add right stats for the potions in potion type
    /** Weak Dungeoneering Potions */
    WEAK_MAGIC_POTION(
        primary = Items.SAGEWORT_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.WEAK_MAGIC_POTION,
        levelRequirement = 3,
        experience = 21.0
    ),
    WEAK_RANGED_POTION(
        primary = Items.VALERIAN_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.WEAK_RANGED_POTION,
        levelRequirement = 5,
        experience = 34.0
    ),
    WEAK_MELEE_POTION(
        primary = Items.VALERIAN_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.WEAK_MELEE_POTION,
        levelRequirement = 7,
        experience = 37.5
    ),
    WEAK_DEFENCE_POTION(
        primary = Items.ALOE_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.WEAK_DEFENCE_POTION,
        levelRequirement = 9,
        experience = 41.0
    ),
    WEAK_STAT_RESTORE_POTION(
        primary = Items.ALOE_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.WEAK_STAT_RESTORE_POTION,
        levelRequirement = 12,
        experience = 47.0
    ),
    WEAK_CURE_POTION(
        primary = Items.ALOE_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.WEAK_CURE_POTION,
        levelRequirement = 15,
        experience = 23.0
    ),
    WEAK_REJUVENATION_POTION(
        primary = Items.ALOE_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.WEAK_REJUVENATION_POTION,
        levelRequirement = 18,
        experience = 53.5
    ),
    WEAK_POISON_POTION(
        primary = Items.SAGEWORT_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.WEAK_WEAPON_POISON,
        levelRequirement = 21,
        experience = 61.0
    ),
    WEAK_GATHERER_POTION(
        primary = Items.SAGEWORT_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.WEAK_GATHERERS_POTION,
        levelRequirement = 24,
        experience = 65.0
    ),
    WEAK_ARTISAN_POTION(
        primary = Items.VALERIAN_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.WEAK_ARTISANS_POTION,
        levelRequirement = 27,
        experience = 68.5
    ),
    WEAK_NATURALIST_POTION(
        primary = Items.SAGEWORT_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.WEAK_NATURALISTS_POTION,
        levelRequirement = 30,
        experience = 72.0
    ),
    WEAK_SURVIVALIST_POTION(
        primary = Items.VALERIAN_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.WEAK_SURVIVALISTS_POTION,
        levelRequirement = 33,
        experience = 75.0
    ),

    //TODO add right stats for the potions in potion type
    /** Regular Dungeoneering Potions */
    REGULAR_MAGIC_POTION(
        primary = Items.WORMWOOD_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.MAGIC_POTION,
        levelRequirement = 36,
        experience = 79.5
    ),
    REGULAR_RANGED_POTION(
        primary = Items.MAGEBANE_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.RANGED_POTION,
        levelRequirement = 38,
        experience = 83.0
    ),
    REGULAR_MELEE_POTION(
        primary = Items.MAGEBANE_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.MELEE_POTION,
        levelRequirement = 40,
        experience = 86.5
    ),
    REGULAR_DEFENCE_POTION(
        primary = Items.FEATHERFOIL_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.DEFENCE_POTION,
        levelRequirement = 42,
        experience = 89.0
    ),
    REGULAR_STAT_RESTORE_POTION(
        primary = Items.FEATHERFOIL_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.STAT_RESTORE_POTION,
        levelRequirement = 45,
        experience = 93.0
    ),
    REGULAR_CURE_POTION(
        primary = Items.FEATHERFOIL_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.CURE_POTION,
        levelRequirement = 48,
        experience = 98.5
    ),
    REGULAR_REJUVENATION_POTION(
        primary = Items.FEATHERFOIL_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.REJUVENATION_POTION,
        levelRequirement = 51,
        experience = 105.5
    ),
    REGULAR_POISON_POTION(
        primary = Items.WORMWOOD_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.WEAPON_POISON_17596,
        levelRequirement = 54,
        experience = 114.0
    ),
    REGULAR_GATHERER_POTION(
        primary = Items.WORMWOOD_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.GATHERERS_POTION,
        levelRequirement = 57,
        experience = 123.5
    ),
    REGULAR_ARTISAN_POTION(
        primary = Items.MAGEBANE_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.ARTISANS_POTION,
        levelRequirement = 60,
        experience = 131.0
    ),
    REGULAR_NATURALIST_POTION(
        primary = Items.WORMWOOD_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.NATURALISTS_POTION,
        levelRequirement = 63,
        experience = 139.5
    ),
    REGULAR_SURVIVALIST_POTION(
        primary = Items.MAGEBANE_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.SURVIVALISTS_POTION,
        levelRequirement = 66,
        experience = 147.0
    ),

    //TODO add right stats for the potions in potion type
    /** Strong Dungeoneering Potions */
    STRONG_MAGIC_POTION(
        primary = Items.WINTERS_GRIP_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.STRONG_MAGIC_POTION,
        levelRequirement = 69,
        experience = 79.5
    ),
    STRONG_RANGED_POTION(
        primary = Items.LYCOPUS_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.STRONG_RANGED_POTION,
        levelRequirement = 71,
        experience = 83.0
    ),
    STRONG_MELEE_POTION(
        primary = Items.LYCOPUS_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.STRONG_MELEE_POTION,
        levelRequirement = 73,
        experience = 86.5
    ),
    STRONG_DEFENCE_POTION(
        primary = Items.BUCKTHORN_POTION_UNF,
        secondary = Items.VOID_DUST,
        product = Items.STRONG_DEFENCE_POTION,
        levelRequirement = 75,
        experience = 89.0
    ),
    STRONG_STAT_RESTORE_POTION(
        primary = Items.BUCKTHORN_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.STRONG_STAT_RESTORE_POTION,
        levelRequirement = 78,
        experience = 93.0
    ),
    STRONG_CURE_POTION(
        primary = Items.BUCKTHORN_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.STRONG_CURE_POTION,
        levelRequirement = 81,
        experience = 98.5
    ),
    STRONG_REJUVENATION_POTION(
        primary = Items.BUCKTHORN_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.STRONG_REJUVENATION_POTION,
        levelRequirement = 84,
        experience = 105.5
    ),
    STRONG_POISON_POTION(
        primary = Items.WINTERS_GRIP_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.STRONG_WEAPON_POISON,
        levelRequirement = 87,
        experience = 114.0
    ),
    STRONG_GATHERER_POTION(
        primary = Items.WINTERS_GRIP_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.STRONG_GATHERERS_POTION,
        levelRequirement = 90,
        experience = 123.5
    ),
    STRONG_ARTISAN_POTION(
        primary = Items.LYCOPUS_POTION_UNF,
        secondary = Items.RED_MOSS,
        product = Items.STRONG_ARTISANS_POTION,
        levelRequirement = 93,
        experience = 131.0
    ),
    STRONG_NATURALIST_POTION(
        primary = Items.WINTERS_GRIP_POTION_UNF,
        secondary = Items.MISSHAPEN_CLAW,
        product = Items.STRONG_NATURALISTS_POTION,
        levelRequirement = 96,
        experience = 139.5
    ),
    STRONG_SURVIVALIST_POTION(
        primary = Items.LYCOPUS_POTION_UNF,
        secondary = Items.FIREBREATH_WHISKEY,
        product = Items.STRONG_SURVIVALISTS_POTION,
        levelRequirement = 99,
        experience = 147.0
    ),


    //TODO add right stats for the potions in potion type
    /** Juju Potions */
    JUJU_HUNTER_POTION(
        primary = Items.ERZILLE_POTION_UNF,
        secondary = Items.CORRUPT_VINE,
        product = Items.JUJU_HUNTER_POTION_3,
        levelRequirement = 54,
        experience = 123.0
    ),
    JUJU_SCENTLESS_POTION(
        primary = Items.ARGWAY_POTION_UNF,
        secondary = Items.SHADOW_VINE,
        product = Items.SCENTLESS_POTION_3,
        levelRequirement = 59,
        experience = 135.0
    ),
    JUJU_FARMING_POTION(
        primary = Items.UGUNE_POTION_UNF,
        secondary = Items.MARBLE_VINE,
        product = Items.JUJU_FARMING_POTION_3,
        levelRequirement = 64,
        experience = 146.0
    ),
    JUJU_COOKING_POTION(
        primary = Items.SHENGO_POTION_UNF,
        secondary = Items.PLANT_TEETH,
        product = Items.JUJU_COOKING_POTION_3,
        levelRequirement = 67,
        experience = 152.0
    ),
    JUJU_FISHING_POTION(
        primary = Items.SHENGO_POTION_UNF,
        secondary = Items.AQUATIC_VINE,
        product = Items.JUJU_FISHING_POTION_3,
        levelRequirement = 70,
        experience = 158.0
    ),
    JUJU_WOODCUTTING_POTION(
        primary = Items.SAMADEN_POTION_UNF,
        secondary = Items.OILY_VINE,
        product = Items.JUJU_WOODCUTTING_POTION_3,
        levelRequirement = 71,
        experience = 160.0
    ),
    JUJU_MINING_POTION(
        primary = Items.SAMADEN_POTION_UNF,
        secondary = Items.DRACONIC_VINE,
        product = Items.JUJU_MINING_POTION_3,
        levelRequirement = 74,
        experience = 168.0
    ),
    SARADOMIN_BLESSING_POTION(
        primary = Items.SAMADEN_POTION_UNF,
        secondary = Items.SARADOMIN_VINE,
        product = Items.SARADOMINS_BLESSING_3,
        levelRequirement = 75,
        experience = 179.0
    ),
    GUTHIX_GIFT_POTION(
        primary = Items.SAMADEN_POTION_UNF,
        secondary = Items.GUTHIX_VINE,
        product = Items.GUTHIXS_GIFT_3,
        levelRequirement = 75,
        experience = 179.0
    ),
    ZAMORAK_FAVOUR_POTION(
        primary = Items.SAMADEN_POTION_UNF,
        secondary = Items.ZAMORAK_VINE,
        product = Items.ZAMORAKS_FAVOUR_3,
        levelRequirement = 75,
        experience = 179.0
    ),



    //TODO add right stats for the potions in potion type
    /** Barbarian Mixes  */
    AGILITY_MIX(
        primary = Items.AGILITY_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.AGILITY_MIX_2,
        levelRequirement = 37,
        experience = 27.0
    ),

    ATTACK_MIX(
        primary = Items.ATTACK_POTION_2,
        secondary = Items.ROE,
        product = Items.ATTACK_MIX_2,
        levelRequirement = 4,
        experience = 7.0
    ),

    ANTIPOISON_MIX(
        primary = Items.ANTIPOISON_2,
        secondary = Items.ROE,
        product = Items.ANTIPOISON_MIX_2,
        levelRequirement = 6,
        experience = 12.0
    ),
    ANTIPOISON_SUPERMIX(
        primary = Items.SUPER_ANTIPOISON_2,
        secondary = Items.CAVIAR,
        product = Items.ANTIP_SUPERMIX_2,
        levelRequirement = 51 ,
        experience = 35.0
    ),

    ANTIDOTE_PLUS_MIX(
        primary = Items.ANTIPOISON_2_5947,
        secondary = Items.CAVIAR,
        product = Items.ANTIDOTE_MIX_2,
        levelRequirement = 74,
        experience = 52.0
    ),

    ANTIFIRE_MIX(
        primary = Items.ANTIFIRE_2,
        secondary = Items.CAVIAR,
        product = Items.ANTIFIRE_MIX_2,
        levelRequirement = 75,
        experience = 53.0
    ),

    COMBAT_MIX(
    primary = Items.COMBAT_POTION_2,
    secondary = Items.CAVIAR,
    product = Items.COMBAT_MIX_2,
    levelRequirement = 40,
    experience = 28.0
    ),

    DEFENCE_MIX(
        primary = Items.DEFENCE_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.DEFENCE_MIX_2,
        levelRequirement = 33,
        experience = 25.0
    ),

    ENERGY_MIX(
        primary = Items.ENERGY_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.ENERGY_MIX_2,
        levelRequirement = 29,
        experience = 23.0
    ),

    FISHING_MIX(
        primary = Items.FISHING_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.FISHING_MIX_2,
        levelRequirement = 53,
        experience = 38.0
    ),

    HUNTING_MIX(
        primary = Items.HUNTER_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.HUNTING_MIX_2,
        levelRequirement = 58,
        experience = 40.0
    ),

    MAGIC_ESSENCE_MIX(
        primary = Items.MAGIC_ESSENCE_2,
        secondary = Items.CAVIAR,
        product = Items.MAGIC_POTION_2,
        levelRequirement = 61,
        experience = 43.0
    ),

    MAGIC_MIX(
        primary = Items.MAGIC_POTION_2,
        secondary = Items.ROE,
        product = Items.MAGIC_MIX_2,
        levelRequirement = 83,
        experience = 57.0
    ),

    PRAYER_MIX(
        primary = Items.PRAYER_POTION_2,
        secondary = Items.CAVIAR,
        product = Items.PRAYER_MIX_2,
        levelRequirement = 42,
        experience = 29.0
    ),

    RANGING_MIX(
        primary = Items.RANGING_POTION_2,
        secondary = Items.ROE,
        product = Items.RANGING_MIX_2,
        levelRequirement = 80,
        experience = 54.0
    ),

    RELICYMS_MIX(
        primary = Items.RELICYMS_BALM_2,
        secondary = Items.ROE,
        product = Items.RELICYMS_MIX_2,
        levelRequirement = 9,
        experience = 14.0
    ),

    RESTORE_MIX(
        primary = Items.RESTORE_POTION_2,
        secondary = Items.ROE,
        product = Items.RESTORE_MIX_2,
        levelRequirement = 24,
        experience = 21.0
    ),

    STRENGTH_MIX(
        primary = Items.STRENGTH_POTION_2,
        secondary = Items.ROE,
        product = Items.STRENGTH_MIX_2,
        levelRequirement = 14,
        experience = 17.0
    ),

    SUPER_DEFENCE_MIX(
        primary = Items.SUPER_DEFENCE_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_DEFENCE_MIX_2,
        levelRequirement = 71,
        experience = 50.0
    ),

    SUPER_ENERGY_MIX(
        primary = Items.SUPER_ENERGY_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_ENERGY_MIX_2,
        levelRequirement = 56,
        experience = 39.0
    ),


    SUPER_MAGIC_MIX_(
        primary = Items.MAGIC_MIX_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_RESTORE_MIX_2,
        levelRequirement = 67,
        experience = 48.0
    ),

    SUPER_RESTORE_MIX_(
        primary = Items.SUPER_RESTORE_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_RESTORE_MIX_2,
        levelRequirement = 67,
        experience = 48.0
    ),

    SUPER_STRENGTH_MIX(
        primary = Items.SUPER_STRENGTH_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_STRENGTH_MIX_2,
        levelRequirement = 59,
        experience = 42.0
    ),

    SUPER_ATTACK_MIX_(
        primary = Items.SUPER_ATTACK_2,
        secondary = Items.CAVIAR,
        product = Items.SUPER_ATTACK_MIX_2,
        levelRequirement = 47,
        experience = 33.0
    ),

    ZAMORAK_MIX(
        primary = Items.ZAMORAK_BREW_2,
        secondary = Items.CAVIAR,
        product = Items.ZAMORAK_MIX_2,
        levelRequirement = 85,
        experience = 58.0
    );



    companion object {
        val definitions = values().associateBy { it.product }
    }

}