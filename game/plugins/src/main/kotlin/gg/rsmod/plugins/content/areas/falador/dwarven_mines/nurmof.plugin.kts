package gg.rsmod.plugins.content.areas.falador.dwarven_mines

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

val shopkeepers = arrayOf(Npcs.NURMOF)

create_shop(
    "Nurmof's Pickaxe Shop",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_PICKAXE, 6)
    items[1] = ShopItem(Items.IRON_PICKAXE, 5)
    items[2] = ShopItem(Items.STEEL_PICKAXE, 4)
    items[3] = ShopItem(Items.MITHRIL_PICKAXE, 3)
    items[4] = ShopItem(Items.ADAMANT_PICKAXE, 2)
    items[5] = ShopItem(Items.RUNE_PICKAXE, 1)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Nurmof's Pickaxe Shop")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Can I help you at all?")
            when (options("Yes please. What are you selling?", "No thanks.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Nurmof's Pickaxe Shop")
                }
                2 -> {
                    chatPlayer("No thanks.")
                }
            }
        }
    }
}
