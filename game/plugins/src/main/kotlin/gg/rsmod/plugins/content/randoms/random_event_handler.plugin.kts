import gg.rsmod.game.model.attr.ANTI_CHEAT_EVENT_ACTIVE
import gg.rsmod.game.model.attr.BOTTING_SCORE
import gg.rsmod.game.model.attr.LAST_KNOWN_POSITION
import gg.rsmod.game.model.timer.ANTI_CHEAT_TIMER
import gg.rsmod.game.model.timer.LOGOUT_TIMER
import gg.rsmod.plugins.content.combat.isAttacking
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.combat.isPoisoned
import kotlin.random.Random

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

val spawnTimer = 16200 // 3 hrs in game ticks.

on_login {
    if (!player.timers.has(ANTI_CHEAT_TIMER)) {
        player.timers[ANTI_CHEAT_TIMER] = spawnTimer
    }
}

// Set up a timer event for the Drill Demon event
on_timer(ANTI_CHEAT_TIMER) {

    if (player.isAttacking() ||
        player.isBeingAttacked() ||
        player.isLocked() ||
        player.isDead() ||
        player.attr[ANTI_CHEAT_EVENT_ACTIVE] == true ||
        player.isPoisoned() ||
        player.interfaces.currentModal != -1
    ) {
        player.timers[ANTI_CHEAT_TIMER] = 10
        return@on_timer
    }

    // TODO: Handle a random, random event when we've added more.
    // Note: For now, as Drill Demon is the only one we have, we'll continue on...

    // Create and spawn the NPC Sergeant Damien one step north of the player
    val drillDemon = Npc(Npcs.SERGEANT_DAMIEN, player.findWesternTile(), world)

    // Spawn the drill sergeant
    world.spawn(drillDemon)

    // Mark the event as active for the player
    player.attr[ANTI_CHEAT_EVENT_ACTIVE] = true

    // Apply a graphic effect to Sergeant Damien
    drillDemon.graphic(86)

    // Make Sergeant Damien face the player
    drillDemon.facePawn(player)
    // TODO: Find the actual dialogue here?
    drillDemon.forceChat("Do you think you can be the best?")

    player.interruptQueues()
    player.stopMovement()
    player.animate(-1)
    player.lockingQueue {
        val lastKnownPosition: Tile = player.tile
        val teleportToDrillDemon = Tile(3163, 4821)
        player.attr[LAST_KNOWN_POSITION] = lastKnownPosition
        wait(3)
        player.moveTo(teleportToDrillDemon)
        wait(3)
        player.graphic(86)
    }

    drillDemon.queue {
        wait(8)
        world.spawn(TileGraphic(drillDemon.tile, height = 0, id = 86))
        world.remove(drillDemon)
    }

    // Set a random delay for the next event occurrence
    player.timers[ANTI_CHEAT_TIMER] = spawnTimer

    // Add a logout timer
    player.timers[LOGOUT_TIMER] = 500
}

on_logout {
    if (player.tile.regionId == 12619 || player.attr[ANTI_CHEAT_EVENT_ACTIVE] == true) {
        val lastKnownPosition: Tile? = player.attr[LAST_KNOWN_POSITION]
        player.timers.remove(LOGOUT_TIMER)
        if (lastKnownPosition != null) {
            player.moveTo(lastKnownPosition)
        } else {
            player.moveTo(3222, 3222, 0)
        }
        player.attr[ANTI_CHEAT_EVENT_ACTIVE] = false
        player.timers[ANTI_CHEAT_TIMER] = Random.nextInt(3000, 10000)
        player.attr[BOTTING_SCORE] = (player.attr[BOTTING_SCORE] ?: 0) + 1
    }
}

on_timer(LOGOUT_TIMER) {
    player.handleLogout()
}
