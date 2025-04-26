package gg.rsmod.plugins.content.areas.ardougne

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Legends' Guild General Store", currency = CoinCurrency(), containsSamples = false, purchasePolicy =
    PurchasePolicy.BUY_TRADEABLES) {
    // TODO: Make this shop stock unlimited once the feature is added, for now we will do 100 stock with a fast
    //  resupply rate
    items[0] = ShopItem(Items.SWORDFISH, 100, resupplyCycles = 1)
    items[1] = ShopItem(Items.APPLE_PIE, 100, resupplyCycles = 1)
    items[2] = ShopItem(Items.ATTACK_POTION_3, 100, resupplyCycles = 1)
    items[3] = ShopItem(Items.STEEL_ARROW, 100, resupplyCycles = 1)
}

on_npc_option(Npcs.FIONELLA, "Talk-to") {
    player.queue {
        chatNpc("Can I help you at all?",
            facialExpression = FacialExpression.CONFUSED,
            wrap = true)
        when (options(
            "Yes please. What are you selling?",
            "No thanks."
        )) {
            FIRST_OPTION -> {
                chatPlayer("Yes please. What are you selling?")
                chatNpc("Take a look.")
                openShop(player)
            }
            SECOND_OPTION -> chatPlayer("No thanks.")
        }
    }
}

on_npc_option(Npcs.FIONELLA, "Trade") {
    openShop(player)
}

fun openShop(player: Player) {
    player.openShop("Legends' Guild General Store")
}
