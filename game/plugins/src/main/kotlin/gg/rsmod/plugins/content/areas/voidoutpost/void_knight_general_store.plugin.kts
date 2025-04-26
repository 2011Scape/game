package gg.rsmod.plugins.content.areas.voidoutpost

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val shopkeepers = arrayOf(Npcs.SQUIRE_3799)

create_shop("Void Knight General Store", CoinCurrency()) {
    items[0] = ShopItem(Items.EMPTY_POT, 30)
    items[1] = ShopItem(Items.JUG, 10)
    items[2] = ShopItem(Items.SHEARS, 10)
    items[3] = ShopItem(Items.BUCKET, 30)
    items[4] = ShopItem(Items.BOWL, 10)
    items[5] = ShopItem(Items.CAKE_TIN, 10)
    items[6] = ShopItem(Items.TINDERBOX_590, 10)
    items[7] = ShopItem(Items.CHISEL, 10)
    items[8] = ShopItem(Items.HAMMER, 10)
    items[9] = ShopItem(Items.BRONZE_HATCHET, 16)
    items[10] = ShopItem(Items.FIELD_RATION, 50)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Void Knight General Store")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Hi, how can I help you?")
            when (options("What do you have for sale? ", "I'm fine thanks.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Void Knight General Store")
                }
                2 -> {
                    chatPlayer("I'm fine thanks.")
                }
            }
        }
    }
}
