package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Vanessa's Store", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_TRADEABLES) {
    var index = 0
    items[index++] = ShopItem(Items.COMPOST, 300)
    items[index++] = ShopItem(Items.RAKE, 10)
    items[index++] = ShopItem(Items.GARDENING_TROWEL, 10)
    items[index++] = ShopItem(Items.PLANT_CURE, 10)
    items[index++] = ShopItem(Items.SEED_DIBBER, 10)
    items[index++] = ShopItem(Items.WATERING_CAN_8, 30)
    items[index++] = ShopItem(Items.SECATEURS, 10)
    items[index++] = ShopItem(Items.SPADE, 10)
    items[index++] = ShopItem(Items.PLANT_POT, 100)
    items[index++] = ShopItem(Items.BUCKET, 100)
    items[index++] = ShopItem(Items.EMPTY_SACK, 10)
    items[index++] = ShopItem(Items.EMPTY_SACK_PACK, 1)
    items[index++] = ShopItem(Items.BASKET, 1)
    items[index] = ShopItem(Items.BASKET_PACK, 10)

}

on_npc_option(npc = Npcs.VANESSA, option = "trade") {
    player.openShop("Vanessa's Store")
}

on_npc_option(npc = Npcs.VANESSA, option = "talk-to") {
    player.queue {
        chatNpc("Hello. How can I help you?")
        when (this.options("What are you selling?", "Can you give me any Farming advice?", "I'm okay, thank you.")) {
            1 -> player.openShop("Vanessa's Store")
            2 -> chatNpc("Yes - ask a gardener.")
            3 -> Unit
        }
    }
}
