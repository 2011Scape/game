package gg.rsmod.plugins.content.areas.apeatroll

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    name = "Solihib's Food Stall",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.MONKEY_NUTS, 200)
    items[1] = ShopItem(Items.BANANA, 1000)
    items[2] = ShopItem(Items.BANANA_STEW, 10)
    items[3] = ShopItem(Items.MONKEY_BAR, 20)
}

on_npc_option(npc = Npcs.SOLIHIB, option = "talk-to") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.queue {
            chatNpc(
                "Would you like to buy or sell some food?",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
            when (options("Yes, please.", "No, thanks.")) {
                FIRST_OPTION -> {
                    chatPlayer("Yes, please.")
                    player.openShop("Solihib's Food Stall")
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

on_npc_option(npc = Npcs.SOLIHIB, option = "trade") {
    // TODO Add missing function greegree
    if (player.hasEquipped(EquipmentType.AMULET, Items.MONKEYSPEAK_AMULET)) {
        player.openShop("Solihib's Food Stall")
    } else {
        player.queue {
            chatNpc(
                "Ook ook eek!",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}
