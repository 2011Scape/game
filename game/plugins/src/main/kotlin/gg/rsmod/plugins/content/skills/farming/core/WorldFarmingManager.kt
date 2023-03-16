package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.World
import gg.rsmod.plugins.content.skills.farming.constants.Constants

object WorldFarmingManager {
    fun onWorldInit(world: World) {
        val farmTicker = FarmTicker(world)

        world.attr[Constants.worldFarmTicker] = farmTicker
        world.timers[Constants.worldFarmingTimer] = Constants.worldFarmingTickLength - farmTicker.gameTicksSinceLastFarmTick
    }

    fun onFarmingTick(world: World) {
        world.attr[Constants.worldFarmTicker]!!.increase()
        world.timers[Constants.worldFarmingTimer] = Constants.worldFarmingTickLength
    }
}
