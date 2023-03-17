package gg.rsmod.plugins.content.skills.farming.logic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.patchHandler.WeedsHandler

object HarvestManager {
    suspend fun rakeWeeds(task: QueueTask, player: Player, patch: Patch) {
        WeedsHandler(patch, player).rake(task)
    }
}
