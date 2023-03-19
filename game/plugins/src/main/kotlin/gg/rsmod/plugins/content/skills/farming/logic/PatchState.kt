package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.attr.COMPOST_ON_PATCHES
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.logic.handler.WeedsHandler

class PatchState(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    var seed: Seed?
        private set

    var growthStage: Int?
        private set

    var diseased: Boolean
        private set

    var died: Boolean
        private set

    var compostState: CompostState
        private set

    init {
        compostState = player.attr[COMPOST_ON_PATCHES]!![patch.id]!!.let(CompostState::fromPersistenceId)
        seed = findSeed()
        growthStage = seed?.growthStage(varbitValue)
        diseased = seed?.isDiseased(varbitValue) ?: false
        died = seed?.isDead(varbitValue) ?: false
    }

    val isWeedy get() = varbitValue in weedVarbits
    val isWeedsFullyGrown get() = varbitValue == fullyGrownWeedsVarbit

    val isEmpty get() = varbitValue == emptyVarbitValue

    val isPlantFullyGrown get() = seed?.isFullyGrown(varbitValue) ?: false

    val isFullyGrown get() = isEmpty || isWeedsFullyGrown || isPlantFullyGrown

    fun removeWeed() {
        increaseVarbitByOne()
    }

    fun addWeed() {
        decreaseVarbitByOne()
    }

    fun plantSeed(seed: Seed) {
        setVarbit(seed.plantedVarbitValue)
    }

    private fun findSeed(): Seed? {
        if (isWeedy || isEmpty) {
            return null
        }

        val varbit = varbitValue
        return Seed.values().firstOrNull { it.isPlanted(patch, varbit) }
    }

    companion object {
        private const val emptyVarbitValue = 3

        private val weedVarbits = 0..2
        private const val fullyGrownWeedsVarbit = 0
    }
}