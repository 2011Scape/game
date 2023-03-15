package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Hickton's Archery Emporium", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.BRONZE_BOLTS, 300)
    items[1] = ShopItem(Items.BRONZE_ARROW, 1000)
    items[2] = ShopItem(Items.IRON_ARROW, 100)
    items[3] = ShopItem(Items.STEEL_ARROW, 0)
    items[4] = ShopItem(Items.MITHRIL_ARROW, 0)
    items[5] = ShopItem(Items.ADAMANT_ARROW, 0)
    items[6] = ShopItem(Items.RUNE_ARROW, 0)
    items[7] = ShopItem(Items.BRONZE_BRUTAL, 0)
    items[8] = ShopItem(Items.IRON_BRUTAL, 0)
    items[9] = ShopItem(Items.STEEL_BRUTAL, 0)
    items[10] = ShopItem(Items.BLACK_BRUTAL, 0)
    items[11] = ShopItem(Items.MITHRIL_BRUTAL, 0)
    items[12] = ShopItem(Items.ADAMANT_BRUTAL, 0)
    items[13] = ShopItem(Items.RUNE_BRUTAL, 0)
    items[14] = ShopItem(Items.BRONZE_ARROWTIPS, 100)
    items[15] = ShopItem(Items.IRON_ARROWTIPS, 100)
    items[16] = ShopItem(Items.STEEL_ARROWTIPS, 100)
    items[17] = ShopItem(Items.MITHRIL_ARROWTIPS, 100)
    items[18] = ShopItem(Items.ADAMANT_ARROWTIPS, 30)
    items[19] = ShopItem(Items.RUNE_ARROWTIPS, 10)
    items[20] = ShopItem(Items.SHORTBOW, 10)
    items[21] = ShopItem(Items.LONGBOW, 10)
    items[22] = ShopItem(Items.CROSSBOW, 10)
    items[23] = ShopItem(Items.OAK_SHORTBOW, 10)
    items[24] = ShopItem(Items.OAK_LONGBOW, 10)
    items[25] = ShopItem(Items.COMP_OGRE_BOW, 10)
    items[26] = ShopItem(Items.STUDDED_BODY, 10)
    items[27] = ShopItem(Items.STUDDED_CHAPS, 10)

}

on_npc_option(npc = Npcs.HICKTON, option = "trade") {
    player.openShop("Hickton's Archery Emporium")
}