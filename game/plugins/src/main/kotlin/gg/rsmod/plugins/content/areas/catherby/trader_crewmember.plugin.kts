package gg.rsmod.plugins.content.areas.catherby

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */


// ** Note: Ally
// ** This shop uses the OSRS stocks as it provides QoL benefits for all players
// ** Most specifically the fruits, sand, and soda ash
// ** This will most likely be fixed in the future
// ** TODO: check this note

val ids = arrayOf(Npcs.TRADER_CREWMEMBER_SHIPYARD, Npcs.TRADER_CREWMEMBER_CATHERBY, Npcs.TRADER_CREWMEMBER_PORT_KHAZARD, Npcs.TRADER_CREWMEMBER_MUSAPOINT, Npcs.TRADER_CREWMEMBER_PORT_PHASMATY)
create_shop("Trader Stan's Trading Post", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_TRADEABLES) {
    items[0] = ShopItem(Items.EMPTY_POT, 5)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.SHEARS, 2)
    items[3] = ShopItem(Items.BUCKET, 3)
    items[4] = ShopItem(Items.BOWL, 2)
    items[5] = ShopItem(Items.CAKE_TIN, 2)
    items[6] = ShopItem(Items.TINDERBOX_590, 2)
    items[7] = ShopItem(Items.CHISEL, 2)
    items[8] = ShopItem(Items.HAMMER, 5)
    items[9] = ShopItem(Items.NEWCOMER_MAP, 5)
    items[10] = ShopItem(Items.SECURITY_BOOK, 5)
    items[11] = ShopItem(Items.ROPE, 2)
    items[12] = ShopItem(Items.KNIFE, 2)
    items[13] = ShopItem(Items.PINEAPPLE, 15)
    items[14] = ShopItem(Items.BANANA, 15)
    items[15] = ShopItem(Items.ORANGE, 10)
    items[16] = ShopItem(Items.BUCKET_OF_SLIME, 10)
    items[17] = ShopItem(Items.GLASSBLOWING_PIPE, 15)
    items[18] = ShopItem(Items.BUCKET_OF_SAND, 10)
    items[19] = ShopItem(Items.SEAWEED, 20)
    items[20] = ShopItem(Items.SODA_ASH, 10)
    items[21] = ShopItem(Items.LOBSTER_POT, 20)
    items[22] = ShopItem(Items.FISHING_ROD, 20)
    items[23] = ShopItem(Items.SWAMP_PASTE, 30)
    items[24] = ShopItem(Items.TYRAS_HELM, 25)
    items[25] = ShopItem(Items.RAW_RABBIT, 20)
    items[26] = ShopItem(Items.EYE_PATCH, 5)
}

ids.forEach {
    on_npc_option(npc = it, option = "trade") {
        player.openShop("Trader Stan's Trading Post")
    }
}