package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class ClearHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {

    private fun canClear() = state.isDead && player.inventory.contains(Items.SPADE)

    fun clear() {
        if (canClear()) {
            player.lockingQueue {
                player.animate(animation)
                player.playSound(sound)
                wait(3)
                state.clear()
                player.message("You have successfully cleared this patch for new crops.")
            }
        }
    }

    companion object {
        private const val animation = 830
        private const val sound = 1470
    }
}
