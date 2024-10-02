package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Culinaromancer's Chest",
    CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.CHOCOLATE_BAR, 300)
    items[1] = ShopItem(Items.CHEESE, 10)
    items[2] = ShopItem(Items.TOMATO, 10)
    items[3] = ShopItem(Items.COOKING_APPLE, 50)
    items[4] = ShopItem(Items.GRAPES, 5)
    items[5] = ShopItem(Items.POT_OF_FLOUR, 100)
    items[6] = ShopItem(Items.PIZZA_BASE, 1)
    items[7] = ShopItem(Items.EGG, 10)
    items[8] = ShopItem(Items.BUCKET_OF_MILK, 10)
    items[9] = ShopItem(Items.POT_OF_CREAM, 10)
    items[10] = ShopItem(Items.PAT_OF_BUTTER, 10)
    items[11] = ShopItem(Items.SPICE, 10)
    items[12] = ShopItem(Items.PIE_DISH, 10)
    items[13] = ShopItem(Items.CAKE_TIN, 10)
    items[14] = ShopItem(Items.BOWL, 10)
    items[15] = ShopItem(Items.JUG, 10)
    items[16] = ShopItem(Items.EMPTY_POT, 10)
    items[17] = ShopItem(Items.EMPTY_CUP, 10)
    items[18] = ShopItem(Items.BUCKET, 10)
}

create_shop(
    "Culinaromancer's Chest ",
    CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    stockSize = 20,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.GLOVES_7453, 10)
    items[1] = ShopItem(Items.GLOVES_7454, 10)
    items[2] = ShopItem(Items.GLOVES_7455, 10)
    items[3] = ShopItem(Items.GLOVES_7456, 10)
    items[4] = ShopItem(Items.GLOVES_7457, 10)
    items[5] = ShopItem(Items.GLOVES_7458, 10)
    items[6] = ShopItem(Items.GLOVES_7459, 10)
    items[7] = ShopItem(Items.GLOVES_7460, 10)
    items[8] = ShopItem(Items.GLOVES_7461, 10)
    items[9] = ShopItem(Items.GLOVES_7462, 10)
    items[10] = ShopItem(Items.WOODEN_SPOON, 10)
    items[11] = ShopItem(Items.EGG_WHISK, 10)
    items[12] = ShopItem(Items.SPORK, 10)
    items[13] = ShopItem(Items.SPATULA, 10)
    items[14] = ShopItem(Items.FRYING_PAN, 10)
    items[15] = ShopItem(Items.SKEWER, 10)
    items[16] = ShopItem(Items.ROLLING_PIN, 10)
    items[17] = ShopItem(Items.KITCHEN_KNIFE, 10)
    items[18] = ShopItem(Items.MEAT_TENDERISER, 10)
    items[19] = ShopItem(Items.CLEAVER, 10)
}

on_obj_option(obj = Objs.TRAPDOOR_36687, option = "climb-down") {
    player.handleLadder(height = 0, underground = true)
}

on_obj_option(obj = Objs.CHEST_12309, option = "buy-food") {
    player.openShop("Culinaromancer's Chest")
}

on_obj_option(obj = Objs.CHEST_12309, option = "buy-items") {
    player.openShop("Culinaromancer's Chest ")
}

on_obj_option(obj = Objs.HOLE_6905, option = "squeeze-through", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (player.tile.x < 3220) {
            enterHole(player, obj, 2, 0)
        } else {
            enterHole(player, obj, -2, 0)
        }
    }
}

fun enterHole(
    p: Player,
    obj: GameObject,
    xOffset: Int,
    zOffset: Int,
) {
    val tile = obj.tile
    p.queue {
        p.animate(10578, idleOnly = true)
        wait(2)
        p.animate(10579)
        p.teleportTo(tile.x + xOffset, tile.z + zOffset)
        p.message("You squeeze through the hole.")
    }
}
