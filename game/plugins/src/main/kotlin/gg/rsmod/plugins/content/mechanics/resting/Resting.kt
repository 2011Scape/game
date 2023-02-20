package gg.rsmod.plugins.content.mechanics.resting

import gg.rsmod.game.model.attr.LAST_KNOWN_RUN_STATE
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.mechanics.run.RunEnergy

object Resting {

    fun rest(player: Player, musician: Boolean = false) {
        if (player.isResting()) {
            return
        }
        player.attr[LAST_KNOWN_RUN_STATE] = player.getVarp(RunEnergy.RUN_ENABLED_VARP)
        player.setVarp(RunEnergy.RUN_ENABLED_VARP, if (musician) 4 else 3)
        player.queue {
            player.animate(11786)
            wait(150)
            player.animate(11788)
            player.setVarp(173, player.attr[LAST_KNOWN_RUN_STATE]!!.toInt())
        }

    }

    fun stopRest(player: Player) {
        val standUpAnimation = 11788
        player.queue {
            player.animate(standUpAnimation, delay = 0)
            wait(3)
            player.varps.setState(173, player.attr[LAST_KNOWN_RUN_STATE]!!.toInt())
        }
    }
}