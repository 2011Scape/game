package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

// A list of obelisk object IDs that can be activated.
val ObeliskIDs =
    listOf(
        Objs.OBELISK_14826,
        Objs.OBELISK_14827,
        Objs.OBELISK_14828,
        Objs.OBELISK_14829,
        Objs.OBELISK_14830,
        Objs.OBELISK_14831,
    )

// Set up object interaction listeners for each obelisk.
ObeliskIDs.forEach { obeliskID ->
    // Check if the obelisk has the 'activate' option.
    if (if_obj_has_option(obeliskID, option = "activate")) {
        // Define the behavior when a player activates an obelisk.
        on_obj_option(obeliskID, option = "activate") {
            val obj = player.getInteractingGameObj()
            activateObelisk(player, obj)
        }
    }
}

// Randomly selects a new obelisk to teleport to, excluding the current one.
fun randomObelisk(exclude: Obelisk): Obelisk {
    val eligibleObelisks = Obelisk.values().filter { it != exclude }
    return eligibleObelisks[world.random(eligibleObelisks.indices)]
}

// Activates an obelisk and teleports the players to another random obelisk.
fun activateObelisk(
    player: Player,
    obj: GameObject,
) {
    // Prevent teleportation if the player is currently in combat.
    if (player.isBeingAttacked()) {
        player.message("You can't use this while in combat.")
        return
    }

    // The rest of this function handles the teleportation logic.
    val obeliskTile = obj.tile
    val stationObelisk = Obelisk.forLocation(obeliskTile) ?: return
    val world: World = player.world

    // Define the corners relative to the obelisk location for animation purposes.
    val corners = arrayOf(Tile(2, 2, 0), Tile(-2, 2, 0), Tile(-2, -2, 0), Tile(2, -2, 0))

    // Animate the obelisk by spawning temporary animated objects on the obelisk platform corners.
    corners.forEach { corner ->
        val newTile = stationObelisk.location.transform(corner.x, corner.z, corner.height)
        val newObj = DynamicObject(Objs.OBELISK_14825, obj.type, obj.rot, newTile)
        val oldObj = DynamicObject(obj.id, obj.type, obj.rot, newTile)
        world.spawnTemporaryObject(newObj, 9, oldObj)
    }

    // Handle the actual teleportation after a short delay.
    world.queue {
        val newObelisk = randomObelisk(stationObelisk)
        val destination = newObelisk.location
        val filteredPlayers = mutableListOf<Player>()
        val tile = stationObelisk.location

        wait(10)

        // Collect all players within a 1-tile radius of the obelisk.
        for (p in world.players.entries) {
            if (p != null && p.tile.isWithinRadius(tile, 1)) {
                filteredPlayers.add(p)
            }
        }

        // Abort if no players are near the obelisk.
        if (filteredPlayers.isEmpty()) {
            return@queue
        }

        // Teleport each nearby player.
        for (filteredPlayer in filteredPlayers) {
            // Calculate the offset based on player's current position and the obelisk's center tile
            val offsetX = filteredPlayer.tile.x - tile.x
            val offsetZ = filteredPlayer.tile.z - tile.z

            // Apply the offset to the destination
            val destinationWithOffset = destination.transform(offsetX, offsetZ, 0)

            // Teleport the player to the new destination with the offset
            filteredPlayer.teleport(destinationWithOffset, TeleportType.OBELISK)

            // Play sound and send message to the teleported player.
            filteredPlayer.playSound(Sfx.WILDERNESS_TELEPORT)
            filteredPlayer.message("Ancient magic teleports you somewhere in the wilderness.")
        }

        // Creates 3x3 graphic effect on the obelisk platform to signify multiple player teleportation function.
        for (x in -1..1) {
            for (z in -1..1) {
                val tileGraphicLocation = stationObelisk.location.transform(x, z, 0)
                world.spawn(TileGraphic(tile = tileGraphicLocation, id = 342, height = 0))
            }
        }
    }
}

/**
 * Represents an obelisk type.
 */
enum class Obelisk(
    val location: Tile,
) {
    LEVEL_13(Tile(3156, 3620, 0)),
    LEVEL_19(Tile(3219, 3656, 0)),
    LEVEL_27(Tile(3035, 3732, 0)),
    LEVEL_35(Tile(3106, 3794, 0)),
    LEVEL_44(Tile(2980, 3866, 0)),
    LEVEL_50(Tile(3307, 3916, 0)),
    ;

    companion object {
        fun forLocation(location: Tile): Obelisk? {
            return values().firstOrNull { it.location.getDistance(location) <= 20 }
        }
    }
}
