package gg.rsmod.plugins.content.skills.farming.logic.handler

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.skills.farming.constants.Constants

class FarmingTimerDelayer(private val player: Player) {
    fun delayIfNeeded(waitTime: Int) {
        val leftOnTimer = player.timers[Constants.playerFarmingTimer]
        if (leftOnTimer <= waitTime) {
            player.timers[Constants.playerFarmingTimer] = waitTime + 1
        }
    }
}
