package gg.rsmod.plugins.content.areas.morytania

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    name = "Alice's Farming shop.",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES
) {
    items[0] = ShopItem(Items.RAKE, 500)
    items[1] = ShopItem(Items.SEED_DIBBER, 500)
    items[2] = ShopItem(Items.SECATEURS, 500)
    items[3] = ShopItem(Items.SPADE, 500)
    items[4] = ShopItem(Items.GARDENING_TROWEL, 500)
    items[5] = ShopItem(Items.WATERING_CAN, 500)
    items[6] = ShopItem(Items.PLANT_POT, 500)
    items[7] = ShopItem(Items.COMPOST, 500)
    items[8] = ShopItem(Items.EMPTY_SACK, 500)
    items[9] = ShopItem(Items.EMPTY_SACK_PACK, 30)
    items[10] = ShopItem(Items.BASKET, 500)
    items[11] = ShopItem(Items.BASKET_PACK, 30)
    items[12] = ShopItem(Items.BUCKET, 100)
    items[13] = ShopItem(Items.POTATO, 0)
    items[14] = ShopItem(Items.ONION, 0)
    items[15] = ShopItem(Items.CABBAGE, 0)
    items[16] = ShopItem(Items.TOMATO, 0)
    items[17] = ShopItem(Items.SWEETCORN, 0)
    items[18] = ShopItem(Items.STRAWBERRY, 0)
    items[19] = ShopItem(Items.WATERMELON, 0)
    items[20] = ShopItem(Items.HAMMERSTONE_HOPS, 0)
    items[21] = ShopItem(Items.ASGARNIAN_HOPS, 0)
    items[22] = ShopItem(Items.YANILLIAN_HOPS, 0)
    items[23] = ShopItem(Items.KRANDORIAN_HOPS, 0)
    items[23] = ShopItem(Items.WILDBLOOD_HOPS, 0)
    items[23] = ShopItem(Items.JUTE_FIBRE, 0)
    items[23] = ShopItem(Items.BARLEY, 0)
    items[23] = ShopItem(Items.PLANT_CURE, 100)

}

on_npc_option(npc = Npcs.ALICE, option = "talk-to") {
    player.queue {
        chatNpc("Hello. How can I help you?")
        when(options("What are you selling?", "Can you give me any Farming advice?", "I'm okay, thank you.")) {
            FIRST_OPTION -> {
                player.openShop("Alice's Farming shop.")
            }
            SECOND_OPTION -> {
                chatPlayer("Can you give me any Farming advice?")
                chatNpc("Yes - ask a gardener.")
            }
            THIRD_OPTION -> {
                chatPlayer("I'm okay, thank you.")
            }
        }
    }
}

on_npc_option(npc = Npcs.ALICE, option = "trade") {
    player.openShop("Alice's Farming shop.")
}