package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop(
    "Slayer Equipment",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_NONE,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.ENCHANTED_GEM, amount = 300)
    items[1] = ShopItem(Items.MIRROR_SHIELD, amount = 10)
    items[2] = ShopItem(Items.LEAFBLADED_SPEAR, amount = 10)
    items[3] = ShopItem(Items.BROAD_ARROW, amount = 3000)
    items[4] = ShopItem(Items.BAG_OF_SALT, amount = 1000)
    items[5] = ShopItem(Items.ROCK_HAMMER, amount = 10)
    items[6] = ShopItem(Items.FACE_MASK, amount = 10)
    items[7] = ShopItem(Items.EARMUFFS, amount = 10)
    items[8] = ShopItem(Items.NOSE_PEG, amount = 10)
    items[9] = ShopItem(Items.SLAYERS_STAFF, amount = 10)
    items[10] = ShopItem(Items.SPINY_HELMET, amount = 10)
    items[11] = ShopItem(Items.FISHING_EXPLOSIVE_6664, amount = 1000)
    items[12] = ShopItem(Items.ICE_COOLER, amount = 1000)
    items[13] = ShopItem(Items.SLAYER_GLOVES, amount = 10)
    items[14] = ShopItem(Items.UNLIT_BUG_LANTERN, amount = 10)
    items[15] = ShopItem(Items.INSULATED_BOOTS, amount = 10)
    items[16] = ShopItem(Items.FUNGICIDE_SPRAY_10, amount = 10)
    items[17] = ShopItem(Items.FUNGICIDE, amount = 1000)
    items[18] = ShopItem(Items.WITCHWOOD_ICON, amount = 10)
    items[19] = ShopItem(Items.SLAYER_BELL, amount = 10)
    items[20] = ShopItem(Items.BROAD_ARROW_HEADS, amount = 3000)
    items[21] = ShopItem(Items.UNFINISHED_BROAD_BOLTS, amount = 3000)
}

val masters = arrayOf(Npcs.TURAEL, Npcs.VANNAKA, Npcs.MAZCHNA)

masters.forEach {
    on_npc_option(npc = it, option = "trade") {
        player.openShop("Slayer Equipment")
    }
}
