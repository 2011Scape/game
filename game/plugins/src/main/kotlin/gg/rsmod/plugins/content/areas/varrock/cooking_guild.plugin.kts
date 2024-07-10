package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency


create_shop(
    name = "Pie Shop",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.PIE_RECIPE_BOOK, 10)
    items[1] = ShopItem(Items.REDBERRY_PIE, 1)
    items[2] = ShopItem(Items.MEAT_PIE, 0)
    items[3] = ShopItem(Items.MUD_PIE, 0)
    items[4] = ShopItem(Items.APPLE_PIE, 0)
    items[5] = ShopItem(Items.GARDEN_PIE, 0)
    items[6] = ShopItem(Items.FISH_PIE, 0)
    items[7] = ShopItem(Items.ADMIRAL_PIE, 0)
    items[8] = ShopItem(Items.WILD_PIE, 0)
    items[9] = ShopItem(Items.SUMMER_PIE, 0)
    items[10] = ShopItem(Items.POT_OF_FLOUR, 100)
    items[11] = ShopItem(Items.PIE_DISH, 100)
}

on_npc_option(npc = Npcs.ROMILY_WEAKLAX, option = "trade") {
    player.openShop("Pie Shop")
}

on_obj_option(obj = Objs.DOOR_2712, option = "open") {
    val obj = player.getInteractingGameObj()
    if (player.tile.z <= obj.tile.z && player.skills.getMaxLevel(Skills.COOKING) < 32) {
        player.queue {
            messageBox("You need a Cooking level of 32 to access the Cooking Guild.")
        }
        return@on_obj_option
    }
    if (player.tile.z <= obj.tile.z && !player.hasEquipped(EquipmentType.HEAD, Items.CHEFS_HAT)) {
        player.queue {
            chatNpc(
                *"You can't come in here unless you're wearing a chef's hat or something like that.".splitForDialogue(),
                npc = Npcs.HEAD_CHEF,
            )
        }
        return@on_obj_option
    }

    val dynamicObj = DynamicObject(obj)
    val moveX = obj.tile.x
    val moveZ = if (player.tile.z == 3443) 3444 else 3443
    val changedDoorId = 2713
    val doorX = obj.tile.x
    val doorZ = obj.tile.z + 1
    val rotation = 2
    val wait = 3
    player.handleTemporaryDoor(
        obj = dynamicObj,
        movePlayerX = moveX,
        movePlayerZ = moveZ,
        newDoorId = changedDoorId,
        moveObjX = doorX,
        moveObjZ = doorZ,
        newRotation = rotation,
        waitTime = wait,
    )
}
