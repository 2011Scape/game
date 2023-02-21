package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(Npcs.DONIE, option = "talk-to") {
    player.queue { chat(this) }
}
suspend fun chat(it: QueueTask) {
    it.chatPlayer("Hello Donnie, how's it going?")
    when (world.random(17)) {
        0 -> it.chatNpc("Not too bad thanks.")
        1 -> {
            it.chatNpc("I'm fine, how are you?")
            it.chatPlayer("Very well thank you.")
        }

        2 -> it.chatNpc("I think we need a new king.", "The one we've got isn't very good.")
        3 -> it.chatNpc("Get out of my way, I'm in a hurry!")
        4 -> it.chatNpc("Do I know you? I'm in a hurry!")
        5 -> it.chatNpc("None of your business.")
        6 -> {
            it.chatNpc("Not too bad, but I'm a little worried about the", "increase of goblins these days.")
            it.chatPlayer("Don't worry, I'll kill them.")
        }

        7 -> it.chatNpc("I'm busy right now.")
        8 -> {
            it.chatNpc("Who are you?")
            it.chatPlayer("I'm a bold adventurer.")
            it.chatNpc("Ah, a very noble profession.")
        }

        9 -> it.chatNpc("No I don't have any spare change.")
        10 -> {
            it.chatNpc("How can I help you?")
            when (
                it.options(
                    "Do you want to trade?",
                    "I'm in search of a quest.",
                )
            ) {
                1 -> {
                    it.chatPlayer("Do you want to trade?")
                    it.chatNpc("No, I have nothing I wish to get rid of.", "If you want to do some trading there are plenty of shops", "and market stalls around.")
                }

                2 -> {
                    it.chatPlayer("I'm in search of a quest.")
                    it.chatNpc("I hear Father Aereck has a ghost problem...")
                }
            }
        }

        11 -> it.chatNpc("I'm very well thank you.")
        12 -> it.chatNpc("I'm a little worried. I've heard there are people going", "about killing citizens at random!")
        13 -> it.chatNpc("That is classified information.")
        14 -> it.chatNpc("Yo, wassup!")
        15 -> it.chatNpc("No, I don't want to buy anything!")
        16 -> it.chatNpc("Hello.")
    }
}