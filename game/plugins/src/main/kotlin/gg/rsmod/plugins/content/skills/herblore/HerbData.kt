package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.plugins.api.cfg.Items

enum class HerbData(val grimy: Int, val clean: Int, val unf: Int, val levelRequirement: Int, val experience: Double, val requiredVial: Int) {

    GUAM(
        grimy = Items.GRIMY_GUAM,
        clean = Items.CLEAN_GUAM,
        unf = Items.GUAM_POTION_UNF,
        levelRequirement = 3,
        experience = 2.5,
        requiredVial = Items.VIAL_OF_WATER

    ),

    MARRENTILL(
        grimy = Items.GRIMY_MARRENTILL,
        clean = Items.CLEAN_MARRENTILL,
        unf = Items.MARRENTILL_POTION_UNF,
        levelRequirement = 5,
        experience = 3.8,
        requiredVial = Items.VIAL_OF_WATER
    ),

    TARROMIN(
        grimy = Items.GRIMY_TARROMIN,
        clean = Items.CLEAN_TARROMIN,
        unf = Items.TARROMIN_POTION_UNF,
        levelRequirement = 11,
        experience = 5.0,
        requiredVial = Items.VIAL_OF_WATER
    ),

    HARRALANDER(
        grimy = Items.GRIMY_HARRALANDER,
        clean = Items.CLEAN_HARRALANDER,
        unf = Items.HARRALANDER_POTION_UNF,
        levelRequirement = 20,
        experience = 6.3,
        requiredVial = Items.VIAL_OF_WATER
    ),

    RANARR(
        grimy = Items.GRIMY_RANARR,
        clean = Items.CLEAN_RANARR,
        unf = Items.RANARR_POTION_UNF,
        levelRequirement = 25,
        experience = 7.5,
        requiredVial = Items.VIAL_OF_WATER
    ),

    TOADFLAX(
        grimy = Items.GRIMY_TOADFLAX,
        clean = Items.CLEAN_TOADFLAX,
        unf = Items.TOADFLAX_POTION_UNF,
        levelRequirement = 30,
        experience = 8.0,
        requiredVial = Items.VIAL_OF_WATER
    ),

    SPIRIT_WEED(
        grimy = Items.GRIMY_SPIRIT_WEED,
        clean = Items.CLEAN_SPIRIT_WEED,
        unf = Items.SPIRIT_WEED_POTION_UNF,
        levelRequirement = 35,
        experience = 7.8,
        requiredVial = Items.VIAL_OF_WATER
    ),

    IRIT(
        grimy = Items.GRIMY_IRIT,
        clean = Items.CLEAN_IRIT,
        unf = Items.IRIT_POTION_UNF,
        levelRequirement = 40,
        experience = 8.8,
        requiredVial = Items.VIAL_OF_WATER
    ),

    WERGALI(
        grimy = Items.GRIMY_WERGALI,
        clean = Items.CLEAN_WERGALI,
        unf = Items.WERGALI_POTION_UNF,
        levelRequirement = 41,
        experience = 9.5,
        requiredVial = Items.VIAL_OF_WATER
    ),

    AVANTOE(
        grimy = Items.GRIMY_AVANTOE,
        clean = Items.CLEAN_AVANTOE,
        unf = Items.AVANTOE_POTION_UNF,
        levelRequirement = 48,
        experience = 10.0,
        requiredVial = Items.VIAL_OF_WATER
    ),

    KWUARM(
        grimy = Items.GRIMY_KWUARM,
        clean = Items.CLEAN_KWUARM,
        unf = Items.KWUARM_POTION_UNF,
        levelRequirement = 54,
        experience = 11.3,
        requiredVial = Items.VIAL_OF_WATER
    ),

    SNAPDRAGON(
        grimy = Items.GRIMY_SNAPDRAGON,
        clean = Items.CLEAN_SNAPDRAGON,
        unf = Items.SNAPDRAGON_POTION_UNF,
        levelRequirement = 59,
        experience = 11.8,
        requiredVial = Items.VIAL_OF_WATER
    ),

    CADANTINE(
        grimy = Items.GRIMY_CADANTINE,
        clean = Items.CLEAN_CADANTINE,
        unf = Items.CADANTINE_POTION_UNF,
        levelRequirement = 65,
        experience = 12.5,
        requiredVial = Items.VIAL_OF_WATER
    ),

    LANTADYME(
        grimy = Items.GRIMY_LANTADYME,
        clean = Items.CLEAN_LANTADYME,
        unf = Items.LANTADYME_POTION_UNF,
        levelRequirement = 67,
        experience = 13.1,
         requiredVial = Items.VIAL_OF_WATER
    ),

    DWARF_WEED(
        grimy = Items.GRIMY_DWARF_WEED,
        clean = Items.CLEAN_DWARF_WEED,
        unf = Items.DWARF_WEED_POTION_UNF,
        levelRequirement = 70,
        experience = 13.8,
        requiredVial = Items.VIAL_OF_WATER
    ),

