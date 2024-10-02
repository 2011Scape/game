package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Seed
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to clearing a patch that has died
 */
class ClearHandler(
    private val state: PatchState,
    private val player: Player,
) {
    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    private fun canClear() =
        (
            state.isDead ||
                (state.isProducing && state.livesLeft == 0 && state.seed!!.harvest.choppedDownVarbit == null) ||
                state.isChoppedDown ||
                containsScarecrow()
        ) &&
            player.inventory.contains(Items.SPADE)

    private fun containsScarecrow() = state.seed == Seed.Scarecrow

    fun clear() {
        if (canClear()) {
            if (containsScarecrow() && player.inventory.isFull) {
                player.message("You need at least one free inventory space to do that.")
                return
            }

            if (state.seed!!.seedType == SeedType.Tree && state.isChoppedDown && player.inventory.freeSlotCount < 4) {
                player.message("You need at least four free inventory slots to do that.")
                return
            }

            player.lockingQueue {
                player.animate(animation)
                player.playSound(Sfx.DIGSPADE)
                farmingTimerDelayer.delayIfNeeded(clearWaitTime)
                wait(clearWaitTime)
                if (containsScarecrow()) {
                    player.inventory.add(Items.SCARECROW)
                } else if (state.seed!!.seedType == SeedType.Tree && state.isChoppedDown) {
                    player.inventory.add(state.seed!!.produce)
                }
                state.clear()
                player.filterableMessage("You have successfully cleared this patch for new crops.")
            }
        }
    }

    companion object {
        private const val animation = 830
        private const val clearWaitTime = 3
    }
}
