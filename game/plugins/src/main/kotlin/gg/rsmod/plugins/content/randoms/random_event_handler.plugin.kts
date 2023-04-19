import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.attr.BOTTING_SCORE
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
val FOLLOW_TARGET_DELAY = 1

// Define the timer key for checking the distance between Sergeant Damien and the player
val CHECK_DISTANCE_TIMER = TimerKey()

// Define a range for the delay (in ticks) before checking the distance between Sergeant Damien and the player
val CHECK_DISTANCE_DELAY = 5

// Define the follow radius for the random event npcs
val FOLLOW_RADIUS_THRESHOLD = 10

val REMOVAL_TIMER = TimerKey(persistenceKey = "removal_timer", tickOffline = true, resetOnDeath = false, tickForward = false, removeOnZero = true)

val REMOVAL_DELAY = (30..37) // 20 to 25 seconds converted to game ticks (1 second = 0.666 game ticks).

val TARGET_PLAYER = AttributeKey<Player>("target_player")

val spawnTimer = (2252..8108) //25 minutes to 90 minutes converted to game ticks.

on_login {
    if (!player.timers.has(DRILL_DEMON_TIMER)) {
        player.timers[DRILL_DEMON_TIMER] = world.random(spawnTimer)
    }
}

on_global_npc_spawn {
    if (npc.id == SERGEANT_DAMEIN) {
        npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
        npc.timers[CHECK_DISTANCE_TIMER] = CHECK_DISTANCE_DELAY
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

    // Set the target player attribute for Sergeant Damien
    npc_sergeant_damien.attr[TARGET_PLAYER] = player

    //Spawn the drill sergeant
    world.spawn(npc_sergeant_damien)

    // Set the removal timer for Sergeant Damien
    npc_sergeant_damien.timers[REMOVAL_TIMER] = world.random(REMOVAL_DELAY)

    // Mark the event as active for the player
    player.attr[DRILL_DEMON_ACTIVE] = true

    // Apply a graphic effect to Sergeant Damien
    npc_sergeant_damien.graphic(86)

    // Set the follow radius for Sergeant Damien
    npc_sergeant_damien.followRadius = FOLLOW_RADIUS_THRESHOLD

    // Make Sergeant Damien face the player
    npc_sergeant_damien.facePawn(player)

    // Make Sergeant Damien follow the target player
    followTargetPlayer(npc_sergeant_damien, player)

    // Set a random delay before Sergeant Damien starts following the player
    npc_sergeant_damien.timers[FOLLOW_TARGET_TIMER] = FOLLOW_TARGET_DELAY

    // Set a random delay for the next event occurrence
    player.timers[DRILL_DEMON_TIMER] = world.random(spawnTimer)
}

on_timer(REMOVAL_TIMER) {
    if (npc.id == SERGEANT_DAMEIN) {
        val targetPlayer = getTargetPlayer(npc)
        if (targetPlayer != null && targetPlayer.attr.has(DRILL_DEMON_ACTIVE)) {
            targetPlayer.attr[DRILL_DEMON_ACTIVE] = false
            targetPlayer.timers[DRILL_DEMON_TIMER] = world.random(spawnTimer)
            targetPlayer.attr[BOTTING_SCORE] = (targetPlayer.attr[BOTTING_SCORE] ?: 0) + 1
            targetPlayer.message("You were not paying attention and missed the Drill Demon's appearance.")
        }
        world.remove(npc)
    }
}

on_timer(CHECK_DISTANCE_TIMER) {
    if (npc.id == SERGEANT_DAMEIN) {
        val targetPlayer = getTargetPlayer(npc)
        if (targetPlayer != null && targetPlayer.attr.has(DRILL_DEMON_ACTIVE)) {
            val distanceX = npc.tile.x - targetPlayer.tile.x
            val distanceZ = npc.tile.z - targetPlayer.tile.z
            val distance = sqrt((distanceX * distanceX + distanceZ * distanceZ).toDouble()).toInt()
            if (distance > FOLLOW_RADIUS_THRESHOLD) {
                npc.graphic(86)
                world.remove(npc)
                targetPlayer.attr[DRILL_DEMON_ACTIVE] = false
                targetPlayer.timers[DRILL_DEMON_TIMER] = 5
            }
        }
        npc.timers[CHECK_DISTANCE_TIMER] = CHECK_DISTANCE_DELAY
    }
}


on_timer(FOLLOW_TARGET_TIMER) {
    if (npc.id == SERGEANT_DAMEIN) {
        val targetPlayer = getTargetPlayer(npc)
        if (targetPlayer != null && targetPlayer.attr.has(DRILL_DEMON_ACTIVE)) {
            followTargetPlayer(npc, targetPlayer)
        }
        npc.timers[FOLLOW_TARGET_TIMER] = FOLLOW_TARGET_DELAY
    }
}

fun followTargetPlayer(npc: Npc, targetPlayer: Player) {
    val noClip = npc.attr[NO_CLIP_ATTR] ?: false
    if (world.collision.chunks.get(targetPlayer.tile, createIfNeeded = false) != null) {
        npc.walkMask = npc.def.walkMask
        npc.facePawn(targetPlayer)
        npc.walkTo(targetPlayer.tile, detectCollision = !noClip)
        //Handle NPC trying to get players attention
        if (world.random(1) == 0) {
            npc.forceChat("Stop day-dreaming, Private ${targetPlayer.username}!")
            npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
        }
    }
}

fun getTargetPlayer(npc: Npc): Player? {
    return npc.attr[TARGET_PLAYER]
}