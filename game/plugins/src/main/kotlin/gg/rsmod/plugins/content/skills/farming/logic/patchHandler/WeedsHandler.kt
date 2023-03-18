package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.data.Seed

class WeedsHandler(patch: Patch, player: Player): PatchHandler(patch, player) {

    private val canGrowWeeds: Boolean get() = varbitValue in growableVarbits

    override fun grow() {
        if (canGrowWeeds) {
            decreaseVarbitByOne()
        }
    }

    override suspend fun harvest(task: QueueTask) {
        while (canRake) {
            player.animate(rakingAnimation)
            task.wait(rakingWaitTime)

            // Another check whether raking is possible - something might have changed in the past few ticks
            if (canRake) {
                increaseVarbitByOne()
                player.inventory.add(Items.WEEDS)
                player.addXp(Skills.FARMING, rakingXp)
                if (!patchHasWeeds) {
                    break
                }
            }
        }
        player.animate(-1)
    }

    private val canRake: Boolean get() {
        if (!patchHasWeeds) {
            player.message("The ${patch.patchName} doesn't need weeding right now.")
            return false
        }

        if (player.inventory.isFull) {
            player.message("You don't have enough inventory space.")
            return false
        }

        if (!player.inventory.contains(Items.RAKE)) {
            player.message("You need a rake to do that.")
            return false
        }

        return true
    }

    companion object {
        private const val rakingAnimation = 2273
        private const val rakingWaitTime = 4
        private const val rakingXp = 4.0

        private val growableVarbits = 1..3
    }
}
