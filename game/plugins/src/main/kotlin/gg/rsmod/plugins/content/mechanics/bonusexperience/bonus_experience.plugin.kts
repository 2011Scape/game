package gg.rsmod.plugins.content.mechanics.bonusexperience

import gg.rsmod.game.model.timer.BONUS_EXPERIENCE_TIME_ELAPSED

/**
 * @author Alycia <https://github.com/alycii>
 */

val ENABLED_VARBIT = 7232
val TIME_ELAPSED_VARBIT = 7233
val REFRESH_BONUS_EXP_SCRIPT = 776
val XP_EARNED_VARP = 1878

on_timer(key = BONUS_EXPERIENCE_TIME_ELAPSED) {
    player.setVarbit(TIME_ELAPSED_VARBIT, player.getVarbit(TIME_ELAPSED_VARBIT).plus(1))
    player.runClientScript(REFRESH_BONUS_EXP_SCRIPT)
    player.timers[BONUS_EXPERIENCE_TIME_ELAPSED] = 100
}

on_login {
    /**
     * Handle the timer for bonus experience
     */
    val isBonusXPEnabled =
        world.gameContext.bonusExperience || world.playersWithBonusXP.contains(player.username.lowercase())
    when (isBonusXPEnabled) {
        true -> {

            // Begin counting down the time elapsed timer
            player.timers[BONUS_EXPERIENCE_TIME_ELAPSED] = 100

            // Enable bonus experience counter orb
            player.setVarbit(ENABLED_VARBIT, 1)

            // Refresh the orb
            player.runClientScript(REFRESH_BONUS_EXP_SCRIPT)
            player.setComponentSprite(interfaceId = 548, component = 0, sprite = 5568)
            player.setComponentSprite(interfaceId = 746, component = 229, sprite = 5568)
        }
        false -> {

            // If the player has a bonus experience timer
            // then remove it
            if (player.timers.has(BONUS_EXPERIENCE_TIME_ELAPSED)) {
                player.timers.remove(BONUS_EXPERIENCE_TIME_ELAPSED)
            }

            // Set their bonus experience gained varp to 0
            player.setVarp(XP_EARNED_VARP, 0)

            // Set their time elapsed varbit to 0
            player.setVarbit(TIME_ELAPSED_VARBIT, 0)

            // Disable the bonus experience counter
            player.setVarbit(ENABLED_VARBIT, 0)

            // Refresh the orb
            player.runClientScript(REFRESH_BONUS_EXP_SCRIPT)
            player.setComponentSprite(interfaceId = 548, component = 0, sprite = 2730)
            player.setComponentSprite(interfaceId = 746, component = 229, sprite = 2730)
        }
    }
}
