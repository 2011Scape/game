package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

class CompostHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun addCompost(compost: CompostState) {
        when {
            compost == CompostState.None -> Unit
            state.isWeedy -> player.message("This patch needs weeding first.")
            !state.isEmpty -> player.message("This patch needs to be empty and weeded to do that.")
            state.compostState == compost || (state.compostState == CompostState.SuperCompost && compost == CompostState.Compost) -> "The ${patch.patchName} has already been treated with ${player.world.definitions.get(ItemDef::class.java, state.compostState.itemId).name.lowercase()}."
            else -> {
                state.compost(compost)
                player.message("You treat the ${patch.patchName} with compost.")
            }
        }
    }
}
