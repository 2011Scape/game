package gg.rsmod.plugins.content.areas.lumbridge


on_npc_option(npc = Npcs.WILFRED, option = "talk-to") {
    player.queue { mainChat(this) }
}


suspend fun mainChat(it: QueueTask) {
    when (it.options("Who are you?", "What is that cape you're wearing?", "Nevermind.")) {
        1 -> {
            it.chatPlayer("Who are you?")
            it.chatNpc("My name is Wilfred and I'm the best woodsman in", "Asgarnia! I've spent my life studying the best methods for", "woodcutting. That's why I have this cape, the Skillcape of", "Woodcutting.")
        }
        2 -> {
            it.chatPlayer("What is that cape you're wearing?")
            it.chatNpc("This is a Skillcape of Woodcutting.")
        }
        3 -> {
            it.terminateAction
        }

    }
}