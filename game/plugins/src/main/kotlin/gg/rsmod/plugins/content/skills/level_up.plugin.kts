package gg.rsmod.plugins.content.skills

import gg.rsmod.game.model.attr.LAST_COMBAT_LEVEL
import gg.rsmod.game.model.attr.LAST_TOTAL_LEVEL
import gg.rsmod.game.model.attr.LEVEL_UP_INCREMENT
import gg.rsmod.game.model.attr.LEVEL_UP_SKILL_ID
import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.util.Misc

set_level_up_logic {//so much shit, this is 3x longer than when i started helping you LOL bahaha all good, its content so whateves
    val skill = player.attr[LEVEL_UP_SKILL_ID]!!
    val increment = player.attr[LEVEL_UP_INCREMENT]!!
    val skillName = Skills.getSkillName(player.world, skill)
    val levelFormat = if (increment == 1) Misc.formatForVowel(skillName) else "$increment"

    player.message("You've just advanced $levelFormat $skillName ${"level".pluralSuffix(increment)}. You have reached level ${player.skills.getMaxLevel(skill)}.", type = ChatMessageType.GAME_MESSAGE)

    if (player.skills.getMaxLevel(skill) == SkillSet.MAX_LVL) {
        player.message("<col=800000>Well done! You've achieved the highest possible level in this skill!", type = ChatMessageType.GAME_MESSAGE)
    }

    player.setVarbit(Skills.LEVEL_UP_DIALOGUE_VARBIT, Skills.CLIENTSCRIPT_ID[skill])
    player.setVarbit(Skills.FLASHING_ICON_VARBITS[skill], 1)


    /*
     * * Calculate the combat level for the player if they leveled up a combat skill.
     */
    if (Skills.isCombat(skill)) {
        val oldCombat = player.combatLevel
        player.calculateAndSetCombatLevel()

        val currentCombatLevel = player.combatLevel
        val changed = currentCombatLevel != oldCombat
        if (changed) {
            player.attr[LAST_COMBAT_LEVEL] = oldCombat
            val combatArray = Skills.COMBAT_MILESTONE_ARRAY
            val lastCombat = player.attr[LAST_COMBAT_LEVEL] ?: player.combatLevel
            for (i in combatArray.indices) {
                if (lastCombat >= combatArray[i] || currentCombatLevel < combatArray[i]) {
                    continue
                }
                if (currentCombatLevel == 126) {//TODO CHANGE TO LVL 138 ONCE SUMMONING IS ADDED
                    player.message("<col=800000>Congratulations! Your Combat level is now 126! You've achieved the highest Combat level possible!", type = ChatMessageType.GAME_MESSAGE)
                } else {
                    player.message("<col=800000>Well done! You've reached the Combat level ${combatArray[i]} milestone!", type = ChatMessageType.GAME_MESSAGE)
                }
                player.setVarbit(Skills.COMBAT_MILESTONE_VARBIT, 1)
                player.setVarbit(Skills.COMBAT_MILESTONE_VALUE, i)
            }
            player.setVarbit(Skills.SLAYER_MASTER_MILESTONE_VARBIT, 1)
        }
    }

    if (skill == Skills.CONSTITUTION) {
        player.heal(10 * increment)
    }

    val totalArray = Skills.TOTAL_MILESTONE_ARRAY
    val lastTotal = player.attr[LAST_TOTAL_LEVEL] ?: player.skills.calculateTotalLevel
    val currentTotal = player.skills.calculateTotalLevel
    if (currentTotal == 2475) {
        player.message(
            "<col=800000>Congratulations! Your total level is now 2475! You've achieved the highest total level possible!",
            type = ChatMessageType.GAME_MESSAGE
        )
    }
    for (i in totalArray.indices) {
        if (lastTotal >= totalArray[i] || currentTotal < totalArray[i]) {
            continue
        }
        player.setVarbit(Skills.TOTAL_MILESTONE_VARBIT, 1)
        player.setVarbit(Skills.TOTAL_MILESTONE_VALUE, i)
            player.message(
                "<col=800000>Well done! You've reached the total level ${totalArray[i]} milestone!",
                type = ChatMessageType.GAME_MESSAGE
            )
    }

    /*
     * Show the level-up chatbox interface.
     */
    player.interruptQueues()
    player.queue(TaskPriority.WEAK) {
        levelUpMessageBox(skill, increment)
    }
}
