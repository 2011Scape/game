package gg.rsmod.plugins.content.areas.falador.dwarven_mines

val farli = Objs.FARLI
val farliNPC = Npcs.DWARF_5880
on_obj_option(farli, option = "talk-to") {
    player.queue {
        farliDialogue(this)
    }
}

suspend fun farliDialogue(it: QueueTask) {
    it.chatNpc("Hello there!", npc = farliNPC, title = "Farli")
    it.chatPlayer("Hi.")
    it.chatNpc("What brings you down here then?", npc = farliNPC, title = "Farli")
    val option =
        it.options(
            "What is this place?",
            "Who are you?",
            "What does this pulley do?",
            "Actually, I don't want to talk to you.",
        )
    when (option) {
        1 -> whatIsThisPlace(it)
        2 -> whoAreYou(it)
        3 -> whatDoesThisPulleyDo(it)
        4 -> dontWantToTalk(it)
    }
}

suspend fun whatIsThisPlace(it: QueueTask) {
    it.chatPlayer("What is this place?")
    it.chatNpc("It's a cavern.", npc = farliNPC, title = "Farli")
    it.chatPlayer("Really? Great, thanks for that.")
    it.chatNpc("No problem!", npc = farliNPC, title = "Farli")
    it.chatPlayer("So, is there actually anything in this cavern?")
    it.chatNpc("Well, there's rocks. A bit of water here and there too.", npc = farliNPC, title = "Farli")
    it.chatPlayer(
        "Right. Good to know. I'm glad you were here, actually,",
        "because, you know, I'd have been lost without that.",
    )
    it.chatNpc("Happy to help!", npc = farliNPC, title = "Farli")
    // (End of dialogue)
}

suspend fun whoAreYou(it: QueueTask) {
    it.chatPlayer("Who are you?")
    it.chatNpc(
        "I'm Farli. I came down here to explore the moment",
        "the cavern entrance opened up.",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("Have you discovered anything down there?")
    it.chatNpc(
        "Well, I did stumble across some nice-looking deposits of ore",
        "and some strange fish in the waters, but I haven't really",
        "been able to investigate further.",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("Why not?")
    it.chatNpc(
        "It's as if the rocks are moving...",
        "Whenever I've tried to map this place, I find rocks are in",
        "different places to when I checked before!",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("Moving rocks? Are you sure you haven't been drinking?")
    it.chatNpc(
        "Well, yes. I mean no. Look, that's not important.",
        "What is important is that the rocks down here are moving!",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("I'll be sure to check that out...")
    it.chatNpc(
        "It's true! One time I was mining a rock,",
        "or what I thought was a rock, and it upped",
        "and ran off with my best pickaxe!",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("...")
    // (End of dialogue)
}

suspend fun whatDoesThisPulleyDo(it: QueueTask) {
    it.chatPlayer("What does this pulley do?")
    it.chatNpc("Oh, that's just to get my supplies in and out of the cavern.", npc = farliNPC, title = "Farli")
    it.chatPlayer("How does that work then?")
    it.chatNpc(
        "The miners above use the mine cart system to move about",
        "what we need from Keldagrim.",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer(
        "Hmm, that could be handy for depositing things into my bank.",
        "You think you could arrange that?",
    )
    it.chatNpc(
        "Hmm, maybe. I suppose if you're down here to help then",
        "I can sort something out.",
        npc = farliNPC,
        title = "Farli",
    )
    it.chatPlayer("Thanks!")
    // (End of dialogue)
}

suspend fun dontWantToTalk(it: QueueTask) {
    it.chatPlayer("Actually, I don't want to talk to you.")
    it.chatNpc("Hmph! How rude!", npc = farliNPC, title = "Farli")
// (End of dialogue)
}
