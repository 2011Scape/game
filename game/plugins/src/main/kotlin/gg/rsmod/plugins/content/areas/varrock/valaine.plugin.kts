package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Valaine's Shop of Champions", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_TRADEABLES, containsSamples =
    false) {
    items[0] = ShopItem(Items.CAPE_1019, 10)
    items[1] = ShopItem(Items.BLACK_FULL_HELM, 10)
    items[2] = ShopItem(Items.BLACK_PLATELEGS, 10)
    items[3] = ShopItem(Items.ADAMANT_PLATEBODY, 10)
}

on_npc_option(Npcs.VALAINE, "Talk-to") {
    player.queue {
        chatNpc("Hello there. Want to have a look at what we're selling today?",
            facialExpression = FacialExpression.HAPPY_TALKING,
            wrap = true)
        when (options(
            "Yes, please.",
            "How should I use your shop?",
            "No, thank you."
        )) {
            FIRST_OPTION -> openShop(player)
            SECOND_OPTION -> {
                chatNpc("I'm glad you ask! You can buy as many of the items stocked as you wish. You can also sell " +
                    "most items to the shop.",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
            }
            THIRD_OPTION -> { } // Nothing. Dialogue ends
        }
    }
}

on_npc_option(Npcs.VALAINE, "Trade") {
    openShop(player)
}

fun openShop(player: Player) {
    player.openShop("Valaine's Shop of Champions")
}
