package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Flynn's Mace Market",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.BRONZE_MACE, 10)
    items[1] = ShopItem(Items.IRON_MACE, 10)
    items[2] = ShopItem(Items.STEEL_MACE, 10)
    items[3] = ShopItem(Items.MITHRIL_MACE, 10)
    items[4] = ShopItem(Items.ADAMANT_MACE, 10)
}

on_npc_option(Npcs.FLYNN, "trade") {
    player.openShop("Flynn's Mace Market")
}

on_npc_option(Npcs.FLYNN, option = "talk-to") {
    player.queue {
        chatNpc("Hello. Do you want to buy or sell any maces?")
        when (options("No, thanks.", "Well, I'll have a look, at least.")) {
            1 -> chatPlayer("No, thanks.")
            2 -> player.openShop("Flynn's Mace Market")
        }
    }
}
