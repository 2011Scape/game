package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Diango's Toy Store",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_NONE,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.TOY_HORSEY, 10)
    items[1] = ShopItem(Items.TOY_HORSEY_2522, 10)
    items[2] = ShopItem(Items.TOY_HORSEY_2524, 10)
    items[3] = ShopItem(Items.TOY_HORSEY_2526, 10)
    items[4] = ShopItem(Items.SPINNING_PLATE, 10)
    items[5] = ShopItem(Items.TOY_KITE, 10)
    items[6] = ShopItem(Items.CELEBRATION_CAKE, 10)
    items[7] = ShopItem(Items.CELEBRATION_CANDLES, 100)
    items[8] = ShopItem(Items.FIREWORK_20720, 50)
    items[9] = ShopItem(Items.FIRECRACKER, 400)
    items[10] = ShopItem(Items.BUBBLE_MAKER, 10)
    items[11] = ShopItem(Items.CONFETTI, 10)
    items[12] = ShopItem(Items.SOUVENIR_MUG, 10)
}

on_npc_option(Npcs.DIANGO, "trade") {
    openShop(player)
}

on_npc_option(Npcs.DIANGO, "holiday-items") {
    player.queue {
        holidayItemDialogue(this)
    }
}

on_npc_option(Npcs.DIANGO, "redeem-code") {
    player.queue {
        enterCode(this)
    }
}

on_npc_option(Npcs.DIANGO, "talk-to") {
    player.queue {
        chatNpc("Howdy there, partner.")
        chatPlayer("Hi, Diango.")
        chatNpc("Want to see my spinning plates or kites? Want to check out my range of party items? Or did " +
            "you want an item back?", facialExpression = FacialExpression.CONFUSED, wrap = true)
        when (options(
            "Spinning plates?",
            "Wow, a kite!",
            "Party items, you say?",
            "I'd like to check my items, please"
        )) {
            FIRST_OPTION -> {
                chatPlayer("Spining plates?", facialExpression = FacialExpression.CONFUSED, wrap = true)
                chatNpc("That's right - there's a funny story behind them. Their shipment was held up by thieves.",
                    facialExpression = FacialExpression.LAUGHING,
                    wrap = true)
                chatNpc("The crate was marked 'Dragon Plates'. Apparently they thought it was some kind of " +
                    "armour, when really it's just a plate with a dragon on it!",
                    facialExpression = FacialExpression.LAUGHING,
                    wrap = true)
                openShop(player)
            }
            SECOND_OPTION -> {
                chatPlayer("Wow, a kite!", facialExpression = FacialExpression.CHEERFUL, wrap = true)
                chatNpc("You're not the first to say that...",
                    facialExpression = FacialExpression.SUSPICIOUS,
                    wrap = true)
                chatPlayer("Can I have one, please?", facialExpression = FacialExpression.CHEERFUL, wrap = true)
                chatNpc("Well, I suppose I did order more than I need... It's yours for the bargain price of " +
                    "100 coins.",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                when (options(
                    "That's a bargain! I'll take one.",
                    "No, thanks, I don't want one."
                )) {
                    FIRST_OPTION -> {
                        chatPlayer("That's a bargain! I'll take one.", facialExpression = FacialExpression.CHEERFUL, wrap = true)
                        openShop(player)
                    }
                    SECOND_OPTION -> chatPlayer("No, thanks, I don't want one",
                        facialExpression = FacialExpression.CALM_TALK,
                        wrap = true)
                }
            }
            THIRD_OPTION -> {
                chatPlayer("Party items, you say?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("Yep! In a partnership with my pal Party Pete, we've decided to launch a whole range of " +
                    "partyware to help celebrate the Royal Wedding of the King Black Dragon and the Kalphite Queen. ",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
                chatNpc("Confetti, bubble makers, fireworks, firecrackers. We even have a commemorative mug!",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
                chatPlayer("King Black Dragon and Kalphite... Wait, what?",
                    facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                chatNpc("Er, well, that's what I was told; perhaps our providers got it wrong. Mind you, it " +
                    "wouldn't be the first time. Like, this once, we were due to receive a shipment of dragon mail, and",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                chatNpc("we got a pile of slightly singed letters instead.")
                chatNpc("Anyway, we've got all those items on sale, and I even have a suitable bouquet available for " +
                    "those who want to emulate the happy bride!",
                    facialExpression = FacialExpression.LAUGHING,
                    wrap = true)
                chatPlayer("She had a bouquet? The Kalphite Queen? Big bug,", " beady-eyed, occasionally flies about" +
                    " when it's not", "laying eggs...", "Are we talking about the same creature?",
                    facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                chatNpc("Listen, partner, I'm not the one writing the advertising pitch here. I got the goods and " +
                    "been told what to say in order to sell them. It's not the most convincing pitch I've ever had to" +
                    " do, but, if you ask me,",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                chatNpc("the products don't need a pitch. They speak for themselves. Have a butcher's.",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                openShop(player)
            }
            FOURTH_OPTION -> holidayItemDialogue(this)
        }
    }
}

fun openShop(player: Player) {
    player.openShop("Diango's Toy Store")
}

suspend fun holidayItemDialogue(it: QueueTask) {
    // TODO Change once the retrieval service is implemented
    it.chatPlayer("I'd like to check my items, please.", facialExpression = FacialExpression.NORMAL, wrap = true)
    it.chatNpc("I'm afraid I can't do that for you right now, partner. Check back with me later on it.",
        facialExpression = FacialExpression.SAD,
        wrap = true)
}

suspend fun enterCode(it: QueueTask) {
    // There only existed 1 code at the time, for a banner from Runefest 2010,
    // But the codes were custom and not publicly known.
    it.messageBox("Not currently available.")
}
