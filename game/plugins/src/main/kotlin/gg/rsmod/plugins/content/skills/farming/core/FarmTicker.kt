package gg.rsmod.plugins.content.skills.farming.core

import gg.rsmod.game.model.World
import gg.rsmod.plugins.content.skills.farming.constants.Constants.firstFarmTickDate
import gg.rsmod.plugins.content.skills.farming.constants.Constants.worldFarmingTickLength
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime

class FarmTicker(private val world: World) {
    private var currentTick: Long
    private var lastSeedTypeCalculated = -1L
    private var seedTypeCache = listOf<SeedType>()

    init {
        val gameTicksSinceStart = msSinceStart() / world.gameContext.cycleTime
        currentTick = gameTicksSinceStart / worldFarmingTickLength
    }

    val currentSeedTypes: List<SeedType>
        get() {
            if (lastSeedTypeCalculated != currentTick) {
                seedTypeCache = seedTypes(currentTick)
                lastSeedTypeCalculated = currentTick
            }
            return seedTypeCache
        }

    val gameTicksSinceLastFarmTick: Int get() {
        val gameTicksSinceStart = msSinceStart() / world.gameContext.cycleTime
        return (gameTicksSinceStart % worldFarmingTickLength).toInt()
    }

    val gameTicksUntilNextFarmTick: Int get() = worldFarmingTickLength - gameTicksSinceLastFarmTick

    fun pastSeedTypes(startingTick: Long, includeCurrentTick: Boolean): Sequence<List<SeedType>> = sequence {
        val range = if (includeCurrentTick) {
            startingTick..currentTick
        } else {
            startingTick until currentTick
        }
        for (tick in range) {
            yield(seedTypes(tick))
        }
    }

    fun increase() {
        currentTick++
    }

    private fun msSinceStart(): Long {
        val timeOfDay = ZonedDateTime.now(ZoneOffset.UTC)
        return Duration.between(firstFarmTickDate, timeOfDay).toMillis()
    }

    private fun seedTypes(tick: Long) = SeedType.values().filter { tick % it.growthFrequency == 0L }
}
