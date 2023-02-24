package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Aubury's Rune Shop", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.AIR_RUNE, 30, resupplyCycles = 99)
    sampleItems[1] = ShopItem(Items.MIND_RUNE, 30, resupplyCycles = 99)
    items[0] = ShopItem(Items.FIRE_RUNE, 300)
    items[1] = ShopItem(Items.WATER_RUNE, 300)
    items[2] = ShopItem(Items.AIR_RUNE, 300)
    items[3] = ShopItem(Items.EARTH_RUNE, 300)
    items[4] = ShopItem(Items.MIND_RUNE, 100)
    items[5] = ShopItem(Items.BODY_RUNE, 100)
    items[6] = ShopItem(Items.CHAOS_RUNE, 30)
    items[7] = ShopItem(Items.DEATH_RUNE, 10)
}

on_npc_option(npc = Npcs.AUBURY, option = "Trade") {
    player.openShop("Aubury's Rune Shop")
}