package gg.rsmod.plugins.content.areas.morytania

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    "Alice's Farming Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    var index = 0
    items[index++] = ShopItem(Items.RAKE, 10)
    items[index++] = ShopItem(Items.SEED_DIBBER, 10)
    items[index++] = ShopItem(Items.SECATEURS, 10)
    items[index++] = ShopItem(Items.SPADE, 10)
    items[index++] = ShopItem(Items.GARDENING_TROWEL, 10)
    items[index++] = ShopItem(Items.WATERING_CAN, 30)
    items[index++] = ShopItem(Items.PLANT_POT, 100)
    items[index++] = ShopItem(Items.COMPOST, 300)
    items[index++] = ShopItem(Items.BUCKET, 100)
    items[index++] = ShopItem(Items.EMPTY_SACK, 10)
    items[index++] = ShopItem(Items.EMPTY_SACK_PACK, 1)
    items[index++] = ShopItem(Items.BASKET, 10)
    items[index++] = ShopItem(Items.BASKET_PACK, 1)
    items[index++] = ShopItem(Items.POTATO, 0)
    items[index++] = ShopItem(Items.ONION, 0)
    items[index++] = ShopItem(Items.CABBAGE, 0)
    items[index++] = ShopItem(Items.TOMATO, 0)
    items[index++] = ShopItem(Items.SWEETCORN, 0)
    items[index++] = ShopItem(Items.STRAWBERRY, 0)
    items[index++] = ShopItem(Items.WATERMELON, 0)
    items[index++] = ShopItem(Items.HAMMERSTONE_HOPS, 0)
    items[index++] = ShopItem(Items.ASGARNIAN_HOPS, 0)
    items[index++] = ShopItem(Items.YANILLIAN_HOPS, 0)
    items[index++] = ShopItem(Items.KRANDORIAN_HOPS, 0)
    items[index++] = ShopItem(Items.WILDBLOOD_HOPS, 0)
    items[index++] = ShopItem(Items.JUTE_FIBRE, 0)
    items[index++] = ShopItem(Items.BARLEY, 0)
    items[index++] = ShopItem(Items.PLANT_CURE, 10)
    items[index] = ShopItem(Items.AMULET_OF_FARMING_8, 10)
}

on_npc_option(npc = Npcs.ALICE, option = "trade") {
    player.openShop("Alice's Farming Shop")
}

on_npc_option(npc = Npcs.ALICE, option = "talk-to") {
    player.queue {
        chatNpc("Hello. How can I help you?")
        when (this.options("What are you selling?", "Can you give me any Farming advice?", "I'm okay, thank you.")) {
            1 -> player.openShop("Alice's Farming Shop")
            2 -> chatNpc("Yes - ask a gardener.")
            3 -> Unit
        }
    }
}
