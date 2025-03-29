package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(Objs.BOOKCASE_45246, "Search") {
    player.queue {
        player.lock = LockState.FULL
        player.message("You search the books...")
        wait(2)
        player.unlock()
        wait(2)
        player.message("You don't find anything that you'd ever want to read.")
    }
}

on_obj_option(Objs.BOOKCASE_45247, "Search") {
    player.queue {
        player.lock = LockState.FULL
        player.message("You search the books...")
        wait(2)
        player.unlock()
        wait(2)
        player.message("None of them look very interesting.")
    }
}
