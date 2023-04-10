package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to composting an empty patch
 */
class CompostHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {

    private val farmingTimerDelayer = FarmingTimerDelayer(player)

    fun addCompost(compost: CompostState) {
        when {
            compost == CompostState.None -> Unit
            !player.inventory.contains(compost.itemId) -> Unit
            state.isWeedy -> player.message("This patch needs weeding first.")
            !state.isEmpty -> player.message("This patch needs to be empty and weeded to do that.")
            state.compostState != CompostState.None -> player.message("The ${patch.patchName} has already been treated with ${player.world.definitions.get(ItemDef::class.java, state.compostState.itemId).name.lowercase()}.")
            else -> {
                player.lockingQueue {
                    player.animate(animation)
                    player.playSound(sound)
                    farmingTimerDelayer.delayIfNeeded(compostWaitTime)
                    wait(compostWaitTime)
                    val slot = player.inventory.getItemIndex(compost.itemId, false)
                    if (player.inventory.remove(compost.itemId, beginSlot = slot).hasSucceeded()) {
                        player.inventory.add(compost.replacement, beginSlot = slot)
                        state.compost(compost)
                        player.addXp(Skills.FARMING, compost.xp)
                        player.filterableMessage("You treat the ${patch.patchName} with compost.")
                    }
                }
            }
        }
    }

    companion object {
        private const val animation = 2283
        private const val sound = 2427
        private const val compostWaitTime = 3
    }
}
