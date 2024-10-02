package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.game.model.attr.CONSECUTIVE_SLAYER_TASKS
import gg.rsmod.game.model.attr.SLAYER_AMOUNT
import gg.rsmod.game.model.attr.SLAYER_ASSIGNMENT
import gg.rsmod.game.model.attr.SLAYER_MASTER
import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playJingle
import gg.rsmod.plugins.content.skills.slayer.data.SlayerMaster

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
        // Increment the consecutive slayer tasks counter
        val consecutiveTasks = attr.getOrDefault(CONSECUTIVE_SLAYER_TASKS, 0) + 1
        attr.put(CONSECUTIVE_SLAYER_TASKS, consecutiveTasks)

        // Retrieve the slayer master id from player's attributes
        val slayerMasterId = attr.getOrDefault(SLAYER_MASTER, 0)

        // Check that the master exists for the given id
        val getMaster = SlayerMaster.getMaster(slayerMasterId)
        if (getMaster != null) {
            // Determine the amount of slayer points to award
            val pointsAmount =
                when {
                    consecutiveTasks % 50 == 0 -> getMaster.getPoints50()
                    consecutiveTasks % 10 == 0 -> getMaster.getPoints10()
                    else -> getMaster.getPoints()
                }

            // Add the slayer points to the player
            addSlayerPoints(pointsAmount)

            // Send messages to the player
            message("You have completed $consecutiveTasks tasks in a row and receive $pointsAmount slayer points!")
            message("You've completed your slayer task; return to a Slayer master.")
        } else {
            message("Slayer master not found for id $slayerMasterId")
        }
        playJingle(61)
        attr.remove(SLAYER_ASSIGNMENT, SLAYER_AMOUNT)
    }
}

/**
 * Removes the current slayer task from the player by deleting the slayer assignment
 * and slayer amount attributes. If the player has no slayer task, no action is taken.
 */
fun Player.removeSlayerAssignment() {
    if (attr.has(SLAYER_ASSIGNMENT)) {
        message("Your slayer assignment has been removed.")
        attr.remove(SLAYER_ASSIGNMENT)
    }
    if (attr.has(SLAYER_AMOUNT)) {
        attr.remove(SLAYER_AMOUNT)
    }
}
