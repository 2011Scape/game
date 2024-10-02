package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.farming.constants.CompostState
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to inspecting a patch
 */
class InspectHandler(
    private val state: PatchState,
    private val patch: Patch,
    private val player: Player,
) {
    fun inspect() {
        val status =
            when {
                state.isWeedy -> "The patch needs weeding."
                state.isEmpty -> "The patch is empty and weeded."
                state.isDiseased -> "The patch is diseased and needs attending to before it dies."
                state.isDead -> "The patch has become infected by disease and has died."
                state.isPlantFullyGrown -> "The patch is fully grown."
                else -> "The patch has something growing in it."
                // TODO: "The patch has the remains of a tree stump in it."
            }
        val soil =
            when (state.compostState) {
                CompostState.None -> "The soil has not been treated."
                CompostState.Compost -> "The soil has been treated with compost."
                CompostState.SuperCompost -> "The soil has been treated with super compost."
            }
        player.queue {
            player.message("This is ${patch.patchName.prefixAn()}.")
            player.message(soil)
            player.message(status)
            if (state.isProtected) {
                player.message("A nearby gardener is looking after this patch for you.")
            }
            // TODO: "A nearby scarecrow is giving protection for this patch."
            // TODO: "A nearby flower is giving protection for this patch."
        }
    }
}
