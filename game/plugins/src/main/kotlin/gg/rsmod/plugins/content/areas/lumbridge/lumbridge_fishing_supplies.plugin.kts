package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Lumbridge Fishing Supplies", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.SMALL_FISHING_NET, 1, resupplyCycles = 1000)
    sampleItems[1] = ShopItem(Items.CRAYFISH_CAGE, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.SMALL_FISHING_NET, 5)
    items[1] = ShopItem(Items.FISHING_ROD, 10)
    items[2] = ShopItem(Items.FLY_FISHING_ROD, 10)
    items[3] = ShopItem(Items.CRAYFISH_CAGE, 10)
    items[4] = ShopItem(Items.FISHING_BAIT, 1000)
    items[5] = ShopItem(Items.FEATHER, 1000)
    items[6] = ShopItem(Items.RAW_SHRIMPS, 0)
    items[7] = ShopItem(Items.RAW_SARDINE, 0)
    items[8] = ShopItem(Items.RAW_HERRING, 0)
    items[9] = ShopItem(Items.RAW_ANCHOVIES, 0)
    items[10] = ShopItem(Items.RAW_TROUT, 0)
    items[11] = ShopItem(Items.RAW_PIKE, 0)
    items[12] = ShopItem(Items.RAW_SALMON, 0)
    items[13] = ShopItem(Items.RAW_CRAYFISH, 0)
}

on_npc_option(Npcs.HANK, "trade") {
    player.openShop("Lumbridge Fishing Supplies")
}
