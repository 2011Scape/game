package gg.rsmod.plugins.content.mechanics.run

import gg.rsmod.game.model.bits.INFINITE_VARS_STORAGE
import gg.rsmod.game.model.bits.InfiniteVarsType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*

/**
 * @author Tom <rspsmods@gmail.com>
 */
object RunEnergy {

    val RUN_DRAIN = TimerKey()

    /**
     * Reduces run energy depletion by 70%
     */
    private val STAMINA_BOOST = TimerKey("stamina_boost", tickOffline = false)

    const val RUN_ENABLED_VARP = 173

    fun toggle(p: Player) {
        if (p.isResting()) {
            return
        }
        if (p.runEnergy >= 1.0) {
            p.toggleVarp(RUN_ENABLED_VARP)
        } else {
            p.setVarp(RUN_ENABLED_VARP, 0)
            p.message("You don't have enough run energy left.")
        }
    }

    fun drain(p: Player) {
        if (p.isRunning() && p.hasMoveDestination()) {
            if (!p.hasStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.RUN)) {
                val weight = 0.0.coerceAtLeast(p.weight)
                var decrement = (weight.coerceAtMost(64.0) / 100.0) + 0.64
                if (p.timers.has(STAMINA_BOOST)) {
                    decrement *= 0.3
                }
                p.runEnergy = 0.0.coerceAtLeast((p.runEnergy - decrement))
                if (p.runEnergy <= 0) {
                    p.varps.setState(RUN_ENABLED_VARP, 0)
                }
                p.sendRunEnergy(p.runEnergy.toInt())
            }
        } else if (p.runEnergy < 100.0 && p.lock.canRestoreRunEnergy()) {
            var recovery = (8.0 + (p.getSkills().getCurrentLevel(Skills.AGILITY) / 6.0)) / 100.0
            if (p.getVarp(RUN_ENABLED_VARP) == 3) {
                recovery *= 6.33
            }
            if (p.getVarp(RUN_ENABLED_VARP) == 4) {
                recovery *= 8.88
            }
            p.runEnergy = 100.0.coerceAtMost((p.runEnergy + recovery))
            p.sendRunEnergy(p.runEnergy.toInt())
        }
    }
}