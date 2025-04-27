package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency


create_shop("Scavvo's Rune Store", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    items[0] = ShopItem(Items.RUNE_PLATESKIRT, 10)
    items[1] = ShopItem(Items.RUNE_PLATELEGS, 10)
    items[2] = ShopItem(Items.RUNE_MACE, 10)
    items[3] = ShopItem(Items.RUNE_CHAINBODY, 10)
    items[4] = ShopItem(Items.RUNE_LONGSWORD, 10)
    items[5] = ShopItem(Items.RUNE_SWORD, 10)
    items[6] = ShopItem(Items.GREEN_DHIDE_CHAPS, 10)
    items[7] = ShopItem(Items.GREEN_DHIDE_VAMBRACES, 10)
    items[8] = ShopItem(Items.COIF, 10)
}

on_npc_option(Npcs.SCAVVO, "Trade") {
    openShop(player)
}

on_npc_option(Npcs.SCAVVO, "Talk-to") {
    player.queue {
        chatNpc("'Ello, matey! D'ya wanna buy some exciting new toys?")
        when (options(
            "No - toys are for kids.",
            "Let's have a look, then.",
            "Ooh, goody-goody - toys!",
            "Why do you sell most rune armour but not platebodies?"
        )) {
            FIRST_OPTION -> { } // Nothing, dialogue just ends
            SECOND_OPTION -> openShop(player)
            THIRD_OPTION -> {
                chatPlayer("Ooh, goody-goody - toys!",
                    facialExpression = FacialExpression.HAPPY_TALKING)
                openShop(player)
            }
            FOURTH_OPTION -> {
               chatPlayer("Why do you sell most rune armour but not platebodies?",
                   facialExpression = FacialExpression.CONFUSED,
                   wrap = true)
                chatNpc("Oh, you have to complete a special quest in order to wear rune platebodies. You should talk " +
                    "to the guild master downstairs about that.",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
            }
        }
    }
}

fun openShop(player: Player) {
    player.openShop("Scavvo's Rune Store")
}
