package gg.rsmod.plugins.content.mechanics.resting

import gg.rsmod.game.model.attr.LAST_KNOWN_RUN_STATE
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.TaskPriority
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Varps
import gg.rsmod.plugins.api.ext.clearMapFlag
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.combat.isBeingAttacked

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
        player.attr[LAST_KNOWN_RUN_STATE] = player.getVarp(Varps.RUN_STATE)
        player.setVarp(Varps.RUN_STATE, if (musician) 4 else 3)
        player.queue(TaskPriority.STRONG) {
            player.animate(Anims.START_RESTING)
            wait(150)
            player.animate(Anims.STOP_RESTING)
            player.setVarp(Varps.RUN_STATE, player.attr[LAST_KNOWN_RUN_STATE]!!.toInt())
        }
    }
}
