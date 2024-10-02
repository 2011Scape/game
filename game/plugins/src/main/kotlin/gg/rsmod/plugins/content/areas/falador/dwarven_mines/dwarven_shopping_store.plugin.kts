package gg.rsmod.plugins.content.areas.falador.dwarven_mines

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

val shopkeepers = arrayOf(Npcs.DWARF_582)

create_shop("Dwarven Shopping Store", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.EMPTY_POT, 5)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.SHEARS, 2)
    items[3] = ShopItem(Items.BUCKET, 3)
    items[4] = ShopItem(Items.BOWL, 2)
    items[5] = ShopItem(Items.CAKE_TIN, 2)
    items[6] = ShopItem(Items.TINDERBOX_590, 2)
    items[7] = ShopItem(Items.CHISEL, 2)
    items[8] = ShopItem(Items.HAMMER, 5)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Dwarven Shopping Store")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Can I help you at all?")
            when (options("Yes please. What are you selling?", "No thanks.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Dwarven Shopping Store")
                }
                2 -> {
                    chatPlayer("No thanks.")
                }
            }
        }
    }
}
