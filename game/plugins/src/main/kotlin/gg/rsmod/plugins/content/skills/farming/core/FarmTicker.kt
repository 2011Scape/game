package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.World
import gg.rsmod.plugins.content.skills.farming.constants.Constants
import gg.rsmod.plugins.content.skills.farming.constants.Constants.firstFarmTickDate
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmingTickLength
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * Keeps track of the global farming ticks. Every 5 minutes the current tick is increased by 1 through a world timer.
 * Depending on what tick we are in, different types of seeds have the opportunity to grow. These seeds will grow
 * when a player farming tick occurs.
 */
object FarmTicker {

    /**
     * The seed types that have an opportunity to grow in the current tick
     */
    var currentSeedTypes = setOf<SeedType>()
        private set

    /**
     * Initializes the global farm tick and the current seed types
     */
    fun initialize(world: World) {
        val gameTicksSinceStart = msSinceStart() / world.gameContext.cycleTime
        val currentFarmingTick = (gameTicksSinceStart / worldFarmingTickLength).toInt()
        setCurrentFarmingTick(world, currentFarmingTick)
        currentSeedTypes = seedTypes(currentFarmingTick)
    }

    /**
     * Calculates how many game ticks have occurred since the last global farming tick
     */
    fun gameTicksSinceLastFarmTick(world: World): Int {
        val gameTicksSinceStart = msSinceStart() / world.gameContext.cycleTime
        return (gameTicksSinceStart % worldFarmingTickLength).toInt()
    }

    /**
     * Calculates how many game ticks need to occur before the next global farming tick
     */
    fun gameTicksUntilNextFarmTick(world: World): Int = worldFarmingTickLength - gameTicksSinceLastFarmTick(world)

    /**
     * Provides a sequence of seed types for a range of past ticks, starting from `startingTick` up to
     * the current tick (if `includeCurrentTick` is true) or the previous tick (if `includeCurrentTick` is false)
     */
    fun pastSeedTypes(world: World, startingTick: Int, includeCurrentTick: Boolean): Sequence<Set<SeedType>> = sequence {
        val currentTick = world.attr[Constants.worldFarmTick]!!
        val range = if (includeCurrentTick) {
            startingTick..currentTick
        } else {
            startingTick until currentTick
        }
        for (tick in range) {
            yield(seedTypes(tick))
        }
    }

    /**
     * Move to the next global farming tick
     */
    fun increase(world: World) {
        val tick = world.attr[Constants.worldFarmTick]!! + 1
        setCurrentFarmingTick(world, tick)
        currentSeedTypes = seedTypes(tick)
    }

    /**
     * Calculates the amount of milliseconds that have occurred since the start of farming ticks as
     * defined in `Constants.firstFarmTickDate`
     */
    private fun msSinceStart(): Long {
        val timeOfDay = ZonedDateTime.now(ZoneOffset.UTC)
        return Duration.between(firstFarmTickDate, timeOfDay).toMillis()
    }

    /**
     * Returns the seed types that have an opportunity to grow in the provided tick
     */
    private fun seedTypes(tick: Int) = SeedType.values().filter { tick % it.growthFrequency == 0 }.toSet()

    /**
     * Updates the world attribute storing the current farming tick
     */
    private fun setCurrentFarmingTick(world: World, tick: Int) {
        world.attr[Constants.worldFarmTick] = tick
    }
}
