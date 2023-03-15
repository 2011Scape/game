package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Arhein's Store", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_TRADEABLES) {
    items[0] = ShopItem(Items.BUCKET, 30)
    items[1] = ShopItem(Items.BRONZE_PICKAXE, 10)
    items[2] = ShopItem(Items.BOWL, 10)
    items[3] = ShopItem(Items.CAKE_TIN, 10)
    items[4] = ShopItem(Items.TINDERBOX_590, 10)
    items[5] = ShopItem(Items.CHISEL, 10)
    items[6] = ShopItem(Items.HAMMER, 10)
    items[7] = ShopItem(Items.ROPE, 10)
    items[8] = ShopItem(Items.EMPTY_POT, 30)
    items[9] = ShopItem(Items.KNIFE, 10)
}

on_npc_option(npc = Npcs.ARHEIN, option = "trade") {
    player.openShop("Arhein's Store")
}