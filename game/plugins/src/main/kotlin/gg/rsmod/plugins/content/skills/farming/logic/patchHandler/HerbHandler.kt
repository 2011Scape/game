package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.skills.farming.data.Patch

class HerbHandler(patch: Patch, player: Player): PatchHandler(patch, player) {

    override fun grow() {
        TODO()
    }

    override suspend fun harvest(task: QueueTask) {
        TODO()
    }
}
