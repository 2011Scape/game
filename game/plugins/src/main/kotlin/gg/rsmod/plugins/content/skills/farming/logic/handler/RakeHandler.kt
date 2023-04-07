package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to raking a patch that contains weeds
 */
class RakeHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    private fun canGrowWeeds() = state.seed == null && !state.isWeedsFullyGrown

    fun growWeeds() {
        if (canGrowWeeds()) {
            state.addWeed()
        }
    }

    fun rake() {
        player.queue {
            while (canRake) {
                player.animate(rakingAnimation)
                player.playSound(rakingSound)
                farmingTimerDelayer.delayIfNeeded(rakingWaitTime)
                wait(rakingWaitTime)

                // Another check whether raking is possible - something might have changed in the past few ticks
                if (canRake) {
                    if (rollRakingSuccess()) {
                        state.removeWeed()
                        player.inventory.add(Items.WEEDS)
                        player.addXp(Skills.FARMING, rakingXp)
                        if (!state.isWeedy) {
                            break
                        }
                    }
                }
            }
            player.animate(-1)
        }
    }

    private val canRake: Boolean get() {
        if (!state.isWeedy) {
            player.filterableMessage("The ${patch.patchName} doesn't need weeding right now.")
            return false
        }

        if (player.inventory.isFull) {
            player.filterableMessage("You don't have enough inventory space.")
            return false
        }

        if (!player.inventory.contains(Items.RAKE)) {
            player.filterableMessage("You need a rake to do that.")
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
        private const val rakingSound = 2442
        private const val rakingWaitTime = 3
        private const val rakingXp = 4.0
    }
}
