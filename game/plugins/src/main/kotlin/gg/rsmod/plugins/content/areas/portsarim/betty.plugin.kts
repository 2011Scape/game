package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Harley
 */

create_shop("Betty's Magic Emporium", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    // Add the items that Betty will sell in her shop
}

on_npc_option(npc = Npcs.BETTY, option = "Trade") {
    player.openShop("Betty's Magic Emporium")
}

on_npc_option(npc = Npcs.BETTY, "talk-to") {
    player.queue {
        chatNpc("Welcome to the magic emporium.")
        when (options("Can I see your wares?", "Sorry, I'm not into magic.", title = "Select an Option")) {
            1 -> {
                player.openShop("Betty's Magic Emporium")
            }
            2 -> {
                chatPlayer("Sorry, I'm not into magic.")
                chatNpc("Well, if you see anyone who is into magic,",
                                "please send them my way.")
            }
        }
    }
}