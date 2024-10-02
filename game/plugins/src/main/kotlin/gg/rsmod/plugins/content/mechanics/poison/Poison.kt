package gg.rsmod.plugins.content.mechanics.poison

import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.POISON_IMMUNITY
import gg.rsmod.game.model.timer.POISON_TIMER
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.setVarp

/**
 * This object represents the game mechanic for poisoning a Pawn and
 * causing periodic damage to them over time. It provides methods for
 * poisoning a Pawn, getting the damage dealt by a given number of ticks,
 * checking if a Pawn is immune to poison, and setting the state of the
 * player's HP orb.
 *
 * @author Tom <rspsmods@gmail.com>
 * @author Alycia <https://github.com/alycii>
 */
object Poison {
    /** The VARP id for the player's HP orb. */
    private const val POISON_VARP = 102

    /**
     * Gets the damage that will be dealt after a given number of ticks.
     *
     * @param ticks the number of ticks left until the poison expires
     * @return the damage that will be dealt after the specified number of ticks
     * @since 1.0
     */
    fun getDamageForTicks(ticks: Int) = ((ticks / 5) + 1) * 10

    /**
     * Checks if a given pawn is immune to poison.
     *
     * @param pawn the pawn to check for poison immunity
     * @return true if the pawn is immune to poison, false otherwise
     * @since 1.0
     */
    fun isImmune(pawn: Pawn): Boolean =
        when (pawn) {
            is Player -> pawn.timers.has(POISON_IMMUNITY)
            is Npc -> pawn.combatDef.poisonImmunity
            else -> false
        }

    /**
     * Poisons a pawn, causing periodic damage to them over time.
     *
     * @param pawn the pawn to poison
     * @param initialDamage the initial damage dealt by the poison
     * @return true if the poison was applied successfully, false otherwise
     * @since 1.0
     */
    fun poison(
        pawn: Pawn,
        initialDamage: Int,
    ): Boolean {
        if (!pawn.attr.has(POISON_TICKS_LEFT_ATTR)) {
            val ticks = (initialDamage * 5) - 4
            val oldDamage = getDamageForTicks(pawn.attr[POISON_TICKS_LEFT_ATTR] ?: 0)
            val newDamage = getDamageForTicks(ticks)
            if (oldDamage > newDamage) {
                return false
            }
            pawn.timers[POISON_TIMER] = 30
            pawn.attr[POISON_TICKS_LEFT_ATTR] = ticks
            if (pawn is Player) {
                pawn.message("You have been poisoned!")
            }
        }
        return true
    }

    /**
     * Sets the state of the player's HP orb to indicate whether they
     * are currently poisoned or not.
     *
     * @param pawn the player to set the HP orb state for
     * @param state the new state of the HP orb
     * @since 1.0
     */
    fun setPoisonVarp(
        pawn: Pawn,
        state: OrbState,
    ) {
        if (pawn is Player) {
            val value =
                when (state) {
                    OrbState.NONE -> 0
                    OrbState.POISON -> 1
                }
            pawn.setVarp(POISON_VARP, value)
        }
    }

    /**
     * An enum representing the possible states of the player's HP orb.
     *
     * @since 1.0
     */
    enum class OrbState {
        /** The player is not poisoned. */
        NONE,

        /** The player is poisoned. */
        POISON,
    }
}
