package gg.rsmod.plugins.content.objs

val crateIds = listOf(
    Objs.CRATE_21806,                   // Lumbridge
    Objs.CRATE_2974, Objs.CRATE_3035,   // Draynor
    Objs.CRATE_31137, Objs.CRATES_31138, Objs.CRATES_31139 // Legends Guild Basement
)

crateIds.forEach {
    on_obj_option(it, "Search") {
        player.lockingQueue(lockState = LockState.FULL) {
            wait(1)
            player.filterableMessage("You search the crate but find nothing.")
            wait(1)
        }
    }
}
