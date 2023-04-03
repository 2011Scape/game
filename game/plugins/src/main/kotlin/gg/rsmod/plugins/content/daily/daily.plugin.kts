package gg.rsmod.plugins.content.daily

import gg.rsmod.game.model.timer.DAILY_TIMER

/**
 * @author Alycia <https://github.com/alycii>
 */

on_timer(DAILY_TIMER) {

    // Add daily loyalty points
    player.addLoyalty(amount = 500)

    // Reset the daily timer
    player.timers[DAILY_TIMER] = 144000
}