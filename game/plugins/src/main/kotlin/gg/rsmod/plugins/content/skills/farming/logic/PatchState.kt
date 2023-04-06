package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed

/**
 * Stores all relevant data related to a patch. This should be the only single class that handles mutations related to a patch
 */
class PatchState(private val patch: Patch, private val player: Player) {

    private val mainVarbit = VarbitUpdater(patch.varbit, player)
    private val protectedVarbit = VarbitUpdater(patch.id + 20000, player)
    private val compostVarbit = VarbitUpdater(patch.id + 30000, player)
    private val livesVarbit = VarbitUpdater(patch.id + 40000, player)

    private val weeds get() = if (mainVarbit.value in weedVarbits) 3 - mainVarbit.value else 0

    val seed get() = findSeed()
    val growthStage get() = seed?.growthStage(mainVarbit.value)
    val isDiseased get() = seed?.isDiseased(mainVarbit.value) ?: false
    val isDead get() = seed?.isDead(mainVarbit.value) ?: false
    val compostState get() = CompostState.fromVarbit(compostVarbit.value)
    val isProtectedThroughPayment get() = protectedVarbit.value == 1
    val isWatered get() = seed?.isWatered(mainVarbit.value) ?: false
    val livesLeft get() = livesVarbit.value
    val isWeedy get() = weeds > 0
    val isWeedsFullyGrown get() = weeds == maxWeeds
    val isEmpty get() = mainVarbit.value == emptyPatchVarbit
    val isPlantFullyGrown get() = seed?.let {
        if (it.seedType.harvest.livesReplenish) {
            it.growth.growthStages == growthStage!! || it.isProducing(mainVarbit.value)
        } else {
            it.growth.growthStages == growthStage!!
        }
    } ?: false
    val isFullyGrown get() = isWeedsFullyGrown || isPlantFullyGrown
    val isProtected get() = isProtectedThroughPayment // TODO: flowers protecting allotments
    val healthCanBeChecked get() = seed?.let { it.harvest.healthCheckXp != null && it.isAtHealthCheck(mainVarbit.value) } ?: false
    val isProducing get() = seed?.isProducing(mainVarbit.value) ?: false
    val canBeChopped get() = isPlantFullyGrown && seed!!.harvest.choppedDownVarbit != null && livesLeft == 0
    val isChoppedDown get() = seed != null && seed!!.harvest.choppedDownVarbit == mainVarbit.value

    fun removeWeed() {
        mainVarbit.increaseByOne()
    }

    fun addWeed() {
        mainVarbit.decreaseByOne()
    }

    fun plantSeed(plantedSeed: Seed) {
        mainVarbit.set(plantedSeed.plant.plantedVarbit)
        updateLives(seed!!.plant.baseLives)
    }

    fun compost(type: CompostState) {
        compostVarbit.set(type.varbitValue)
        updateLives(type.lives)
    }

    fun growSeed() {
        if (seed!!.seedType.harvest.livesReplenish && growthStage == seed!!.growth.growthStages - 1) {
            mainVarbit.set(seed!!.harvest.healthCheckVarbit!!)
        } else {
            mainVarbit.set(seed!!.plant.plantedVarbit + growthStage!! + 1)
        }
    }

    fun water() {
        mainVarbit.set(seed!!.growth.waterVarbit!! + growthStage!!)
    }

    fun protect() {
        protectedVarbit.set(1)
    }

    fun removeProtection() {
        protectedVarbit.set(0)
    }

    fun disease() {
        mainVarbit.set(seed!!.growth.diseaseVarbit + growthStage!!)
    }

    fun cure() {
        mainVarbit.set(seed!!.plant.plantedVarbit + growthStage!!)
    }

    fun die() {
        mainVarbit.set(seed!!.growth.diedVarbit + growthStage!!)
    }

    fun chopDown() {
        mainVarbit.set(seed!!.harvest.choppedDownVarbit!!)
    }

    fun removeLive() {
        updateLives(-1)
        if (seed!!.seedType.harvest.livesReplenish) {
            mainVarbit.decreaseByOne()
        }
    }

    fun addLive() {
        updateLives(1)
        if (seed!!.seedType.harvest.livesReplenish) {
            mainVarbit.increaseByOne()
        }
    }

    fun checkHealth() {
        mainVarbit.set(seed!!.plant.plantedVarbit + seed!!.growth.growthStages + seed!!.plant.baseLives)
    }

    private fun updateLives(delta: Int) = setLives(livesLeft + delta)

    fun setLives(lives: Int) {
        livesVarbit.set(lives)
    }

    private fun removeAllLives() {
        setLives(0)
    }

    fun clear() {
        mainVarbit.set(emptyPatchVarbit)
        compost(CompostState.None)
        removeProtection()
        removeAllLives()
    }

    private fun findSeed(): Seed? {
        if (isWeedy || isEmpty) {
            return null
        }

        val varbit = mainVarbit.value
        return Seed.values().firstOrNull { it.isPlanted(patch, varbit) }
    }

    override fun toString(): String {
        return "seed: $seed; growthStage: $growthStage; isDiseased: $isDiseased; isDead: $isDead; compostState: $compostState; isProtectedThroughPayment: $isProtectedThroughPayment; isWatered: $isWatered; livesLeft: $livesLeft; isWeedy: $isWeedy; isWeedsFullyGrown: $isWeedsFullyGrown; isEmpty: $isEmpty; isPlantFullyGrown: $isPlantFullyGrown; isFullyGrown: $isFullyGrown; isProtected: $isProtected; "
    }

    companion object {
        private const val maxWeeds = 3
        private const val emptyPatchVarbit = 3

        private val weedVarbits = 0..2
    }
}