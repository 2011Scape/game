package gg.rsmod.plugins.content.areas.edgeville

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val RICHARD = Npcs.RICHARD

create_shop("Richard's Wilderness Cape Shop", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.TEAM6_CAPE, amount = 10)
    items[1] = ShopItem(Items.TEAM16_CAPE, amount = 10)
    items[2] = ShopItem(Items.TEAM26_CAPE, amount = 10)
    items[3] = ShopItem(Items.TEAM36_CAPE, amount = 10)
    items[4] = ShopItem(Items.TEAM46_CAPE, amount = 10)
}

on_npc_option(npc = RICHARD, option = "trade") {
    player.openShop("Richard's Wilderness Cape Shop")
}

on_npc_option(npc = RICHARD, option = "talk-to") {
    player.queue {
        chatNpc("Hello there!" ,"Are you interested in buying one of my special team capes?")
        when(options("What do team capes do?", "Yes please!", "No thanks.")) {
            1 -> {
                chatPlayer("What do team capes do?")
                chatNpc("If you and your friends all wear the same team cape,", "you'll be less likely to attack your friends by accident," ,"and you'll find it easier to attack everyone else.")
                chatNpc("They're very useful in Clan Wars and other", "player-vs-player combat areas where you might", "come across friends you don't want to harm.")
                chatNpc("So would you like to buy one?")
                when (options("Yes please!", "No thanks.")) {
                    1 -> player.openShop("Richard's Wilderness Cape Shop")
                    2 -> {
                        chatPlayer("No thanks.")
                    }
                }
            }
            2 -> player.openShop("Richard's Wilderness Cape Shop")
            3 -> chatPlayer("No thanks.")
        }
    }
}