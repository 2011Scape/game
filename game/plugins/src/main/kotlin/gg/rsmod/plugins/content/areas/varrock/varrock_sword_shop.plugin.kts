package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val shopkeepers = arrayOf(Npcs.SHOPKEEPER_551, Npcs.SHOP_ASSISTANT_552)

create_shop("Varrock Sword Shop", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.BRONZE_SWORD, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.BRONZE_SWORD, 10)
    items[1] = ShopItem(Items.IRON_SWORD, 10)
    items[2] = ShopItem(Items.STEEL_SWORD, 10)
    items[3] = ShopItem(Items.BLACK_SWORD, 10)
    items[4] = ShopItem(Items.MITHRIL_SWORD, 10)
    items[5] = ShopItem(Items.ADAMANT_SWORD, 10)
    items[6] = ShopItem(Items.BRONZE_LONGSWORD, 10)
    items[7] = ShopItem(Items.IRON_LONGSWORD, 10)
    items[8] = ShopItem(Items.STEEL_LONGSWORD, 10)
    items[9] = ShopItem(Items.BLACK_LONGSWORD, 10)
    items[10] = ShopItem(Items.MITHRIL_LONGSWORD, 10)
    items[11] = ShopItem(Items.ADAMANT_LONGSWORD, 10)
    items[12] = ShopItem(Items.BRONZE_DAGGER, 10)
    items[13] = ShopItem(Items.IRON_DAGGER, 10)
    items[14] = ShopItem(Items.STEEL_DAGGER, 10)
    items[15] = ShopItem(Items.BLACK_DAGGER, 10)
    items[16] = ShopItem(Items.MITHRIL_DAGGER, 10)
    items[17] = ShopItem(Items.ADAMANT_DAGGER, 10)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Varrock Sword Shop")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Hello, bold adventurer! Can I interest you in some", "swords?")
            when(options("Yes, please!", "No, I'm okay for swords right now.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Varrock Sword Shop")
                }
                2 -> {
                    chatPlayer("No, I'm okay for swords right now.")
                }
            }
        }
    }
}
