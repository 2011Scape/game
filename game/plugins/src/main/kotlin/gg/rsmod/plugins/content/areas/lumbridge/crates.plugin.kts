package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(Objs.CRATE_21806, "Search") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("You search the crate but find nothing.")
        wait(1)
    }
}
