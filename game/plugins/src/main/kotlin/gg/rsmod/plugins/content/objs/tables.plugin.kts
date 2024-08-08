package gg.rsmod.plugins.content.objs

import gg.rsmod.game.message.impl.SynthSoundMessage
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

val tableIds: Set<Int> by lazy {
    Objs::class
        .declaredMemberProperties
        .filter { it.name.contains("TABLE", ignoreCase = true) || it.name.contains("COUNTER", ignoreCase = true) }
        .mapNotNull { property ->
            property.isAccessible = true
            property.getter.call() as? Int
        }.toSet()
}

tableIds.forEach { table ->
    on_any_item_on_obj(obj = table) {
        val obj = player.getInteractingGameObj()
        val item = player.getInteractingItem()
        val itemDef = item.getDef(world.definitions)
        val objDef = obj.getDef(world.definitions)
        val nearestTile = findNearestTile(pawn.tile, obj.tile, objDef.width, objDef.length, obj.rot)
        val groundItem = GroundItem(item = item.id, amount = 1, tile = nearestTile)

        if (obj.id == Objs.TABLE_8700) {
            player.message("The table appears to be in use.")
            return@on_any_item_on_obj
        }
        // Check if the player has the item
        if (player.inventory.contains(item.id)) {
            player.queue(TaskPriority.STRONG) {
                wait(2)
                player.faceTile(nearestTile)
                player.animate(535)
                player.inventory.remove(item.id, 1)
                player.write(SynthSoundMessage(2582, 1, 0))
                world.spawn(groundItem) // TODO: Items placed on tables should despawn after 10 minutes.
                player.message("You place the ${itemDef.name} on the table.")
            }
        } else {
            // Send a message to the player if they don't have the item
            player.message("You don't have that item.")
        }
    }
}

fun findNearestTile(
    playerTile: Tile,
    objectTile: Tile,
    width: Int,
    length: Int,
    rotation: Int,
): Tile {
    val adjustedWidth = if (rotation == 1 || rotation == 3) length else width
    val adjustedLength = if (rotation == 1 || rotation == 3) width else length

    val objectArea =
        (objectTile.x until objectTile.x + adjustedWidth).flatMap { x ->
            (objectTile.z until objectTile.z + adjustedLength).map { z ->
                Tile(x, z, objectTile.height)
            }
        }

    // Find the nearest tile within the object's area that is not the player's current tile
    return objectArea
        .filter { it != playerTile }
        .minByOrNull { it.getDistance(playerTile) } ?: playerTile
}
