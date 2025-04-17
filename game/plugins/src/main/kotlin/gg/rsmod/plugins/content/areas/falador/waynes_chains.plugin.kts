import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Wayne's Chains",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.BRONZE_CHAINBODY, 10)
    items[1] = ShopItem(Items.IRON_CHAINBODY, 10)
    items[2] = ShopItem(Items.STEEL_CHAINBODY, 10)
    items[3] = ShopItem(Items.BLACK_CHAINBODY, 10)
    items[4] = ShopItem(Items.MITHRIL_CHAINBODY, 10)
    items[5] = ShopItem(Items.ADAMANT_CHAINBODY, 10)
}

on_npc_option(Npcs.WAYNE, "talk-to") {
    player.queue {
        chatNpc("Welcome to Wayne's Chaines. DO you wanna buy or sell some chainmail?", wrap = true)
        when (
            options(
                "Yes, please.",
                "No, thanks.",
            )
        ) {
            1 -> {
                player.openShop("Wayne's Chains")
            }
        }
    }
}

on_npc_option(Npcs.WAYNE, "trade") {
    player.openShop("Wayne's Chains")
}
