package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Ranael's Super Skirt Store",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_PLATESKIRT, amount = 5)
    items[1] = ShopItem(Items.IRON_PLATESKIRT, amount = 3)
    items[2] = ShopItem(Items.STEEL_PLATESKIRT, amount = 2)
    items[3] = ShopItem(Items.BLACK_PLATESKIRT, amount = 1)
    items[4] = ShopItem(Items.MITHRIL_PLATESKIRT, amount = 1)
    items[5] = ShopItem(Items.ADAMANT_PLATESKIRT, amount = 1)
}

on_npc_option(Npcs.RANAEL, option = "trade") {
    player.openShop("Ranael's Super Skirt Store")
}

on_npc_option(Npcs.RANAEL, option = "talk-to") {
    player.queue {
        chatNpc(
            "Do you want to buy any armoured skirts? Designed",
            "especially for ladies who like to fight.",
            facialExpression = FacialExpression.HAPPY_TALKING,
        )
        when (options("Yes please.", "No thank you, that's not my scene.")) {
            1 -> player.openShop("Ranael's Super Skirt Store")
            2 -> chatPlayer("No thank you, that's not my scene.", facialExpression = FacialExpression.DISAGREE)
        }
    }
}
