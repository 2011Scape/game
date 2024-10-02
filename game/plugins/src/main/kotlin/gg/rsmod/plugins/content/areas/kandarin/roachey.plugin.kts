package gg.rsmod.plugins.content.areas.kandarin

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_npc_option(npc = Npcs.ROACHEY, option = "talk-to") {
    player.queue {
        mainChat(this)
    }
}

create_shop("Fishing Guild Shop", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.FISHING_BAIT, 1000)
    items[1] = ShopItem(Items.FEATHER, 1000)
    items[2] = ShopItem(Items.RAW_COD, 0)
    items[3] = ShopItem(Items.RAW_MACKEREL, 0)
    items[4] = ShopItem(Items.RAW_BASS, 0)
    items[5] = ShopItem(Items.RAW_TUNA, 0)
    items[6] = ShopItem(Items.RAW_LOBSTER, 0)
    items[7] = ShopItem(Items.RAW_SWORDFISH, 0)
    items[8] = ShopItem(Items.COD, 0)
    items[9] = ShopItem(Items.MACKEREL, 0)
    items[10] = ShopItem(Items.BASS, 0)
    items[11] = ShopItem(Items.TUNA, 0)
    items[12] = ShopItem(Items.LOBSTER, 0)
    items[13] = ShopItem(Items.SWORDFISH, 0)
}

on_npc_option(Npcs.ROACHEY, "trade") {
    player.openShop("Fishing Guild Shop")
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc(
        "Would you like to buy some Fishing equipment",
        "or sell some fish?",
    )
    when (
        it.options(
            "Yes, please.",
            "No, thank you.",
        )
    ) {
        FIRST_OPTION -> {
            it.player.openShop("Fishing Guild Shop")
        }
        SECOND_OPTION -> {
            // Do Nothing
        }
    }
}
