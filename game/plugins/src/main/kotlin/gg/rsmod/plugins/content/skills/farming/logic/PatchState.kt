package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.attr.COMPOST_ON_PATCHES
import gg.rsmod.game.model.attr.PATCH_LIVES_LEFT
import gg.rsmod.game.model.attr.PROTECTED_PATCHES
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed

class PatchState(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private var weeds = if (varbitValue in weedVarbits) 3 - varbitValue else 0

    var seed: Seed?; private set
    var growthStage: Int?; private set
    var isDiseased: Boolean; private set
    var isDead: Boolean; private set
    var compostState: CompostState; private set
    var isProtectedThroughPayment: Boolean; private set
    var isWatered: Boolean; private set
    var livesLeft: Int; private set

    init {
        compostState = player.attr[COMPOST_ON_PATCHES]!![patch.persistenceId]!!.let(CompostState::fromPersistenceId)
        isProtectedThroughPayment = patch.persistenceId in player.attr[PROTECTED_PATCHES]!!
        seed = findSeed()
        growthStage = seed?.growthStage(varbitValue)
        isDiseased = seed?.isDiseased(varbitValue) ?: false
        isDead = seed?.isDead(varbitValue) ?: false
        isWatered = seed?.isWatered(varbitValue) ?: false
        livesLeft = player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId]!!.toInt()
    }

    override fun toString(): String {
        return "seed: $seed; growthStage: $growthStage; isDiseased: $isDiseased; isDead: $isDead; compostState: $compostState; isProtectedThroughPayment: $isProtectedThroughPayment; isWatered: $isWatered; livesLeft: $livesLeft; isWeedy: $isWeedy; isWeedsFullyGrown: $isWeedsFullyGrown; isEmpty: $isEmpty; isPlantFullyGrown: $isPlantFullyGrown; isFullyGrown: $isFullyGrown; isProtected: $isProtected; "
    }

    val isWeedy get() = weeds > 0
    val isWeedsFullyGrown get() = weeds == maxWeeds
    val isEmpty get() = varbitValue == emptyPatchVarbit
    val isPlantFullyGrown get() = seed?.let { it.growth.growthStages == growthStage!! } ?: false
    val isFullyGrown get() = isWeedsFullyGrown || isPlantFullyGrown
    val isProtected get() = isProtectedThroughPayment // TODO: flowers protecting allotments

    fun removeWeed() {
        increaseVarbitByOne()
        weeds--
    }

    fun addWeed() {
        decreaseVarbitByOne()
        weeds++
    }

    fun plantSeed(plantedSeed: Seed) {
        setVarbit(plantedSeed.plant.plantedVarbit)
        seed = plantedSeed
        growthStage = 0
        updateLives(seed!!.plant.baseLives)
    }

    fun compost(type: CompostState) {
        compostState = type
        player.attr[COMPOST_ON_PATCHES]!![patch.persistenceId] = type.persistenceId
        updateLives(type.lives)
    }

    fun growSeed() {
        growthStage = growthStage!! + 1
        setVarbit(seed!!.plant.plantedVarbit + growthStage!!)
        isWatered = false
    }

    fun water() {
        setVarbit(seed!!.growth.waterVarbit!! + growthStage!!)
        isWatered = true
    }

    fun protect() {
        player.attr[PROTECTED_PATCHES]!!.add(patch.persistenceId)
        isProtectedThroughPayment = true
    }

    fun removeProtection() {
        player.attr[PROTECTED_PATCHES]!!.remove(patch.persistenceId)
        isProtectedThroughPayment = false
    }

    fun disease() {
        setVarbit(seed!!.growth.diseaseVarbit + growthStage!!)
        isDiseased = true
    }

    fun cure() {
        setVarbit(seed!!.plant.plantedVarbit + growthStage!!)
        isDiseased = false
    }

    fun die() {
        setVarbit(seed!!.growth.diedVarbit + growthStage!!)
        isDead = true
        isDiseased = false
    }

    fun removeLive() {
        updateLives(-1)
    }

    private fun updateLives(delta: Int) {
        livesLeft += delta
        player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId] = livesLeft.toString()
    }

    private fun setLives(lives: Int) {
        livesLeft = lives
        player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId] = livesLeft.toString()
    }

    private fun removeAllLives() {
        setLives(0)
    }

    fun clear() {
        setVarbit(emptyPatchVarbit)
        compost(CompostState.None)
        removeProtection()
        removeAllLives()
        isDead = false
        isDiseased = false
        seed = null
        growthStage = null
        isWatered = false
    }

    private fun findSeed(): Seed? {
        if (isWeedy || isEmpty) {
            return null
        }

        val varbit = varbitValue
        return Seed.values().firstOrNull { it.isPlanted(patch, varbit) }
    }

    companion object {
        private const val maxWeeds = 3
        private const val emptyPatchVarbit = 3

        private val weedVarbits = 0..2
    }
}