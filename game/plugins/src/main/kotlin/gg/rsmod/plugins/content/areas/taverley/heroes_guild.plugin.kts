package gg.rsmod.plugins.content.areas.taverley

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

// Define an array of jewelry IDs that can be recharged
val GloryOrROW =
    intArrayOf(1704, 1706, 1708, 1710, 1712, 10354, 10356, 10358, 10360, 10362, 2572, 20653, 20655, 20657, 20659)

// Iterate over each jewelry ID
for (jewelryId in GloryOrROW) {
    // Define an action when an item from the array is used on the specified object
    on_item_on_obj(Objs.FOUNTAIN_OF_HEROES, jewelryId) {

        player.queue(TaskPriority.STRONG) {
            val inventory = player.inventory
            val def = player.world.definitions
            var itemNameUsed: String? = null

            // Process each item in the player's inventory
            inventory.rawItems
                .filterNotNull()
                .filter { it.id in GloryOrROW && !it.getName(def).lowercase().contains("(4)") }
                .forEach { nonNullItem ->
                    val fullyChargedItemId = getChargedId(nonNullItem.id)
                    if (fullyChargedItemId != -1 && inventory.remove(item = nonNullItem).hasSucceeded()) {
                        itemNameUsed =
                            itemNameUsed ?: nonNullItem.getName(def).split("\\s+".toRegex()).firstOrNull() ?: ""
                        inventory.add(fullyChargedItemId)
                        player.animate(827)
                    }
                }

            itemNameUsed?.let {
                player.message("You dip the ${it.lowercase()} into the fountain restoring all charges.")
                wait(3)
                itemMessageBox(
                    message =
                        "You feel a power emanating from the fountain as it recharges your ${it.lowercase()}." +
                            "You can now rub the ${it.lowercase()} to teleport.",
                    item = jewelryId,
                )
            } ?: player.message("No suitable item found to recharge.")
        }
    }
}

// Function to get the fully charged item ID
fun getChargedId(itemId: Int): Int {
    // Return the corresponding fully charged item ID
    return when (itemId) {
        in intArrayOf(1704, 1706, 1708, 1710) -> 1712
        in intArrayOf(10356, 10358, 10360, 10362) -> 10354
        in intArrayOf(2572, 20653, 20655, 20657) -> 20659
        else -> -1
    }
}

// Heroes' Guild Doors
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

fun handleGuildDoors(
    player: Player,
    originalDoorX: Int,
    blockedDoorX: Int,
    doorIDSouth: Int,
    doorIDNorth: Int,
) {
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
        val x = if (player.tile.x in originalDoorX..originalDoorX + 3) blockedDoorX else originalDoorX
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
