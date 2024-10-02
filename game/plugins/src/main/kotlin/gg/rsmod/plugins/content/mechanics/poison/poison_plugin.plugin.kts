package gg.rsmod.plugins.content.mechanics.poison

import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.timer.POISON_TIMER

val poisonTickDelay = 30

/**
 * Callback for the poison timer. Deals damage to the pawn and decrements the number of ticks left for the poison effect.
 */
on_timer(POISON_TIMER) {
    val pawn = pawn // The pawn being affected by the poison effect
    val ticksLeft = pawn.attr[POISON_TICKS_LEFT_ATTR] ?: 0 // The number of ticks left for the poison effect. Defaults to 0 if not set.

    // If the pawn is a player, and they have a modal open, reset the timer to 1 tick
    // Resetting the timer to 1 tick ensures that when the modal is closed, poison will continue
    if (pawn is Player) {
        if (pawn.interfaces.currentModal != -1) {
            pawn.timers[POISON_TIMER] = 1
            return@on_timer
        }
    }

    // If there are no ticks left for the poison effect, remove the poison orb from the player and exit the function.
    if (ticksLeft <= 0) {
        if (pawn is Player) {
            pawn.message("The poison has wore off.")
            Poison.setPoisonVarp(pawn, Poison.OrbState.NONE)
        }
        pawn.attr.remove(POISON_TICKS_LEFT_ATTR)
        return@on_timer
    }

    // If there are ticks left for the poison effect, deal damage to the pawn and decrement the number of ticks left.
    when {
        ticksLeft > 0 -> {
            pawn.attr[POISON_TICKS_LEFT_ATTR] = ticksLeft.minus(1)
            pawn.hit(damage = Poison.getDamageForTicks(ticksLeft), type = HitType.POISON)
        }
        ticksLeft < 0 -> pawn.attr[POISON_TICKS_LEFT_ATTR] = ticksLeft.plus(1)
    }

    // Set the timer for the next tick of the poison effect.
    pawn.timers[POISON_TIMER] = poisonTickDelay
}

// Reset the players poison varp on death
on_player_death {
    player.attr.remove(POISON_TICKS_LEFT_ATTR)
    Poison.setPoisonVarp(player, Poison.OrbState.NONE)
}
