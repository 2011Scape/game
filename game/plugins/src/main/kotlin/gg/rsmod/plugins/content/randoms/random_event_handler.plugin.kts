import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.attr.DRILL_DEMON_ACTIVE
import gg.rsmod.game.model.attr.NO_CLIP_ATTR
import gg.rsmod.game.model.timer.TimerKey
import kotlin.math.sqrt

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

// Define the timer key for the Drill Demon event
val DRILL_DEMON_TIMER = TimerKey(persistenceKey = "drill_demon", tickOffline = false, resetOnDeath = false, tickForward = false, removeOnZero = true)

// Define a reference to the Sergeant Damien NPC
val SERGEANT_DAMEIN = Npcs.SERGEANT_DAMIEN

// Define the timer key for force chat actions
val FORCE_CHAT_TIMER = TimerKey()

// Define a range for the delay (in ticks) before displaying yellow overhead NPC text
val DELAY = 10..20

// Define the timer key for following the target player
val FOLLOW_TARGET_TIMER = TimerKey()

// Define a range for the delay (in ticks) before Sergeant Damien starts following the target player
val FOLLOW_TARGET_DELAY = 1..3


val spawnTimer = (2252..8108) //25 minutes to 90 minutes converted to game ticks.

on_login {
    if (!player.timers.has(DRILL_DEMON_TIMER)) {
        player.timers[DRILL_DEMON_TIMER] = world.random(spawnTimer)
    }
}

on_global_npc_spawn {
    if (npc.id == SERGEANT_DAMEIN) {
        npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
    }
}

on_timer(FORCE_CHAT_TIMER) {
    if (world.random(1) == 0) {
        npc.forceChat("Stop day-dreaming, Private!")
        npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
    }
}

// Set up a timer event for the Drill Demon event
on_timer(DRILL_DEMON_TIMER) {
    if (player.isLocked() || player.isDead() || player.attr[DRILL_DEMON_ACTIVE] == true || player.interfaces.currentModal != -1) {
        player.timers[DRILL_DEMON_TIMER] = 10
        return@on_timer
    }
    // Create and spawn the NPC Sergeant Damien one step north of the player
    val npc_sergeant_damien = Npc(player, SERGEANT_DAMEIN, player.tile.step(direction = Direction.NORTH), world)

    //Spawn the drill sergeant
    world.spawn(npc_sergeant_damien)

    // Mark the event as active for the player
    player.attr[DRILL_DEMON_ACTIVE] = true

    // Apply a graphic effect to Sergeant Damien
    npc_sergeant_damien.graphic(86)

    // Set the follow radius for Sergeant Damien
    npc_sergeant_damien.followRadius = 5

    // Make Sergeant Damien face the player
    npc_sergeant_damien.facePawn(player)

    // Make Sergeant Damien follow the target player
    followTargetPlayer(npc_sergeant_damien, player)

    // Set a random delay before Sergeant Damien starts following the player
    npc_sergeant_damien.timers[FOLLOW_TARGET_TIMER] = world.random(FOLLOW_TARGET_DELAY)

    // Set a random delay for the next event occurrence
    player.timers[DRILL_DEMON_TIMER] = world.random(spawnTimer)
}

on_timer(FOLLOW_TARGET_TIMER) {
    if (npc.id == SERGEANT_DAMEIN) {
        val targetPlayer = getNearestPlayer(npc)
        if (targetPlayer != null && targetPlayer.attr.has(DRILL_DEMON_ACTIVE)) {
            followTargetPlayer(npc, targetPlayer)
        }
        npc.timers[FOLLOW_TARGET_TIMER] = world.random(FOLLOW_TARGET_DELAY)
    }
}

fun followTargetPlayer(npc: Npc, targetPlayer: Player) {
    val noClip = npc.attr[NO_CLIP_ATTR] ?: false
    if (world.collision.chunks.get(targetPlayer.tile, createIfNeeded = false) != null) {
        npc.walkMask = npc.def.walkMask
        npc.facePawn(targetPlayer)
        npc.walkTo(targetPlayer.tile, detectCollision = !noClip)
    }
}

fun getNearestPlayer(npc: Npc): Player? {
    // Get the follow radius for the NPC
    val radius = npc.followRadius
// Initialize variables for tracking the nearest player and minimum distance
    var nearestPlayer: Player? = null
    var minDistance = Int.MAX_VALUE

// Iterate through all tiles within the follow radius
    for (x in -radius..radius) {
        for (z in -radius..radius) {
            // Calculate the tile position based on the current offsets (x, z) from the NPC's tile
            val tile = npc.tile.transform(x, z)

            // Get the chunk corresponding to the current tile
            val chunk = world.chunks.get(tile, createIfNeeded = false) ?: continue

            // Get all players in the current chunk and filter them by their position on the tile
            val players = chunk.getEntities<Player>(tile, EntityType.PLAYER, EntityType.CLIENT)

            // If no players are present on the current tile, move on to the next tile
            if (players.isEmpty()) {
                continue
            }

            // Iterate through all players on the tile and calculate their distance to the NPC
            players.forEach { player ->
                val distanceX = npc.tile.x - player.tile.x
                val distanceZ = npc.tile.z - player.tile.z
                // Calculate the Euclidean distance between the NPC and the player
                val distance = sqrt((distanceX * distanceX + distanceZ * distanceZ).toDouble()).toInt()

                // Update the nearest player and minimum distance if the current player is closer
                if (distance < minDistance) {
                    nearestPlayer = player
                    minDistance = distance
                }
            }
        }
    }
// Return the nearest player, or null if no players are found within the follow radius
    return nearestPlayer
}