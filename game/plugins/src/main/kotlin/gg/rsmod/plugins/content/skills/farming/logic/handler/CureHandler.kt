package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.data.CureType
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to curing a patch that is diseased
 */
class CureHandler(private val state: PatchState, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    fun cure(cureType: CureType) {
        if (canCure(cureType)) {
            player.lockingQueue {
                player.animate(cureType.animation)
                player.playSound(cureType.sound)
                farmingTimerDelayer.delayIfNeeded(cureWaitTime)
                wait(cureWaitTime)
                if (canCure(cureType)) {
                    state.cure()
                    val slot = player.inventory.getItemIndex(Items.PLANT_CURE, false)
                    if (player.inventory.remove(Items.PLANT_CURE, beginSlot = slot).hasSucceeded()) {
                        player.inventory.add(Items.VIAL, beginSlot = slot)
                    }
                }
            }
        }
    }

    private fun canCure(cureType: CureType): Boolean {
        if (!player.inventory.contains(cureType.itemId)) {
            player.message("You need a ${cureType.toUse} to do that.")
            return false
        }

        if (state.seed == null) {
            player.message("This farming patch is empty.")
            return false
        }

        if (!state.seed!!.seedType.growth.canBeCured) {
            player.message("You can't cure this patch with plant cure.")
            return false
        }

        if (!state.isDiseased) {
            player.message("This patch doesn't need curing.")
            return false
        }

        return true
    }

    companion object {
        private const val cureWaitTime = 3
    }
}
