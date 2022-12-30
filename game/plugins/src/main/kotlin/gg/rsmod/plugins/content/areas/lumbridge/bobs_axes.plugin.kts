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