package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.RELOBO_BLINYO_LOGS) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("Just one more offer! One more!")
        1 -> npc.forceChat("Such a joy it is to be in this building.")
        2 -> npc.forceChat("Yes! Yet another great deal.")
        3 -> npc.forceChat("My people shall be so proud!")
        4 -> npc.forceChat("Hmmm. These numbers don't quite add up.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop("Relobo Blingo's Logs", CoinCurrency(), StockType.INFINITE, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.TINDERBOX_590, 1)
    sampleItems[1] = ShopItem(Items.KNIFE, 1)
    items[0] = ShopItem(Items.LOGS_NOTED, 50, sellPrice = 50)
    items[1] = ShopItem(Items.OAK_LOGS_NOTED, 100, sellPrice = 100)
    items[2] = ShopItem(Items.TEAK_LOGS_NOTED, 200, sellPrice = 200)
    items[3] = ShopItem(Items.MAHOGANY_LOGS_NOTED, 500, sellPrice = 500)
    items[4] = ShopItem(Items.YEW_LOGS_NOTED, 450, sellPrice = 450)
    items[5] = ShopItem(Items.ACHEY_TREE_LOGS_NOTED, 50, sellPrice = 50)
    items[6] = ShopItem(Items.WILLOW_LOGS_NOTED, 30, sellPrice = 30)
    items[7] = ShopItem(Items.MAPLE_LOGS_NOTED, 500, sellPrice = 500)
    items[8] = ShopItem(Items.ARCTIC_PINE_LOGS_NOTED, 150, sellPrice = 150)
    items[9] = ShopItem(Items.MAGIC_LOGS_NOTED, 1000, sellPrice = 1000)
}

on_npc_option(npc = Npcs.RELOBO_BLINYO_LOGS, option = "info-logs") {
    player.openShop("Relobo Blingo's Logs", customPrices = true)
}

on_npc_option(npc = Npcs.RELOBO_BLINYO_LOGS, "talk-to") {
    player.queue {
        chatNpc("Hello, my friend. Would you like me to show you the prices of logs?", wrap = true)
        when(options("You look like you've travelled a fair distance.", "Okay, show me the prices of logs.",
            "Sorry, I need to be making tracks.", title = "Select an Option")) {
            1 -> {
                chatPlayer("You look like you've travelled a fair distance.", wrap = true)
                chatNpc("What gave me away?", wrap = true)
                chatPlayer("I don't mean to be rude, but the clothing and hair, for starters.", wrap = true)
                chatNpc("Ah, yes. I'm from Shilo Village on Karamja, it's a style I've had since I was little.", wrap = true)
                chatPlayer("Then tell me, why are you so far from home?", wrap = true)
                chatNpc("This Grand Exchange! Isn't it marvellous! I've never seen anything like it in my life.", wrap = true)
                chatNpc("My people were saddened when I chose to leave home and travel here, but I hope to make some serious profit, then return to my tribe.", wrap = true)
                chatPlayer("So, what are you selling?", wrap = true)
                chatNpc("Logs! Of all kinds! That's my plan, at least. Living in the jungle as we do, my people understand exotic wood very well indeed.", wrap = true)
                when(options("Okay, show me the prices of logs.", "Sorry, I need to be making tracks.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Okay, show me the prices of logs.", wrap = true)
                        player.openShop("Relobo Blingo's Logs", customPrices = true)
                    }
                    2 -> {
                        chatPlayer("Sorry, I need to be making tracks.")
                        chatPlayer("Okay, nice talking to you!")
                    }
                }
            }
            2 -> {
                chatPlayer("Okay, show me the prices of logs.", wrap = true)
                player.openShop("Relobo Blingo's Logs", customPrices = true)
            }
            3 -> {
                chatPlayer("Sorry, I need to be making tracks.")
                chatNpc("Okay, nice talking to you!")
            }
        }
    }
}
