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

    val isWeedy get() = weeds > 0
    val isWeedsFullyGrown get() = weeds == maxWeeds
    val isEmpty get() = weeds == 0 && seed == null
    val isPlantFullyGrown get() = seed?.let { it.growthStages == growthStage!! } ?: false
    val isFullyGrown get() = isEmpty || isWeedsFullyGrown || isPlantFullyGrown
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
        setVarbit(plantedSeed.plantedVarbitValue)
        seed = plantedSeed
        growthStage = 0

    }

    fun compost(type: CompostState) {
        compostState = type
        player.attr[COMPOST_ON_PATCHES]!![patch.persistenceId] = type.persistenceId
    }

    fun growSeed() {
        increaseVarbitByOne()
        growthStage = growthStage!! + 1

        // TODO: remove watered state
    }

    fun water() {
        isWatered = true
        setVarbit(seed!!.wateredVarbitValue!! + growthStage!!)
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
        setVarbit(seed!!.diseasedVarbitValue + growthStage!! - 1)
        isDiseased = true
    }

    fun cure() {
        setVarbit(seed!!.plantedVarbitValue + growthStage!!)
        isDiseased = false
    }

    fun die() {
        setVarbit(seed!!.diedVarbitValue + growthStage!! - 1)
        isDead = true
        isDiseased = false
    }

    fun removeLive() {
        livesLeft--
        player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId] = (player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId]!!.toInt() - 1).toString()
    }

    fun removeAllLives() {
        livesLeft = 0
        player.attr[PATCH_LIVES_LEFT]!![patch.persistenceId] = "0"
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