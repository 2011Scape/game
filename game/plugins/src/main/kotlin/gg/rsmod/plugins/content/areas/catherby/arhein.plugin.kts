package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop("Arhein's Store", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_TRADEABLES) {
    items[0] = ShopItem(Items.BUCKET, 30)
    items[1] = ShopItem(Items.BRONZE_PICKAXE, 10)
    items[2] = ShopItem(Items.BOWL, 10)
    items[3] = ShopItem(Items.CAKE_TIN, 10)
    items[4] = ShopItem(Items.TINDERBOX_590, 10)
    items[5] = ShopItem(Items.CHISEL, 10)
    items[6] = ShopItem(Items.HAMMER, 10)
    items[7] = ShopItem(Items.ROPE, 10)
    items[8] = ShopItem(Items.EMPTY_POT, 30)
    items[9] = ShopItem(Items.KNIFE, 10)
}

on_npc_option(Npcs.ARHEIN, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.ARHEIN, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Arhein's Store")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Hello! Would you like to trade?",
        facialExpression = FacialExpression.HAPPY_TALKING,
    )

    when (it.options("Yes.", "No thank you.", "Is that your ship?")) {
        1 -> {
            it.chatPlayer(
                "Sure.",
                facialExpression = FacialExpression.CALM_TALK,
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "No thank you.",
                facialExpression = FacialExpression.CALM_TALK,
            )
        }
        3 -> {
            it.chatPlayer(
                "Is that your ship?",
                facialExpression = FacialExpression.CONFUSED,
            )
            it.chatNpc(
                "Yes, I use it to make deliveries to my customers ",
                "along the coast.",
                "These crates here are all ready for my next trip.",
                facialExpression = FacialExpression.CALM_TALK,
            )

            when (it.options("Where do you deliver to?", "Are you rich then?")) {
                1 -> {
                    it.chatPlayer(
                        "Where do you deliver to?",
                        facialExpression = FacialExpression.CALM_TALK,
                    )
                    it.chatNpc(
                        "Oh, various places up and down the coast. ",
                        "Mostly Karamja and Port Sarim.",
                        facialExpression = FacialExpression.CALM_TALK,
                    )

                    when (
                        it.options(
                            "I don't suppose I could get a lift anywhere?",
                            "Well, good luck with your business.",
                        )
                    ) {
                        1 -> {
                            it.chatPlayer(
                                "I don't suppose I could get a lift anywhere?",
                                facialExpression = FacialExpression.CALM_TALK,
                            )
                            it.chatNpc(
                                "Sorry pal, but I'm afraid I'm not quite ready to sail yet.",
                                facialExpression = FacialExpression.DISAGREE,
                            )
                            it.chatNpc(
                                "I'm waiting on a big delivery of candles ",
                                "which I need to deliver further along the coast.",
                                facialExpression = FacialExpression.CONFUSED,
                            )
                        }

                        2 -> {
                            it.chatPlayer(
                                "Well, good luck with your business.",
                                facialExpression = FacialExpression.CALM_TALK,
                            )
                            it.chatNpc(
                                "Thanks buddy!",
                                facialExpression = FacialExpression.HAPPY_TALKING,
                            )
                        }
                    }
                }

                2 -> {
                    it.chatPlayer(
                        "Are you rich then?",
                        facialExpression = FacialExpression.CALM_TALK,
                    )
                    it.chatNpc(
                        "Business is going reasonably well... ",
                        "I wouldn't say I was the richest of merchants ever, ",
                        "but I'm doing fairly well all things considered.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                    )
                }
            }
        }
    }
}
