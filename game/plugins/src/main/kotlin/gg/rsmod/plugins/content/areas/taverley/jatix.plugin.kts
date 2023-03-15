package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */
create_shop("Jatix's Herblore Shop", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.VIAL, 300)
    items[1] = ShopItem(Items.PESTLE_AND_MORTAR, 10)
    items[2] = ShopItem(Items.EYE_OF_NEWT, 1000)
}

on_npc_option(npc = Npcs.JATIX, option = "trade") {
    player.openShop("Jatix's Herblore Shop")
}