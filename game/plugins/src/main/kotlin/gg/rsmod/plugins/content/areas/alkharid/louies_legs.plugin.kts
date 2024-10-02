package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Louie's Armoured Legs Bazaar",
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    currency = CoinCurrency(),
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_PLATELEGS, amount = 5)
    items[1] = ShopItem(Items.IRON_PLATELEGS, amount = 3)
    items[2] = ShopItem(Items.STEEL_PLATELEGS, amount = 2)
    items[3] = ShopItem(Items.BLACK_PLATELEGS, amount = 1)
    items[4] = ShopItem(Items.MITHRIL_PLATELEGS, amount = 1)
    items[5] = ShopItem(Items.ADAMANT_PLATELEGS, amount = 1)
}

on_npc_option(Npcs.LOUIE_LEGS, option = "trade") {
    player.openShop("Louie's Armoured Legs Bazaar")
}

on_npc_option(Npcs.LOUIE_LEGS, option = "talk-to") {
    player.queue {
        chatNpc("Hey, wanna buy some armour?", facialExpression = FacialExpression.HAPPY_TALKING)
        when (options("What have you got?", "No, thank you.")) {
            1 -> {
                chatPlayer("What have you got?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "I provide items to help you keep your legs!",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                )
                player.openShop("Louie's Armoured Legs Bazaar")
            }
            2 -> chatPlayer("No, thank you.", facialExpression = FacialExpression.TALKING)
        }
    }
}
