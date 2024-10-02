package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */
create_shop(
    "Harry's Fishing Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.SMALL_FISHING_NET, 10)
    items[1] = ShopItem(Items.FISHING_ROD, 10)
    items[2] = ShopItem(Items.HARPOON, 1000)
    items[3] = ShopItem(Items.LOBSTER_POT, 10)
    items[4] = ShopItem(Items.FISHING_BAIT, 1000)
    items[5] = ShopItem(Items.BIG_FISHING_NET, 10)
    items[6] = ShopItem(Items.RAW_SHRIMPS, 0)
    items[7] = ShopItem(Items.RAW_SARDINE, 0)
    items[8] = ShopItem(Items.RAW_HERRING, 0)
    items[9] = ShopItem(Items.RAW_MACKEREL, 0)
    items[10] = ShopItem(Items.RAW_COD, 0)
    items[11] = ShopItem(Items.RAW_ANCHOVIES, 0)
    items[12] = ShopItem(Items.RAW_TUNA, 0)
    items[13] = ShopItem(Items.RAW_LOBSTER, 0)
    items[14] = ShopItem(Items.RAW_BASS, 0)
    items[15] = ShopItem(Items.RAW_SWORDFISH, 0)
    items[16] = ShopItem(Items.RAW_SHARK, 0)
}

on_npc_option(Npcs.HARRY, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.HARRY, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Harry's Fishing Shop")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Welcome! You can buy fishing equipment at my store.",
        "We'll also give you a good pirce for any fish that you",
        "catch.",
        facialExpression = FacialExpression.TALKING,
    )
    when (it.options("Let's see what you've got then.", "Sorry, I'm not interested.")) {
        1 -> {
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
