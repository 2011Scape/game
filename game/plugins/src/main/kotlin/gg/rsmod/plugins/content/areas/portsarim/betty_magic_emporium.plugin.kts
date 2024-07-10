package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Betty's Magic Emporium.",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.FIRE_RUNE, 300)
    items[1] = ShopItem(Items.WATER_RUNE, 300)
    items[2] = ShopItem(Items.AIR_RUNE, 300)
    items[3] = ShopItem(Items.EARTH_RUNE, 300)
    items[4] = ShopItem(Items.MIND_RUNE, 100)
    items[5] = ShopItem(Items.BODY_RUNE, 100)
    items[6] = ShopItem(Items.CHAOS_RUNE, 30)
    items[7] = ShopItem(Items.DEATH_RUNE, 10)
    items[8] = ShopItem(Items.EYE_OF_NEWT, 300)
    items[9] = ShopItem(Items.WIZARD_HAT, 10)
    items[10] = ShopItem(Items.WIZARD_HAT_1017, 10)
}

on_npc_option(Npcs.BETTY, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.BETTY, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Betty's Magic Emporium.")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Welcome to the magic emporium.", facialExpression = FacialExpression.HAPPY_TALKING)
    when (it.options("Can I see your wares?", "Sorry, I'm not into magic..")) {
        1 -> {
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "Sorry, I'm not into magic.",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc(
                "Well, if you see anyone who is into magic",
                "please send them my way.",
                facialExpression = FacialExpression.TALKING,
            )
        }
    }
}
