package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Apprentice's Smithing Shop", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_NONE, containsSamples = false) {
    items[0] = ShopItem(Items.BRONZE_PICKAXE, 1)
    items[1] = ShopItem(Items.BRONZE_HATCHET, 1)
    items[2] = ShopItem(Items.BRONZE_DAGGER, 1)
    items[3] = ShopItem(Items.BRONZE_CHAINBODY, 1)
}

on_npc_option(Npcs.APPRENTICE_SMITH, option = "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatPlayer("Hello, how's it going?")
    when (world.random(2)) {
        /*0 -> it.chatNpc("Not too bad thanks.")
        1 -> {
            it.chatNpc("I'm fine, how are you?")
            it.chatPlayer("Very well thank you.")
        }

        2 -> it.chatNpc("I think we need a new king...", "The one we've got isn't very good.")
        3 -> it.chatNpc("Get out of my way, I'm in a hurry!")
        4 -> it.chatNpc("Do I know you? I'm in a hurry!")
        5 -> it.chatNpc("None of your business.")
        6 -> {
            it.chatNpc("Not too bad, but I'm a little worried about the increase of goblins these days.")
            it.chatPlayer("Don't worry, I'll kill them.")
        }

        7 -> it.chatNpc("I'm busy right now.")
        8 -> {
            it.chatNpc("Who are you?")
            it.chatPlayer("I'm a bold adventurer.")
            it.chatNpc("Ah, a very noble profession.")
        }

        9 -> it.chatNpc("No, I don't have any spare change.")*/
        1 -> {
            it.chatNpc("How can I help you?")
            when (
                it.options(
                    "Do you have anything for sale?",
                    "I'm in search of a quest.",
                    "I'm in search of enemies to kill.",
                )
            ) {
                1 -> {
                    it.chatPlayer("Do you have anything for sale?")
                    it.chatNpc("Here's what the smithing shop is selling.")
                    it.player.openShop("Apprentice's Smithing Shop")
                }

                2 -> {
                    it.chatPlayer("I'm in search of a quest.")
                    it.chatNpc("I'm sorry I can't help you there.")
                }

                3 -> {
                    it.chatPlayer("I'm in search of enemies to kill.")
                    it.chatNpc("I've heard there are many fearsome creatures", "that dwell under the ground...")
                }
            }
        }

        11 -> it.chatNpc("I'm very well thank you.")
        13 -> it.chatNpc("I'm a little worried. I've heard there are people going", "about killing citizens at random!")
        14 -> it.chatNpc("That is classified information.")
        15 -> it.chatNpc("A good axe is like a trusty friend,", "it'll never let you down when you need it most!")
        16 -> it.chatNpc("No, I don't want to buy anything!")
        17 -> it.chatNpc(
            "I remember the time when I sold an axe to a",
            "famous adventurer. They used it to chop down a",
            "giant tree that was blocking a road. True story!",
        )

        18 -> it.chatNpc(
            "You know, some people say that an axe is just a tool.",
            "But I say, it's much more than that. It's a",
            "symbol of power and determination.",
        )

        19 -> it.chatNpc(
            "There's something special about the sound",
            "an axe makes when it hits a target just right.",
            "It's like music to my ears!",
        )
    }
}
