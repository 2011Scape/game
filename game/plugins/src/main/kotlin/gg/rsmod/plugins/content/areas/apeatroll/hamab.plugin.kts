package gg.rsmod.plugins.content.areas.apeatroll

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    name = "Hamab's Crafting Emporium",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.CHISEL, 2)
    items[1] = ShopItem(Items.RING_MOULD, 4)
    items[2] = ShopItem(Items.NECKLACE_MOULD, 2)
    items[3] = ShopItem(Items.MAMULET_MOULD, 2)
    items[4] = ShopItem(Items.NEEDLE, 3)
    items[5] = ShopItem(Items.THREAD, 100)
    items[6] = ShopItem(Items.BALL_OF_WOOL, 100)
    items[7] = ShopItem(Items.BRACELET_MOULD, 2)
}

on_npc_option(npc = Npcs.HAMAB, option = "talk-to") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.queue {
            chatNpc(
                "Would you like to buy or sell some crafting equipment?",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
            when (options("Yes, please.", "No, thanks.")) {
                FIRST_OPTION -> {
                    chatPlayer("Yes, please.")
                    player.openShop("Hamab's Crafting Emporium")
                }
                SECOND_OPTION -> {
                    chatPlayer("No, thanks.")
                }
            }
        }
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}

on_npc_option(npc = Npcs.HAMAB, option = "trade") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.openShop("Hamab's Crafting Emporium")
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}
