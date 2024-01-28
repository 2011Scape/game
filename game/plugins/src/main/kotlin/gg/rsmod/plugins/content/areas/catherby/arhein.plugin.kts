package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
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
        facialExpression = FacialExpression.TALKING
    )

    when (it.options("Yes.", "No thank you.", "Is that your ship?")) {
        1 -> {
            it.chatPlayer(
                "Yes.",
                facialExpression = FacialExpression.TALKING
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "No thank you.",
                facialExpression = FacialExpression.TALKING
            )
        }
            3 -> {
                it.chatPlayer(
                    "Is that your ship?",
                    facialExpression = FacialExpression.TALKING
                )
                it.chatNpc(
                    "Yes, I use it to make deliveries to my customers ",
                     "along the coast.",
                    "These crates here are all ready for my next trip.",
                    facialExpression = FacialExpression.TALKING
                )


            when (it.options("Where do you deliver to?", "Are you rich then?")) {
                1 -> {
                    it.chatPlayer(
                        "Where do you deliver to?",
                        facialExpression = FacialExpression.TALKING
                    )
                    it.chatNpc(
                        "Oh, various places up and down the coast. ",
                         "Mostly Karamja and Port Sarim.",
                        facialExpression = FacialExpression.TALKING
                    )

                    when (it.options("I don't suppose I could get a lift anywhere?", "Well, good luck with your business.")) {
                        1 -> {
                            it.chatPlayer(
                                "I don't suppose I could get a lift anywhere?",
                                facialExpression = FacialExpression.TALKING
                            )
                            it.chatNpc(
                                "Sorry pal, but I'm afraid I'm not quite ready to sail yet.",
                                facialExpression = FacialExpression.TALKING
                            )
                            it.chatNpc(
                                "I'm waiting on a big delivery of candles ",
                                "which I need to deliver further along the coast.",
                                facialExpression = FacialExpression.TALKING
                            )
                        }

                        2 -> {
                            it.chatPlayer(
                                "Well, good luck with your business.",
                                facialExpression = FacialExpression.TALKING
                            )
                            it.chatNpc(
                                "Thanks buddy!",
                                facialExpression = FacialExpression.TALKING
                            )
                        }
                    }
                }

                2 -> {
                    it.chatPlayer(
                        "Are you rich then?",
                        facialExpression = FacialExpression.TALKING
                    )
                    it.chatNpc(
                        "Business is going reasonably well... ",
                        "I wouldn't say I was the richest of merchants ever, ",
                        "but I'm doing fairly well all things considered.",
                        facialExpression = FacialExpression.TALKING
                    )
                }
            }
        }
    }
}