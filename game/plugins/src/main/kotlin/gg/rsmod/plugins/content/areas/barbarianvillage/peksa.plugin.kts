package gg.rsmod.plugins.content.areas.barbarianvillage

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Peksa's Helmet Shop",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_MED_HELM, amount = 10)
    items[1] = ShopItem(Items.IRON_MED_HELM, amount = 10)
    items[2] = ShopItem(Items.STEEL_MED_HELM, amount = 10)
    items[3] = ShopItem(Items.MITHRIL_MED_HELM, amount = 10)
    items[4] = ShopItem(Items.ADAMANT_MED_HELM, amount = 10)
    items[5] = ShopItem(Items.BRONZE_FULL_HELM, amount = 10)
    items[6] = ShopItem(Items.IRON_FULL_HELM, amount = 10)
    items[7] = ShopItem(Items.STEEL_FULL_HELM, amount = 10)
    items[8] = ShopItem(Items.MITHRIL_FULL_HELM, amount = 10)
    items[9] = ShopItem(Items.ADAMANT_FULL_HELM, amount = 10)
}

on_npc_option(Npcs.PEKSA, "trade") {
    player.openShop("Peksa's Helmet Shop")
}

on_npc_option(Npcs.PEKSA, "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Are you interested in buying or selling a helmet?")
    when (it.options("I could be, yes.", "No, I'll pass on that.")) {
        1 -> it.player.openShop("Peksa's Helmet Shop")
        2 -> {
            it.chatPlayer("No, I'll pass on that.")
            it.chatNpc("Well, come back if you change your mind.")
        }
    }
}
