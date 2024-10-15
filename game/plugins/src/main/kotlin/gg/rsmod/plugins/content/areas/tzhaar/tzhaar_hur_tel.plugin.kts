package gg.rsmod.plugins.content.areas.tzhaar

import gg.rsmod.plugins.content.mechanics.shops.TokkulCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    name = "Tzhaar-Hur-Tel's Equipment Store",
    currency = TokkulCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_NONE,
) {
    items[0] = ShopItem(Items.TOKTZXILUL, 10, sellPrice = 375)
    items[1] = ShopItem(Items.TOKTZXILAK, 10, sellPrice = 60_000)
    items[2] = ShopItem(Items.TOKTZKETXIL, 10, sellPrice = 67_500)
    items[3] = ShopItem(Items.TOKTZXILEK, 10, sellPrice = 37_500)
    items[4] = ShopItem(Items.TOKTZMEJTAL, 10, sellPrice = 52_500)
    items[5] = ShopItem(Items.TZHAARKETEM, 10, sellPrice = 45_000)
    items[6] = ShopItem(Items.TZHAARKETOM, 10, sellPrice = 75_000)
    items[7] = ShopItem(Items.OBSIDIAN_CAPE, 10, sellPrice = 90_000)
}

on_npc_option(npc = Npcs.TZHAARHURTEL, option = "trade") {
    player.openShop("Tzhaar-Hur-Tel's Equipment Store")
}
