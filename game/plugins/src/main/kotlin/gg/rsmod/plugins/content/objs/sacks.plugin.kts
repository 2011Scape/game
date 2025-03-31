package gg.rsmod.plugins.content.objs

val sackIds = listOf(Objs.SACKS_3132)

sackIds.forEach {
    on_obj_option(it, "Search") {
        player.lockingQueue(lockState = LockState.FULL) {
            player.message("There's nothing interesting in these sacks.")
            wait(1)
        }
    }
}
