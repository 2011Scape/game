package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.handler.PlantingHandler
import gg.rsmod.plugins.content.skills.farming.logic.handler.WeedsHandler

class PatchManager(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private val state = PatchState(patch, player)
    private val weedsHandler = WeedsHandler(state, patch, player)
    private val plantingHandler = PlantingHandler(state, patch, player)

    val fullyGrown get() = state.isFullyGrown

    fun grow(seedTypesToGrow: Set<SeedType>) {
        weedsHandler.growWeeds()

        if (patch.seedTypes.intersect(seedTypesToGrow).any()) {
            TODO()
        }
    }

    fun rake() {
        weedsHandler.rake()
    }

    fun plant(seed: Seed) {
        plantingHandler.plant(seed)
    }
}
