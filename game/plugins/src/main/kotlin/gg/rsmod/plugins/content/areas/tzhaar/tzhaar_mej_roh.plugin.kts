package gg.rsmod.plugins.content.areas.tzhaar

import gg.rsmod.plugins.content.mechanics.shops.TokkulCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    name = "TzHaar-Mej-Roh's Rune Store",
    currency = TokkulCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_NONE,
) {
    items[0] = ShopItem(Items.FIRE_RUNE, 1000, sellPrice = 26)
    items[1] = ShopItem(Items.WATER_RUNE, 1000, sellPrice = 26)
    items[2] = ShopItem(Items.EARTH_RUNE, 1000, sellPrice = 26)
    items[3] = ShopItem(Items.AIR_RUNE, 1000, sellPrice = 26)
    items[4] = ShopItem(Items.MIND_RUNE, 1000, sellPrice = 26)
    items[5] = ShopItem(Items.BODY_RUNE, 1000, sellPrice = 24)
    items[6] = ShopItem(Items.CHAOS_RUNE, 300, sellPrice = 210)
    items[7] = ShopItem(Items.DEATH_RUNE, 100, sellPrice = 465)
}

on_npc_option(npc = Npcs.TZHAARMEJROH, option = "trade") {
    player.openShop("TzHaar-Mej-Roh's Rune Store")
}
