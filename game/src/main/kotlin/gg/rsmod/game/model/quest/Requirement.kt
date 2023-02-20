package gg.rsmod.game.model.quest

import gg.rsmod.game.model.entity.Player

/**
 * @author Alycia <https://github.com/alycii>
 */
interface Requirement { fun hasRequirement(player: Player): Boolean }

class SkillRequirement(val skill: Int, val level: Int) : Requirement {
    override fun hasRequirement(player: Player): Boolean {
        return player.getSkills().getCurrentLevel(skill) >= level
    }

}