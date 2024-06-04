package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.BOB_BARTER_HERBS) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("please, please, please work.")
        1 -> npc.forceChat("I'm in the money!")
        2 -> npc.forceChat("I could have sworn that would have worked.")
        3 -> npc.forceChat("Now what should I buy?")
        4 -> npc.forceChat("Hope this item sells well.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop("Bob Barter's Herbs", CoinCurrency(), stockType = StockType.INFINITE, purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    val itemsList = listOf(
        ShopItem(Items.VIAL_PACK, amount = 250),
        ShopItem(Items.VIAL_OF_WATER_PACK, amount = 500),
        ShopItem(Items.EYE_OF_NEWT_PACK, amount = 150),
        ShopItem(Items.CLEAN_GUAM_NOTED, 250, sellPrice = 250, buyPrice = 150),
        ShopItem(Items.CLEAN_MARRENTILL_NOTED, 300, sellPrice = 300, buyPrice = 150),
        ShopItem(Items.CLEAN_TARROMIN_NOTED, 450, sellPrice = 450, buyPrice = 350),
        ShopItem(Items.CLEAN_HARRALANDER_NOTED, 400, sellPrice = 400, buyPrice = 300),
        ShopItem(Items.CLEAN_RANARR_NOTED, 3100, sellPrice = 3100, buyPrice = 3000),
        ShopItem(Items.CLEAN_TOADFLAX_NOTED, 2600, sellPrice = 2600, buyPrice = 2500),
        ShopItem(Items.CLEAN_IRIT_NOTED, 1600, sellPrice = 1600, buyPrice = 1500),
        ShopItem(Items.CLEAN_SPIRIT_WEED_NOTED, 1900, sellPrice = 1900, buyPrice = 1800),
        ShopItem(Items.CLEAN_AVANTOE_NOTED, 2100, sellPrice = 2100, buyPrice = 2000),
        ShopItem(Items.CLEAN_KWUARM_NOTED, 2600, sellPrice = 2600, buyPrice = 2500),
        ShopItem(Items.CLEAN_SNAPDRAGON_NOTED, 6100, sellPrice = 6100, buyPrice = 6000),
        ShopItem(Items.CLEAN_CADANTINE_NOTED, 3100, sellPrice = 3100, buyPrice = 3000),
        ShopItem(Items.CLEAN_LANTADYME_NOTED, 4100, sellPrice = 4100, buyPrice = 4000),
        ShopItem(Items.CLEAN_DWARF_WEED_NOTED, 5100, sellPrice = 5100, buyPrice = 5000),
        ShopItem(Items.CLEAN_TORSTOL_NOTED, 7100, sellPrice = 7100, buyPrice = 7000),
        ShopItem(Items.CLEAN_WERGALI_NOTED, 1100, sellPrice = 1100, buyPrice = 1000)
    )

    for ((slotId, item) in itemsList.withIndex()) {
        items[slotId] = item
    }
}

on_npc_option(npc = Npcs.BOB_BARTER_HERBS, option = "info-herbs") {
    player.openShop("Bob Barter's Herbs", customPrices = true)
}

on_npc_option(npc = Npcs.BOB_BARTER_HERBS, "talk-to") {
    player.queue {
        chatNpc("Hello, chum, fancy buyin' some designer jewellry? They've come all the way from Ardougne! Most pukka!", wrap = true)
        chatPlayer("Erm, no. I'm all set, thanks.")
        chatNpc("Okay, chum, would you like to show you the very latest potion prices?", wrap = true)
        when(options("Who are you?", "Can you show me the prices for potions?", "I'll leave you to it.", title = "Select an Option")) {
            1 -> {
                chatPlayer("Who are you?", wrap = true)
                chatNpc("Why, I'm Bob! Your friendly seller of smashin' goods!", wrap = true)
                chatPlayer("So what do you have to sell?")
                chatNpc("Oh, not much at the moment. Cuz, ya know, business being so well and cushie.", wrap = true)
                chatPlayer("You don't really look like you're being so successful.", wrap = true)
                chatNpc("You plonka! It's all a show, innit! If I let people knows I'm in good business they'll want a share of the moolah!", wrap = true)
                chatPlayer("You conveniently have a response for everything.", wrap = true)
                chatNpc("That's the Ardougne way, my son.")
                when(options("Can you show me the prices for potions?", "I'll leave you to it.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Can you show me the prices for potions?", wrap = true)
                        player.openShop("Bob Barter's Herbs", customPrices = true)
                    }
                    2 -> {
                        chatPlayer("I'll leave you to it.")
                    }
                }
            }
            2 -> {
                chatPlayer("Can you show me the prices for potions?", wrap = true)
                player.openShop("Bob Barter's Herbs", customPrices = true)
            }
            3 -> {
                chatPlayer("I'll leave you to it.")
            }
        }
    }
}
