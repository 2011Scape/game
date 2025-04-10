package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.game.message.impl.LocAnimMessage
import gg.rsmod.game.model.attr.killedCountDraynor

// Count draynor boss music
val COUNTING_ON_YOU: Int = 717

// Coffin
on_obj_option(obj = Objs.COFFIN_162, option = "Open") {
    player.queue {
        this.chatPlayer("Count Draynor isn't here. He'll probably be back soon...")
    }
}

on_obj_option(obj = Objs.COFFIN_158, option = "Open") {
    if (player.inventory.contains(Items.STAKE) && player.inventory.contains(Items.STAKE_HAMMER)) {
        val COFFIN = player.getInteractingGameObj()
        val countDrayorNPC =
            Npc(
                id = Npcs.COUNT_DRAYNOR,
                tile = Tile(COFFIN.tile.x + 1, COFFIN.tile.z + 1, COFFIN.tile.height),
                world = world,
                owner = player,
            )
        val NEW_COFFIN_OBJ =
            DynamicObject(
                id = Objs.COFFIN_162,
                type = 10,
                rot = COFFIN.rot,
                tile = Tile(x = COFFIN.tile.x, z = COFFIN.tile.z),
            )

        player.playSong(COUNTING_ON_YOU)
        world.remove(obj = COFFIN)
        world.spawnTemporaryObject(obj = NEW_COFFIN_OBJ, 300, COFFIN) // Respawn timer ~3 minutes

        player.lockingQueue(lockState = LockState.FULL) {

            player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_OVERLAY, interfaceId = 115)
            wait(3)

            player.walkTo(Tile(3079, 9786, 0))
            wait(1)

            player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_OVERLAY, interfaceId = 170)
            wait(1)

            player.faceTile(Tile(COFFIN.tile.x, COFFIN.tile.z + 1, 0)) // TODO: Add faceObject function.
            wait(1)

            player.animate(Anims.OPEN_COFFIN)
            player.write(LocAnimMessage(gameObject = COFFIN, animation = Anims.COUNT_COFFIN_OPEN))
            if (!player.attr.has(killedCountDraynor)) {
                world.spawn(countDrayorNPC)
                countDrayorNPC.respawns = false
                countDrayorNPC.animate(Anims.COUNT_ASLEEP_COFFIN)
                wait(5)

                player.inventory.remove(Items.STAKE, 1)
                player.animate(Anims.MISSING_STAKE_IN_COFFIN)
                player.message("You stab the vampyre with the stake, but it does not go deep enough.")
                countDrayorNPC.animate(Anims.COUNT_AWAKEN)
                wait(2)

                player.animate(Anims.REACH_DOWN_PULL_UP)
                wait(10)
                countDrayorNPC.teleportNpc(3081, 9777, 0)
                countDrayorNPC.animate(Anims.COUNT_SPAWN)
                wait(3)

                countDrayorNPC.resetInteractions()
                countDrayorNPC.faceTile(
                    Tile(countDrayorNPC.tile.x, countDrayorNPC.tile.z + 1, countDrayorNPC.tile.height),
                )
                player.facePawn(countDrayorNPC)
                wait(2)

                if (player.inventory.contains(Items.GARLIC)) {
                    player.message("The garlic has weakened Count Draynor...")
                    countDrayorNPC.stats.decrementCurrentLevel(Skills.DEFENCE, 5, true)
                    countDrayorNPC.stats.decrementCurrentLevel(Skills.STRENGTH, 5, true)
                }
            } else {
                wait(5)
            }
            player.unlock()
        }
    } else {
        player.queue {
            this.chatPlayer(
                "I'll need both a stake and stake hammer or hammer, better go get those...",
                wrap = true,
            )
        }
    }
}

// Stairs
on_obj_option(obj = Objs.STAIRS_47643, option = "Walk-down") {
    val obj = player.getInteractingGameObj()

    when (obj.tile.x) {
        3115 -> {
            player.handleStairs(x = 3081, z = 9776)
        } else ->
            player.message("Nothing interesting happens.")
    }
}

on_obj_option(obj = Objs.STAIRS_164, option = "Climb-up") {
    val obj = player.getInteractingGameObj()

    when (obj.tile.x) {
        3080 -> {
            player.handleStairs(x = 3116, z = 3355)
        } else ->
            player.message("Nothing interesting happens.")
    }
}

on_obj_option(Objs.LADDER_47574, "Climb-up") {
    player.handleLadder(3105, 3362, 2)
}

on_obj_option(Objs.LADDER_47575, "Climb-down") {
    player.handleLadder(3105, 3364, 1)
}

on_obj_option(Objs.STAIRCASE_47364, "Climb-up") {
    val x = if (player.tile.x <= 3108) 3108 else 3109
    player.handleStairs(x, 3366, 1)
}

on_obj_option(Objs.STAIRCASE_47657, "Climb-down") {
    val x = if (player.tile.x <= 3108) 3108 else 3109
    player.handleStairs(x, 3361, 0)
}
