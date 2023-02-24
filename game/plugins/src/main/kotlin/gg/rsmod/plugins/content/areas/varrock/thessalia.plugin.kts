package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Thessalia's Fine Clothes", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.WHITE_APRON, 10)
    items[1] = ShopItem(Items.LEATHER_BODY, 10)
    items[2] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[3] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[4] = ShopItem(Items.BROWN_APRON, 10)
    items[5] = ShopItem(Items.PINK_SKIRT, 10)
    items[6] = ShopItem(Items.BLACK_SKIRT, 10)
    items[7] = ShopItem(Items.BLUE_SKIRT, 10)
    items[8] = ShopItem(Items.CAPE, 10)
    items[9] = ShopItem(Items.SILK, 10)
    items[10] = ShopItem(Items.PRIEST_GOWN_428, 10)
    items[11] = ShopItem(Items.PRIEST_GOWN, 10)
    items[12] = ShopItem(Items.NEEDLE, 10)
    items[13] = ShopItem(Items.THREAD, 1000)
}

on_npc_option(npc = Npcs.THESSALIA, option = "Trade") {
    player.openShop("Thessalia's Fine Clothes")
}