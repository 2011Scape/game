package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Gerrant's Fishy Business",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.SMALL_FISHING_NET, 10)
    items[1] = ShopItem(Items.FISHING_ROD, 10)
    items[2] = ShopItem(Items.FLY_FISHING_ROD, 10)
    items[3] = ShopItem(Items.HARPOON, 1000)
    items[4] = ShopItem(Items.LOBSTER_POT, 10)
    items[5] = ShopItem(Items.CRAYFISH_CAGE, 10)
    items[6] = ShopItem(Items.FISHING_BAIT, 1000)
    items[7] = ShopItem(Items.FEATHER, 1000)
    items[8] = ShopItem(Items.RAW_SHRIMPS, 0)
    items[9] = ShopItem(Items.RAW_SARDINE, 10)
    items[10] = ShopItem(Items.RAW_HERRING, 0)
    items[11] = ShopItem(Items.RAW_ANCHOVIES, 0)
    items[12] = ShopItem(Items.RAW_TROUT, 0)
    items[13] = ShopItem(Items.RAW_PIKE, 0)
    items[14] = ShopItem(Items.RAW_SALMON, 0)
    items[15] = ShopItem(Items.RAW_TUNA, 0)
    items[16] = ShopItem(Items.RAW_LOBSTER, 0)
    items[17] = ShopItem(Items.RAW_SWORDFISH, 0)
}

on_npc_option(Npcs.GERRANT, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.GERRANT, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Gerrant's Fishy Business")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Welcome! You can buy fishing equipment at my store.",
        "We'll also buy anything you catch off you.",
        facialExpression = FacialExpression.HAPPY_TALKING,
    )
    when (it.options("Let's see what you've got then.", "Sorry, I'm not interested.")) {
        1 -> {
            it.chatPlayer(
                "Let's see what you've got then.",
                facialExpression = FacialExpression.TALKING,
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "Sorry, I'm not interested.",
                facialExpression = FacialExpression.TALKING,
            )
        }
    }
}
