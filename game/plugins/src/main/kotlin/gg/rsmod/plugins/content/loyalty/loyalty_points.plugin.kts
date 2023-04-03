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
    containsSamples = false
) {
    items[0] = ShopItem(Items.DISK_OF_RETURNING, amount = 1, sellPrice = 10_000)
    items[1] = ShopItem(Items.CHRISTMAS_CRACKER, amount = 1, sellPrice = 8_500)
    items[2] = ShopItem(Items.SANTA_HAT, amount = 1, sellPrice = 7_000)
    items[3] = ShopItem(Items.RED_HWEEN_MASK, amount = 1, sellPrice = 4_000)
    items[4] = ShopItem(Items.GREEN_HWEEN_MASK, amount = 1, sellPrice = 4_000)
    items[5] = ShopItem(Items.BLUE_HWEEN_MASK, amount = 1, sellPrice = 4_000)
    items[6] = ShopItem(Items.EASTER_EGG, amount = 1, sellPrice = 2_000)
    items[7] = ShopItem(Items.PUMPKIN, amount = 1, sellPrice = 2_000)
}

on_npc_option(npc = Npcs.XUAN, option = "open-shop") {
    player.openShop("Loyalty Rewards")
    player.setComponentText(interfaceId = 620, component = 24, text = "You currently have ${player.attr[LOYALTY_POINTS]!!.format()} loyalty points.")
}