package gg.rsmod.plugins.content.areas.canifis

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_npc_option(npc = Npcs.BARKER, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

create_shop(
    "Barkers' Haberdashery",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.BOOTS, sellPrice = 650, buyPrice = 200, amount = 10) // GREY BOOTS
    items[1] = ShopItem(Items.ROBE_TOP, sellPrice = 650, buyPrice = 200, amount = 10) // GREY ROBE TOP
    items[2] = ShopItem(Items.ROBE_BOTTOMS, sellPrice = 650, buyPrice = 200, amount = 10) // GREY ROBE BOTTOMS
    items[3] = ShopItem(Items.HAT, sellPrice = 650, buyPrice = 200, amount = 10) // GREY HAT
    items[4] = ShopItem(Items.GLOVES, sellPrice = 650, buyPrice = 200, amount = 10) // GREY GLOVES
    items[5] = ShopItem(Items.BOOTS_2904, sellPrice = 650, buyPrice = 200, amount = 10) // RED BOOTS
    items[6] = ShopItem(Items.ROBE_TOP_2906, sellPrice = 650, buyPrice = 200, amount = 10) // RED ROBE TOP
    items[7] = ShopItem(Items.ROBE_BOTTOMS_2908, sellPrice = 650, buyPrice = 200, amount = 10) // RED BOTTOMS
    items[8] = ShopItem(Items.HAT_2910, sellPrice = 650, buyPrice = 200, amount = 10) // RED HAT
    items[9] = ShopItem(Items.GLOVES_2912, sellPrice = 650, buyPrice = 200, amount = 10) // RED GLOVES
    items[10] = ShopItem(Items.BOOTS_2914, sellPrice = 650, buyPrice = 200, amount = 10) // YELLOW BOOTS
    items[11] = ShopItem(Items.ROBE_TOP_2916, sellPrice = 650, buyPrice = 200, amount = 10) // YELLOW ROBE TOP
    items[12] = ShopItem(Items.ROBE_BOTTOMS_2918, sellPrice = 650, buyPrice = 200, amount = 10) // YELLOW ROBE BOTTOMS
    items[13] = ShopItem(Items.HAT_2920, sellPrice = 650, buyPrice = 200, amount = 10) // YELLOW HAT
    items[14] = ShopItem(Items.GLOVES_2922, sellPrice = 650, buyPrice = 200, amount = 10) // YELLOW GLOVES
    items[15] = ShopItem(Items.BOOTS_2924, sellPrice = 650, buyPrice = 200, amount = 10) // TEAL BOOTS
    items[16] = ShopItem(Items.ROBE_TOP_2926, sellPrice = 650, buyPrice = 200, amount = 10) // TEAL ROBE TOP
    items[17] = ShopItem(Items.ROBE_BOTTOMS_2928, sellPrice = 650, buyPrice = 200, amount = 10) // TEAL ROBE BOTTOMS
    items[18] = ShopItem(Items.HAT_2930, sellPrice = 650, buyPrice = 200, amount = 10) // TEAL HAT
    items[19] = ShopItem(Items.GLOVES_2932, sellPrice = 650, buyPrice = 200, amount = 10) // TEAL GLOVES
    items[20] = ShopItem(Items.BOOTS_2934, sellPrice = 650, buyPrice = 200, amount = 10) // PURPLE BOOTS
    items[21] = ShopItem(Items.ROBE_TOP_2936, sellPrice = 650, buyPrice = 200, amount = 10) // PURPLE ROBE TOP
    items[22] = ShopItem(Items.ROBE_BOTTOMS_2938, sellPrice = 650, buyPrice = 200, amount = 10) // PURPLE BOTTOMS
    items[23] = ShopItem(Items.HAT_2940, sellPrice = 650, buyPrice = 200, amount = 10) // PURPLE HAT
    items[24] = ShopItem(Items.GLOVES_2942, sellPrice = 650, buyPrice = 200, amount = 10) // PURPLE GLOVES
    items[25] = ShopItem(Items.CAPE, sellPrice = 20, buyPrice = 200, amount = 10) // RED CAPE
    items[26] = ShopItem(Items.CAPE_1019, sellPrice = 20, buyPrice = 8, amount = 10) // BLACK CAPE
    items[27] = ShopItem(Items.CAPE_1021, sellPrice = 20, buyPrice = 8, amount = 10) // BLUE CAPE
    items[28] = ShopItem(Items.CAPE_1023, sellPrice = 20, buyPrice = 8, amount = 10) // YELLOW CAPE
    items[29] = ShopItem(Items.CAPE_1027, sellPrice = 20, buyPrice = 8, amount = 10) // GREEN CAPE
}

on_npc_option(npc = Npcs.BARKER, option = "trade") {
    player.openShop("Barkers' Haberdashery")
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "You are looking for clothes, yes? You look at my products!",
        "I have very many nice clothes, yes?",
    )
    when (
        it.options(
            "Yes, please.",
            "No thanks.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Yes, please.")
            player.openShop("Barkers' Haberdashery")
        }
        SECOND_OPTION -> {
            it.chatPlayer("No thanks.")
            it.chatNpc(
                "Unfortunate for you, yes? Many bargains,",
                "won't find elsewhere!",
            )
        }
    }
}
