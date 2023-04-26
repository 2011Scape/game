package gg.rsmod.plugins.content.mechanics.lvlup

import gg.rsmod.game.model.attr.LEVEL_UP_INCREMENT
import gg.rsmod.game.model.attr.LEVEL_UP_SKILL_ID

/**
 * Increases prayer points when a player gains prayer levels
 */
set_level_up_logic {
    val skill = player.attr[LEVEL_UP_SKILL_ID]!!
    val increment = player.attr[LEVEL_UP_INCREMENT]!!

    if (skill == Skills.PRAYER) {
        val prayerPoints = player.getCurrentPrayerPoints()
        if (prayerPoints < player.getMaximumPrayerPoints()) {
            val newPrayerPoints = (prayerPoints + increment * 10).coerceAtMost(player.getMaximumPrayerPoints())
            player.setCurrentPrayerPoints(newPrayerPoints)
        }
    }
}
