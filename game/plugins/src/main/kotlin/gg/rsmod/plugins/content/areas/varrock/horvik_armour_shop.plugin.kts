package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val shopkeepers = arrayOf(Npcs.HORVIK)

create_shop("Horvik's Armour Shop", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.BRONZE_CHAINBODY, 10)
    items[1] = ShopItem(Items.IRON_CHAINBODY, 10)
    items[2] = ShopItem(Items.BRONZE_MED_HELM, 10)
    items[3] = ShopItem(Items.IRON_MED_HELM, 10)
    items[4] = ShopItem(Items.BRONZE_PLATEBODY, 10)
    items[5] = ShopItem(Items.IRON_PLATEBODY, 10)
    items[6] = ShopItem(Items.BRONZE_SQ_SHIELD, 10)
    items[7] = ShopItem(Items.IRON_SQ_SHIELD, 10)
    items[8] = ShopItem(Items.BRONZE_PLATELEGS, 10)
    items[9] = ShopItem(Items.IRON_PLATELEGS, 10)
    items[10] = ShopItem(Items.BRONZE_PLATESKIRT, 10)
    items[11] = ShopItem(Items.IRON_PLATESKIRT, 10)
    items[12] = ShopItem(Items.STEEL_PLATEBODY, 10)
    items[13] = ShopItem(Items.BLACK_PLATEBODY, 10)
    items[14] = ShopItem(Items.MITHRIL_PLATEBODY, 10)
}

shopkeepers.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Horvik's Armour Shop")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc("Hello, do you need any help?")
            when(options("No thanks. I'm just looking around.", "Do you want to trade?", title = "Select an Option")) {
                2 -> {
                    player.openShop("Horvik's Armour Shop")
                }
                1 -> {
                    chatPlayer("No, I'm okay for swords right now.")
                }
            }
        }
    }
}
