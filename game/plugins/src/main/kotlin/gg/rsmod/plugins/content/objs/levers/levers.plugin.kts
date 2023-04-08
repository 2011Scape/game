package gg.rsmod.plugins.content.objs.levers

import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.combat.isBeingAttacked

// List of valid lever object IDs
val lever = listOf(Objs.LEVER_1814, Objs.LEVER_1815)

/**
 * Moves the lever to the specified object ID.
 */
fun moveLever(objectId: Int, obj: GameObject) {
    val pulledLever = DynamicObject(objectId, obj.type, obj.rot, obj.tile)
    world.spawn(pulledLever)
}

/**
 * Handles the pulling of the lever and teleports the player.
 */
fun pullLever(p: Player, obj: GameObject, xDestination: Int, zDestination: Int): Boolean {
    // Check if the object is a valid lever
    if (obj.id !in lever) {
        p.message("You can only use this function on a lever.")
        return false
    }

    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                // Start the lever pull animation
                1 -> {
                    p.animate(2140)
                    moveLever(36, obj)
                }

                // Wait for 1 tick
                2 -> wait(1)

                // Play teleport animation and graphic
                3 -> {
                    p.animate(8939)
                    p.graphic(1576)
                }

                // Wait for 1 tick
                4 -> wait(1)

                // Play teleport animation and graphic, then move the player
                5 -> {
                    p.animate(8941)
                    p.graphic(1577)
                    p.moveTo(xDestination, zDestination)
                }

                // Move the lever back to its original state and exit the loop
                6 -> {
                    moveLever(obj.id, obj)
                    break
                }
            }
            ticks++
            wait(1)
        }
        p.unlock()
    }
    return true
}

/**
 * Shows a warning message to the player before pulling the lever.
 */
fun showWarningAndPullLever(player: Player, obj: GameObject, xDestination: Int, zDestination: Int) {
    player.queue {
        messageBox("Warning! Pulling the lever will teleport you deep into the Wilderness.")
        when (options("Yes. I'm brave.", "Eeep! The Wilderness... No thank you.", "Yes please, don't show this message again.")) {
            1, 3 -> pullLever(player, obj, xDestination, zDestination)
            else -> { /* do nothing */ }
        }
    }
}

/**
 * Event handler for pulling the levers outside the wilderness.
 */
on_obj_option(obj = Objs.LEVER_1814, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (obj.tile.x in listOf(2561, 3090)) {
            showWarningAndPullLever(player, obj, 3154, 3923)
        }
    }
}

/**
 * Event handler for pulling the lever in the wilderness.
 */
on_obj_option(obj = Objs.LEVER_1815, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3153 -> {
                // Check if the player is being attacked, locked, dead, or has a modal interface open
                if(player.isBeingAttacked() || player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) {
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 2561, 3311)
                }
            }
        }
    }
}