    TORSTOL(
        grimy = Items.GRIMY_TORSTOL,
        clean = Items.CLEAN_TORSTOL,
        unf = Items.TORSTOL_POTION_UNF,
        levelRequirement = 75,
        experience = 15.0,
        requiredVial = Items.VIAL_OF_WATER
    ),

    FELLSTALK(
        grimy = Items.GRIMY_FELLSTALK,
        clean = Items.CLEAN_FELLSTALK,
        unf = Items.FELLSTALK_POTION_UNF,
        levelRequirement = 91,
        experience = 16.8,
        requiredVial = Items.VIAL_OF_WATER

    ),

    /**Dungeoneering Herbs*/

    SAGAWORT(
        grimy = Items.GRIMY_SAGEWORT,
        clean = Items.CLEAN_SAGEWORT,
        unf = Items.SAGEWORT_POTION_UNF,
        levelRequirement = 3,
        experience = 2.1,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    FEATHERFOIL(
        grimy = Items.GRIMY_FEATHERFOIL,
        clean = Items.CLEAN_FEATHERFOIL,
        unf = Items.FEATHERFOIL_POTION_UNF,
        levelRequirement = 41,
        experience = 8.6,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    VALERIAN(
        grimy = Items.GRIMY_VALERIAN,
        clean = Items.CLEAN_VALERIAN,
        unf = Items.VALERIAN_POTION_UNF,
        levelRequirement = 4,
        experience = 3.2,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    ALOE(
        grimy = Items.GRIMY_ALOE,
        clean = Items.CLEAN_ALOE,
        unf = Items.ALOE_POTION_UNF,
        levelRequirement = 8,
        experience = 4.0,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    WORMWOOD(
        grimy = Items.GRIMY_WORMWOOD_LEAF,
        clean = Items.CLEAN_WORMWOOD_LEAF,
        unf = Items.WORMWOOD_POTION_UNF,
        levelRequirement = 34,
        experience = 7.2,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    MAGEBANE(
        grimy = Items.GRIMY_MAGEBANE,
        clean = Items.CLEAN_MAGEBANE,
        unf = Items.MAGEBANE_POTION_UNF,
        levelRequirement = 37,
        experience = 7.7,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    WINTER_GRIP(
        grimy = Items.GRIMY_WINTERS_GRIP,
        clean = Items.CLEAN_WINTERS_GRIP,
        unf = Items.WINTERS_GRIP_POTION_UNF,
        levelRequirement = 67,
        experience = 12.7,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    LYCOPUS(
        grimy = Items.GRIMY_LYCOPUS,
        clean = Items.CLEAN_LYCOPUS,
        unf = Items.LYCOPUS_POTION_UNF,
        levelRequirement = 70,
        experience = 13.1,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    BUCKTHORN(
        grimy = Items.GRIMY_BUCKTHORN,
        clean = Items.CLEAN_BUCKTHORN,
        unf = Items.BUCKTHORN_POTION_UNF,
        levelRequirement = 74,
        experience = 13.8,
        requiredVial = Items.VIAL_OF_WATER_17492
    ),

    /** Juju Herbs */

    ERZILLE(
        grimy = Items.GRIMY_ERZILLE,
        clean = Items.CLEAN_ERZILLE,
        unf = Items.ERZILLE_POTION_UNF,
        levelRequirement = 54,
        experience = 10.0,
        requiredVial = Items.JUJU_VIAL_OF_WATER
    ),

    ARGWAY(
        grimy = Items.GRIMY_ARGWAY,
        clean = Items.CLEAN_ARGWAY,
        unf = Items.ARGWAY_POTION_UNF,
        levelRequirement = 56,
        experience = 11.6,
        requiredVial = Items.JUJU_VIAL_OF_WATER
    ),

    UGUNE(
        grimy = Items.GRIMY_UGUNE,
        clean = Items.CLEAN_UGUNE,
        unf = Items.UGUNE_POTION_UNF,
        levelRequirement = 55,
        experience = 11.5,
        requiredVial = Items.JUJU_VIAL_OF_WATER
    ),

    SHENGO(
        grimy = Items.GRIMY_SHENGO,
        clean = Items.CLEAN_SHENGO,
        unf = Items.SHENGO_POTION_UNF,
        levelRequirement = 57,
        experience = 11.7,
        requiredVial = Items.JUJU_VIAL_OF_WATER
    ),

    SAMADEN(
        grimy = Items.GRIMY_SAMADEN,
        clean = Items.CLEAN_SAMADEN,
        unf = Items.SAMADEN_POTION_UNF,
        levelRequirement = 58,
        experience = 11.7,
        requiredVial = Items.JUJU_VIAL_OF_WATER
    );

    companion object {
        val grimyHerbDefinitions = values().associateBy { it.grimy }
        val unfinishedPotionDefinitions = values().associateBy { it.unf }
    }

}