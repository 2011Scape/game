package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Grum's Gold Exchange.",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.GOLD_RING, amount = 0)
    items[1] = ShopItem(Items.SAPPHIRE_RING, amount = 0)
    items[2] = ShopItem(Items.EMERALD_RING, amount = 0)
    items[3] = ShopItem(Items.RUBY_RING, amount = 0)
    items[4] = ShopItem(Items.DIAMOND_RING, amount = 0)
    items[5] = ShopItem(Items.GOLD_NECKLACE, amount = 0)
    items[6] = ShopItem(Items.SAPPHIRE_NECKLACE, amount = 0)
    items[7] = ShopItem(Items.EMERALD_NECKLACE, amount = 0)
    items[8] = ShopItem(Items.RUBY_NECKLACE, amount = 0)
    items[9] = ShopItem(Items.DIAMOND_NECKLACE, amount = 0)
    items[10] = ShopItem(Items.GOLD_AMULET_1692, amount = 0)
    items[11] = ShopItem(Items.SAPPHIRE_AMULET_1694, amount = 0)
    items[12] = ShopItem(Items.EMERALD_AMULET_1696, amount = 0)
    items[13] = ShopItem(Items.RUBY_AMULET_1698, amount = 0)
    items[14] = ShopItem(Items.DIAMOND_AMULET_1700, amount = 0)
    items[15] = ShopItem(Items.GOLD_BRACELET, amount = 0)
    items[16] = ShopItem(Items.SAPPHIRE_BRACELET, amount = 0)
    items[17] = ShopItem(Items.EMERALD_BRACELET, amount = 0)
    items[18] = ShopItem(Items.RUBY_BRACELET, amount = 0)
    items[19] = ShopItem(Items.DIAMOND_BRACELET, amount = 0)
}

on_npc_option(Npcs.GRUM, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.GRUM, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Grum's Gold Exchange.")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Would you like to buy or sell some gold jewellery?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (it.options("Yes please.", "No, I'm not that rich.")) {
        1 -> {
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "No, I'm not that rich.",
                facialExpression = FacialExpression.TALKING,
            )
            it.chatNpc(
                "Get out then.! We don't want any riff-raff in here.",
                facialExpression = FacialExpression.TALKING,
            )
        }
    }
}
