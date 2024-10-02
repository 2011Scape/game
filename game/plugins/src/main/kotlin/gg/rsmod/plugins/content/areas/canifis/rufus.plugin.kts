
package gg.rsmod.plugins.content.areas.canifis

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_npc_option(npc = Npcs.RUFUS, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

create_shop(
    "Rufus' Meat Emporium",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.RAW_BEEF, 100)
    items[1] = ShopItem(Items.RAW_CHICKEN, 30)
    items[2] = ShopItem(Items.RAW_RAT_MEAT, 10)
    items[3] = ShopItem(Items.RAW_BEAR_MEAT, 10)
    items[4] = ShopItem(Items.RAW_TROUT, 0)
    items[5] = ShopItem(Items.RAW_PIKE, 0)
    items[6] = ShopItem(Items.RAW_SALMON, 0)
    items[7] = ShopItem(Items.RAW_SHARK, 0)
}

on_npc_option(npc = Npcs.RUFUS, option = "trade") {
    player.openShop("Rufus' Meat Emporium")
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Grrreetings frrriend! Welcome to my worrrld",
        "famous food emporrrium! All my meats are so frrresh",
        "you'd swear you killed them yourrrself!",
    )
    when (
        it.options(
            "Why do you only sell meats?",
            "Do you sell cooked food?",
            "Can I buy some food?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Why do you only sell meats?")
            it.chatNpc(
                "What? Why, what else would you want to eat?",
                "What kind of lycanthrrrope are you anyway?",
            )
            it.chatPlayer("...A vegetarian one?")
            it.chatNpc("Vegetarrrian...?")
            it.chatPlayer("Never mind.")
        }
        SECOND_OPTION -> {
            it.chatPlayer("Do you sell cooked food?")
            it.chatNpc(
                "Cooked food? Who would want that?",
                "You lose all the flavourrr of the meat when you",
                "can't taste the blood!",
            )
        }
        THIRD_OPTION -> {
            it.chatPlayer("Can I buy some food?")
            it.chatNpc("Cerrrtainly!")
            player.openShop("Rufus' Meat Emporium")
        }
    }
}
