package gg.rsmod.plugins.content.areas.apeatroll

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    name = "Tutab's Magical Market",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.FIRE_RUNE, 1000)
    items[1] = ShopItem(Items.WATER_RUNE, 1000)
    items[2] = ShopItem(Items.AIR_RUNE, 1000)
    items[3] = ShopItem(Items.EARTH_RUNE, 1000)
    items[4] = ShopItem(Items.LAW_RUNE, 250)
    items[5] = ShopItem(Items.EYE_OF_GNOME, 10)
    items[6] = ShopItem(Items.MONKEY_DENTURES, 100)
    items[7] = ShopItem(Items.MONKEY_TALISMAN, 50)
}

on_npc_option(npc = Npcs.TUTAB, option = "talk-to") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.queue {
            chatNpc(
                "Would you like to buy or sell some magical items?",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
            when (options("Yes, please.", "No, thanks.")) {
                FIRST_OPTION -> {
                    chatPlayer("Yes, please.")
                    player.openShop("Tutab's Magical Market")
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

on_npc_option(npc = Npcs.TUTAB, option = "trade") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.openShop("Tutab's Magical Market")
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}
