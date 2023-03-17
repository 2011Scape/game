package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.farming.data.Patch

class WeedsHandler(patch: Patch, player: Player): PatchHandler(patch, player) {

    private val hasWeeds: Boolean get() = varbitValue in rakeableVarbits

    private val canGrowWeeds: Boolean get() = varbitValue in growableVarbits

    fun grow() {
        if (canGrowWeeds) {
            decreaseVarbitByOne()
        }
    }

    suspend fun rake(task: QueueTask) {
        while (canRake) {
            player.animate(rakingAnimation)
            task.wait(rakingWaitTime)
            increaseVarbitByOne()
            player.addXp(Skills.FARMING, rakingXp)
            if (player.inventory.add(Items.WEEDS).hasFailed()) {
                player.world.spawn(GroundItem(Item(Items.WEEDS), player.tile, player))
            }
        }
        player.animate(-1)
    }

    private val canRake: Boolean get() {
        if (!hasWeeds) {
            return false
        }

        if (!player.inventory.contains(Items.RAKE)) {
            // TODO: player message
            return false
        }

        return true
    }

    companion object {
        private const val rakingAnimation = 2273
        private const val rakingWaitTime = 4
        private const val rakingXp = 4.0

        private val rakeableVarbits = 0..2
        private val growableVarbits = 1..3
    }
}
