package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.skills.farming.data.SeedType
import gg.rsmod.plugins.content.skills.farming.logic.PatchState
import gg.rsmod.plugins.content.skills.woodcutting.AxeType

/**
 * Logic related to chopping down a tree in a patch
 */
class ChopHandler(private val state: PatchState, private val player: Player) {

    fun chopDown() {
        val axe = AxeType.values.reversed().firstOrNull {
            player.getSkills()
                    .getMaxLevel(Skills.WOODCUTTING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                    it.item
            ))
        }
        if (axe == null) {
            player.message("You need a hatchet to chop this tree.")
            return
        }

        if (state.canBeChopped && state.seed!!.seedType == SeedType.FruitTree) {
            player.queue {
                player.animate(axe.animation)
                wait(6)
                state.chopDown()
            }
        }
    }
}
