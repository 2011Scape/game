package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val shopkeepers = listOf(Npcs.SHOPKEEPER_524, Npcs.SHOP_ASSISTANT_525)

create_shop("Al Kharid General Store", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.EMPTY_POT, 30)
    items[1] = ShopItem(Items.JUG, 10)
    items[2] = ShopItem(Items.SHEARS, 10)
    items[3] = ShopItem(Items.BUCKET, 30)
    items[4] = ShopItem(Items.BOWL, 10)
    items[5] = ShopItem(Items.CAKE_TIN, 10)
    items[6] = ShopItem(Items.TINDERBOX_590, 10)
    items[7] = ShopItem(Items.CHISEL, 10)
    items[8] = ShopItem(Items.HAMMER, 10)
    items[9] = ShopItem(Items.NEWCOMER_MAP, 10)
    items[10] = ShopItem(Items.SECURITY_BOOK, 10)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Al Kharid General Store")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Can I help you at all?")
            when (options("Yes please. What are you selling?", "No thanks.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Al Kharid General Store")
                }
                2 -> {
                    chatPlayer("No thanks.")
                }
            }
        }
    }
}
