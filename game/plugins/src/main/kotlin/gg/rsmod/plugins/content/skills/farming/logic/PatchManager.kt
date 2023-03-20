package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.*

class PatchManager(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private val state = PatchState(patch, player)
    private val weedsHandler = WeedsHandler(state, patch, player)
    private val plantingHandler = PlantingHandler(state, patch, player)
    private val growingHandler = GrowingHandler(state, patch, player)
    private val compostHandler = CompostHandler(state, patch, player)
    private val waterHandler = WaterHandler(state, patch, player)
    private val cureHandler = CureHandler(state, patch, player)

    val fullyGrown get() = state.isFullyGrown

    fun grow(seedTypesToGrow: Set<SeedType>) {
        weedsHandler.growWeeds()

        if (patch.seedTypes.intersect(seedTypesToGrow).any()) {
            growingHandler.grow()
        }
    }

    fun rake() {
        weedsHandler.rake()
    }

    fun plant(seed: Seed) {
        plantingHandler.plant(seed)
    }

    fun addCompost(compost: CompostState) {
        compostHandler.addCompost(compost)
    }

    fun water(inventorySlot: Int) {
        waterHandler.water(inventorySlot)
    }

    fun cure(inventorySlot: Int) {
        cureHandler.cure(inventorySlot)
    }
}
