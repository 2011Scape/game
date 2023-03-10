package gg.rsmod.plugins.content.skills

import gg.rsmod.game.model.attr.LEVEL_UP_INCREMENT
import gg.rsmod.game.model.attr.LEVEL_UP_SKILL_ID

set_level_up_logic {
    val skill = player.attr[LEVEL_UP_SKILL_ID]!!
    val increment = player.attr[LEVEL_UP_INCREMENT]!!
    player.setVarbit(Skills.LEVEL_UP_DIALOGUE_VARBIT, Skills.CLIENTSCRIPT_ID[skill])
    player.setVarbit(Skills.FLASHING_ICON_VARBITS[skill], 1)
    /*
     * * Calculate the combat level for the player if they leveled up a combat skill.
     */
    if (Skills.isCombat(skill)) {
        player.calculateAndSetCombatLevel()
        if (player.combatLevel > player.getSkills().getLastCombatLevel())
            player.getSkills().setLastCombatLevel(player.combatLevel)
    }

    if (skill == Skills.HITPOINTS) {
        player.heal(10 * increment)
    }

    val lastCombatLevel = player.getSkills().getLastCombatLevel()
    val currentCombatLevel = player.combatLevel
    val combatArray = Skills.COMBAT_MILESTONE_ARRAY
    val combatLeveled = lastCombatLevel < currentCombatLevel
    if (combatLeveled) {
        for (i in combatArray.indices) {
            if (lastCombatLevel >= combatArray[i] || currentCombatLevel < combatArray[i]) {
                continue
            }
            player.setVarbit(Skills.COMBAT_MILESTONE_VARBIT, 1)
            player.setVarbit(Skills.COMBAT_MILESTONE_VALUE, i)
        }
        player.setVarbit(Skills.SLAYER_MASTER_MILESTONE_VARBIT, 1)
    }

    val totalArray = Skills.TOTAL_MILESTONE_ARRAY
    val lastTotal = player.getSkills().getLastTotalLevel()
    val currentTotal = player.getSkills().calculateTotalLevel
    for (i in totalArray.indices) {
        if (lastTotal >= totalArray[i] || currentTotal < totalArray[i]) {
            continue
        }
        player.setVarbit(Skills.TOTAL_MILESTONE_VARBIT, 1)
        player.setVarbit(Skills.TOTAL_MILESTONE_VALUE, i)
    }

    /*
     * Show the level-up chatbox interface.
     */
    player.interruptQueues()
    player.queue {
        levelUpMessageBox(skill, increment)
    }
}
