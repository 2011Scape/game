package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Food Store.",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.POT_OF_FLOUR, amount = 500)
    items[1] = ShopItem(Items.RAW_BEEF, amount = 10)
    items[2] = ShopItem(Items.RAW_CHICKEN, amount = 10)
    items[3] = ShopItem(Items.CABBAGE, amount = 10)
    items[4] = ShopItem(Items.BANANA, amount = 0)
    items[5] = ShopItem(Items.REDBERRIES, amount = 0)
    items[6] = ShopItem(Items.BREAD, amount = 10)
    items[7] = ShopItem(Items.CHOCOLATE_BAR, amount = 10)
    items[8] = ShopItem(Items.CHEESE, amount = 10)
    items[9] = ShopItem(Items.TOMATO, amount = 10)
    items[10] = ShopItem(Items.POTATO, amount = 10)
    items[11] = ShopItem(Items.GARLIC, amount = 10)
}

on_npc_option(Npcs.WYDIN, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.WYDIN, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Food Store.")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Is it nice and tidy round the back now?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (
        it.options(
            "Yes, can I work out front now?",
            "Yes, are you going to pay me yet?",
            "No, it's a complete mess",
            "Can I buy something please?",
        )
    ) {
        1 -> {
            it.chatPlayer(
                "Yes, can I work out front now?",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("No, I'm the one who works here.", facialExpression = FacialExpression.TALKING)
        }

        2 -> {
            it.chatPlayer(
                "Yes, are you going to pay me yet?",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Umm... No, not yet.", facialExpression = FacialExpression.TALKING)
        }

        3 -> {
            it.chatPlayer(
                "No, it's a complete mess",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Ah well it`ll give you something to do, won't it.", facialExpression = FacialExpression.TALKING)
        }

        4 -> {
            it.chatPlayer(
                "Can I buy something please?",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc("Yes, of course..", facialExpression = FacialExpression.TALKING)
            sendShop(it.player)
        }
    }
}
