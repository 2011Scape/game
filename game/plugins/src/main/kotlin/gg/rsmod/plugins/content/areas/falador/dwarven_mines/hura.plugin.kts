package gg.rsmod.plugins.content.areas.falador.dwarven_mines

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

val shopkeepers = arrayOf(Npcs.HURA)

create_shop(
    "Hura's Crossbow Shop",
    CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.WOODEN_STOCK, 5)
    items[1] = ShopItem(Items.OAK_STOCK, 5)
    items[2] = ShopItem(Items.WILLOW_STOCK, 5)
    items[3] = ShopItem(Items.TEAK_STOCK, 5)
    items[4] = ShopItem(Items.MAPLE_STOCK, 5)
    items[5] = ShopItem(Items.MAHOGANY_STOCK, 0)
    items[6] = ShopItem(Items.YEW_STOCK, 0)
    items[7] = ShopItem(Items.BRONZE_LIMBS, 5)
    items[8] = ShopItem(Items.IRON_LIMBS, 5)
    items[9] = ShopItem(Items.STEEL_LIMBS, 5)
    items[10] = ShopItem(Items.MITHRIL_LIMBS, 5)
    items[11] = ShopItem(Items.ADAMANTITE_LIMBS, 0)
    items[12] = ShopItem(Items.RUNITE_LIMBS, 0)
    items[13] = ShopItem(Items.BRONZE_CROSSBOW, 0)
    items[14] = ShopItem(Items.IRON_CROSSBOW, 0)
    items[15] = ShopItem(Items.STEEL_CROSSBOW, 0)
    items[16] = ShopItem(Items.MITH_CROSSBOW, 0)
    items[17] = ShopItem(Items.ADAMANT_CROSSBOW, 0)
    items[18] = ShopItem(Items.RUNE_CROSSBOW, 0)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Hura's Crossbow Shop")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Can I help you at all?")
            when (options("Yes please. What are you selling?", "No thanks.", title = "Select an Option")) {
                1 -> {
                    player.openShop("Hura's Crossbow Shop")
                }
                2 -> {
                    chatPlayer("No thanks.")
                }
            }
        }
    }
}
