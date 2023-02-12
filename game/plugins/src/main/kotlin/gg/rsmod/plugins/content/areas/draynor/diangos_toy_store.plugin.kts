package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Diango's Toy Store", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_NONE, containsSamples = false) {
    items[0] = ShopItem(Items.TOY_HORSEY, 10)
    items[1] = ShopItem(Items.TOY_HORSEY_2522, 10)
    items[2] = ShopItem(Items.TOY_HORSEY_2524, 10)
    items[3] = ShopItem(Items.TOY_HORSEY_2526, 10)
    items[4] = ShopItem(Items.SPINNING_PLATE, 10)
    items[5] = ShopItem(Items.TOY_KITE, 10)
    items[6] = ShopItem(Items.CELEBRATION_CAKE, 10)
    items[7] = ShopItem(Items.CELEBRATION_CANDLES, 100)
    items[8] = ShopItem(Items.FIREWORK_20720, 50)
    items[9] = ShopItem(Items.FIRECRACKER, 400)
    items[10] = ShopItem(Items.BUBBLE_MAKER, 10)
    items[11] = ShopItem(Items.CONFETTI, 10)
    items[12] = ShopItem(Items.SOUVENIR_MUG, 10)
}

on_npc_option(Npcs.DIANGO, "trade") {
    player.openShop("Diango's Toy Store")
}