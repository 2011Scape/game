package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class GrowingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun grow() {
        when {
            state.seed == null -> Unit
            state.isDead -> Unit
            state.isPlantFullyGrown -> Unit
            state.isDiseased -> state.die()
            state.growthStage == 0 -> state.growSeed()
            immuneToDisease() -> state.growSeed()
            rollForDisease() -> state.disease()
            else -> state.growSeed()
        }
    }

    private fun immuneToDisease(): Boolean {
        if (state.seed!!.growth.canDisease) {
            return false
        }

        if (!state.seed!!.seedType.growth.canDiseaseOnFirstStage && state.growthStage == 0) {
            return true
        }

        return state.isProtected
    }

    private fun rollForDisease(): Boolean {
        val waterFactor = if (state.isWatered) waterDiseaseFactor else 1.0
        val slots = (state.seed!!.growth.diseaseSlots * state.compostState.diseaseChanceFactor * waterFactor).toInt().coerceAtLeast(1)
        return player.world.chance(slots, diseaseSlots)
    }

    companion object {
        private const val diseaseSlots = 128
        private const val waterDiseaseFactor = 0.9
    }
}
