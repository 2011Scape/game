package gg.rsmod.plugins.content.areas.karamja

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    name = "Jiminua's Jungle Store",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.TINDERBOX_590, 10)
    items[1] = ShopItem(Items.CANDLE, 10)
    items[2] = ShopItem(Items.TORCH, 10)
    items[3] = ShopItem(Items.EMPTY_POT, 30)
    items[4] = ShopItem(Items.ROPE, 10)
    items[5] = ShopItem(Items.LEATHER_BODY, 10)
    items[6] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[7] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[8] = ShopItem(Items.COOKED_MEAT, 10)
    items[9] = ShopItem(Items.BREAD, 10)
    items[10] = ShopItem(Items.EMPTY_VIAL, 300)
    items[11] = ShopItem(Items.VIAL_OF_WATER, 300)
    items[12] = ShopItem(Items.PESTLE_AND_MORTAR, 10)
    items[13] = ShopItem(Items.ANTIPOISON_3, 10)
    items[14] = ShopItem(Items.PAPYRUS, 10)
    items[15] = ShopItem(Items.CHARCOAL, 10)
    items[16] = ShopItem(Items.KNIFE, 10)
    items[17] = ShopItem(Items.HAMMER, 10)
    items[18] = ShopItem(Items.MACHETE, 10)
    items[19] = ShopItem(Items.CHISEL, 10)
    items[20] = ShopItem(Items.SPADE, 10)
    items[21] = ShopItem(Items.BRONZE_HATCHET, 10)
    items[22] = ShopItem(Items.BRONZE_PICKAXE, 10)
    items[23] = ShopItem(Items.IRON_HATCHET, 10)
}

on_npc_option(npc = Npcs.JIMINUA, option = "talk-to") {
    player.queue {
        chatNpc("Welcome to the Jungle Store. Can I help you at all?")
        when (options("Yes please. What are you selling?", "No thanks.")) {
            FIRST_OPTION -> {
                chatPlayer("Yes please. What are you selling?")
                chatNpc("Take yourself a good look.")
                player.openShop("Jiminua's Jungle Store")
            }
            SECOND_OPTION -> {
                chatPlayer("No thanks.")
            }
        }
    }
}

on_npc_option(npc = Npcs.JIMINUA, option = "trade") {
    player.openShop("Jiminua's Jungle Store")
}
