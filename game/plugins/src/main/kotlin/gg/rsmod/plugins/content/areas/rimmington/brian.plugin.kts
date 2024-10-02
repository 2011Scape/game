package gg.rsmod.plugins.content.areas.rimmington

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod
 * Date: 01/02/2024
 */

create_shop(
    name = "Brian's Archery Supplies.",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.STEEL_ARROW, 1500)
    items[1] = ShopItem(Items.MITHRIL_ARROW, 1000)
    items[2] = ShopItem(Items.ADAMANT_ARROW, 800)
    items[3] = ShopItem(Items.OAK_SHORTBOW, 4)
    items[4] = ShopItem(Items.OAK_LONGBOW, 4)
    items[5] = ShopItem(Items.WILLOW_SHORTBOW, 3)
    items[6] = ShopItem(Items.WILLOW_LONGBOW, 3)
    items[7] = ShopItem(Items.MAPLE_SHORTBOW, 2)
    items[8] = ShopItem(Items.MAPLE_LONGBOW, 2)
}

on_npc_option(npc = Npcs.BRIAN_1860, option = "talk-to") {
    player.queue {
        chatNpc("Would you like to buy some archery equipment?")
        when (options("No thanks, I've got all the archery equipment I need.", "Let's see what you've got, then.")) {
            FIRST_OPTION -> {
                chatPlayer("No thanks, I've got all the archery equipment I need.")
                chatNpc("Okay. Fare well on your travels.")
            }
            SECOND_OPTION -> {
                player.openShop("Brian's Archery Supplies.")
            }
        }
    }
}

on_npc_option(npc = Npcs.BRIAN_1860, option = "trade") {
    player.openShop("Brian's Archery Supplies.")
}
