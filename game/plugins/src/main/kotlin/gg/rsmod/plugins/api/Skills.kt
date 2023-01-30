package gg.rsmod.plugins.api

import gg.rsmod.game.fs.def.EnumDef
import gg.rsmod.game.model.World

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Skills {

    const val ATTACK = 0
    const val DEFENCE = 1
    const val STRENGTH = 2
    const val HITPOINTS = 3
    const val RANGED = 4
    const val PRAYER = 5
    const val MAGIC = 6
    const val COOKING = 7
    const val WOODCUTTING = 8
    const val FLETCHING = 9
    const val FISHING = 10
    const val FIREMAKING = 11
    const val CRAFTING = 12
    const val SMITHING = 13
    const val MINING = 14
    const val HERBLORE = 15
    const val AGILITY = 16
    const val THIEVING = 17
    const val SLAYER = 18
    const val FARMING = 19
    const val RUNECRAFTING = 20
    const val HUNTER = 21
    const val CONSTRUCTION = 22
    const val SUMMONING = 23
    const val DUNGEONEERING = 24

    const val MIN_COMBAT_LVL = 3
    const val MAX_COMBAT_LVL = 138

    // Sets the dialogue skill icon
    const val LEVEL_UP_DIALOGUE_VARBIT = 4757

    /**
     * The varbit for sending a flashing icon
     * in the skill tab. Ordered by skill id
     */
    val FLASHING_ICON_VARBITS = arrayOf(
        4732, 4734, 4733, 4738, 4735, 4736, 4737, 4747, 4749, 4743,
        4746, 4748, 4742, 4745, 4744, 4740, 4739, 4741, 4751, 4752,
        4750, 4754, 4753, 4755, 7756
    )

    /**
     * The varc the client requires to determine
     * how many levels you have made since you last
     * clicked on a flashing icon. Ordered by
     * "client" skill ids (i.e, defence is 5 instead of 1)
     */
    val LEVELLED_AMOUNT_VARC = arrayOf(
        1469, 1470, 1472, 1474, 1471, 1475, 1473, 1476, 1477, 1478,
        1479, 1487, 1481, 1482, 1483, 1484, 1485, 1486, 1480, 1488,
        1489, 1490, 1491, 1492, 1493
    )

    /**
     * The "clientscript id" for the skill
     */
    val CLIENTSCRIPT_ID = intArrayOf(
        1, 5, 2, 6, 3, 7, 4, 16,
        18, 19, 15, 17, 11, 14, 13, 9,
        8, 10, 20, 21, 12, 23, 22, 24, 25
    )


    fun getSkillName(world: World, skill: Int): String {
        val enum = world.definitions.get(EnumDef::class.java, 680)
        return enum.getString(skill)
    }

    fun isCombat(skill: Int): Boolean = when (skill) {
        ATTACK, DEFENCE, HITPOINTS, STRENGTH,
        RANGED, PRAYER, MAGIC, SUMMONING -> true
        else -> false
    }

    fun getSkillForName(world: World, maxSkills: Int, skillName: String): Int {
        for (i in 0 until maxSkills) {
            if (getSkillName(world, i).lowercase() == skillName) {
                return i
            }
        }
        return -1
    }
}