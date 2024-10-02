package gg.rsmod.plugins.content.skills

import gg.rsmod.game.model.attr.EXPERIENCE_UP_SKILL_ID

set_experience_up_logic {
    val skill = player.attr[EXPERIENCE_UP_SKILL_ID]!!
    val target = Skills.getTargetIdBySkillId(skill)
    if (player.enabledSkillTarget[target]) {
        if (Skills.reachedTargetGoal(player, skill)) {
            player.message(
                "Congratulations! You have completed your target for the ${Skills.getSkillName(world, skill)} skill.",
                type = ChatMessageType.GAME_MESSAGE,
            )
            player.setSkillTargetMode(target, false)
            player.setSkillTargetEnabled(target, false)
            player.setSkillTargetValue(target, 0)
        }
    }

    // refresh the bonus experience counter
    player.runClientScript(776)
}
