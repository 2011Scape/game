package gg.rsmod.plugins.content.objs

val crateIds = listOf(
    Objs.CRATE_21806,                   // Lumbridge
    Objs.CRATE_2974, Objs.CRATE_3035,   // Draynor
)

crateIds.forEach {
    on_obj_option(it, "Search") {
        player.lockingQueue(lockState = LockState.FULL) {
            player.message("You search the crate but find nothing.")
            wait(1)
        }
    }
}
