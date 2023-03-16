package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.attr.LAST_LOGOUT_DATE
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmTick

class PlayerManager {
    fun onLogin(player: Player) {
        val farmTick = player.world.attr[worldFarmTick]!!

        val totalGameTicksToHandle = gameTicksSinceLastPlayerFarmTick(player)
        val farmingTicksToHandle = totalGameTicksToHandle / Constants.playerFarmingTickLength
        val ticksLeftOnNextTimer = Constants.playerFarmingTickLength - (totalGameTicksToHandle % Constants.playerFarmingTickLength)
        val includeCurrentFarmTick = includeCurrentTickForLoginLogic(farmTick, ticksLeftOnNextTimer)

        for (seedList in ) {
            // do farming logic
            // TODO: maximize amount of ticks. After a while you know there's nothing that needs to be handled anymore
        }
        player.timers[Constants.playerFarmingTimer] = ticksLeftOnNextTimer.toInt()
    }

    private fun gameTicksSinceLastPlayerFarmTick(player: Player): Long {
        val lastLogout = player.attr[LAST_LOGOUT_DATE]
        val ticksSpentOffline = lastLogout?.let { (System.currentTimeMillis() - it) / player.world.gameContext.cycleTime } ?: 0
        val ticksLeftOnTimer = if (player.timers.has(Constants.playerFarmingTimer)) {
            player.timers[Constants.playerFarmingTimer]
        } else {
            0
        }
        return ticksSpentOffline + ticksLeftOnTimer
    }

    private fun includeCurrentTickForLoginLogic(farmTick: FarmTick, ticksLeftOnNextTimer: Long): Boolean {
        val gameTicksUntilNextFarmTick = farmTick.gameTicksUntilNextFarmTick()
        return gameTicksUntilNextFarmTick < ticksLeftOnNextTimer
    }
}