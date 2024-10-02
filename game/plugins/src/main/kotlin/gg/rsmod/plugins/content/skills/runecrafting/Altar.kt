package gg.rsmod.plugins.content.skills.runecrafting

import gg.rsmod.game.model.Tile
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs

/**
 * @author Triston Plummer ("Dread")
 *
 * Represents the various Runecrafting altars
 *
 * @param ruins         An array of object ids that should include the Mysterious Ruins and it's transformed counterpart (when wearing the tiara)
 * @param altar         The object of the Runecrafting altar
 * @param exitPortal    The object id of the altar exit portal
 * @param talisman      The talisman item id
 * @param tiara         The tiara item id
 * @param varbit        The varbit used to transform the ruins object when wearing a tiara
 * @param entrance      The tile the player is teleported to upon entering the ruins
 * @param exit          The tile the player is teleported to upon exiting the altar
 *
 */
enum class Altar(
    val ruins: IntArray? = null,
    val altar: Int,
    val exitPortal: Int? = null,
    val talisman: Int? = null,
    val tiara: Int? = null,
    val varbit: Int = 0,
    val rune: Rune,
    val entrance: Tile? = null,
    val exit: Tile? = null,
    val option: String = "craft-rune",
) {
    AIR(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS, Objs.MYSTERIOUS_RUINS_7104),
        altar = Objs.ALTAR_2478,
        exitPortal = Objs.PORTAL_2465,
        talisman = Items.AIR_TALISMAN,
        tiara = Items.AIR_TIARA,
        varbit = 607,
        rune = Rune.AIR,
        entrance = Tile(2841, 4829),
        exit = Tile(3127, 3407),
    ),
    MIND(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7105, Objs.MYSTERIOUS_RUINS_7106),
        altar = Objs.ALTAR_2479,
        exitPortal = Objs.PORTAL_2466,
        talisman = Items.MIND_TALISMAN,
        tiara = Items.MIND_TIARA,
        varbit = 608,
        rune = Rune.MIND,
        entrance = Tile(2793, 4829),
        exit = Tile(2980, 3511),
    ),
    WATER(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7107, Objs.MYSTERIOUS_RUINS_7108),
        altar = Objs.ALTAR_2480,
        exitPortal = Objs.PORTAL_2467,
        talisman = Items.WATER_TALISMAN,
        tiara = Items.WATER_TIARA,
        varbit = 609,
        rune = Rune.WATER,
        entrance = Tile(3490, 4832),
        exit = Tile(3183, 3164),
    ),
    EARTH(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7109, Objs.MYSTERIOUS_RUINS_7110),
        altar = Objs.ALTAR_2481,
        exitPortal = Objs.PORTAL_2468,
        talisman = Items.EARTH_TALISMAN,
        tiara = Items.EARTH_TIARA,
        varbit = 610,
        rune = Rune.EARTH,
        entrance = Tile(2657, 4830),
        exit = Tile(3304, 3475),
    ),
    FIRE(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7111, Objs.MYSTERIOUS_RUINS_7112),
        altar = Objs.ALTAR_2482,
        exitPortal = Objs.PORTAL_2469,
        talisman = Items.FIRE_TALISMAN,
        tiara = Items.FIRE_TIARA,
        varbit = 611,
        rune = Rune.FIRE,
        entrance = Tile(2578, 4845),
        exit = Tile(3310, 3252),
    ),
    BODY(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7113, Objs.MYSTERIOUS_RUINS_7114),
        altar = Objs.ALTAR_2483,
        exitPortal = Objs.PORTAL_2470,
        talisman = Items.BODY_TALISMAN,
        tiara = Items.BODY_TIARA,
        varbit = 612,
        rune = Rune.BODY,
        entrance = Tile(2521, 4834),
        exit = Tile(3051, 3445),
    ),
    COSMIC(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7115, Objs.MYSTERIOUS_RUINS_7116),
        altar = Objs.ALTAR_2484,
        exitPortal = Objs.PORTAL_2471,
        talisman = Items.COSMIC_TALISMAN,
        tiara = Items.COSMIC_TIARA,
        varbit = 613,
        rune = Rune.COSMIC,
        entrance = Tile(2144, 4831),
        exit = Tile(2410, 4379),
    ),

    CHAOS(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7121, Objs.MYSTERIOUS_RUINS_7122),
        altar = Objs.ALTAR_2487,
        exitPortal = Objs.PORTAL_2474,
        talisman = Items.CHAOS_TALISMAN,
        tiara = Items.CHAOS_TIARA,
        varbit = 614,
        rune = Rune.CHAOS,
        entrance = Tile(2273, 4841),
        exit = Tile(3059, 3589),
    ),

    NATURE(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7119, Objs.MYSTERIOUS_RUINS_7120),
        altar = Objs.ALTAR_2486,
        exitPortal = Objs.PORTAL_2473,
        talisman = Items.NATURE_TALISMAN,
        tiara = Items.NATURE_TIARA,
        varbit = 615,
        rune = Rune.NATURE,
        entrance = Tile(2398, 4842),
        exit = Tile(2867, 3020),
    ),

    LAW(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_7117, Objs.MYSTERIOUS_RUINS_7118),
        altar = Objs.ALTAR_2485,
        exitPortal = Objs.PORTAL_2472,
        talisman = Items.LAW_TALISMAN,
        tiara = Items.LAW_TIARA,
        varbit = 616,
        rune = Rune.LAW,
        entrance = Tile(2466, 4831),
        exit = Tile(2858, 3379),
    ),

    ASTRAL(
        altar = Objs.ALTAR_17010,
        rune = Rune.ASTRAL,
    ),

    DEATH(
        ruins = intArrayOf(Objs.MYSTERIOUS_RUINS_2462),
        altar = Objs.ALTAR_2488,
        exitPortal = Objs.PORTAL_2475,
        talisman = Items.DEATH_TALISMAN,
        tiara = Items.DEATH_TIARA,
        varbit = 617,
        rune = Rune.DEATH,
        entrance = Tile(2208, 4830),
        exit = Tile(1863, 4639),
    ),

    BLOOD(
        altar = Objs.ALTAR_30624,
        talisman = Items.BLOOD_TALISMAN,
        tiara = Items.BLOOD_TIARA,
        varbit = 618,
        rune = Rune.BLOOD,
        entrance = Tile(2468, 4889),
        exit = Tile(3561, 9779),
    ),
    ;

    companion object {
        val values = enumValues<Altar>()
    }
}
