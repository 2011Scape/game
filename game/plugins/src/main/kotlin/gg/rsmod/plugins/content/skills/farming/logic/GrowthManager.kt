package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.SeedType

object GrowthManager {
    fun growWeeds(player: Player) {
        for (patch in Patch.values()) {
            WeedsHandler.grow(player, patch)
        }
    }

    fun growSeeds(player: Player, seedTypesToGrow: List<SeedType>) {
        player.message("Seed growing not yet implemented")
    }

    fun everythingFullyGrown(player: Player): Boolean {
        return false
    }
}
