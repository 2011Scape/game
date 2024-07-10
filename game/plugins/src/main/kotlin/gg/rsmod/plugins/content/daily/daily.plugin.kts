package gg.rsmod.plugins.content.daily

import gg.rsmod.game.model.attr.LOYALTY_POINTS
import gg.rsmod.game.model.timer.DAILY_TIMER

/**
 * @author Alycia <https://github.com/alycii>
 */

val time = 120000

on_timer(DAILY_TIMER) {

    // Add daily loyalty points
    player.addLoyalty(amount = 500)

    // Reset the daily timer
    player.timers[DAILY_TIMER] = time

    // Send the message
    player.message("Your daily timer has been reset, and you have been awarded 500 loyalty points.")
    player.message("You now have a total of ${player.attr[LOYALTY_POINTS]!!.format()} loyalty points.")
}
