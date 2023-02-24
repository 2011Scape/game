package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Gem Trader", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.UNCUT_SAPPHIRE, 0)
    items[1] = ShopItem(Items.UNCUT_EMERALD, 0)
    items[2] = ShopItem(Items.UNCUT_RUBY, 0)
    items[3] = ShopItem(Items.UNCUT_DIAMOND, 0)
    items[4] = ShopItem(Items.SAPPHIRE, 0)
    items[5] = ShopItem(Items.EMERALD, 0)
    items[6] = ShopItem(Items.RUBY, 0)
    items[7] = ShopItem(Items.DIAMOND, 0)
}

on_npc_option(Npcs.GEM_TRADER, "trade") {
    player.openShop("Gem Trader")
}

on_npc_option(Npcs.GEM_TRADER, "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Good day to you traveller.", "Would you be interested in buying some gems?")
    when (world.random(0)) {
        0 -> when(it.options("Yes please.", "No thank you.")) {
            1 -> it.player.openShop("Gem Trader")
            2 -> {
                it.chatPlayer("No thank you.")
                it.chatNpc("Eh, suit yourself.")
            }
        }
    }
}