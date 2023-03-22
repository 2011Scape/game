package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    "Burthorpe Supplies",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
    containsSamples = false
) {
    items[0] = ShopItem(Items.EMPTY_POT, amount = 30)
    items[1] = ShopItem(Items.JUG, amount = 10)
    items[2] = ShopItem(Items.SHEARS, amount = 10)
    items[3] = ShopItem(Items.BUCKET, amount = 30)
    items[4] = ShopItem(Items.TINDERBOX_590, amount = 10)
    items[5] = ShopItem(Items.CHISEL, amount = 10)
    items[6] = ShopItem(Items.HAMMER, amount = 10)
    items[7] = ShopItem(Items.KNIFE, amount = 10)
    items[8] = ShopItem(Items.EMPTY_CUP, amount = 10)
}

on_npc_option(Npcs.WISTAN, "trade") {
    player.openShop("Burthorpe Supplies")
}
