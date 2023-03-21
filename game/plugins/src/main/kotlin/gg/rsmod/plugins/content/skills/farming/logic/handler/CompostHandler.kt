package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class CompostHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun addCompost(compost: CompostState) {
        when {
            compost == CompostState.None -> Unit
            state.isWeedy -> player.message("This patch needs weeding first.")
            !state.isEmpty -> player.message("This patch needs to be empty and weeded to do that.")
            state.compostState != CompostState.None -> "The ${patch.patchName} has already been treated with ${player.world.definitions.get(ItemDef::class.java, state.compostState.itemId).name.lowercase()}."
            else -> {
                player.lockingQueue {
                    val slot = player.inventory.getItemIndex(compost.itemId, false)
                    if (slot != -1 && player.inventory.remove(compost.itemId, beginSlot = slot).hasSucceeded()) {
                        player.animate(animation)
                        player.playSound(sound)
                        wait(3)
                        player.inventory.add(compost.replacement, beginSlot = slot)
                        state.compost(compost)
                        player.addXp(Skills.FARMING, compost.xp)
                        player.message("You treat the ${patch.patchName} with compost.")
                    }
                }
            }
        }
    }

    companion object {
        private const val animation = 2283
        private const val sound = 2427
    }
}
