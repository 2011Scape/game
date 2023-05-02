package gg.rsmod.plugins.content.mechanics.restoration

import gg.rsmod.game.model.skill.SkillSet
import gg.rsmod.game.model.timer.STAT_RESTORE
import kotlin.math.sign

/**
 * @author Alycia <https://github.com/alycii>
 */

on_timer(key = STAT_RESTORE) {
    val tempLevels = Array(SkillSet.DEFAULT_SKILL_COUNT) { player.skills.getCurrentLevel(it) }
    val actualLevels = Array(SkillSet.DEFAULT_SKILL_COUNT) { player.skills.getMaxLevel(it) }

    actualLevels.forEachIndexed { index, actualLevel ->
        val difference = actualLevel - tempLevels[index]
        val boost = sign(difference.toDouble()).toInt()

        if (difference != 0) {
            val cap = 125 * boost
            when (index) {
                Skills.CONSTITUTION -> {
                    // Do nothing here, since hitpoints is not coupled to constitution level
                }
                Skills.PRAYER -> {
                    // Do nothing, as Prayer does not naturally restore.
                }
                else -> {
                    player.skills.alterCurrentLevel(skill = index, value = boost, capValue = cap)
                }
            }
        }
    }

    if (player.getMaximumLifepoints() > player.getCurrentLifepoints()) {
        player.alterLifepoints(value = 10, capValue = 0)
    }

    player.timers[STAT_RESTORE] = 100
}
