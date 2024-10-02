package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Zaff's Superior Staves", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.STAFF, 10)
    items[1] = ShopItem(Items.MAGIC_STAFF, 10)
    items[2] = ShopItem(Items.STAFF_OF_AIR, 10)
    items[3] = ShopItem(Items.STAFF_OF_WATER, 10)
    items[4] = ShopItem(Items.STAFF_OF_EARTH, 10)
    items[5] = ShopItem(Items.STAFF_OF_FIRE, 10)
}

on_npc_option(npc = Npcs.ZAFF, option = "Trade") {
    player.openShop("Zaff's Superior Staves")
}

on_npc_option(npc = Npcs.ZAFF, "talk-to") {
    player.queue {
        chatNpc("Would you like to buy or sell some staffs?")
        when(options("Yes, please!", "No, thank you.", title = "Select an Option")) {
            1 -> {
                player.openShop("Zaff's Superior Staves")
            }
            2 -> {
                chatPlayer("No, thank you.")
                chatNpc("Well, 'stick' your head in again if you change", "your mind.")
                chatPlayer("Huh, terrible pun! You just can't get the 'staff' these", "days!")
            }
        }
    }
}
