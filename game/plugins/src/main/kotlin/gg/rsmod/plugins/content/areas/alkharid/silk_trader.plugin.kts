package gg.rsmod.plugins.content.areas.alkharid

on_npc_option(Npcs.SILK_TRADER, "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Do you want to buy any fine silks?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (it.options("How much are they?", "No. Silk doesn't suit me.")) {
        1 -> {
            it.chatPlayer("How much are they?", facialExpression = FacialExpression.THINK)
            it.chatNpc("3 gp.", facialExpression = FacialExpression.HAPPY_TALKING)
            when (it.options("No. That's too much for me", "Okay, that sounds good.")) {
                1 -> {
                    it.chatPlayer("No. That's too much for me.", facialExpression = FacialExpression.UNCERTAIN)
                    it.chatNpc("2 gp and that's as low as I'll go.", facialExpression = FacialExpression.ANNOYED)
                    it.chatNpc(
                        "I'm not selling it for any less. You'll probably",
                        "go and sell it in Varrock for a profit, anyway.",
                        facialExpression = FacialExpression.ANNOYED,
                    )
                    when (it.options("2 gp sounds good.", "No, really. I don't want it.")) {
                        1 -> {
                            it.chatPlayer("2 gp sounds good.", facialExpression = FacialExpression.HAPPY_TALKING)
                            buySilk(it, price = 2)
                        }

                        2 -> {
                            it.chatPlayer(
                                "No, really. I don't want it.",
                                facialExpression = FacialExpression.ANNOYED,
                            )
                            it.chatNpc(
                                "Okay, but that's the best price you're going to get.",
                                facialExpression = FacialExpression.HAPPY_TALKING,
                            )
                        }
                    }
                }

                2 -> {
                    it.chatPlayer("Okay, that sounds good.", facialExpression = FacialExpression.HAPPY_TALKING)
                    buySilk(it, price = 3)
                }
            }
        }

        2 -> it.chatPlayer("No. Silk doesn't suit me.", facialExpression = FacialExpression.TALKING)
    }
}

suspend fun buySilk(
    it: QueueTask,
    price: Int,
) {
    val player = it.player
    if (player.inventory.remove(Items.COINS_995, amount = price, assureFullRemoval = true).hasSucceeded()) {
        val addSilk = player.inventory.add(Items.SILK)
        if (addSilk.hasFailed()) {
            val silk = GroundItem(Items.SILK, amount = 1, tile = it.player.tile, owner = it.player)
            world.spawn(silk)
        }
        it.itemMessageBox("You buy some silk for $price gp.", Items.SILK)
    } else {
        it.chatPlayer(
            "Oh dear. I don't have enough money.",
            facialExpression = FacialExpression.SAD_2,
        )
        it.chatNpc(
            "Well, come back when you do have some money!",
            facialExpression = FacialExpression.ANNOYED,
        )
    }
}
