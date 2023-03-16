package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.attr.LAST_LOGOUT_DATE
import gg.rsmod.game.model.attr.LAST_WORLD_FARMING_TICK
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmTicker
import gg.rsmod.plugins.content.skills.farming.logic.GrowthManager

object PlayerManager {

    fun onFarmingTick(player: Player) {
        val farmTicker = player.world.attr[worldFarmTicker]!!
        GrowthManager.growWeeds(player)
        GrowthManager.growSeeds(player, farmTicker.currentSeedTypes)

        player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
    }

    fun onLogin(player: Player) {
        val farmTicker = player.world.attr[worldFarmTicker]!!
        val lastWorldFarmTick = player.attr[LAST_WORLD_FARMING_TICK]
        if (lastWorldFarmTick != null) {
            val totalGameTicksToHandle = gameTicksSinceLastPlayerFarmTick(player)
            val ticksLeftOnNextTimer = Constants.playerFarmingTickLength - (totalGameTicksToHandle % Constants.playerFarmingTickLength)
            val includeCurrentFarmTick = farmTicker.gameTicksUntilNextFarmTick < ticksLeftOnNextTimer

            for (seedList in farmTicker.pastSeedTypes(lastWorldFarmTick, includeCurrentFarmTick)) {
                GrowthManager.growSeeds(player, farmTicker.currentSeedTypes)
                if (GrowthManager.everythingFullyGrown(player)) {
                    break
                }
            }
            player.timers[Constants.playerFarmingTimer] = ticksLeftOnNextTimer.toInt()
        } else {
            player.timers[Constants.playerFarmingTimer] = Constants.playerFarmingTickLength
        }
    }

    private fun gameTicksSinceLastPlayerFarmTick(player: Player): Long {
        val timer = if (player.timers.has(Constants.playerFarmingTimer)) {
            player.timers[Constants.playerFarmingTimer]
        } else {
            return 0
        }
        val lastLogout = player.attr[LAST_LOGOUT_DATE] ?: return 0

        val ticksSpentOffline = (System.currentTimeMillis() - lastLogout) / player.world.gameContext.cycleTime
        val ticksUsedOnTimer = Constants.playerFarmingTickLength - timer
        return ticksSpentOffline + ticksUsedOnTimer
    }
}