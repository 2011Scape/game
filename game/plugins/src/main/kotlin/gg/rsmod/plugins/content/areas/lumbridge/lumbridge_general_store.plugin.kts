package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Lumbridge General Store", CoinCurrency()) {
    sampleItems[0] = ShopItem(Items.TINDERBOX_590, 1, resupplyCycles = 1000)
    sampleItems[1] = ShopItem(Items.HAMMER, 1, resupplyCycles = 1000)
    sampleItems[2] = ShopItem(Items.BRONZE_DAGGER, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.EMPTY_POT, 5)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.SHEARS, 2)
    items[3] = ShopItem(Items.BUCKET, 3)
    items[4] = ShopItem(Items.BOWL, 2)
    items[5] = ShopItem(Items.CAKE_TIN, 2)
    items[6] = ShopItem(Items.TINDERBOX_590, 2)
    items[7] = ShopItem(Items.CHISEL, 2)
    items[8] = ShopItem(Items.HAMMER, 5)
    items[9] = ShopItem(Items.NEWCOMER_MAP, 5)
    items[10] = ShopItem(Items.SECURITY_BOOK, 5)
}

on_npc_option(Npcs.SHOPKEEPER, "trade") {
    player.openShop("Lumbridge General Store")
}

on_npc_option(Npcs.SHOP_ASSISTANT, "trade") {
    player.openShop("Lumbridge General Store")
}