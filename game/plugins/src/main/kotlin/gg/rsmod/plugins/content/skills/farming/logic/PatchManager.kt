package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.*
import mu.KLogging

class PatchManager(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private val state = PatchState(patch, player)
    private val weedsHandler = WeedsHandler(state, patch, player)
    private val plantingHandler = PlantingHandler(state, patch, player)
    private val growingHandler = GrowingHandler(state, patch, player)
    private val compostHandler = CompostHandler(state, patch, player)
    private val waterHandler = WaterHandler(state, patch, player)
    private val cureHandler = CureHandler(state, patch, player)
    private val harvestHandler = HarvestingHandler(state, patch, player)

    val fullyGrown get() = state.isFullyGrown

    fun grow(seedTypesToGrow: Set<SeedType>) {
        logger.warn("Before growing weeds: $state")
        weedsHandler.growWeeds()
        logger.warn("After growing weeds: $state")

        if (patch.seedTypes.intersect(seedTypesToGrow).any()) {
            logger.warn("Before growing seeds: $state")
            growingHandler.grow()
            logger.warn("After growing seeds: $state")
        }
    }

    fun rake() {
        logger.warn("Before raking: $state")
        weedsHandler.rake()
        logger.warn("After raking: $state")
    }

    fun plant(seed: Seed) {
        logger.warn("Before planting: $state")
        plantingHandler.plant(seed)
        logger.warn("After planting: $state")
    }

    fun addCompost(compost: CompostState) {
        logger.warn("Before composting: $state")
        compostHandler.addCompost(compost)
        logger.warn("After composting: $state")
    }

    fun water(wateringCan: Int) {
        logger.warn("Before watering: $state")
        waterHandler.water(wateringCan)
        logger.warn("After watering: $state")
    }

    fun cure() {
        logger.warn("Before curing: $state")
        cureHandler.cure()
        logger.warn("After curing: $state")
    }

    fun harvest() {
        logger.warn("Before harvesting: $state")
        harvestHandler.harvest()
        logger.warn("After harvesting: $state")
    }

    companion object : KLogging()
}
