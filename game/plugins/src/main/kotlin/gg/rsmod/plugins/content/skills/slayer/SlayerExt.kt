package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.game.model.attr.SLAYER_AMOUNT
import gg.rsmod.game.model.attr.SLAYER_ASSIGNMENT
import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message

/**
 * @author Alycia <https://github.com/alycii>
 */

/**
 * Returns the [SlayerAssignment] associated with this player's current slayer task, if any.
 * If the player does not have a slayer task or the slayer assignment cannot be found,
 * this function returns null.
 *
 * @return The [SlayerAssignment] associated with this player's current slayer task, or null
 * if the player does not have a slayer task or the slayer assignment cannot be found.
 */
fun Player.getSlayerAssignment(): SlayerAssignment? {
    return attr.getOrNull(SLAYER_ASSIGNMENT)?.let { identifier ->
        SlayerAssignment.values().find { it.identifier == identifier }
    }
}

/**
 * Decreases the slayer task count for this player by one, adds slayer XP to the player's
 * [Skills.SLAYER] skill, and removes the player's [SLAYER_AMOUNT] attribute if the count
 * reaches zero. If the attribute is not present, no action is taken.
 *
 * @param npc The NPC that the player killed to decrease their slayer task count.
 */
fun Player.handleDecrease(npc: Npc) {
    if (attr.has(SLAYER_AMOUNT)) {
        addXp(Skills.SLAYER, npc.combatDef.slayerXp)
        val amount = attr.getOrDefault(SLAYER_AMOUNT, 0)
        attr.put(SLAYER_AMOUNT, amount - 1)
    }
    if (attr.getOrDefault(SLAYER_AMOUNT, 0) <= 0) {
        message("You've completed your slayer task; return to a Slayer master.")
        attr.remove(SLAYER_ASSIGNMENT, SLAYER_AMOUNT)
    }
}