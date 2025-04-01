package gg.rsmod.plugins.content.areas.draynor

on_obj_option(Objs.TRAPDOOR_10804, "Climb-down") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("The trapdoor is locked.")
        wait(1)
    }
}
