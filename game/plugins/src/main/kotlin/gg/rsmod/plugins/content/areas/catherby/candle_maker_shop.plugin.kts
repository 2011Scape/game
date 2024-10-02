package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    "Candle Maker Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.CANDLE, 10)
    items[1] = ShopItem(Items.BLACK_CANDLE, 10)
}

on_npc_option(Npcs.CANDLEMAKER, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.CANDLEMAKER, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Candle Maker Shop")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Hi! Would you be interested in some of my fine.",
        "candle?",
        facialExpression = FacialExpression.TALKING,
    )
    when (it.options("Yes Please.", "No thank you.")) {
        1 -> {
            it.chatPlayer(
                "Yes please.",
                facialExpression = FacialExpression.TALKING,
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "No thank you.",
                facialExpression = FacialExpression.TALKING,
            )
        }
    }
}
