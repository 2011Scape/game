package gg.rsmod.plugins.api.cfg

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.content.quests.QUEST_POINT_VARP
import gg.rsmod.plugins.content.quests.Quest
import gg.rsmod.plugins.content.quests.finishedQuest

/**
 * @author Alycia <https://github.com/alycii>
 */
interface Requirement {
    fun hasRequirement(player: Player): Boolean
}

class SkillRequirement(
    val skill: Int,
    val level: Int,
) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.skills.getMaxLevel(skill) >= level
    }
}

class QuestRequirement(
    val quest: Quest,
) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.finishedQuest(quest)
    }
}

class QuestPointRequirement(
    val points: Int,
) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.getVarp(QUEST_POINT_VARP) >= points
    }
}

class CombatRequirement(
    val combatLevel: Int,
) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.combatLevel >= combatLevel
    }
}
