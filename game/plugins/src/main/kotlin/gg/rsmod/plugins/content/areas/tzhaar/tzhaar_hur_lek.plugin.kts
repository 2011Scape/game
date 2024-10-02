package gg.rsmod.plugins.content.areas.tzhaar

import gg.rsmod.plugins.content.mechanics.shops.TokkulCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    name = "Tzhaar-Hur-Lek's Ore and Gem Store",
    currency = TokkulCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_NONE,
) {
    items[0] = ShopItem(Items.TIN_ORE, 5, sellPrice = 25)
    items[1] = ShopItem(Items.COPPER_ORE, 5, sellPrice = 25)
    items[2] = ShopItem(Items.IRON_ORE, 2, sellPrice = 37)
    items[3] = ShopItem(Items.UNCUT_SAPPHIRE, 1, sellPrice = 37)
    items[4] = ShopItem(Items.UNCUT_EMERALD, 1, sellPrice = 75)
    items[5] = ShopItem(Items.UNCUT_ONYX, 1, sellPrice = 2_700_000)
    items[6] = ShopItem(Items.ONYX_BOLT_TIPS, 50, sellPrice = 13_500)
}

on_npc_option(npc = Npcs.TZHAARHURLEK, option = "trade") {
    player.openShop("Tzhaar-Hur-Lek's Ore and Gem Store")
}
