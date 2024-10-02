package gg.rsmod.plugins.content.areas.taverley

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.DOOR_31838, option = "open") {
    if (player.tile.z != 9689) {
        player.message("This door is locked.")
    } else {
        handleJailDoor(player)
    }
}

on_item_on_obj(obj = Objs.DOOR_31838, item = Items.JAIL_KEY) {
    handleJailDoor(player)
}

on_obj_option(obj = Objs.GATE_2623, option = "open") {
    if (!player.inventory.contains(Items.DUSTY_KEY)) {
        player.message("This gate is locked.")
    } else {
        handleDustyGate(player)
    }
}

on_item_on_obj(obj = Objs.GATE_2623, item = Items.DUSTY_KEY) {
    handleDustyGate(player)
}

on_npc_option(npc = Npcs.VELRAK_THE_EXPLORER, option = "talk-to") {
    player.queue {
        if (!player.inventory.contains(Items.DUSTY_KEY)) {
            chatNpc("Thank you for rescuing me! It isn't very comfy in this", "cell!")
            when (options("So... do you know anywhere good to explore?", "Do I get a reward?")) {
                FIRST_OPTION -> {
                    chatPlayer("So... do you know anywhere good to explore?")
                    chatNpc(
                        "Well, this dungeon was quite good to explore ...until I",
                        "got captured, anyway. I was given a key to an inner",
                        "part of this dungeon by a mysterious cloaked stranger!",
                    )
                    chatNpc(
                        "It's rather tough for me to get that far into the",
                        "dungeon however... I just keep getting captured! Would",
                        "you like to give it a go?",
                    )
                    when (options("Yes please!", "No, it's too dangerous for me too.")) {
                        FIRST_OPTION -> {
                            chatPlayer("Yes please!")
                            player.inventory.add(Items.DUSTY_KEY)
                            itemMessageBox(
                                "Velrak reaches somewhere mysterious and passes you a<br>key.",
                                item = Items.DUSTY_KEY,
                            )
                        }
                        SECOND_OPTION -> {
                            chatPlayer("No, it's too dangerous for me too.")
                            chatNpc("I don't blame you!")
                        }
                    }
                }
                SECOND_OPTION -> {
                    chatPlayer("Do I get a reward? For freeing you and all...")
                    chatNpc(
                        "Well... not really... The Black Knights took all of my",
                        "stuff before throwing me in here to rot!",
                    )
                }
            }
        } else {
            chatPlayer("Are you still here?")
            chatNpc("Yes... I'm still plucking up the courage to run out past", "those Black Knights...")
        }
    }
}

/**
 * This function handles the interaction with the dusty gate for a given player.
 *
 * @param player the player object to handle the dusty gate interaction for.
 */
fun handleDustyGate(player: Player) {
    /**
     * Checks if the player has the DUSTY_KEY item in their inventory. If not, displays a message
     * and returns.
     */
    if (!player.inventory.contains(Items.DUSTY_KEY)) {
        player.message("This gate is locked.")
        return
    }

    /**
     * Creates closed and opened gate objects using the DynamicObject class and respective IDs,
     * types, rotations, and tile positions.
     */
    val closedDoor = DynamicObject(id = Objs.GATE_2623, type = 0, rot = 0, tile = Tile(x = 2924, z = 9803))
    val openedDoor = DynamicObject(id = Objs.GATE_2623, type = 0, rot = 1, tile = Tile(x = 2924, z = 9803))

    // Removes the closed gate object and spawns the opened gate object in the game world.
    world.remove(closedDoor)
    world.spawn(openedDoor)

    /**
     * Sets the player's locking queue to delay actions and performs the following actions:
     * 1. Determines the target tile for the player to walk to based on their current position.
     * 2. Displays a message to the player indicating they have unlocked the gate.
     * 3. Makes the player walk to the target tile.
     * 4. Waits for 3 game ticks (approximately 1.8 seconds).
     * 5. Removes the opened gate object and spawns the closed gate object in the game world.
     */
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        val x = if (player.tile.x >= 2924) 2923 else 2924
        val z = 9803
        player.message("You unlock the gate.")
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(4)
        world.remove(openedDoor)
        world.spawn(closedDoor)
    }
}

/**
 * This function handles the interaction with the jail door for a given player.
 *
 * @param player the player object to handle the jail door interaction for.
 */
fun handleJailDoor(player: Player) {
    /**
     * Checks if the player has the JAIL_KEY item in their inventory and is not standing on the
     * tile for the open jail door. If either condition is not met, displays a message and returns.
     */
    if (!player.inventory.contains(Items.JAIL_KEY) && player.tile.z != 9689) {
        player.message("This door is locked.")
        return
    }

    /**
     * Creates closed and opened door objects using the DynamicObject class and respective IDs,
     * types, rotations, and tile positions.
     */
    val closedDoor = DynamicObject(id = Objs.DOOR_31838, type = 0, rot = 1, tile = Tile(x = 2931, z = 9689))
    val openedDoor = DynamicObject(id = Objs.DOOR_31839, type = 0, rot = 0, tile = Tile(x = 2931, z = 9689))

    // Removes the closed door object and spawns the opened door object in the game world.
    world.remove(closedDoor)
    world.spawn(openedDoor)

    /**
     * Sets the player's locking queue to delay actions and performs the following actions:
     * 1. Determines the target tile for the player to walk to based on their current position.
     * 2. Makes the player walk to the target tile.
     * 3. Waits for 3 game ticks (approximately 1.8 seconds).
     * 4. Removes the opened door object and spawns the closed door object in the game world.
     */
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        val x = 2931
        val z = if (player.tile.z == 9690) 9689 else 9690
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(4)
        world.remove(openedDoor)
        world.spawn(closedDoor)
    }
}
