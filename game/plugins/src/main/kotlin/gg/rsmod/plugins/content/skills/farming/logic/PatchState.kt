package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.farmingManager
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.FlowerProtection
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import mu.KLogging

/**
 * Stores all relevant data related to a patch. This should be the only single class that handles mutations related to a patch
 */
class PatchState(
    val patch: Patch,
    private val player: Player,
) {
    private val mainVarbit = VarbitUpdater(patch.varbit, player)
    private val protectedVarbit = VarbitUpdater(patch.id + if (patch.id < 10000) 20000 else 200000, player)
    private val compostVarbit = VarbitUpdater(patch.id + if (patch.id < 10000) 30000 else 300000, player)
    private val livesVarbit = VarbitUpdater(patch.id + if (patch.id < 10000) 40000 else 400000, player)

    private val weeds get() = if (mainVarbit.value in weedVarbits) maxWeeds - mainVarbit.value else 0

    val seed get() = findSeed()
    val growthStage get() = seed?.growthStage(mainVarbit.value)
    val isDiseased get() = seed?.diseasedVarbits?.let { mainVarbit.value in it } ?: false
    val isDead get() = seed?.diedVarbits?.let { mainVarbit.value in it } ?: false
    val compostState get() = CompostState.fromVarbit(compostVarbit.value) ?: CompostState.None
    val isProtectedThroughPayment get() = protectedVarbit.value == 1
    val isWatered get() = seed?.wateredVarbits?.let { mainVarbit.value in it } ?: false
    val livesLeft get() = if (seed?.seedType?.harvest?.livesReplenish == true) produceAvailable else livesVarbit.value
    val isWeedy get() = weeds > 0
    val isWeedsFullyGrown get() = weeds == maxWeeds
    val isEmpty get() = mainVarbit.value == emptyPatchVarbit
    val isPlantFullyGrown get() =
        seed?.let {
            if (it.seedType.harvest.livesReplenish) {
                mainVarbit.value in
                    (
                        it.harvestableVarbits + it.producingVarbits + it.harvest.choppedDownVarbit +
                            it.harvest.clearableVarbit +
                            it.harvest.healthCheckVarbit +
                            it.harvest.choppableVarbit
                    ).mapNotNull { it }.toSet()
            } else {
                it.growth.growthStages == growthStage!!
            }
        } ?: false
    val isFullyGrown get() = isWeedsFullyGrown || isPlantFullyGrown
    val isProtected get() = isProtectedThroughPayment || isProtectedByFlower
    val healthCanBeChecked get() = mainVarbit.value == seed?.harvest?.healthCheckVarbit
    val isProducing get() = seed?.producingVarbits?.let { mainVarbit.value in it } ?: false
    val produceAvailable get() = seed?.producingLivesVarbits?.indexOf(mainVarbit.value) ?: 0
    val canBeChopped get() = mainVarbit.value == seed?.harvest?.choppableVarbit
    val isChoppedDown get() = mainVarbit.value == seed?.harvest?.choppedDownVarbit
    val isProtectedByFlower get() =
        seed?.let { s ->
            val patch = FlowerProtection.allotmentLinks[patch] ?: return@let null
            val flowerManager = player.farmingManager().getPatchManager(patch)
            val flower = flowerManager.state.seed ?: return@let null
            flowerManager.state.isPlantFullyGrown && s in (FlowerProtection.protections[flower] ?: listOf())
        } ?: false

    fun removeWeed() {
        if (isWeedy) {
            mainVarbit.increaseByOne()
        } else {
            logInvalidOperation("remove weed")
        }
    }

    fun addWeed() {
        if (mainVarbit.value in 1..3) {
            mainVarbit.decreaseByOne()
        } else {
            logInvalidOperation("add weed")
        }
    }

    fun plantSeed(plantedSeed: Seed) {
        if (isEmpty && plantedSeed.seedType in patch.seedTypes) {
            mainVarbit.set(plantedSeed.plant.plantedVarbit)
            if (seed!!.seedType.harvest.fixedLives) {
                setLives(seed!!.plant.baseLives)
            } else {
                setLives(seed!!.plant.baseLives + compostState.lives)
            }
        } else {
            logInvalidOperation("plant seed")
        }
    }

    fun compost(type: CompostState) {
        if (compostState == CompostState.None || type == CompostState.None) {
            compostVarbit.set(type.varbitValue)
        } else {
            logInvalidOperation("compost")
        }
    }

    fun growSeed() {
        seed?.let {
            val wateredVarbits = it.wateredVarbits ?: listOf()
            if (mainVarbit.value in it.growableVarbits || mainVarbit.value in wateredVarbits) {
                val nextIndex =
                    (
                        it.growableVarbits.indexOf(mainVarbit.value).takeUnless { it == -1 }
                            ?: wateredVarbits.indexOf(mainVarbit.value)
                    ) + 1

                if (nextIndex == it.growableVarbits.size) {
                    mainVarbit.set(it.harvest.healthCheckVarbit ?: it.harvest.fullLivesHarvestableVarbit)
                } else {
                    mainVarbit.set(it.growableVarbits[nextIndex])
                }
            } else {
                logInvalidOperation("grow")
            }
        } ?: logInvalidOperation("grow null seed")
    }

    fun water() {
        seed?.let {
            if (it.wateredVarbits != null && mainVarbit.value in it.growableVarbits) {
                val index = it.growableVarbits.indexOf(mainVarbit.value)
                mainVarbit.set(it.wateredVarbits[index])
            } else {
                logInvalidOperation("water")
            }
        } ?: logInvalidOperation("water null seed")
    }

    fun protect() {
        protectedVarbit.set(1)
    }

    fun removeProtection() {
        protectedVarbit.set(0)
    }

    fun disease() {
        seed?.let {
            val wateredVarbits = it.wateredVarbits ?: listOf()
            if (mainVarbit.value in it.growableVarbits || mainVarbit.value in wateredVarbits) {
                val index =
                    it.growableVarbits.indexOf(mainVarbit.value).takeUnless { it == -1 }
                        ?: wateredVarbits.indexOf(mainVarbit.value)

                mainVarbit.set(it.diseasedVarbits[index])
            } else {
                logInvalidOperation("disease")
            }
        } ?: logInvalidOperation("disease null seed")
    }

    fun cure() {
        seed?.let {
            if (mainVarbit.value in it.diseasedVarbits) {
                val index = it.diseasedVarbits.indexOf(mainVarbit.value)
                mainVarbit.set(it.growableVarbits[index])
            } else {
                logInvalidOperation("cure")
            }
        } ?: logInvalidOperation("cure null seed")
    }

    fun die() {
        seed?.let {
            if (mainVarbit.value in it.diseasedVarbits) {
                val index = it.diseasedVarbits.indexOf(mainVarbit.value)
                mainVarbit.set(it.diedVarbits[index])
            } else {
                logInvalidOperation("die")
            }
        } ?: logInvalidOperation("die null seed")
    }

    fun chopDown() {
        seed?.let {
            if (mainVarbit.value == it.harvest.choppableVarbit) {
                mainVarbit.set(it.harvest.choppedDownVarbit!!)
            } else {
                logInvalidOperation("chop down")
            }
        } ?: logInvalidOperation("chop down null seed")
    }

    fun regrowChoppedDownCrop() {
        seed?.let {
            if (mainVarbit.value == it.harvest.choppedDownVarbit) {
                mainVarbit.set(it.harvest.choppableVarbit!!)
            } else {
                logInvalidOperation("regrow")
            }
        } ?: logInvalidOperation("regrow null seed")
    }

    fun removeLife() {
        if (mainVarbit.value in (seed?.harvestableVarbits ?: listOf())) {
            updateLifes(-1)
            if (seed!!.seedType.harvest.livesReplenish) {
                mainVarbit.decreaseByOne()
            }
        } else {
            logInvalidOperation("remove life")
        }
    }

    fun addLife() {
        updateLifes(1)
        if (seed!!.seedType.harvest.livesReplenish) {
            mainVarbit.increaseByOne()
        }
    }

    fun checkHealth() {
        if (mainVarbit.value == seed?.harvest?.healthCheckVarbit) {
            mainVarbit.set(seed!!.harvest.fullLivesHarvestableVarbit)
        }
    }

    private fun updateLifes(delta: Int) = setLives(livesLeft + delta)

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

    private fun logInvalidOperation(operation: String) {
        logger.error(
            "Invalid operation called for player '${player.username}' on patch ${patch.id} with varbit ${mainVarbit.value}: $operation",
        )
    }

    override fun toString(): String {
        return "seed: $seed; growthStage: $growthStage; isDiseased: $isDiseased; isDead: $isDead; compostState: $compostState; isProtectedThroughPayment: $isProtectedThroughPayment; isWatered: $isWatered; livesLeft: $livesLeft; isWeedy: $isWeedy; isWeedsFullyGrown: $isWeedsFullyGrown; isEmpty: $isEmpty; isPlantFullyGrown: $isPlantFullyGrown; isFullyGrown: $isFullyGrown; isProtected: $isProtected; "
    }

    companion object : KLogging() {
        private const val maxWeeds = 3
        private const val emptyPatchVarbit = 3

        private val weedVarbits = 0..2
    }
}
