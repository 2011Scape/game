package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Fortunato's Fine Wine", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.JUG_OF_WINE, 10)
    items[1] = ShopItem(Items.BOTTLE_OF_WINE, 10)
    items[2] = ShopItem(Items.JUG, 10)
}

on_npc_option(Npcs.FORTUNATO, "trade") {
    player.openShop("Fortunato's Fine Wine")
}