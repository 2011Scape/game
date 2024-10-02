package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Lowe's Archery Emporium", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.BRONZE_ARROW, 30, resupplyCycles = 99)
    sampleItems[1] = ShopItem(Items.SHORTBOW, 1, resupplyCycles = 1000)
    sampleItems[2] = ShopItem(Items.LONGBOW, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.BRONZE_ARROW, 300)
    items[1] = ShopItem(Items.IRON_ARROW, 100)
    items[2] = ShopItem(Items.STEEL_ARROW, 100)
    items[3] = ShopItem(Items.MITHRIL_ARROW, 100)
    items[4] = ShopItem(Items.ADAMANT_ARROW, 30)
    items[5] = ShopItem(Items.BRONZE_BOLTS, 300)
    items[6] = ShopItem(Items.SHORTBOW, 10)
    items[7] = ShopItem(Items.LONGBOW, 10)
    items[8] = ShopItem(Items.OAK_SHORTBOW, 10)
    items[9] = ShopItem(Items.OAK_LONGBOW, 10)
    items[10] = ShopItem(Items.WILLOW_SHORTBOW, 10)
    items[11] = ShopItem(Items.WILLOW_LONGBOW, 10)
    items[12] = ShopItem(Items.MAPLE_SHORTBOW, 10)
    items[13] = ShopItem(Items.MAPLE_LONGBOW, 10)
    items[14] = ShopItem(Items.CROSSBOW, 10)
    items[15] = ShopItem(Items.LEATHER_BODY, 10)
    items[16] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[17] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[18] = ShopItem(Items.LEATHER_CHAPS, 10)
    items[19] = ShopItem(Items.STUDDED_CHAPS, 10)
    items[20] = ShopItem(Items.STUDDED_BODY, 10)
}

on_npc_option(npc = Npcs.LOWE, option = "Trade") {
    player.openShop("Lowe's Archery Emporium")
}

on_npc_option(npc = Npcs.LOWE, "talk-to") {
    player.queue {
        chatNpc("Welcome to Lowe's Archery Emporium. Do you want", "to see my wares?")
        when(options("Yes, please.", "No, I prefer to bash things close up.", title = "Select an Option")) {
            1 -> {
                player.openShop("Lowe's Archery Emporium")
            }
            2 -> {
                chatPlayer("No, I prefer to bash things close up.")
                chatNpc("Humph, philistine.")
            }
        }
    }
}
