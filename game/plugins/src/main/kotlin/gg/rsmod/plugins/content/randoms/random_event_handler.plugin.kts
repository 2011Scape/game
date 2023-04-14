import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.attr.DRILL_DEMON_ACTIVE
import gg.rsmod.game.model.attr.NO_CLIP_ATTR
import gg.rsmod.game.model.timer.TimerKey
import kotlin.math.sqrt

val DRILL_DEMON_TIMER = TimerKey(persistenceKey = "drill_demon", tickOffline = false, resetOnDeath = false, tickForward = false, removeOnZero = true)
val SERGEANT_DAMEIN = Npcs.SERGEANT_DAMIEN
val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 10..20
val FOLLOW_TARGET_TIMER = TimerKey()
val FOLLOW_TARGET_DELAY = 1..3


val spawnRange = (1500..5400) //25 minutes to 90 minutes converted to seconds.

on_login {
    if (!player.timers.has(DRILL_DEMON_TIMER)) {
        player.timers[DRILL_DEMON_TIMER] = world.random(spawnRange)
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

on_timer(DRILL_DEMON_TIMER) {
    if (player.isLocked() || player.isDead() || player.attr[DRILL_DEMON_ACTIVE] == true || player.interfaces.currentModal != -1) {
        player.timers[DRILL_DEMON_TIMER] = 10
        return@on_timer
    }
    val npc_sergeant_damien = Npc(player, SERGEANT_DAMEIN, player.tile.step(direction = Direction.NORTH), world)
    world.spawn(npc_sergeant_damien)
    player.attr[DRILL_DEMON_ACTIVE] = true
    npc_sergeant_damien.graphic(86)
    npc_sergeant_damien.followRadius = 5 // Set the follow radius for Sergeant Damien
    npc_sergeant_damien.facePawn(player)
    followTargetPlayer(npc_sergeant_damien, player)
    npc_sergeant_damien.timers[FOLLOW_TARGET_TIMER] = world.random(FOLLOW_TARGET_DELAY)
    player.timers[DRILL_DEMON_TIMER] = world.random(spawnRange)
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
    val radius = npc.followRadius
    var nearestPlayer: Player? = null
    var minDistance = Int.MAX_VALUE

    for (x in -radius..radius) {
        for (z in -radius..radius) {
            val tile = npc.tile.transform(x, z)
            val chunk = world.chunks.get(tile, createIfNeeded = false) ?: continue

            val players = chunk.getEntities<Player>(tile, EntityType.PLAYER, EntityType.CLIENT)
            if (players.isEmpty()) {
                continue
            }

            players.forEach { player ->
                val distanceX = npc.tile.x - player.tile.x
                val distanceZ = npc.tile.z - player.tile.z
                val distance = sqrt((distanceX * distanceX + distanceZ * distanceZ).toDouble()).toInt()

                if (distance < minDistance) {
                    nearestPlayer = player
                    minDistance = distance
                }
            }
        }
    }
    return nearestPlayer
}