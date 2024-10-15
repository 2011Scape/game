package gg.rsmod.plugins.api

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TIME_ONLINE

class FormatTimePlayed {
    companion object {
        // Time conversion values
        private const val MILLISECONDS_PER_SECOND = 1000
        private const val MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * 60
        private const val MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * 60
        private const val MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * 24
        private const val MILLISECONDS_PER_INCREMENT = 600

        fun getTimePlayed(player: Player): String {
            // Get the total time in increments of 600ms
            val totalTimeIncrements = player.timers[TIME_ONLINE]

            // Convert the total increments into milliseconds
            val totalTimeMs = totalTimeIncrements * MILLISECONDS_PER_INCREMENT

            // Calculate the number of days, hours, minutes, and seconds
            val days = totalTimeMs / MILLISECONDS_PER_DAY
            val hours = (totalTimeMs % MILLISECONDS_PER_DAY) / MILLISECONDS_PER_HOUR
            val minutes = (totalTimeMs % MILLISECONDS_PER_HOUR) / MILLISECONDS_PER_MINUTE

            // Build the time duration string
            val durationStringBuilder = StringBuilder()
            if (days > 0) { // Checks for days of playtime and adjusts the string as such
                durationStringBuilder.append("$days days, ")
            }
            durationStringBuilder.append("$hours hours and $minutes minutes")

            // Return the time duration string formatted.
            return durationStringBuilder.toString()
        }
    }
}
