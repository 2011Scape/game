package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Faruq's Tools for Games",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.MAGIC_SKULLBALL, amount = 10)
    items[1] = ShopItem(Items.RING_OF_SEEKING_HIDE, amount = 10)
    items[2] = ShopItem(Items.RING_OF_SEEKING_SEEK, amount = 10)
    items[3] = ShopItem(Items.RING_OF_SEEKING_BOTH, amount = 10)
    items[4] = ShopItem(Items.MARKER_SEEDS, amount = 10)
    items[5] = ShopItem(Items.TICKER, amount = 10)
    items[6] = ShopItem(Items.EMPTY_BAG_CALLER, amount = 10)
    items[7] = ShopItem(Items.TIMEPIECE_STOPPED, amount = 10)
    items[8] = ShopItem(Items.RACING_BOOTS, amount = 10)
    items[9] = ShopItem(Items.STARTING_HORN, amount = 10)
    items[10] = ShopItem(Items.VOTING_HAT_RED, amount = 10)
    items[11] = ShopItem(Items.ORB_OF_COUNTING, amount = 10)
    items[12] = ShopItem(Items.FARUQS_TOOLONOMICON, amount = 10)
    items[13] = ShopItem(Items.ORB_OF_OCULUS, amount = 10)
}

on_npc_option(Npcs.FARUQ, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.FARUQ, option = "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Hello! Have you come to sample my marvellous wares?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (
        it.options(
            "Yes, I'd like to see what you have.",
            "Perhaps. Your stall has some odd-looking stuff; what are they for?",
            "No, thanks.",
        )
    ) {
        1 -> sendShop(it.player)
        2 -> {
            it.chatPlayer(
                "Perhaps. Your stall has some odd-looking stuff;",
                "what are they for?",
                facialExpression = FacialExpression.UNCERTAIN,
            )
            it.chatNpc(
                "I have the finest of items for those who would find their",
                "own things to do. Would you like to see them?",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            when (it.options("Yes, please.", "But what are they for?")) {
                1 -> sendShop(it.player)
                2 -> {
                    it.chatPlayer("But what are they for?", facialExpression = FacialExpression.ANNOYED)
                    it.chatNpc(
                        "Although the world has many things to do, some people",
                        "like to play games of their own.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                    )
                    it.chatNpc(
                        "I sell them the tools to keep track of time, mark out",
                        "places and routes, decide things randomly, even to hold",
                        "great ballots of their group.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                    )
                    when (
                        it.options(
                            "Let me see, then.",
                            "These tools, are they complicated?",
                            "I don't think this is for me.",
                        )
                    ) {
                        1 -> sendShop(it.player)
                        2 -> {
                            it.chatPlayer(
                                "These tools, are they complicated?",
                                facialExpression = FacialExpression.THINK,
                            )
                            val male = it.player.appearance.gender == Gender.MALE
                            it.chatNpc(
                                "No ${if (male) "mister" else "madam"}, they are not complicated.",
                                facialExpression = FacialExpression.HAPPY_TALKING,
                            )
                            it.chatNpc(
                                "I have a book that explains them, should you need.",
                                facialExpression = FacialExpression.HAPPY_TALKING,
                            )
                            sendShop(it.player)
                        }

                        3 -> {
                            it.chatPlayer("I don't think this is for me.", facialExpression = FacialExpression.ANNOYED)
                            it.chatNpc(
                                "That is a shame. I shall be here if you change your mind",
                                facialExpression = FacialExpression.SAD,
                            )
                        }
                    }
                }
            }
        }

        3 -> it.chatPlayer("No, thanks.")
    }
}

fun sendShop(player: Player) {
    player.openShop("Faruq's Tools for Games")
}
