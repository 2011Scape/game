package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.World
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.data.CompostBin
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed

object WorldFarmingManager {
    /**
     * Initializes the world farming logic. Sets the current world farm tick and starts the timer for the next farm tick
     */
    fun onWorldInit(world: World) {
        FarmTicker.initialize(world)
        world.timers[Constants.worldFarmingTimer] =
            Constants.worldFarmingTickLength - FarmTicker.gameTicksSinceLastFarmTick(world)
        Patch.initialize(world)
        Seed.initialize(world)
        CompostBin.initialize(world)
    }

    /**
     * Updates the current world farm tick
     */
    fun onFarmingTick(world: World) {
        FarmTicker.increase(world)
        world.timers[Constants.worldFarmingTimer] = Constants.worldFarmingTickLength
    }
}
