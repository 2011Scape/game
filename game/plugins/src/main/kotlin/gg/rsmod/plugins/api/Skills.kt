package gg.rsmod.plugins.api

import gg.rsmod.game.fs.def.EnumDef
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.inter.skillguides.SkillGuide
import gg.rsmod.plugins.content.skills.Skillcapes

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Skills {
    const val ATTACK = 0
    const val DEFENCE = 1
    const val STRENGTH = 2
    const val CONSTITUTION = 3
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

    // Sets the current slayer master milestone
    const val SLAYER_MASTER_MILESTONE_VARBIT = 5395

    // Sets the current total milestone
    const val COMBAT_MILESTONE_VALUE = 4727
    const val TOTAL_MILESTONE_VALUE = 4728

    // Set to 0/1, 1 to show milestone, 0 to disable
    const val TOTAL_MILESTONE_VARBIT = 4730
    const val COMBAT_MILESTONE_VARBIT = 4731

    // Sets the dialogue skill icon
    const val LEVEL_UP_DIALOGUE_VARBIT = 4757

    /**
     * The varbit for sending a flashing icon
     * in the skill tab. Ordered by skill id
     */
    val FLASHING_ICON_VARBITS =
        listOf(
            4732,
            4734,
            4733,
            4738,
            4735,
            4736,
            4737,
            4747,
            4749,
            4743,
            4746,
            4748,
            4742,
            4745,
            4744,
            4740,
            4739,
            4741,
            4751,
            4752,
            4750,
            4754,
            4753,
            4755,
            7756,
        )

    /**
     * Milestones for Total levels
     */

    val TOTAL_MILESTONE_ARRAY =
        listOf(
            25,
            50,
            75,
            100,
            200,
            300,
            400,
            500,
            600,
            700,
            800,
            900,
            1000,
            1100,
            1200,
            1300,
            1400,
            1500,
            1600,
            1614,
            1700,
            1800,
            1900,
            2000,
            2100,
            2200,
            2300,
            2400,
            2496,
        )

    /**
     * Milestones for Combat levels
     */

    val COMBAT_MILESTONE_ARRAY =
        listOf(
            3,
            5,
            10,
            15,
            25,
            50,
            75,
            90,
            100,
            110,
            120,
            126,
            130,
            138,
        )

    /**
     * The varc the client requires to determine
     * how many levels you have made since you last
     * clicked on a flashing icon. Ordered by
     * "client" skill ids (i.e, defence is 5 instead of 1)
     */
    val LEVELLED_AMOUNT_VARC =
        listOf(
            1469,
            1470,
            1472,
            1474,
            1471,
            1475,
            1473,
            1476,
            1477,
            1478,
            1479,
            1487,
            1481,
            1482,
            1483,
            1484,
            1485,
            1486,
            1480,
            1488,
            1489,
            1490,
            1491,
            1492,
            1493,
        )

    /**
     * The "clientscript id" for the skill
     */
    val CLIENTSCRIPT_ID =
        listOf(
            1,
            5,
            2,
            6,
            3,
            7,
            4,
            16,
            18,
            19,
            15,
            17,
            11,
            14,
            13,
            9,
            8,
            10,
            20,
            21,
            12,
            23,
            22,
            24,
            25,
        )

    fun getSkillName(
        world: World,
        skill: Int,
    ): String {
        val enum = world.definitions.get(EnumDef::class.java, 680)
        return enum.getString(skill)
    }

    fun isCombat(skill: Int): Boolean =
        when (skill) {
            ATTACK, DEFENCE, CONSTITUTION, STRENGTH,
            RANGED, PRAYER, MAGIC, SUMMONING,
            -> true

            else -> false
        }

    fun getSkillForName(
        world: World,
        maxSkills: Int,
        skillName: String,
    ): Int {
        for (i in 0 until maxSkills) {
            if (getSkillName(world, i).lowercase() == skillName) {
                return i
            }
        }
        return -1
    }

    fun getTargetIdByComponentId(componentId: Int): Int {
        return when (componentId) {
            SkillGuide.ATTACK.child -> 0
            SkillGuide.STRENGTH.child -> 1
            SkillGuide.RANGED.child -> 2
            SkillGuide.MAGIC.child -> 3
            SkillGuide.DEFENCE.child -> 4
            SkillGuide.HITPOINTS.child -> 5
            SkillGuide.PRAYER.child -> 6
            SkillGuide.AGILITY.child -> 7
            SkillGuide.HERBLORE.child -> 8
            SkillGuide.THIEVING.child -> 9
            SkillGuide.CRAFTING.child -> 10
            SkillGuide.RUNECRAFTING.child -> 11
            SkillGuide.MINING.child -> 12
            SkillGuide.SMITHING.child -> 13
            SkillGuide.FISHING.child -> 14
            SkillGuide.COOKING.child -> 15
            SkillGuide.FIREMAKING.child -> 16
            SkillGuide.WOODCUTTING.child -> 17
            SkillGuide.FLETCHING.child -> 18
            SkillGuide.SLAYER.child -> 19
            SkillGuide.FARMING.child -> 20
            SkillGuide.CONSTRUCTION.child -> 21
            SkillGuide.HUNTER.child -> 22
            SkillGuide.SUMMONING.child -> 23
            SkillGuide.DUNGEONEERING.child -> 24
            else -> -1
        }
    }

    fun getTargetIdBySkillId(skillId: Int): Int {
        return when (skillId) {
            SkillGuide.ATTACK.id -> 0
            SkillGuide.STRENGTH.id -> 1
            SkillGuide.RANGED.id -> 2
            SkillGuide.MAGIC.id -> 3
            SkillGuide.DEFENCE.id -> 4
            SkillGuide.HITPOINTS.id -> 5
            SkillGuide.PRAYER.id -> 6
            SkillGuide.AGILITY.id -> 7
            SkillGuide.HERBLORE.id -> 8
            SkillGuide.THIEVING.id -> 9
            SkillGuide.CRAFTING.id -> 10
            SkillGuide.RUNECRAFTING.id -> 11
            SkillGuide.MINING.id -> 12
            SkillGuide.SMITHING.id -> 13
            SkillGuide.FISHING.id -> 14
            SkillGuide.COOKING.id -> 15
            SkillGuide.FIREMAKING.id -> 16
            SkillGuide.WOODCUTTING.id -> 17
            SkillGuide.FLETCHING.id -> 18
            SkillGuide.SLAYER.id -> 19
            SkillGuide.FARMING.id -> 20
            SkillGuide.CONSTRUCTION.id -> 21
            SkillGuide.HUNTER.id -> 22
            SkillGuide.SUMMONING.id -> 23
            SkillGuide.DUNGEONEERING.id -> 24
            else -> -1
        }
    }

    fun reachedTargetGoal(
        player: Player,
        skill: Int,
    ): Boolean {
        val target = getTargetIdBySkillId(skill)
        val level = player.skills.getMaxLevel(skill)
        val experience = player.skills.getCurrentXp(skill)
        if (player.skillTargetMode[target] && level >= player.skillTargetValue[target]) {
            return true
        }
        if (!player.skillTargetMode[target] && experience >= player.skillTargetValue[target]) {
            return true
        }
        return false
    }

    fun hasTwo99s(player: Player): Boolean {
        var count = 0 // initiates the "count" variable, which is "amount of skills 99"
        for (i in 0 until 25) { // loops each skill
            if (player.skills
                    .getMaxLevel(i) >= 99
            ) { // checks each skill for level and if >= 99, add to "count"
                count++
            }
            if (count > 1) { // if "count" is more than 1, returns hasTwo99s = true
                return true
            }
        }
        return false // if "count" is 1 or less, returns hasTwo99s = false
    }

    /**
     * Purchases a skillcape that reflects
     * whether the player has two or more 99s (trimmed)
     * or not (untrimmed)
     */
    fun purchaseSkillcape(
        player: Player,
        data: Skillcapes,
    ): Boolean {
        if (player.inventory.remove(Items.COINS_995, 99000).hasSucceeded()) {
            player.inventory.add(if (hasTwo99s(player)) data.trimmedCape else data.untrimmedCape)
            player.inventory.add(data.hood)
            return true
        }
        return false
    }

    /**
     * Get the appropriate trimmed or untrimmed
     * skillcape for the NPC the player is interacting
     * with
     */
    fun getSkillcape(
        player: Player,
        npcId: Int,
    ): Int {
        val data = Skillcapes.values().firstOrNull { it.npcId == npcId } ?: return -1
        return if (hasTwo99s(player)) data.trimmedCape else data.untrimmedCape
    }

    /**
     * Get the appropriate hood for the
     * skillcape the player is purchasing
     */
    fun getHood(npcId: Int): Int {
        val data = Skillcapes.values().firstOrNull { it.npcId == npcId } ?: return -1
        return data.hood
    }
}
