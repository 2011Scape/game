package gg.rsmod.plugins.content.areas.yanille
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

// Doors
listOf(Objs.MAGIC_GUILD_DOOR, Objs.MAGIC_GUILD_DOOR_1601).forEach {
    on_obj_option(obj = it, option = "open") {
        val obj = player.getInteractingGameObj()
        if (player.tile.z <= obj.tile.z && player.skills.getMaxLevel(Skills.MAGIC) < 66) {
            player.queue {
                messageBox("You need a Magic level of 66 to access the Magic Guild.")
            }
            return@on_obj_option
        }
        player.queue {
            when (obj.tile.x) {
                2597 -> handleMagicGuildDoors(player, 2597, 2596, 1600, 1601)
                2584, 2585 -> handleMagicGuildDoors(player, 2585, 2584, 1600, 1601)
            }
        }
    }
}

fun handleMagicGuildDoors(
    player: Player,
    originalDoorX: Int,
    blockedDoorX: Int,
    doorIDSouth: Int,
    doorIDNorth: Int,
) {
    val southOriginalDoor = DynamicObject(id = doorIDSouth, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3088))
    val northOriginalDoor = DynamicObject(id = doorIDNorth, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3087))

    val southDoorBlocked = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3088))
    val northDoorBlocked = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = originalDoorX, z = 3087))

    val southDoor = DynamicObject(id = doorIDSouth, type = 0, rot = 1, tile = Tile(x = blockedDoorX, z = 3088))
    val northDoor = DynamicObject(id = doorIDNorth, type = 0, rot = 3, tile = Tile(x = blockedDoorX, z = 3087))

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

on_obj_option(obj = Objs.GATE_2154, option = "Open") {
    player.message("The gate is locked")
}
on_obj_option(obj = Objs.GATE_2155, option = "Open") {
    player.message("The gate is locked")
}
// Staircases
on_obj_option(obj = Objs.STAIRCASE_1722, option = "climb-Up") {
    when (player.tile.height) {
        1 -> {
            player.handleStairs(x = 2591, z = 3087, 2)
        }
        else -> player.handleStairs(x = 2591, z = 3092, 1)
    }
}
on_obj_option(obj = Objs.STAIRCASE_1723, option = "climb-down") {
    when (player.tile.height) {
        1 -> {
            player.handleStairs(x = 2591, z = 3088, 0)
        }
        else -> player.handleStairs(x = 2591, z = 3083, 1)
    }
}
// Portals
// East Portal
on_obj_option(obj = Objs.MAGIC_PORTAL, option = "Enter") {
    player.moveTo(3109, 3163, 0)
    player.message("You have been teleported...")
}
// South Portal
on_obj_option(obj = Objs.MAGIC_PORTAL_2157, option = "Enter") {
    player.moveTo(2908, 3336, 0)
    player.message("You have been teleported...")
}
// South Portal
on_obj_option(obj = Objs.MAGIC_PORTAL_2158, option = "Enter") {
    player.moveTo(2702, 3405, 0)
    player.message("You have been teleported...")
}
