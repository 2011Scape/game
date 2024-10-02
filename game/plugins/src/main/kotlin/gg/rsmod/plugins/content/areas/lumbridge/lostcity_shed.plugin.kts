package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.prepareForTeleport
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val inShed =
    listOf(
        Tile(3202, 3168),
        Tile(3202, 3169),
        Tile(3203, 3168),
        Tile(3203, 3169),
        Tile(3204, 3168),
    )

val zanarisTile = Tile(2452, 4471)
val shedTile = Tile(3202, 3169)

on_obj_option(Objs.DOOR_2406, "open") {
    val obj = player.getInteractingGameObj()
    val currentStage = player.getCurrentStage(LostCity)

    // Specify the minimum quest stage and the item ID for the Dramen Staff
    val minQuestStage = LostCity.CREATE_DRAMEN_BRANCH // Set your minimum quest stage here
    val dramenStaffId = Items.DRAMEN_STAFF

    if (player.hasEquipped(intArrayOf(dramenStaffId)) && currentStage >= 5) {
        player.queue {
            handleDoor(player, obj)
            if (player.tile.x == 3201) {
                player.walkTo(shedTile)
            }
            wait(2)
            zanarisTeleport(player)
        }
    } else {
        player.queue {
            handleDoor(player, obj)
        }
    }
}

fun zanarisTeleport(player: Player) {
    val type = TeleportType.FAIRY
    if (player.tile in inShed) {
        player.lockingQueue {
            player.message("The world starts to shimmer...")
            player.playSound(Sfx.FT_FAIRY_TELEPORT)
            player.prepareForTeleport()
            player.animate(type.animation)
            type.graphic?.let {
                player.graphic(it)
            }
            wait(type.teleportDelay)
            player.teleportTo(zanarisTile)
            type.endAnimation?.let {
                player.animate(it)
            }
            type.endGraphic?.let {
                player.graphic(it)
            }
            type.endAnimation?.let {
                val def = world.definitions.get(AnimDef::class.java, it)
                wait(def.cycleLength)
            }
            player.animate(-1)
            player.unlock()
            wait(2)
            if (player.getCurrentStage(LostCity) == LostCity.CREATE_DRAMEN_BRANCH) {
                LostCity.finishQuest(player)
            }
        }
    }
}

suspend fun handleDoor(
    player: Player,
    obj: GameObject,
) {
    val openDoor = DynamicObject(id = 2406, type = 0, rot = 1, tile = Tile(x = 3202, z = 3169))
    val door = DynamicObject(id = 2406, type = 0, rot = 2, tile = Tile(x = 3201, z = 3169))
    player.lockingQueue {
        if (player.tile in inShed) {
            player.moveTo(Tile(3202, 3169, 0))
        } else {
            player.moveTo(Tile(3201, 3169, 0))
        }
        val x = if (player.tile.x == 3201) 3202 else 3201
        val z = 3169
        world.remove(obj)
        world.spawn(openDoor)
        player.playSound(Sfx.DOOR_OPEN)
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(openDoor)
        player.playSound(Sfx.DOOR_CLOSE)
        world.spawn(door)
    }
}
