package gg.rsmod.plugins.content.areas.draynor

on_obj_option(Objs.FOOD_LARDER, "Search") {
    player.lockingQueue(lockState = LockState.FULL) {
        player.message("You search and find nothing of interest.")
        wait(1)
    }
}
