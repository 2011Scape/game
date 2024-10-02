package gg.rsmod.plugins.content.areas.varrock

on_npc_option(npc = Npcs.BARTENDER_733, option = "talk-to", lineOfSightDistance = 3) {
    player.queue {
        bartenderDialogue(this)
    }
}

suspend fun bartenderDialogue(it: QueueTask) {
    it.chatNpc("What can I do yer for?")
    when (
        it.options(
            "A glass of your finest ale please.",
            "Where an adventurer might make his fortune?",
            "Do you know where I can get some good equipment?",
        )
    ) {
        1 -> {
            it.chatPlayer("A glass of your finest ale please.")
            it.chatNpc("No problemo. That'll be 2 coins.")
            if (it.player.inventory.hasFreeSpace()) {
                if (it.player.inventory
                        .remove(Items.COINS_995, 2)
                        .hasSucceeded()
                ) {
                    it.player.inventory.add(Items.BEER)
                    it.itemMessageBox("The bartender hands you a fine ale.", Items.BEER)
                } else {
                    it.chatNpc(
                        "You don't have enough coins in your inventory.",
                        "for me to sell you a beer.",
                    )
                }
            } else {
                it.chatNpc(
                    "You don't have enough free space in your inventory ",
                    "for me to sell you a beer.",
                )
            }
        }
        2 -> {
            it.chatPlayer("Can you recommend where an adventurer might make his fortune?", wrap = true)
            it.chatNpc("Ooh I don't know if I should be giving away information, makes the game to easy", wrap = true)
            when (
                it.options(
                    "Oh ah well...",
                    "Game? What are you talking about?",
                    "Just a small clue?",
                    "Do you know where I can get some good equipment?",
                )
            ) {
                1 -> {
                    it.chatPlayer("Oh ah well...")
                }
                2 -> {
                    it.chatPlayer("Game? What are you talking about?")
                    it.chatNpc("This would around us... is an online game...", "called RuneScape.")
                    it.chatPlayer(
                        "Nope, still don't understand what you are talking about. What does 'online' mean?",
                        wrap = true,
                    )
                    it.chatNpc(
                        "It's a sort of connection between magic boxes across the world, big boxes on" +
                            "people's desktops and little ones people can carry. They talk to each other to play games.",
                        wrap = true,
                    )
                    it.chatPlayer("I give up. You're obviously completely mad!")
                }
                3 -> {
                    it.chatPlayer("Just a small clue?")
                    it.chatNpc(
                        "Go and talk to the bartender at the Jolly Boar Inn, he doesn't seem to mind giving away clues.",
                        wrap = true,
                    )
                }
                4 -> {
                    it.chatPlayer("Do you know where I can get some good equipment?")
                    it.chatNpc(
                        "Well, there's the sword shop across the road, or there's also all sorts of shops up around the market.",
                        wrap = true,
                    )
                }
            }
        }
        3 -> {
            it.chatPlayer("Do you know where I can get some good equipment?")
            it.chatNpc(
                "Well, there's the sword shop across the road, or there's also all sorts of shops up around the market.",
                wrap = true,
            )
        }
    }
}
