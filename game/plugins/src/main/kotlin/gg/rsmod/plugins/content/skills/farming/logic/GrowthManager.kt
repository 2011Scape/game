package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.patchHandler.WeedsHandler

object GrowthManager {
    fun growWeeds(player: Player) {
        for (patch in Patch.values()) {
            WeedsHandler(patch, player).grow()
        }
    }

    fun growSeeds(player: Player, seedTypesToGrow: List<SeedType>) {
        player.message("Seed growing not yet implemented")
    }

    fun everythingFullyGrown(player: Player): Boolean {
        return false
    }
}
