package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Bob's Brilliant Axes", currency = CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.BRONZE_PICKAXE, 1, resupplyCycles = 1000)
    sampleItems[1] = ShopItem(Items.BRONZE_HATCHET, 1, resupplyCycles = 1000)
    items[0] = ShopItem(Items.BRONZE_PICKAXE, 10)
    items[1] = ShopItem(Items.BRONZE_HATCHET, 10)
    items[2] = ShopItem(Items.IRON_HATCHET, 10)
    items[3] = ShopItem(Items.STEEL_HATCHET, 10)
    items[4] = ShopItem(Items.IRON_BATTLEAXE, 10)
    items[5] = ShopItem(Items.STEEL_BATTLEAXE, 10)
    items[6] = ShopItem(Items.MITHRIL_BATTLEAXE, 10)
}

on_npc_option(Npcs.BOB, "trade") {
    player.openShop("Bob's Brilliant Axes")
}

on_npc_option(Npcs.BOB, option = "talk-to") {
    player.queue { optionsDialogue(this) }
}

suspend fun quest(task: QueueTask) {
    task.chatNpc("Get yer own!")
}

suspend fun shop(task: QueueTask) {
    task.chatPlayer("Have you anything to sell?")
    task.chatNpc("Yes! I buy and sell axes! Take your pick (or axe)!")
    task.player.openShop("Bob's Brilliant Axes")
}

suspend fun optionsDialogue(task: QueueTask) {
    when (task.options("Give me a quest!", "Have you anything to sell?", "Can you repair my items for me?")) {
        1 -> {
            quest(task)
        }
        2 -> {
            shop(task)
        }
    }
}
