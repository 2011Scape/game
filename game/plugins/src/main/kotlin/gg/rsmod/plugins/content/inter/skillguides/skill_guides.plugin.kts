package gg.rsmod.plugins.content.inter.skillguides

import gg.rsmod.game.model.attr.SKILL_MENU
import gg.rsmod.game.model.skill.SkillSet

val SKILL_ID_VARBIT = 965
val LEVELED_SKILL_VARBIT = 4729
val LEVELUP_INTERFACE_ID = 741

on_interface_close(LEVELUP_INTERFACE_ID) {
    player.setVarbit(Skills.TOTAL_MILESTONE_VARBIT, 0)
    player.setVarbit(Skills.TOTAL_MILESTONE_VALUE, 0)
    player.setVarbit(Skills.COMBAT_MILESTONE_VARBIT, 0)
    player.setVarbit(Skills.COMBAT_MILESTONE_VALUE, 0)
    player.setVarbit(Skills.SLAYER_MASTER_MILESTONE_VARBIT, 0)
}

SkillGuide.values.forEach { guide ->
    on_button(interfaceId = 320, component = guide.child) {
        if (!player.lock.canInterfaceInteract()) {
            return@on_button
        }
        when (player.getInteractingOpcode()) {
            64 -> setTarget(player, guide, true)
        }
        when (player.getInteractingOpcode()) {
            4 -> setTarget(player, guide, false)
        }
        when (player.getInteractingOpcode()) {
            52 -> resetTarget(player, guide)
        }
        when (player.getInteractingOpcode()) {
            61 -> openSkillMenu(player, guide)
        }
    }
}

for (buttonId in 10..25) {
    on_button(interfaceId = 499, component = buttonId) {
        player.setVarp(SKILL_ID_VARBIT, (buttonId - 10) * 1024 + player.attr[SKILL_MENU]!!)
    }
}

fun openSkillMenu(player: Player, guide: SkillGuide) {
    val skill = guide.id
    val bit = guide.bit

    if (player.getVarbit(Skills.FLASHING_ICON_VARBITS[skill]) > 0) {

        // set the varbit for the skill advance guide we're viewing
        player.setVarbit(LEVELED_SKILL_VARBIT, bit)

        // set the varc for our last viewed level
        player.setVarc(Skills.LEVELLED_AMOUNT_VARC[bit - 1], player.getSkills().getLastLevel(skill))

        // disable the flashing icon
        player.setVarbit(Skills.FLASHING_ICON_VARBITS[skill], 0)

        // set our last viewed level to our current level
        player.getSkills().setLastLevel(skill, player.getSkills().getCurrentLevel(skill))

        // open the skill advance guide
        player.openInterface(interfaceId = 741, dest = InterfaceDestination.MAIN_SCREEN_FULL)
    } else {
        player.setVarp(SKILL_ID_VARBIT, bit)
        player.attr[SKILL_MENU] = guide.bit
        player.openInterface(interfaceId = 499, dest = InterfaceDestination.MAIN_SCREEN_FULL)
    }
}

fun setTarget(player: Player, guide: SkillGuide, usingLevel: Boolean) {
    player.queue(TaskPriority.WEAK) {
        val targetId: Int = getTargetIdByComponentId(guide.child)
        val skillId: Int = guide.id
        var value = inputInt("What " + (if (usingLevel) "level" else "XP") + " target do you wish to set?")
        if (!canSetTarget(player, skillId, value, usingLevel))
            return@queue
        player.setSkillTarget(usingLevel, targetId, value)
    }
}

fun canSetTarget(player: Player, skillId: Int, value: Int, usingLevel: Boolean): Boolean {
    if (usingLevel) {
        if (value > SkillSet.MAX_LVL) {
            player.queue {
                messageBox("You cannot set a level target higher than 99.")
            }
            return false
        }
        if (value <= player.getSkills().getMaxLevel(skillId)) {
            player.queue {
                messageBox("You must set a target higher than your current skill level.")
            }
            return false
        }
    } else {
        if (value <= player.getSkills().getCurrentXp(skillId)) {
            player.queue {
                messageBox("You must set a target higher than your current xp.")
            }
            return false
        }
        if (value > SkillSet.MAX_XP) {
            player.queue {
                messageBox("That number is larger than the amount of XP it is possible to attain.")
            }
            return false
        }
    }
    return true;
}

fun resetTarget(player: Player, guide: SkillGuide) {
    val skill = getTargetIdByComponentId(guide.child)
    player.setSkillTargetEnabled(skill, false)
    player.setSkillTargetValue(skill, 0)
    player.setSkillTargetMode(skill, false)
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
