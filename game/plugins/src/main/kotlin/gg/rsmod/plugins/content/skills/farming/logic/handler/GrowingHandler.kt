package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to growing a patch. Called on each player farming tick, as long as the seed type
 * is part of the related world farming tick
 */
class GrowingHandler(
    private val state: PatchState,
    private val player: Player,
) {
    fun grow() {
        when {
            state.seed == null -> Unit
            state.isDead -> Unit
            state.isPlantFullyGrown -> Unit
            state.isDiseased -> state.die()
            immuneToDisease() -> state.growSeed()
            rollForDisease() -> state.disease()
            else -> state.growSeed()
        }
    }

    fun replenishProduce() {
        if (state.isProducing && state.livesLeft < state.seed!!.plant.baseLives) {
            state.addLife()
        }
    }

    private fun immuneToDisease(): Boolean {
        if (state.isProtected) {
            return true
        }

        if (!state.seed!!
                .seedType.growth.canDiseaseOnFirstStage &&
            state.growthStage == 0
        ) {
            return true
        }

        if (state.isProtectedByFlower) {
            return true
        }

        return !state.seed!!.growth.canDisease
    }

    private fun rollForDisease(): Boolean {
        val waterFactor = if (state.isWatered) waterDiseaseFactor else 1.0
        val slots =
            (state.seed!!.growth.diseaseSlots * state.compostState.diseaseChanceFactor * waterFactor)
                .toInt()
                .coerceAtLeast(
                    1,
                )
        return player.world.chance(slots, diseaseSlots)
    }

    companion object {
        private const val diseaseSlots = 128
        private const val waterDiseaseFactor = 0.9
    }
}
