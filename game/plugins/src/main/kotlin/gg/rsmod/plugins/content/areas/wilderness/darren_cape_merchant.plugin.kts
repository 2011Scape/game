package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val DARREN = Npcs.DARREN

create_shop("Darren's Wilderness Cape Shop", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.TEAM4_CAPE, amount = 10)
    items[1] = ShopItem(Items.TEAM14_CAPE, amount = 10)
    items[2] = ShopItem(Items.TEAM24_CAPE, amount = 10)
    items[3] = ShopItem(Items.TEAM34_CAPE, amount = 10)
    items[4] = ShopItem(Items.TEAM44_CAPE, amount = 10)
}

on_npc_option(npc = DARREN, option = "trade") {
    player.openShop("Darren's Wilderness Cape Shop")
}

on_npc_option(npc = DARREN, option = "talk-to") {
    player.queue {
        chatNpc("Hello there!" ,"Are you interested in buying one of my special team capes?")
        when(options("What do team capes do?", "Yes please!", "No thanks.")) {
            1 -> {
                chatPlayer("What do team capes do?")
                chatNpc("If you and your friends all wear the same team cape,", "you'll be less likely to attack your friends by accident," ,"and you'll find it easier to attack everyone else.")
                chatNpc("They're very useful in Clan Wars and other", "player-vs-player combat areas where you might", "come across friends you don't want to harm.")
                chatNpc("So would you like to buy one?")
                when (options("Yes please!", "No thanks.")) {
                    1 -> player.openShop("Darren's Wilderness Cape Shop")
                    2 -> {
                        chatPlayer("No thanks.")
                    }
                }
            }
            2 -> player.openShop("Darren's Wilderness Cape Shop")
            3 -> chatPlayer("No thanks.")
        }
    }
}