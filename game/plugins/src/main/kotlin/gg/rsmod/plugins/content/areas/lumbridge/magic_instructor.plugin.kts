package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.MAGIC_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Hello there adventurer, I am the Magic combat tutor. ", "Would you like to learn about magic combat, or perhaps ", "how to make runes?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("Tell me about magic combat please.", "How do I make runes?", "I'd like training runes.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("Tell me about magic combat.")

        }
        2 -> {

        }
        3 -> {

        }
        4 -> {

        }
        5 -> {
            it.chatPlayer("Goodbye.")
        }

    }
}