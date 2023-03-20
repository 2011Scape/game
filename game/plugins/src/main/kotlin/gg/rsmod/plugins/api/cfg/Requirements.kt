package gg.rsmod.plugins.api.cfg

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.quests.Quest
import gg.rsmod.plugins.content.quests.finishedQuest

/**
 * @author Alycia <https://github.com/alycii>
 */

/**
 * @author Alycia <https://github.com/alycii>
 */
interface Requirement { fun hasRequirement(player: Player): Boolean }
class SkillRequirement(val skill: Int, val level: Int) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.getSkills().getCurrentLevel(skill) >= level
    }
}

class QuestRequirement(val quest: Quest) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.finishedQuest(quest)
    }
}

class CombatRequirement(val combatLevel: Int) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.combatLevel >= combatLevel
    }

}