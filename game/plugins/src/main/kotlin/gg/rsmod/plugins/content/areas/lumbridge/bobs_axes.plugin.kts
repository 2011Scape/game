package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Bob's Brilliant Axes", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.BRONZE_PICKAXE, 1, resupplyCycles = 1000)
    sampleItems[1] = ShopItem(Items.BRONZE_HATCHET, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.BRONZE_PICKAXE, 10)
    items[1] = ShopItem(Items.BRONZE_HATCHET, 10)
    items[2] = ShopItem(Items.IRON_HATCHET, 10)
    items[3] = ShopItem(Items.STEEL_HATCHET, 10)
    items[4] = ShopItem(Items.IRON_BATTLEAXE, 10)
    items[5] = ShopItem(Items.STEEL_BATTLEAXE, 10)
    items[6] = ShopItem(Items.MITHRIL_BATTLEAXE, 10)
}

on_npc_option(Npcs.BOB, "trade") {
    player.openShop("Bob's Brilliant Axes")
}

on_npc_option(Npcs.BOB, option = "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatPlayer("Hello, how's it going?")
    when (world.random(15)) {
        0 -> it.chatNpc("Not too bad thanks.")
        1 -> {
            it.chatNpc("I'm fine, how are you?")
            it.chatPlayer("Very well thank you.")
        }

        2 -> it.chatNpc("I think we need a new king...", "The one we've got isn't very good.")
        3 -> it.chatNpc("Do I know you? I'm in a hurry!")
        4 -> it.chatNpc("None of your business.")
        5 -> {
            it.chatNpc("Not too bad, but I'm a little worried about the", "increase of goblins these days.")
            it.chatPlayer("Don't worry, I'll kill them.")
        }

        6 -> {
            it.chatNpc("Who are you?")
            it.chatPlayer("I'm a bold adventurer.")
            it.chatNpc("Ah, a very noble profession.")
        }

        7 -> {
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
                        "Here's what I have to offer.",)
                    it.player.openShop("Bob's Brilliant Axes")
                }

                2 -> {
                    it.chatPlayer("I'm in search of a quest.")
                    it.chatNpc("Someone just posted a news update on the signpost", "right ouside. That might have some useful information.")
                }

                3 -> {
                    it.chatPlayer("I'm in search of enemies to kill.")
                    it.chatNpc("I've heard there are many fearsome creatures", "that dwell under the ground...")
                }
            }
        }

        8 -> it.chatNpc("I'm very well thank you.")
        9 -> it.chatNpc("I'm a little worried. I've heard there are people going", "about killing citizens at random!")
        10 -> it.chatNpc("That is classified information.")
        11 -> it.chatNpc("A good axe is like a trusty friend,", "it'll never let you down when you need it most!")
        12 -> it.chatNpc(
            "I remember the time when I sold an axe to a",
            "famous adventurer. They used it to chop down a",
            "giant tree that was blocking a road. True story!",
        )

        13 -> it.chatNpc(
            "You know, some people say that an axe is just a tool.",
            "But I say, it's much more than that. It's a",
            "symbol of power and determination.",
        )

        14 -> it.chatNpc(
            "There's something special about the sound",
            "an axe makes when it hits a target just right.",
            "It's like music to my ears!",
        )
    }
}
