package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Dommik's Crafting Store",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.CHISEL, amount = 10)
    items[1] = ShopItem(Items.RING_MOULD, amount = 10)
    items[2] = ShopItem(Items.NECKLACE_MOULD, amount = 10)
    items[3] = ShopItem(Items.AMULET_MOULD, amount = 10)
    items[4] = ShopItem(Items.NEEDLE, amount = 10)
    items[5] = ShopItem(Items.THREAD, amount = 1000)
    items[6] = ShopItem(Items.HOLY_MOULD, amount = 10)
    items[7] = ShopItem(Items.SICKLE_MOULD, amount = 10)
    items[8] = ShopItem(Items.TIARA_MOULD, amount = 10)
    items[9] = ShopItem(Items.BOLT_MOULD, amount = 10)
    items[10] = ShopItem(Items.BRACELET_MOULD, amount = 10)
}

on_npc_option(Npcs.DOMMIK, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.DOMMIK, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Dommik's Crafting Store")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Would you like to buy some crafting equipment?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (it.options("No thanks; I've got all the Crafting equipment I need.", "Let's see what you've got, then.")) {
        1 -> {
            it.chatPlayer(
                "No thanks; I've got all the Crafting equipment I need.",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Okay. Fare well on your travels.", facialExpression = FacialExpression.TALKING)
        }
        2 -> sendShop(it.player)
    }
}
