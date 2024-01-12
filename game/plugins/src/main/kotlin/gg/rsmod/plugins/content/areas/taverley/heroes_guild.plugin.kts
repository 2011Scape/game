package gg.rsmod.plugins.content.areas.taverley

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val GloryOrROW = intArrayOf(1704, 1706, 1708, 1710, 1712, 10354, 10356, 10358, 10360, 10362, 2572, 20653, 20655, 20657, 20659)

for (jewelryId in GloryOrROW) {
    on_item_on_obj(Objs.FOUNTAIN_OF_HEROES, jewelryId) {
        val inventory = player.inventory
        val def = player.world.definitions

        inventory.rawItems.forEach { item ->
            item?.let { nonNullItem ->
                if (nonNullItem.id in GloryOrROW && !nonNullItem.getName(def).lowercase().contains("(4)")) {
                    val fullyChargedItemId = getChargedId(nonNullItem.id)
                    if (fullyChargedItemId != -1) {
                        if (inventory.remove(item = nonNullItem).hasSucceeded()) {
                            inventory.add(fullyChargedItemId)
                            player.animate(827)
                            player.message("You dip the ${nonNullItem.getName(def).lowercase()} into the fountain restoring all charges.")
                        }
                    }
                }
            }
        }
    }
}

fun getChargedId(itemId: Int): Int {
    //glory
    if ((itemId == 1704) || (itemId == 1706) || (itemId == 1708) || (itemId == 1710)) return 1712

    //trimmed glory
    if ((itemId == 10356) || (itemId == 10358) || (itemId == 10360) || (itemId == 10362)) return 10354

    //row
    if ((itemId == 2572) || (itemId == 20653) || (itemId == 20655) || (itemId == 20657)) return 20659

    return -1
}

//Heroes' Guild Doors
listOf(Objs.DOOR_2624, Objs.DOOR_2625).forEach {
    on_obj_option(obj = it, option = "open") {
        val obj = player.getInteractingGameObj()
        if (player.tile.z <= obj.tile.z && !player.completedAllQuests()) {
            player.queue {
                messageBox("You complete all quests before you can access the Heroes' Guild.")
            }
            return@on_obj_option
        }
        player.queue {
            when (obj.tile.x) {
                2902 -> handleGuildDoors(player, 2902, 2901, 2624, 2625)
            }
        }
    }
}

fun handleGuildDoors(player: Player, originalDoorX: Int, blockedDoorX: Int, doorIDSouth: Int, doorIDNorth: Int) {
    val southOriginalDoor = DynamicObject(id = doorIDSouth, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3510))
    val northOriginalDoor = DynamicObject(id = doorIDNorth, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3511))

    val southDoorBlocked = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3510))
    val northDoorBlocked = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3511))

    val southDoor = DynamicObject(id = doorIDSouth, type = 0, rot = 3, tile = Tile(x = blockedDoorX, z = 3510))
    val northDoor = DynamicObject(id = doorIDNorth, type = 0, rot = 1, tile = Tile(x = blockedDoorX, z = 3511))

    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        world.remove(southOriginalDoor)
        world.remove(northOriginalDoor)
        player.playSound(Sfx.DOOR_OPEN)
        world.spawn(southDoorBlocked)
        world.spawn(northDoorBlocked)
        world.spawn(southDoor)
        world.spawn(northDoor)
        val x = if (player.tile.x in originalDoorX..originalDoorX+3) blockedDoorX else originalDoorX
        val z = player.tile.z
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(southDoor)
        world.remove(northDoor)
        world.spawn(southOriginalDoor)
        world.spawn(northOriginalDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.STAIRCASE_1738, option = "climb-up") {
 val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2895 -> player.handleStairs(2897, 3513, 1)
    }
}

on_obj_option(obj = Objs.STAIRCASE_1740, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2896 -> player.handleStairs(2897, 3513, 0)
    }
}