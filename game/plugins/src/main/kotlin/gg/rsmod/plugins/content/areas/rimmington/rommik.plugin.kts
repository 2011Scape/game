package gg.rsmod.plugins.content.areas.rimmington

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod
 * Date: 01/02/2024
 */

create_shop(
    name = "Rommik's Crafty Supplies.",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.CHISEL, 10)
    items[1] = ShopItem(Items.RING_MOULD, 10)
    items[2] = ShopItem(Items.NECKLACE_MOULD, 10)
    items[3] = ShopItem(Items.AMULET_MOULD, 10)
    items[4] = ShopItem(Items.NEEDLE, 10)
    items[5] = ShopItem(Items.THREAD, 1000)
    items[6] = ShopItem(Items.HOLY_MOULD, 10)
    items[7] = ShopItem(Items.SICKLE_MOULD, 10)
    items[8] = ShopItem(Items.TIARA_MOULD, 10)
    items[9] = ShopItem(Items.BOLT_MOULD, 10)
    items[10] = ShopItem(Items.BRACELET_MOULD, 10)
}

on_npc_option(npc = Npcs.ROMMIK, option = "talk-to") {
    player.queue {
        chatNpc("Would you like to buy some crafting equipment?")
        when (
            options(
                "No thanks, I've got all the Crafting equipment I need.",
                "Let's see what you've got, then.",
            )
        ) {
            FIRST_OPTION -> {
                chatPlayer("No thanks; I've got all the Crafting equipment I need.")
                chatNpc("Okay. Fare well on your travels.")
            }
            SECOND_OPTION -> {
                player.openShop("Rommik's Crafty Supplies.")
            }
        }
    }
}

on_npc_option(npc = Npcs.ROMMIK, option = "trade") {
    player.openShop("Rommik's Crafty Supplies.")
}
