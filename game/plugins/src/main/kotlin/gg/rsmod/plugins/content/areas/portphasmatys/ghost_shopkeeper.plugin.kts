package gg.rsmod.plugins.content.areas.portphasmatys

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

create_shop(
    name = "Port Phasmatys General Store",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.EMPTY_POT, 300)
    items[1] = ShopItem(Items.BUCKET, 300)
    items[2] = ShopItem(Items.SHEARS, 10)
    items[3] = ShopItem(Items.JUG, 10)
    items[4] = ShopItem(Items.TINDERBOX_590, 10)
    items[5] = ShopItem(Items.CHISEL, 10)
    items[6] = ShopItem(Items.HAMMER, 10)
}

on_npc_option(npc = Npcs.GHOST_SHOPKEEPER, option = "talk-to") {
    if (player.hasEquipped(EquipmentType.AMULET, Items.GHOSTSPEAK_AMULET)) {
        player.queue {
            chatNpc(
                "Can I help you at all?",
                facialExpression = FacialExpression.NORMAL,
            )
            when (options("Yes please. What are you selling?", "No, thanks.")) {
                FIRST_OPTION -> {
                    player.openShop("Port Phasmatys General Store")
                }
                SECOND_OPTION -> {
                    chatPlayer("No, thanks.")
                }
            }
        }
    } else {
        player.queue {
            chatNpc(
                "Woooo wooo wooooo woooo",
                facialExpression = FacialExpression.NORMAL,
            )
        }
    }
}

on_npc_option(npc = Npcs.GHOST_SHOPKEEPER, option = "trade") {
    if (player.hasEquipped(EquipmentType.AMULET, Items.GHOSTSPEAK_AMULET)) {
        player.openShop("Port Phasmatys General Store")
    } else {
        player.queue {
            chatNpc(
                "Woooo wooo wooooo woooo",
                facialExpression = FacialExpression.OLD_NORMAL,
            )
        }
    }
}
