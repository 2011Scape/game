package gg.rsmod.plugins.content.objs

val bookcaseIds = listOf(
    Objs.BOOKCASE_45246, Objs.BOOKCASE_45247, // Lumbridge
    Objs.BOOKCASE_2653                        // Draynor
)

val messages = listOf("You don't find anything that you'd ever want to read.", "None of them look very interesting.",
    "You find nothing to interest you.")

bookcaseIds.forEach { id ->
    on_obj_option(id, "Search") {
        player.queue {
            player.lock = LockState.FULL
            player.message("You search the books...")
            wait(2)
            player.unlock()
            wait(2)
            if (world.random(3) == 0 && (player.inventory.hasFreeSpace() && !player.inventory.contains(Items
                .WILDERNESS_MANUAL))) {
                player.queue {
                    player.inventory.add(Items.WILDERNESS_MANUAL)
                    itemMessageBox("You find a book about the wilderness.", Items.WILDERNESS_MANUAL)
                }
            }
            else {
                player.message(messages.random())
            }

        }
    }
}
