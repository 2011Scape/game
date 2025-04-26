package gg.rsmod.plugins.content.areas.ardougne

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Legends' Guild Shop of Useful Items", currency = CoinCurrency(), containsSamples = false, purchasePolicy =
    PurchasePolicy.BUY_TRADEABLES) {
    // TODO: Make this shop stock unlimited once the feature is added, for now we will do 10 stock with a fast
    //  resupply rate
    items[0] = ShopItem(Items.MITHRIL_SEEDS, 10, resupplyCycles = 1)
    items[1] = ShopItem(Items.DUSTY_KEY, 10, resupplyCycles = 1)
    items[2] = ShopItem(Items.MAZE_KEY, 10, resupplyCycles = 1)
    items[3] = ShopItem(Items.SHIELD_RIGHT_HALF, 10, resupplyCycles = 1)
    items[4] = ShopItem(Items.CAPE_OF_LEGENDS, 10, resupplyCycles = 1)
}

on_npc_option(Npcs.SIEGFRIED_ERKLE, "Talk-to") {
    player.queue {
        chatNpc("Hello there and welcome to the shop of useful items. Can I help you at all?",
            facialExpression = FacialExpression.CONFUSED,
            wrap = true)
        when (options(
            "Yes please. What are you selling?",
            "No thanks.",
            "Didn't you once sell Silverlight?"
        )) {
            FIRST_OPTION -> {
                chatPlayer("Yes please. What are you selling?")
                chatNpc("Take a look.")
                openShop(player)
            }
            SECOND_OPTION -> chatPlayer("No thanks.")
            THIRD_OPTION -> {
                chatPlayer("Didn't you once sell Silverlight?",
                    facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                chatNpc("Silverlight? Oh, Sir Prysin of Varrock explained that was a unique sword and told us to " +
                    "stop selling it. If you want Silverlight and don't have it, you should speak to Sir Prysin.",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
            }
        }
    }
}

on_npc_option(Npcs.SIEGFRIED_ERKLE, "Trade") {
    openShop(player)
}

fun openShop(player: Player) {
    player.openShop("Legends' Guild Shop of Useful Items")
}
