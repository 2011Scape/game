package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Draynor Seed Market",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.POTATO_SEED, 10)
    items[1] = ShopItem(Items.ONION_SEED, 10)
    items[2] = ShopItem(Items.CABBAGE_SEED, 10)
    items[3] = ShopItem(Items.TOMATO_SEED, 0)
    items[4] = ShopItem(Items.SWEETCORN_SEED, 0)
    items[5] = ShopItem(Items.STRAWBERRY_SEED, 0)
    items[6] = ShopItem(Items.WATERMELON_SEED, 0)
    items[7] = ShopItem(Items.BARLEY_SEED, 10)
    items[8] = ShopItem(Items.JUTE_SEED, 10)
    items[9] = ShopItem(Items.MARIGOLD_SEED, 10)
    items[10] = ShopItem(Items.ROSEMARY_SEED, 10)
    items[11] = ShopItem(Items.HAMMERSTONE_SEED, 10)
    items[12] = ShopItem(Items.ASGARNIAN_SEED, 10)
    items[13] = ShopItem(Items.YANILLIAN_SEED, 10)
    items[14] = ShopItem(Items.KRANDORIAN_SEED, 10)
    items[15] = ShopItem(Items.WILDBLOOD_SEED, 10)
}

on_npc_option(Npcs.OLIVIA_2572, "trade") {
    openShop(player)
}

on_npc_option(Npcs.OLIVIA_2572, "Talk-to") {
    val viewedRobber = player.attr.has(BankGuard.SHOWN_BANK_TAPE)
    if (viewedRobber) {
        player.queue {
            when(options(
                "Would you like to trade?",
                "Where do I get higher-level seeds?",
                "About the recent bank robbery...",
                title = "Would you like to trade in seeds?"
            )) {
                FIRST_OPTION -> openShop(player)
                SECOND_OPTION -> {
                    chatPlayer("Where do I get higher-level seeds?")
                    chatNpc("The Master Farmers usually carry a few rare seeds around with them, although I don't know if" +
                        " they'd want to part with them for any price to be honest.", wrap = true)
                }
                THIRD_OPTION -> {
                    chatPlayer("About the recent bank reobbery...", "The robber attacked you, didn't he?",
                        facialExpression = FacialExpression.CALM_TALK)
                    chatNpc("Something like that, yes. I was just minding my own business, trying to keep those pesky" +
                        " thieves off my stalls, when I heard a strange noise and a scream.", facialExpression =
                        FacialExpression.CALM_TALK, wrap = true)
                    chatNpc("I went across to see what was going on, but there was a bright purple flash and " +
                        "everything went black. Before I knew what happened, I was standing in Falador!", facialExpression =
                        FacialExpression.CALM_TALK, wrap = true)
                    chatNpc("It took me ages to get back, and when I arrived I saw there was a huge hole in the bank " +
                        "wall and some of the bankers had been killed!", facialExpression =
                        FacialExpression.CALM_TALK, wrap = true)
                    chatPlayer("Did you see who teleported you to Falador?", facialExpression = FacialExpression
                        .CALM_TALK, wrap = true)
                    chatNpc("No, they were standing behind that old man's door.", facialExpression = FacialExpression
                        .SAD, wrap = true)
                    chatNpc("He really shouldn't leave his door open all day, especially in this neighbourhood - " +
                        "there are thousands of thieves around, and anyone could wander in!", facialExpression =
                        FacialExpression.CALM_TALK, wrap = true)
                    chatPlayer("Okay, thanks for your time.", facialExpression = FacialExpression.CALM_TALK, wrap =
                        true)
                    chatNpc("You're welcome. Would you like to see my fine selection of seeds?", facialExpression =
                        FacialExpression.HAPPY_TALKING, wrap = true)
                    when (options(
                        "Yes please.",
                        "No thanks."
                    )) {
                        FIRST_OPTION -> {
                            openShop(player)
                        }
                        SECOND_OPTION -> {
                            chatPlayer("No, thanks.")
                        }
                    }
                }
            }
        }
    }
    else {
        player.queue {
            when(options(
                "Yes",
                "No",
                "Where do I get higher-level seeds?",
                title = "Would you like to trade in seeds?"
            )) {
                FIRST_OPTION -> openShop(player)
                SECOND_OPTION -> chatPlayer("No, thanks.")
                THIRD_OPTION -> {
                    chatPlayer("Where do I get higher-level seeds?")
                    chatNpc("The Master Farmers usually carry a few rare seeds around with them, although I don't know if" +
                        " they'd want to part with them for any price to be honest.", wrap = true)
                }
            }
        }
    }
}

fun openShop(player: Player) {
    player.openShop("Draynor Seed Market")
}
