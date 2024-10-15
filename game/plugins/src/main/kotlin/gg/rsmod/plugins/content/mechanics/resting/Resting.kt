package gg.rsmod.plugins.content.mechanics.resting

import gg.rsmod.game.model.attr.LAST_KNOWN_RUN_STATE
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.TaskPriority
import gg.rsmod.plugins.api.ext.clearMapFlag
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.mechanics.run.RunEnergy

object Resting {
    fun rest(
        player: Player,
        musician: Boolean = false,
    ) {
        if (player.isResting() || player.isLocked()) {
            return
        }
        if (player.isBeingAttacked()) {
            player.message("You can't rest until 10 seconds after the end of combat.")
            return
        }
        player.stopMovement()
        player.clearMapFlag()
        player.attr[LAST_KNOWN_RUN_STATE] = player.getVarp(RunEnergy.RUN_ENABLED_VARP)
        player.setVarp(RunEnergy.RUN_ENABLED_VARP, if (musician) 4 else 3)
        player.queue(TaskPriority.STRONG) {
            player.animate(11786)
            wait(150)
            player.animate(11788)
            player.setVarp(173, player.attr[LAST_KNOWN_RUN_STATE]!!.toInt())
        }
    }
}
