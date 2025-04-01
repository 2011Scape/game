package gg.rsmod.plugins.content.areas.draynor

on_obj_option(Objs.TRAPDOOR_6434, "Open") {
    val interacting = player.getInteractingGameObj()
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(9429)
        wait(2)
        world.spawn(DynamicObject(Objs.TRAPDOOR_6435, interacting.type, interacting.rot, interacting.tile))
    }
    world.queue {
        // Trapdoor to sewer closes automatically, wait 1 minute and then close it back
        wait(100)
        world.spawn(DynamicObject(Objs.TRAPDOOR_6434, interacting.type, interacting.rot, interacting.tile))
    }
}

on_obj_option(Objs.TRAPDOOR_6435, "Climb-down") {
    player.lockingQueue(lockState = LockState.FULL) {
        wait(2)
        if (player.tile.z < 3247) {
            player.moveTo(Tile(3118, 9643))
        }
        else {
            player.moveTo(Tile(player.tile.x, player.tile.z + 6400))
        }
    }
}

on_obj_option(Objs.LADDER_26518, "Climb-up") {
    player.lockingQueue(lockState = LockState.FULL) {
        wait(2)
        player.moveTo(Tile(player.tile.x, player.tile.z - 6400))
    }
}
