package gg.rsmod.plugins.content.objs.levers

import gg.rsmod.game.model.attr.DISABLE_LEVER_WARNING
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player

// List of valid lever object IDs
val lever =
    listOf(
        Objs.LEVER_1814,
        Objs.LEVER_1815,
        Objs.LEVER_1816,
        Objs.LEVER_1817,
        Objs.LEVER_5959,
        Objs.LEVER_5960,
        Objs.LEVER_9706,
        Objs.LEVER_9707,
    )

/**
 * Moves the lever to the specified object ID.
 */
fun moveLever(
    objectId: Int,
    obj: GameObject,
) {
    val pulledLever = DynamicObject(objectId, obj.type, obj.rot, obj.tile)
    world.spawn(pulledLever)
}

/**
 * Handles the pulling of the lever and teleports the player.
 * @param shouldMoveLever If true, the lever will move during the animation.
 */
fun pullLever(
    p: Player,
    obj: GameObject,
    xDestination: Int,
    zDestination: Int,
    shouldMoveLever: Boolean = true,
): Boolean {
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
                    p.animate(2140, idleOnly = true)
                    if (shouldMoveLever) {
                        moveLever(36, obj)
                    }
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
                    p.teleportTo(xDestination, zDestination)
                }

                // Move the lever back to its original state and exit the loop
                6 -> {
                    if (shouldMoveLever) {
                        moveLever(obj.id, obj)
                    }
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
fun showWarningAndPullLever(
    player: Player,
    obj: GameObject,
    xDestination: Int,
    zDestination: Int,
) {
    if (player.attr.has(DISABLE_LEVER_WARNING)) {
        pullLever(player, obj, xDestination, zDestination)
    } else {
        player.queue {
            messageBox("Warning! Pulling the lever will teleport you deep into the Wilderness.")
            when (
                options(
                    "Yes. I'm brave.",
                    "Eeep! The Wilderness... No thank you.",
                    "Yes please, don't show this message again.",
                )
            ) {
                1 -> pullLever(player, obj, xDestination, zDestination)
                2 -> { // do nothing
                }

                3 -> {
                    player.attr[DISABLE_LEVER_WARNING] = true
                    pullLever(player, obj, xDestination, zDestination)
                }
            }
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
 * Event handler for pulling the lever in the deserted camp.
 */
on_obj_option(obj = Objs.LEVER_1815, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3153 -> {
                // Check if the player is being attacked, locked, dead, or has a modal interface open
                if (player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) { // TODO: Add condition if player is teleblocked once it's added to the game.
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 2561, 3311)
                }
            }
        }
    }
}

/**
 * Event handler for pulling the lever to the wilderness mage bank
 */
on_obj_option(obj = Objs.LEVER_5959, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3090 -> {
                // Check if the player is being attacked, locked, dead, or has a modal interface open
                if (player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) { // TODO: Add condition if player is teleblocked once it's added to the game.
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 2539, 4712)
                }
            }
        }
    }
}

/**
 * Event handler for pulling the lever to the mage arena.
 */
on_obj_option(obj = Objs.LEVER_9706, option = "pull") {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3104 -> {
                // Check if the player is being attacked, locked, dead, or has a modal interface open
                if (player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) { // TODO: Add condition if player is teleblocked once it's added to the game.
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 3105, 3951)
                }
            }
        }
    }
}

/**
 * Event handler for pulling the lever to exit the mage arena.
 */
on_obj_option(obj = Objs.LEVER_9707, option = "pull") {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3105 -> {
                // Check if the player is being attacked, locked, dead, or has a modal interface open
                if (player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) { // TODO: Add condition if player is teleblocked once it's added to the game.
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 3105, 3956)
                }
            }
        }
    }
}

/**
 * Event handler for pulling the lever to the wilderness mage bank
 */
on_obj_option(obj = Objs.LEVER_5960, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (obj.tile.x in listOf(2539)) {
            showWarningAndPullLever(player, obj, 3090, 3956)
        }
    }
}

/**
 * Event handler for pulling the lever to kbd lair
 */
on_obj_option(obj = Objs.LEVER_1816, option = "pull", lineOfSightDistance = -1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (obj.tile.x in listOf(3067)) {
            pullLever(player, obj, 2271, 4680, shouldMoveLever = false)
        }
    }
}

/**
 * Event handler for pulling the lever to kbd lair
 */
on_obj_option(obj = Objs.LEVER_1817, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (obj.tile.x in listOf(2273)) {
            pullLever(player, obj, 3067, 10254)
        }
    }
}
