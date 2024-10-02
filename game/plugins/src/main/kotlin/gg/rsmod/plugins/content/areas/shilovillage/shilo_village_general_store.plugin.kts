package gg.rsmod.plugins.content.areas.shilovillage

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
create_shop("Shilo General Store", CoinCurrency()) {
    items[0] = ShopItem(Items.TINDERBOX_590, 2)
    items[1] = ShopItem(Items.VIAL, 10)
    items[2] = ShopItem(Items.PESTLE_AND_MORTAR, 3)
    items[3] = ShopItem(Items.EMPTY_POT, 3)
    items[4] = ShopItem(Items.BRONZE_HATCHET, 3)
    items[5] = ShopItem(Items.BRONZE_PICKAXE, 2)
    items[6] = ShopItem(Items.IRON_HATCHET, 5)
    items[7] = ShopItem(Items.LEATHER_BODY, 12)
    items[8] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[9] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[10] = ShopItem(Items.COOKED_MEAT, 2)
    items[11] = ShopItem(Items.BREAD, 10)
    items[12] = ShopItem(Items.BRONZE_BAR, 10)
    items[13] = ShopItem(Items.SPADE, 10)
    items[14] = ShopItem(Items.CANDLE, 10)
    items[15] = ShopItem(Items.UNLIT_TORCH, 10)
    items[16] = ShopItem(Items.CHISEL, 10)
    items[17] = ShopItem(Items.HAMMER, 10)
    items[18] = ShopItem(Items.PAPYRUS, 50)
    items[19] = ShopItem(Items.CHARCOAL, 50)
    items[20] = ShopItem(Items.VIAL_OF_WATER, 50)
    items[21] = ShopItem(Items.MACHETE, 50)
    items[22] = ShopItem(Items.ROPE, 10)
}

on_npc_option(npc = Npcs.OBLI, option = "talk-to") {
    player.queue {
        chatNpc("Welcome to Obli's General Store Bwana!", "Would you like to see my items?")
        when (options("Yes please!", "No, but thanks for the offer.")) {
            FIRST_OPTION -> {
                chatPlayer("Yes please.")
                player.openShop("Shilo General Store")
            }
            SECOND_OPTION -> {
                chatPlayer("No, but thanks for the offer.")
            }
        }
    }
}

on_npc_option(npc = Npcs.OBLI, option = "trade") {
    player.openShop("Shilo General Store")
}
