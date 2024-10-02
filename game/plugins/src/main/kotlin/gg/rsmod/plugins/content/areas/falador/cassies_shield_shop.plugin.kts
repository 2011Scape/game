package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Cassie's Shield Shop",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.WOODEN_SHIELD, 10)
    items[1] = ShopItem(Items.BRONZE_SQ_SHIELD, 10)
    items[2] = ShopItem(Items.BRONZE_KITESHIELD, 10)
    items[3] = ShopItem(Items.IRON_SQ_SHIELD, 10)
    items[4] = ShopItem(Items.IRON_KITESHIELD, 10)
    items[5] = ShopItem(Items.STEEL_SQ_SHIELD, 10)
    items[6] = ShopItem(Items.STEEL_KITESHIELD, 10)
    items[7] = ShopItem(Items.BLACK_SQ_SHIELD, 10)
    items[8] = ShopItem(Items.BLACK_KITESHIELD, 10)
    items[9] = ShopItem(Items.MITHRIL_SQ_SHIELD, 10)
    items[10] = ShopItem(Items.MITHRIL_KITESHIELD, 10)
}

on_npc_option(Npcs.CASSIE, "trade") {
    player.openShop("Cassie's Shield Shop")
}

on_npc_option(Npcs.CASSIE, option = "talk-to") {
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

        6 -> {
            it.chatNpc("Not too bad, but I'm a little worried about the", "increase of goblins these days.")
            it.chatPlayer("Don't worry, I'll kill them.")
        }

        8 -> {
            it.chatNpc("Who are you?")
            it.chatPlayer("I'm a bold adventurer.")
            it.chatNpc("Ah, a very noble profession.")
        }

        10 -> {
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
                        "Sure! Here's what I have for sale.",
                    )
                    it.player.openShop("Cassie's Shield Shop")
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
    }
}
