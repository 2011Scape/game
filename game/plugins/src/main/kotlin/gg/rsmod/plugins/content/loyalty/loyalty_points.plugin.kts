package gg.rsmod.plugins.content.loyalty

import gg.rsmod.game.model.attr.LOYALTY_POINTS
import gg.rsmod.plugins.content.mechanics.shops.LoyaltyPointsCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    "Loyalty Rewards",
    currency = LoyaltyPointsCurrency(),
    purchasePolicy = PurchasePolicy.BUY_NONE,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.DISK_OF_RETURNING, amount = 1, sellPrice = 10_000)
    items[1] = ShopItem(Items.CHRISTMAS_CRACKER, amount = 1, sellPrice = 35_000)
    items[2] = ShopItem(Items.SANTA_HAT, amount = 1, sellPrice = 10_000)
    items[3] = ShopItem(Items.RED_HWEEN_MASK, amount = 1, sellPrice = 7_000)
    items[4] = ShopItem(Items.GREEN_HWEEN_MASK, amount = 1, sellPrice = 7_000)
    items[5] = ShopItem(Items.BLUE_HWEEN_MASK, amount = 1, sellPrice = 7_000)
    items[6] = ShopItem(Items.EASTER_EGG, amount = 1, sellPrice = 3_000)
    items[7] = ShopItem(Items.PUMPKIN, amount = 1, sellPrice = 3_000)
    items[8] = ShopItem(Items.GUTHIX_HALO, amount = 1, sellPrice = 2_500)
    items[9] = ShopItem(Items.SARADOMIN_HALO, amount = 1, sellPrice = 2_500)
    items[10] = ShopItem(Items.ZAMORAK_HALO, amount = 1, sellPrice = 2_500)
    items[11] = ShopItem(Items.ANGER_SWORD, amount = 1, sellPrice = 500)
    items[12] = ShopItem(Items.ANGER_SPEAR, amount = 1, sellPrice = 500)
    items[13] = ShopItem(Items.ANGER_MACE, amount = 1, sellPrice = 500)
    items[14] = ShopItem(Items.ANGER_BATTLEAXE, amount = 1, sellPrice = 500)
    items[15] = ShopItem(Items.RUBBER_CHICKEN, amount = 1, sellPrice = 500)
    items[16] = ShopItem(Items.CHOCATRICE_CAPE, amount = 1, sellPrice = 500)
    items[17] = ShopItem(Items.REINDEER_HAT, amount = 1, sellPrice = 500)
    items[18] = ShopItem(Items.JACK_LANTERN_MASK, amount = 1, sellPrice = 500)
    items[19] = ShopItem(Items.GRIM_REAPER_HOOD, amount = 1, sellPrice = 500)
    items[20] = ShopItem(Items.WEB_CLOAK, amount = 1, sellPrice = 500)
}

on_npc_option(npc = Npcs.XUAN, option = "talk-to") {
    player.queue {
        chatNpc("Good day, my friend! Good day!")
        chatNpc(
            *"It is my privilege to offer you access to an exclusive stock of the finest and most exotic wares."
                .splitForDialogue(),
        )
        when (options("Show me what you have.", "How many Loyalty Points do I have?")) {
            FIRST_OPTION -> {
                player.openShop("Loyalty Rewards", points = true)
                player.setComponentText(
                    interfaceId = 620,
                    component = 24,
                    text = "You currently have ${player.attr[LOYALTY_POINTS]!!.format()} loyalty points.",
                )
            }
            SECOND_OPTION -> {
                chatNpc("You have ${player.attr[LOYALTY_POINTS]!!.format()} points, my friend!")
            }
        }
    }
}

on_npc_option(npc = Npcs.XUAN, option = "open-shop") {
    player.openShop("Loyalty Rewards", points = true)
    player.setComponentText(
        interfaceId = 620,
        component = 24,
        text = "You currently have ${player.attr[LOYALTY_POINTS]!!.format()} loyalty points.",
    )
}
