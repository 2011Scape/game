package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Party Pete's Emporium",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.TAUPE_AFRO, 10)
    items[1] = ShopItem(Items.RED_AFRO, 10)
    items[2] = ShopItem(Items.PURPLE_AFRO, 10)
    items[3] = ShopItem(Items.LIGHT_BLUE_AFRO, 10)
    items[4] = ShopItem(Items.YELLOW_AFRO, 10)
    items[5] = ShopItem(Items.PINK_AFRO, 10)
    items[6] = ShopItem(Items.INDIGO_AFRO, 10)
    items[7] = ShopItem(Items.WHITE_AFRO, 10)
    items[8] = ShopItem(Items.PEACH_AFRO, 10)
    items[9] = ShopItem(Items.GREEN_AFRO, 10)
    items[10] = ShopItem(Items.VIOLET_AFRO, 10)
    items[11] = ShopItem(Items.DARK_GREY_AFRO, 10)
    items[12] = ShopItem(Items.ORANGE_AFRO, 10)
    items[13] = ShopItem(Items.VERMILION_AFRO, 10)
    items[14] = ShopItem(Items.MINT_GREEN_AFRO, 10)
    items[15] = ShopItem(Items.DARK_GREY_AFRO, 10)
    items[16] = ShopItem(Items.TURQUOISE_AFRO, 10)
    items[17] = ShopItem(Items.MILITARY_GREY_AFRO, 10)
    items[18] = ShopItem(Items.DARK_BLUE_AFRO, 10)
    items[19] = ShopItem(Items.LIGHT_GREY_AFRO, 10)
    items[20] = ShopItem(Items.LIGHT_BROWN_AFRO, 10)
    items[21] = ShopItem(Items.BURGUNDY_AFRO, 10)
    items[22] = ShopItem(Items.BROWN_AFRO, 10)
    items[23] = ShopItem(Items.DARK_BROWN_AFRO, 10)
    items[24] = ShopItem(Items.BLACK_AFRO, 10)
    items[25] = ShopItem(Items.CELEBRATION_CAKE, 10)
    items[26] = ShopItem(Items.CELEBRATION_CANDLES, 100)
}

on_npc_option(npc = Npcs.PARTY_PETE, option = "trade") {
    player.openShop("Party Pete's Emporium")
}

on_npc_option(npc = Npcs.PARTY_PETE, option = "talk-to") {
    player.queue {
        chatNpc("Hi! I'm Party Pete. Welcome to the Party Room!")
        when (
            options(
                "So, what's this room for?",
                "What's the big lever over there for?",
                "What's the gold chest for?",
                "I wanna party!",
                "Nothing.",
            )
        ) {
            1 -> {
                chatPlayer("So what's this room for?")
                chatNpc("This room is for partying the night away!")
                chatPlayer("How do you have a party in RuneScape?")
                chatNpc("Get a few mates round, get the beers in and have fun!")
                chatNpc("Some players organise parties so keep an eye open!")
                chatPlayer("Woop! Thanks Pete!")
            }
            2 -> {
                chatPlayer("What's the big lever over there for?")
                chatNpc("Simple. With the lever you can do some fun stuff.")
                chatPlayer("What kind of stuff?")
                chatNpc(
                    "A balloon drop costs 1,000 gold. For this, you get 200",
                    "balloons dropped across the whole of the party room. You",
                    "can then have fun popping the balloons!",
                )
                chatNpc(
                    "Any items in the Party Drop Chest will be put into the",
                    "balloons as soon you pull the lever.",
                )
                chatNpc("When the balloons are released, you can burst them to", "get at the items!")
                chatNpc(
                    "For 500 gold, you can summon the Party Room Knights,",
                    "who will dance for your delight. Their singing isn't a",
                    "delight, though.",
                )
                chatPlayer("Cool! Sounds like a fun way to party!")
                chatNpc("Exactly!")
            }
            3 -> {
                chatPlayer("What's the gold chest for?")
                chatNpc("Any items in the chest will be dropped inside the balloons", "when you pull the lever.")
                chatPlayer("Cool! Sounds like a fun way to do a drop party.")
                chatNpc("Exactly!")
                chatNpc(
                    "A word of warning, though. Any items that you put into",
                    "the chest can't be taken out again, and it costs 1,000 gold",
                    "pieces for each drop party.",
                )
            }
            4 -> {
                chatPlayer("I wanna party!")
                chatNpc("I've won the Dance Trophy at the Kandarin Ball three", "years in a trot!")
                chatPlayer("Show me your moves Pete!")
                player.getInteractingNpc().animate(784)
            }
        }
    }
}
