package gg.rsmod.plugins.content.npcs.man

val WOMEN = arrayOf(Npcs.WOMAN_BRUNETTE_LONGSKIRT_4, Npcs.WOMAN_BRUNETTE_PONYTAIL_5, Npcs.WOMAN_WOMAN_BLOND_PONYTAIL_6)

WOMEN.forEach { woman ->
    on_npc_option(npc = woman, option = "talk-to") {
        player.queue { chat(this) }
    }
}

suspend fun chat(it: QueueTask) {
    it.chatPlayer("Hello, how's it going?")
    when (world.random(18)) {
        0 -> it.chatNpc("I'm fine, how are you?")
        1 -> {
            it.chatNpc("I'm very well thank you.")
            it.chatPlayer("I'm very well thank you.")
        }
        2 -> it.chatNpc("Hello there! Nice weather we've been having.")
        3 ->
            it.chatNpc(
                "I'm a little worried - I've heard there's lots",
                "of people going about, killing citizens at random.",
            )
        4 -> {
            it.chatNpc(
                "Not too bad, but I'm a little worried about",
                "the increase of goblins these days.",
            )
            it.chatPlayer("Don't worry, I'll kill them.")
        }
        5 -> it.chatNpc("Hello.")
        6 ->
            it.chatNpc(
                "I think we need a new king. The one we've got",
                "isn't very good.",
            )
        7 -> it.chatNpc("Not too bad thanks.")
        8 -> {
            it.chatNpc("Who are you?")
            it.chatPlayer("I'm a bold adventurer.")
            it.chatNpc("Ah, a very noble profession.")
        }
        9 -> it.chatNpc("I'm sorry I can't help you there.")
        10 -> it.chatNpc("I'm busy right now.")
        11 -> it.chatNpc("Get out of my way, I'm in a hurry.")
        12 -> it.chatNpc("Do I know you? I'm in a hurry.")
        13 -> {
            it.chatNpc("How can I help you?")
            when (
                it.options(
                    "Do you want to trade?",
                    "I'm in search of a quest.",
                    "I'm in search of enemies to kill.",
                )
            ) {
                1 -> {
                    it.chatPlayer("Do you want to trade?")
                    it.chatNpc(
                        "No, I have nothing I wish to get rid of. If you want",
                        "to do some trading, there are plenty of shops and market",
                        "stalls around though.",
                    )
                }
                2 -> {
                    it.chatPlayer("I'm in search of a quest.")
                    it.chatNpc("I'm sorry I can't help you there.")
                }
                3 -> {
                    it.chatPlayer("I'm in search of enemies to kill.")
                    it.chatNpc(
                        "I've heard there are many fearsome creatures",
                        "that dwell under the ground...",
                    )
                }
            }
        }
        14 -> it.chatNpc("That's none of your business.")
        15 -> it.chatNpc("Sorry, I'm not looking for any help right now.")
        16 -> it.chatNpc("I'm just enjoying the day, thank you.")
        17 -> it.chatNpc("No, I don't want to buy anything!")
    }
}
