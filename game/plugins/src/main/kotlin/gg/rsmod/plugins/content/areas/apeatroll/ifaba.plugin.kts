package gg.rsmod.plugins.content.areas.apeatroll

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod
 * Date: 01/02/2024
 */


create_shop(
    name = "Ifaba's General Store",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES
) {
    items[0] = ShopItem(Items.EMPTY_POT, 3)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.ROPE, 8)
    items[3] = ShopItem(Items.BUCKET, 2)
    items[4] = ShopItem(Items.TINDERBOX_590, 2)
    items[5] = ShopItem(Items.HAMMER, 5)
}

on_npc_option(npc = Npcs.IFABA, option = "talk-to") {
    //missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.queue {
            chatNpc("Would you like to buy or sell anything?",
             facialExpression = FacialExpression.OLD_NORMAL)
            when(options("Yes, please.", "No, thanks.")) {
                FIRST_OPTION -> {
                    chatPlayer("Yes, please.")
                    player.openShop("Ifaba's General Store")
                }
                SECOND_OPTION -> {
                    chatPlayer("No, thanks.")
                }
            }
        }
    } else {
        player.queue {
            chatNpc("Ook ook eek!",
            facialExpression = FacialExpression.OLD_NORMAL)
        }
    }
}

on_npc_option(npc = Npcs.IFABA, option = "trade") {
    //missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.openShop("Ifaba's General Store")
    } else {
        player.queue {
            chatNpc("Ook ook eek!",
             facialExpression = FacialExpression.OLD_NORMAL)
        }
    }
}
