package gg.rsmod.plugins.content.skills.farming.logic.patchHandler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch

class WeedsHandler(patch: Patch, player: Player): PatchVarbitUpdater(patch, player) {

    private val canGrowWeeds: Boolean get() = varbitValue in growableVarbits

    private val patchHasWeeds get() = varbitValue in weedVarbits

    val patchIsFullyGrown get() = varbitValue == fullyGrownWeedsVarbit

    fun growWeeds() {
        if (canGrowWeeds) {
            decreaseVarbitByOne()
        }
    }

    suspend fun rake(task: QueueTask) {
        while (canRake) {
            player.animate(rakingAnimation)
            task.wait(rakingWaitTime)

            // Another check whether raking is possible - something might have changed in the past few ticks
            if (canRake) {
                if (rollRakingSuccess()) {
                    increaseVarbitByOne()
                    player.inventory.add(Items.WEEDS)
                    player.addXp(Skills.FARMING, rakingXp)
                    if (!patchHasWeeds) {
                        break
                    }
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

    private fun rollRakingSuccess(): Boolean {
        val farmingLevel = player.getSkills().getCurrentLevel(Skills.FARMING)
        return farmingLevel.interpolate(minChance = 64, maxChance = 512, minLvl = 1, maxLvl = 99, cap = 256)
    }

    companion object {
        private const val rakingAnimation = 2273
        private const val rakingWaitTime = 4
        private const val rakingXp = 4.0
        private const val fullyGrownWeedsVarbit = 0

        private val growableVarbits = 1..3
        private val weedVarbits = 0..2
    }
}
