package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.attr.LAST_LOGOUT_DATE
import gg.rsmod.game.model.attr.LAST_WORLD_FARMING_TICK
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.GrowthManager

object PlayerManager {

    /**
     * Called when a player farming tick occurs. Grows all seeds, weeds and produce, and
     * sets the relevant timer and attribute
     */
    fun onFarmingTick(player: Player) {
        grow(player, FarmTicker.currentSeedTypes)

        player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
        player.attr[LAST_WORLD_FARMING_TICK] = player.world.attr[Constants.worldFarmTick]!!
    }

    /**
     * Initializes the player farming logic upon login. Replays farming ticks that occurred while logged out and
     * starts the farming tick timer
     */
    fun onLogin(player: Player) {
        val lastWorldFarmTick = player.attr[LAST_WORLD_FARMING_TICK]
        if (lastWorldFarmTick != null) {
            val totalGameTicksToHandle = gameTicksSinceLastPlayerFarmTick(player)
            val ticksLeftOnNextTimer = Constants.playerFarmingTickLength - (totalGameTicksToHandle % Constants.playerFarmingTickLength)

            // If there are less game ticks until the next world farming tick than game ticks until the
            // next player farming tick, that means the current world farming tick was activated for the
            // player while logged out
            val includeCurrentFarmTick = FarmTicker.gameTicksUntilNextFarmTick(player.world) < ticksLeftOnNextTimer

            // Replay all player farming ticks that occurred while logged out
            for (seedList in FarmTicker.pastSeedTypes(player.world, lastWorldFarmTick, includeCurrentFarmTick)) {
                grow(player, seedList)
                if (GrowthManager.everythingFullyGrown(player)) {
                    // If everything is fully grown, there's no need to replay any more player farming ticks
                    break
                }
            }
            player.timers[Constants.playerFarmingTimer] = ticksLeftOnNextTimer.toInt()

            // Set the player attribute that stores the last world farm tick the player handled
            // If the current farming tick was not included in the replay, this is the world farming tick minus 1
            player.attr[LAST_WORLD_FARMING_TICK] = player.world.attr[Constants.worldFarmTick]!!.let {
                if (includeCurrentFarmTick) it else it - 1
            }
        } else {
            // lastWorldFarmTick is null, which should only happen for accounts that didn't log in since farming was introduced
            player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
        }
    }

    /**
     * Grows all seeds, weeds and produce
     */
    private fun grow(player: Player, seedTypes: List<SeedType>) {
        GrowthManager.growWeeds(player)
        GrowthManager.growSeeds(player, seedTypes)
    }

    /**
     * Calculates the amount of game ticks that occurred since the last player farm tick
     */
    private fun gameTicksSinceLastPlayerFarmTick(player: Player): Int {
        val timer = if (player.timers.has(Constants.playerFarmingTimer)) {
            player.timers[Constants.playerFarmingTimer]
        } else {
            return 0
        }
        val lastLogout: Long = player.attr[LAST_LOGOUT_DATE]?.toLong() ?: return 0

        val ticksSpentOffline = (System.currentTimeMillis() - lastLogout) / player.world.gameContext.cycleTime
        val ticksUsedOnTimer = Constants.playerFarmingTickLength - timer

        // The amount of ticks since the last player tick is the time spent offline, plus the
        // time that was used already on the player farming tick timer. That timer is paused upon logout
        return (ticksSpentOffline + ticksUsedOnTimer).toInt()
    }
}
