package gg.rsmod.plugins.content.areas.shilovillage

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    "Fernahei's Fishing Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.FISHING_ROD, 10)
    items[1] = ShopItem(Items.FLY_FISHING_ROD, 10)
    items[2] = ShopItem(Items.FISHING_BAIT, 1000)
    items[3] = ShopItem(Items.FEATHER, 1000)
    items[4] = ShopItem(Items.RAW_TROUT, 0)
    items[5] = ShopItem(Items.RAW_PIKE, 0)
    items[6] = ShopItem(Items.RAW_SALMON, 0)
}

on_npc_option(Npcs.FERNAHEI, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.FERNAHEI, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Fernahei's Fishing Shop")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Welcome to Fernahei's Fishing Shop Bwana!",
        "Would you like to see my items?",
        facialExpression = FacialExpression.TALKING,
    )

    when (it.options("Yes please!", "No, but thanks for the offer.")) {
        1 -> {
            it.chatPlayer(
                "Yes please.",
                facialExpression = FacialExpression.TALKING,
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "No, but thanks for the offer.",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("That's fine and thanks for your interest.")
        }
    }
}
