package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Draynor Seed Market", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.POTATO_SEED, 10)
    items[1] = ShopItem(Items.ONION_SEED, 10)
    items[2] = ShopItem(Items.CABBAGE_SEED, 10)
    items[3] = ShopItem(Items.TOMATO_SEED, 0)
    items[4] = ShopItem(Items.SWEETCORN_SEED, 0)
    items[5] = ShopItem(Items.STRAWBERRY_SEED, 0)
    items[6] = ShopItem(Items.WATERMELON_SEED, 0)
    items[7] = ShopItem(Items.BARLEY_SEED, 10)
    items[8] = ShopItem(Items.JUTE_SEED, 10)
    items[9] = ShopItem(Items.MARIGOLD_SEED, 10)
    items[10] = ShopItem(Items.ROSEMARY_SEED, 10)
    items[11] = ShopItem(Items.HAMMERSTONE_SEED, 10)
    items[12] = ShopItem(Items.ASGARNIAN_SEED, 10)
    items[13] = ShopItem(Items.YANILLIAN_SEED, 10)
    items[14] = ShopItem(Items.KRANDORIAN_SEED, 10)
    items[15] = ShopItem(Items.WILDBLOOD_SEED, 10)
}

on_npc_option(Npcs.OLIVIA_2572, "trade") {
    player.openShop("Draynor Seed Market")
}