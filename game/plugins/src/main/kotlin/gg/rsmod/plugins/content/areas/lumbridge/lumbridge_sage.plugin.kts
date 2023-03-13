package gg.rsmod.plugins.content.areas.lumbridge


on_npc_option(npc = Npcs.LUMBRIDGE_SAGE, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}

suspend fun mainDialogue(it: QueueTask, skipStart: Boolean) {
    if (!skipStart)
    it.chatNpc("Greetings, adventurer. How can I help you?")
    when(it.options("Who are you?", "Tell me about the town of Lumbridge.", "I'm fine for now, thanks.")) {
        1 -> {
            optionOne(it)
        }
        2 -> {
            optionTwo(it)
        }
        3 -> {
            optionThree(it)
        }
    }
}

suspend fun optionOne(it: QueueTask) {
    it.chatNpc("I am Phileas, the Lumbridge Sage. In times past, people came from all around to ask me for advice. My renown", "seems to have diminished somewhat in recent years, though. Can I help you with anything?")
    mainDialogue(it, true)
}

suspend fun optionTwo(it: QueueTask) {
    it.chatNpc("Lumbridge is one of the older towns in the human-controlled kingdoms. It was founded over two hundred years", "ago towards the end of the Fourth Age. It's called Lumbridge because of this bridge built over the River Lum.")
    it.chatNpc("The town is governed by Duke Horacio, who is a good friend of our monarch, King Roald of Misthalin.")
    mainDialogue(it, true)
}

suspend fun optionThree(it: QueueTask) {
    it.chatNpc("Good adventuring, traveller.")
}