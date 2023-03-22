package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.*
import mu.KLogging

/**
 * Manager class for all farming-related logic, tied to a specific player and patch
 */
class PatchManager(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private val state = PatchState(patch, player)
    private val rakeHandler = RakeHandler(state, patch, player)
    private val plantingHandler = PlantingHandler(state, patch, player)
    private val growingHandler = GrowingHandler(state, player)
    private val compostHandler = CompostHandler(state, patch, player)
    private val waterHandler = WaterHandler(state, patch, player)
    private val cureHandler = CureHandler(state, patch, player)
    private val harvestHandler = HarvestingHandler(state, patch, player)
    private val clearHandler = ClearHandler(state, player)

    val fullyGrown get() = state.isFullyGrown

    fun grow(seedTypesToGrow: Set<SeedType>) {
        rakeHandler.growWeeds()

        if (patch.seedTypes.intersect(seedTypesToGrow).any()) {
            growingHandler.grow()
        }
    }

    fun rake() {
        rakeHandler.rake()
    }

    fun plant(seed: Seed) {
        plantingHandler.plant(seed)
    }

    fun addCompost(compost: CompostState) {
        compostHandler.addCompost(compost)
    }

    fun water(wateringCan: Int) {
        waterHandler.water(wateringCan)
    }

    fun cure() {
        cureHandler.cure()
    }

    fun harvest() {
        harvestHandler.harvest()
    }

    fun clear() {
        clearHandler.clear()
    }

    companion object : KLogging()
}
