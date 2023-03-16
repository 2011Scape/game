package gg.rsmod.plugins.content.mechanics.dblexperience

import gg.rsmod.game.model.timer.DOUBLE_EXPERIENCE_TIME

/**
 * @author Alycia <https://github.com/alycii>
 */

val ENABLED_VARBIT = 7232
val TIME_ELAPSED_VARBIT = 7233
val REFRESH_DBL_EXP_SCRIPT = 776
val XP_EARNED_VARP = 1878


on_timer(key = DOUBLE_EXPERIENCE_TIME) {
    player.setVarbit(TIME_ELAPSED_VARBIT, player.getVarbit(TIME_ELAPSED_VARBIT).plus(1))
    player.runClientScript(REFRESH_DBL_EXP_SCRIPT)
    player.timers[DOUBLE_EXPERIENCE_TIME] = 60
}

on_login {
    /**
     * Handle the timer for double experience
     */
    when(world.gameContext.doubleExperience) {
        true -> {

            // Begin counting down the time elapsed timer
            player.timers[DOUBLE_EXPERIENCE_TIME] = 60

            // Enable double experience counter orb
            player.setVarbit(ENABLED_VARBIT, 1)

            // Refresh the orb
            player.runClientScript(REFRESH_DBL_EXP_SCRIPT)
            player.setComponentSprite(interfaceId = 548, component = 0, sprite = 5568)
            player.setComponentSprite(interfaceId = 746, component = 229, sprite = 5568)
        }
        false -> {

            // If the player has a double experience timer
            // then remove it
            if(player.timers.has(DOUBLE_EXPERIENCE_TIME)) {
                player.timers.remove(DOUBLE_EXPERIENCE_TIME)
            }

            // Set their bonus experience gained varp to 0
            player.setVarp(XP_EARNED_VARP, 0)

            // Set their time elapsed varbit to 0
            player.setVarbit(TIME_ELAPSED_VARBIT, 0)

            // Disable the double experience counter
            player.setVarbit(ENABLED_VARBIT, 0)

            // Refresh the orb
            player.runClientScript(REFRESH_DBL_EXP_SCRIPT)
            player.setComponentSprite(interfaceId = 548, component = 0, sprite = 2730)
            player.setComponentSprite(interfaceId = 746, component = 229, sprite = 2730)
        }
    }
}