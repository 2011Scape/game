package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.Patch
import gg.rsmod.plugins.content.skills.farming.logic.PatchState

/**
 * Logic related to curing a patch that is diseased
 */
class HealthCheckHandler(private val state: PatchState, private val patch: Patch, private val player: Player) {
    fun checkHealth() {
        if (state.healthCanBeChecked) {
            player.lockingQueue {
                wait(2)
                player.addXp(Skills.FARMING, state.seed!!.harvest.healthCheckXp!!)
                player.filterableMessage("You examine the ${patch.patchName.removeSuffix(" patch")} for signs of disease and find that it's in perfect health.")
                state.checkHealth()
            }
        }
    }
}
