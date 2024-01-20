package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.buildQuestFinish
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val inShed = listOf(
        Tile(3202, 3168),
        Tile(3202, 3169),
        Tile(3203, 3168),
        Tile(3203, 3169),
        Tile(3204, 3168)

)
val zanarisTile = Tile(2452, 4471)
val teleportTile = Tile(3202, 3169)

on_obj_option(Objs.DOOR_2406, "open"){
    if (player.hasEquipped(intArrayOf(Items.DRAMEN_STAFF)) && player.getCurrentStage(LostCity) >= LostCity.CREATE_DRAMEN_STAFF) {
        player.queue{
            handleDoor(player)
            player.lock()
            player.walkTo(teleportTile)
            player.unlock()
            wait(2)
            zanarisTeleport(player)
        }
    }
    else {
        handleDoor(player)
    }
}

fun zanarisTeleport (player: Player) {
    if (player.tile in inShed) {
        player.lock()
        player.message("The world starts to shimmer...")
        player.teleport(zanarisTile, type = TeleportType.SCROLL)
        player.unlock()
        if (player.getCurrentStage(LostCity) == LostCity.ENTER_ZANARIS) {
            LostCity.finishQuest(player)
        }
    }
}

fun handleDoor(player: Player) { //TODO: Fix door
    val openDoor = DynamicObject(id = 2406, type = 0, rot = 1, tile = Tile(x = 3202, z = 3169))
    val door = DynamicObject(id = 2406, type = 0, rot = 2, tile = Tile(x = 3201, z = 3169))
    player.queue {
        val x = if (player.tile.x == 3201) 3202 else 3201
        val z = 3169
        world.remove(door)
        world.remove(openDoor)
        world.spawn(openDoor)
        player.playSound(Sfx.DOOR_OPEN)
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(openDoor)
        world.remove(door)
        player.playSound(Sfx.DOOR_CLOSE)
        world.spawn(door)
    }
}