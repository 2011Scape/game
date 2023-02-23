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
    val option: String = "craft-rune"
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
        exit = Tile(3127, 3407)
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
        exit = Tile(2980, 3511)
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
        exit = Tile(3183, 3164)
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
        exit = Tile(3304, 3475)
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
        exit = Tile(3310, 3252)
    ),

    ;

    companion object {
        val values = enumValues<Altar>()
    }
}