package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to clearing a patch that has died
 */
class ClearHandler(private val state: PatchState, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    private fun canClear() = (state.isDead || (state.isProducing && state.livesLeft == 0 && state.seed!!.harvest.choppedDownVarbit == null) || state.isChoppedDown) && player.inventory.contains(Items.SPADE)

    fun clear() {
        if (canClear()) {
            player.lockingQueue {
                player.animate(animation)
                player.playSound(sound)
                farmingTimerDelayer.delayIfNeeded(clearWaitTime)
                wait(clearWaitTime)
                state.clear()
                player.filterableMessage("You have successfully cleared this patch for new crops.")
            }
        }
    }

    companion object {
        private const val animation = 830
        private const val sound = 1470
        private const val clearWaitTime = 3
    }
}
