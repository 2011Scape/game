package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class GrowingHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun grow() {
        when {
            state.seed == null -> Unit
            state.isPlantFullyGrown -> Unit
            state.isDiseased -> state.die()
            state.growthStage == 0 -> state.growSeed()
            !canDisease() -> state.growSeed()
            rollForDisease() -> state.disease()
            else -> state.growSeed()
        }
    }

    private fun canDisease() = state.seed!!.canDisease || !state.isProtected

    private fun rollForDisease(): Boolean {
        val waterFactor = if (state.seed!!.seedType.canBeWatered && state.isWatered) waterDiseaseFactor else 1.0
        val slots = (state.seed!!.diseaseSlots * state.compostState.diseaseChanceFactor * waterFactor).toInt().coerceAtLeast(1)
        return player.world.chance(slots, diseaseSlots)
    }

    companion object {
        private const val diseaseSlots = 128
        private const val waterDiseaseFactor = 0.85
    }
}
