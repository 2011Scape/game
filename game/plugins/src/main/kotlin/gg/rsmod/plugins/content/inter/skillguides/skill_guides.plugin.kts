package gg.rsmod.plugins.content.inter.skillguides

import gg.rsmod.game.model.attr.SKILL_MENU
import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.plugins.content.skills.farming.data.Patch

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

val patchTransformIds =
    Patch
        .values()
        .flatMap {
            world.definitions
                .get(ObjectDef::class.java, it.id)
                .transforms
                ?.toSet()
                ?: setOf()
        }.toSet()
val patchTransforms = patchTransformIds.mapNotNull { world.definitions.getNullable(ObjectDef::class.java, it) }
patchTransforms.forEach {
    if (if_obj_has_option(it.id, "guide")) {
        on_obj_option(it.id, "guide") {
            Patch.byPatchId(player.getInteractingGameObj().id)?.let { openSkillMenu(player, SkillGuide.FARMING) }
        }
    }
}

for (buttonId in 10..25) {
    on_button(interfaceId = 499, component = buttonId) {
        player.setVarp(SKILL_ID_VARBIT, (buttonId - 10) * 1024 + player.attr[SKILL_MENU]!!)
    }
}

fun openSkillMenu(
    player: Player,
    guide: SkillGuide,
) {
    val skill = guide.id
    val bit = guide.bit

    if (player.getVarbit(Skills.FLASHING_ICON_VARBITS[skill]) > 0) {
        // set the varbit for the skill advance guide we're viewing
        player.setVarbit(LEVELED_SKILL_VARBIT, bit)

        // set the varc for our last viewed level
        player.setVarc(Skills.LEVELLED_AMOUNT_VARC[bit - 1], player.skills.getLastLevel(skill))

        // disable the flashing icon
        player.setVarbit(Skills.FLASHING_ICON_VARBITS[skill], 0)

        // set our last viewed level to our current level
        player.skills.setLastLevel(skill, player.skills.getMaxLevel(skill))

        // open the skill advance guide
        player.openInterface(interfaceId = 741, dest = InterfaceDestination.MAIN_SCREEN_FULL)
    } else {
        player.setVarp(SKILL_ID_VARBIT, bit)
        player.attr[SKILL_MENU] = guide.bit
        player.openInterface(interfaceId = 499, dest = InterfaceDestination.MAIN_SCREEN_FULL)
    }
}

fun setTarget(
    player: Player,
    guide: SkillGuide,
    usingLevel: Boolean,
) {
    player.queue(TaskPriority.WEAK) {
        val targetId: Int = Skills.getTargetIdByComponentId(guide.child)
        val skillId: Int = guide.id
        var value = inputInt("What " + (if (usingLevel) "level" else "XP") + " target do you wish to set?")
        if (!canSetTarget(player, skillId, value, usingLevel)) {
            return@queue
        }
        player.setSkillTarget(usingLevel, targetId, value)
    }
}

fun canSetTarget(
    player: Player,
    skillId: Int,
    value: Int,
    usingLevel: Boolean,
): Boolean {
    if (usingLevel) {
        if (value > SkillSet.MAX_LVL && skillId != Skills.DUNGEONEERING) {
            player.queue {
                messageBox("You cannot set a level target higher than 99.")
            }
            return false
        } else if (value > SkillSet.MAX_LVL_DUNGEONEERING) {
            player.queue {
                messageBox("You cannot set a level target higher than 120.")
            }
            return false
        }
        if (value <= player.skills.getMaxLevel(skillId)) {
            player.queue {
                messageBox("You must set a target higher than your current skill level.")
            }
            return false
        }
    } else {
        if (value <= player.skills.getCurrentXp(skillId)) {
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
    return true
}

fun resetTarget(
    player: Player,
    guide: SkillGuide,
) {
    val skill = Skills.getTargetIdByComponentId(guide.child)
    player.setSkillTargetEnabled(skill, false)
    player.setSkillTargetValue(skill, 0)
    player.setSkillTargetMode(skill, false)
}
