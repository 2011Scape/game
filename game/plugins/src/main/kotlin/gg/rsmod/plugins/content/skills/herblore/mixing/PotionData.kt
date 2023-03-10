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
    );

    companion object {
        val definitions = values().associateBy { it.product }
    }

}