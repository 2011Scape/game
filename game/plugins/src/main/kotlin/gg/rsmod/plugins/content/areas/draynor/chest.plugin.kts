package gg.rsmod.plugins.content.areas.draynor

on_obj_option(Objs.CLOSED_CHEST_46243, "Open") {
    val interacting = player.getInteractingGameObj()
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(9429)
        wait(2)
        world.spawn(DynamicObject(Objs.CHEST_11551, interacting.type, interacting.rot, interacting.tile))
    }
}

on_obj_option(Objs.CHEST_11551, "Close") {
    val interacting = player.getInteractingGameObj()
    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(9429)
        wait(2)
        world.spawn(DynamicObject(Objs.CLOSED_CHEST_46243, interacting.type, interacting.rot, interacting.tile))
    }
}

on_obj_option(Objs.CHEST_11551, "Search") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("You search and find nothing.")
        wait(1)
    }
}
