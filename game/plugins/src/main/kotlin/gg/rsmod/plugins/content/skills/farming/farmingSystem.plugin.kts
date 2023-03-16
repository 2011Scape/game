package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.game.model.attr.LAST_LOGOUT_DATE
import gg.rsmod.plugins.content.skills.farming.constants.Constants.playerFarmingTickLength
import gg.rsmod.plugins.content.skills.farming.constants.Constants.playerFarmingTimer
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmTick
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmingTickLength
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmingTimer
import gg.rsmod.plugins.content.skills.farming.core.FarmTick

on_login {
    val lastLogout = player.attr[LAST_LOGOUT_DATE]
    val ticksSpentOffline = lastLogout?.let { (System.currentTimeMillis() - it) / world.gameContext.cycleTime } ?: 0
    val ticksLeftOnTimer = if (player.timers.has(playerFarmingTimer)) {
        player.timers[playerFarmingTimer]
    } else {
        0
    }
    val totalTicksToHandle = ticksSpentOffline + ticksLeftOnTimer
    val farmingTicksToHandle = totalTicksToHandle / playerFarmingTickLength
    val ticksLeftOnNextTimer = playerFarmingTickLength - (totalTicksToHandle % playerFarmingTickLength)
    for (i in 0 until farmingTicksToHandle) {
        // do farming logic
        // TODO: maximize amount of ticks. After a while you know there's nothing that needs to be handled anymore
    }
    player.timers[playerFarmingTimer] = ticksLeftOnNextTimer.toInt()
}

on_world_init {
    val farmTick = FarmTick(world)

    world.attr[worldFarmTick] = farmTick
    world.timers[worldFarmingTimer] = worldFarmingTickLength - farmTick.gameTicksSinceLastFarmTick()
}

on_timer(playerFarmingTimer) {
    // do farming logic
    player.timers[playerFarmingTimer] = playerFarmingTickLength
}

on_timer(worldFarmingTimer) {
    world.attr[worldFarmTick].increase()
    world.timers[worldFarmingTimer] = worldFarmingTickLength
}
