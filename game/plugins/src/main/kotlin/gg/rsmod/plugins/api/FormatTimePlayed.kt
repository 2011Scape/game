package gg.rsmod.plugins.api

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TIME_ONLINE

class FormatTimePlayed {
    companion object {
        const val MILLISECONDS_PER_SECOND = 1000
        const val MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * 60
        const val MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * 60
        const val MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * 24
        const val MILLISECONDS_PER_INCREMENT = 600

        fun getTimePlayed(player: Player): String {
            // Get the total time in increments of 600ms
            val total_time_increments = player.timers[TIME_ONLINE]

            // Convert the total increments into milliseconds
            val total_time_ms = total_time_increments * MILLISECONDS_PER_INCREMENT

            // Calculate the number of days, hours, minutes, and seconds
            val days = total_time_ms / MILLISECONDS_PER_DAY
            val hours = (total_time_ms % MILLISECONDS_PER_DAY) / MILLISECONDS_PER_HOUR
            val minutes = (total_time_ms % MILLISECONDS_PER_HOUR) / MILLISECONDS_PER_MINUTE

            // Build the time duration string
            val durationStringBuilder = StringBuilder()
            if (days > 0) {
                durationStringBuilder.append("$days days, ")
            }
            durationStringBuilder.append("$hours hours and $minutes minutes")

            return durationStringBuilder.toString()
        }
    }
}




