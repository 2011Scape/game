package gg.rsmod.plugins.content.areas.karamja

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    name = "Karamja Wines, Spirits, and Beers",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.BEER, 10)
    items[1] = ShopItem(Items.KARAMJAN_RUM, 10)
    items[2] = ShopItem(Items.JUG_OF_WINE, 10)
}

on_npc_option(npc = Npcs.ZAMBO, option = "talk-to") {
    player.queue {
        chatNpc("Hey, are you wanting to try some of my fine wines and spirits? All brewed locally on Karamja. ")
        when (options("Yes, please.", "No, thank you.")) {
            FIRST_OPTION -> {
                chatPlayer("Yes please. What are you selling?")
                player.openShop("Karamja Wines, Spirits, and Beers")
            }
            SECOND_OPTION -> {
                chatPlayer("No, thank you.")
            }
        }
    }
}

on_npc_option(npc = Npcs.ZAMBO, option = "trade") {
    player.openShop("Karamja Wines, Spirits, and Beers")
}
