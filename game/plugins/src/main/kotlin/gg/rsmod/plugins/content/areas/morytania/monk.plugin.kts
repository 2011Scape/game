package gg.rsmod.plugins.content.areas.morytania

on_npc_option(npc = Npcs.MONK_3075, option = "talk-to") {
    player.queue {
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    it.player.queue {
        chatNpc(
            "Excuse me...oh, wait, I thought you were someone else.",
        )
        chatPlayer(
            "No problem. Have a good day!",
        )
        chatNpc(
            "And yourself.",
        )
    }
}
