package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(Objs.TRAPDOOR_45800, "Open") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("The trapdoor is locked.")
        wait(1)
    }
}
