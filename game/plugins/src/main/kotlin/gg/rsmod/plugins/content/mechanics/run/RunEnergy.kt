package gg.rsmod.plugins.content.mechanics.run

import gg.rsmod.game.model.MovementQueue
import gg.rsmod.game.model.bits.INFINITE_VARS_STORAGE
import gg.rsmod.game.model.bits.InfiniteVarsType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import kotlin.math.min
import kotlin.math.pow

/**
 * @author Tom <rspsmods@gmail.com>
 */
object RunEnergy {
    val RUN_DRAIN = TimerKey()

    const val RUN_ENABLED_VARP = 173
    private const val BASE_DRAIN = 0.7
    private const val DRAIN_DELTA_PER_AGILITY_LVL = 0.002

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
            if (p.movementQueue.peekLastStep()?.type == MovementQueue.StepType.FORCED_WALK) {
                return
            }
            if (!p.hasStorageBit(INFINITE_VARS_STORAGE, InfiniteVarsType.RUN)) {
                val agilityDrainDelta = p.skills.getCurrentLevel(Skills.AGILITY) * DRAIN_DELTA_PER_AGILITY_LVL
                val weightDrainFactor = 0.92.pow(p.weight.coerceAtLeast(0.0) / 10)
                val decrement = (BASE_DRAIN - agilityDrainDelta) / weightDrainFactor
                p.runEnergy = 0.0.coerceAtLeast((p.runEnergy - decrement))
                if (p.runEnergy <= 0) {
                    p.varps.setState(RUN_ENABLED_VARP, 0)
                }
                p.sendRunEnergy(p.runEnergy.toInt())
            }
        } else if (p.runEnergy < 100.0 && p.lock.canRestoreRunEnergy()) {
            val agilityLevel = p.skills.getCurrentLevel(Skills.AGILITY)
            val recovery =
                when (p.getVarp(RUN_ENABLED_VARP)) {
                    3 -> recoverRateResting(agilityLevel)
                    4 -> recoverRateMusician(agilityLevel)
                    else -> recoverRateWalking(agilityLevel)
                }
            p.runEnergy = 100.0.coerceAtMost((p.runEnergy + recovery))
            p.sendRunEnergy(p.runEnergy.toInt())
        }
    }

    fun renew(
        p: Player,
        amount: Double,
    ) {
        p.runEnergy = min(100.0, p.runEnergy + amount)
        p.sendRunEnergy(p.runEnergy.toInt())
    }

    private fun recoverRateWalking(agilityLevel: Int) = agilityLevel.interpolate(0.27, 0.36, 1, 99)

    private fun recoverRateResting(agilityLevel: Int) = agilityLevel.interpolate(1.68, 2.4, 1, 99)

    private fun recoverRateMusician(agilityLevel: Int) = agilityLevel.interpolate(2.28, 4.0, 1, 99)
}
