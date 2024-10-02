package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.plugins.api.cfg.Items

enum class HerbData(
    val grimy: Int,
    val clean: Int,
    val unf: Int,
    val levelRequirement: Int,
    val experience: Double,
) {
    GUAM(
        grimy = Items.GRIMY_GUAM,
        clean = Items.CLEAN_GUAM,
        unf = Items.GUAM_POTION_UNF,
        levelRequirement = 3,
        experience = 2.5,
    ),

    MARRENTILL(
        grimy = Items.GRIMY_MARRENTILL,
        clean = Items.CLEAN_MARRENTILL,
        unf = Items.MARRENTILL_POTION_UNF,
        levelRequirement = 5,
        experience = 3.8,
    ),

    TARROMIN(
        grimy = Items.GRIMY_TARROMIN,
        clean = Items.CLEAN_TARROMIN,
        unf = Items.TARROMIN_POTION_UNF,
        levelRequirement = 11,
        experience = 5.0,
    ),

    HARRALANDER(
        grimy = Items.GRIMY_HARRALANDER,
        clean = Items.CLEAN_HARRALANDER,
        unf = Items.HARRALANDER_POTION_UNF,
        levelRequirement = 20,
        experience = 6.3,
    ),

    RANARR(
        grimy = Items.GRIMY_RANARR,
        clean = Items.CLEAN_RANARR,
        unf = Items.RANARR_POTION_UNF,
        levelRequirement = 25,
        experience = 7.5,
    ),

    TOADFLAX(
        grimy = Items.GRIMY_TOADFLAX,
        clean = Items.CLEAN_TOADFLAX,
        unf = Items.TOADFLAX_POTION_UNF,
        levelRequirement = 30,
        experience = 8.0,
    ),

    SPIRIT_WEED(
        grimy = Items.GRIMY_SPIRIT_WEED,
        clean = Items.CLEAN_SPIRIT_WEED,
        unf = Items.SPIRIT_WEED_POTION_UNF,
        levelRequirement = 35,
        experience = 7.8,
    ),

    IRIT(
        grimy = Items.GRIMY_IRIT,
        clean = Items.CLEAN_IRIT,
        unf = Items.IRIT_POTION_UNF,
        levelRequirement = 40,
        experience = 8.8,
    ),

    WERGALI(
        grimy = Items.GRIMY_WERGALI,
        clean = Items.CLEAN_WERGALI,
        unf = Items.WERGALI_POTION_UNF,
        levelRequirement = 41,
        experience = 9.5,
    ),

    AVANTOE(
        grimy = Items.GRIMY_AVANTOE,
        clean = Items.CLEAN_AVANTOE,
        unf = Items.AVANTOE_POTION_UNF,
        levelRequirement = 48,
        experience = 10.0,
    ),

    KWUARM(
        grimy = Items.GRIMY_KWUARM,
        clean = Items.CLEAN_KWUARM,
        unf = Items.KWUARM_POTION_UNF,
        levelRequirement = 54,
        experience = 11.3,
    ),

    SNAPDRAGON(
        grimy = Items.GRIMY_SNAPDRAGON,
        clean = Items.CLEAN_SNAPDRAGON,
        unf = Items.SNAPDRAGON_POTION_UNF,
        levelRequirement = 59,
        experience = 11.8,
    ),

    CADANTINE(
        grimy = Items.GRIMY_CADANTINE,
        clean = Items.CLEAN_CADANTINE,
        unf = Items.CADANTINE_POTION_UNF,
        levelRequirement = 65,
        experience = 12.5,
    ),

    LANTADYME(
        grimy = Items.GRIMY_LANTADYME,
        clean = Items.CLEAN_LANTADYME,
        unf = Items.LANTADYME_POTION_UNF,
        levelRequirement = 67,
        experience = 13.1,
    ),

    DWARF_WEED(
        grimy = Items.GRIMY_DWARF_WEED,
        clean = Items.CLEAN_DWARF_WEED,
        unf = Items.DWARF_WEED_POTION_UNF,
        levelRequirement = 70,
        experience = 13.8,
    ),

    TORSTOL(
        grimy = Items.GRIMY_TORSTOL,
        clean = Items.CLEAN_TORSTOL,
        unf = Items.TORSTOL_POTION_UNF,
        levelRequirement = 75,
        experience = 15.0,
    ),

    FELLSTALK(
        grimy = Items.GRIMY_FELLSTALK,
        clean = Items.CLEAN_FELLSTALK,
        unf = Items.FELLSTALK_POTION_UNF,
        levelRequirement = 91,
        experience = 16.8,
    ),
    ;

    companion object {
        val grimyHerbDefinitions = values().associateBy { it.grimy }
        val unfinishedPotionDefinitions = values().associateBy { it.unf }
    }
}
