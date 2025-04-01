package gg.rsmod.plugins.content.areas.draynor

val closedDoorIds = listOf(Objs.LARGE_DOOR_47421, Objs.LARGE_DOOR_47424)

closedDoorIds.forEachIndexed { index, id ->
    on_obj_option(id, "Open") {
        if (player.tile.z <= 3353) {
            player.lockingQueue (lockState = LockState.DELAY_ACTIONS) {
                val blockingObjectLeft = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(3108, 3353))
                val blockingObjectRight = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(3109, 3353))
                val leftDoor = DynamicObject(id = Objs.LARGE_DOOR_47421, type = 0, rot = 1, tile = Tile(3108, 3353))
                val rightDoor = DynamicObject(id = Objs.LARGE_DOOR_47424, type = 0, rot = 1, tile = Tile(3109, 3353))
                val leftOpenDoor = DynamicObject(id = Objs.LARGE_DOOR_47423, type = 0, rot = 0, tile = Tile(3108, 3354))
                val rightOpenDoor = DynamicObject(id = Objs.LARGE_DOOR_47425, type = 0, rot = 2, tile = Tile(3109,
                    3354))
                world.spawn(blockingObjectLeft)
                world.spawn(blockingObjectRight)
                world.spawn(leftOpenDoor)
                world.spawn(rightOpenDoor)
                wait(2)
                player.moveTo(Tile(x = if (player.tile.x == 3108) 3108 else 3109, 3354))
                wait(2)
                world.remove(leftOpenDoor)
                world.remove(rightOpenDoor)
                world.spawn(leftDoor)
                world.spawn(rightDoor)
                wait(1)
                player.message("The doors slam shut behind you.")
            }
        }
        else {
            player.lockingQueue(lockState = LockState.FULL) {
                player.message("The doors won't open.")
                wait(1)
            }
        }
    }
}

