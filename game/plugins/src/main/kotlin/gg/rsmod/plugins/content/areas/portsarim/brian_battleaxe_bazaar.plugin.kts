package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Brian's Battleaxe Bazaar.",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_BATTLEAXE, 10)
    items[1] = ShopItem(Items.IRON_BATTLEAXE, 10)
    items[2] = ShopItem(Items.STEEL_BATTLEAXE, 10)
    items[3] = ShopItem(Items.BLACK_BATTLEAXE, 10)
    items[4] = ShopItem(Items.MITHRIL_BATTLEAXE, 10)
    items[5] = ShopItem(Items.ADAMANT_BATTLEAXE, 10)
}

on_npc_option(Npcs.BRIAN, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.BRIAN, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Brian's Battleaxe Bazaar.")
}

suspend fun chat(it: QueueTask) {
    when (it.options("So, are you selling something?", "Ello.")) {
        1 -> {
            it.chatPlayer(
                "So, are you selling something?",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Yep, take a look at these great axes!", facialExpression = FacialExpression.TALKING)
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "Ello.",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Ello.", facialExpression = FacialExpression.TALKING)
        }
    }
}
