package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.plugins.content.skills.farming.data.Seed

sealed class SeedState(val compostState: CompostState) {
    fun seed() = if (this is Planted) seed else null
}
sealed interface HarvestState {
    val isProducing: Boolean
    val livesLeft: Int
}
sealed interface Planted {
    val seed: Seed
}

sealed interface Clearable

class Weedy(val weeds: Int, compostState: CompostState) : SeedState(compostState) {
    val weedsFullyGrown = weeds == 3
}
class Empty(compostState: CompostState) : SeedState(compostState)
class Growing(val isProtected: Boolean, compostState: CompostState, override val seed: Seed) : SeedState(compostState), Planted
class Watered(val isProtected: Boolean, compostState: CompostState, override val seed: Seed) : SeedState(compostState), Planted
class Diseased(override val seed: Seed, compostState: CompostState) : SeedState(compostState), Planted
class Dead(override val seed: Seed, compostState: CompostState) : SeedState(compostState), Planted, Clearable
class Harvestable(compostState: CompostState, override val isProducing: Boolean, override val livesLeft: Int, override val seed: Seed) : SeedState(compostState), HarvestState, Planted
class Choppable(compostState: CompostState, override val isProducing: Boolean, override val livesLeft: Int, override val seed: Seed) : SeedState(compostState), HarvestState, Planted
class FullyHarvested(compostState: CompostState, val isChoppedDown: Boolean, override val isProducing: Boolean, override val livesLeft: Int, override val seed: Seed) : SeedState(compostState), HarvestState, Planted, Clearable
class HealthCheckable(compostState: CompostState, override val seed: Seed) : SeedState(compostState), Planted
object FarmingError : SeedState(CompostState.None)
