package gg.rsmod.plugins.content.areas.edgeville

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.DOOR_1804, option = "open") {
    if (!player.inventory.contains(Items.BRASS_KEY)) {
        player.message("The door is locked.")
        return@on_obj_option
    }

    handleDoor(player)
}

on_obj_option(obj = Objs.LADDER_12389, option = "Climb-Down") {
    player.handleLadder(underground = true)
}

on_obj_option(obj = Objs.LADDER_29358, option = "Climb-Down") {
    player.handleLadder()
}

fun handleDoor(player: Player) {
    val closedDoor = DynamicObject(id = 1804, type = 0, rot = 1, tile = Tile(x = 3115, z = 3449))
    val door =
        DynamicObject(
            id = 1804,
            type = 0,
            rot =
                if (player.tile.z ==
                    3450
                ) {
                    2
                } else {
                    0
                },
            tile = Tile(x = 3115, z = 3449),
        )
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = 3115
        val z = if (player.tile.z == 3450) 3449 else 3450
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}
