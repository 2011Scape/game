package gg.rsmod.plugins.content.objs.levers

import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.combat.isBeingAttacked

val lever = listOf(Objs.LEVER_1814, Objs.LEVER_1815)

fun moveLever(objectId: Int, obj: GameObject) {
    val pulledLever = DynamicObject(objectId, obj.type, obj.rot, obj.tile)
    world.spawn(pulledLever)
}

fun pullLever(p: Player, obj: GameObject, xDestination: Int, zDestination: Int): Boolean {
    if (obj.id !in lever) {
        p.message("You can only use this function on a lever.")
        return false
    }

    p.lockingQueue {
        var ticks = 0
        while (true) {
            when (ticks) {
                1 -> {
                    p.animate(2140)
                    moveLever(36, obj)
                }

                2 -> wait(1)
                3 -> {
                    p.animate(8939)
                    p.graphic(1576)
                }

                4 -> wait(1)
                5 -> {
                    p.animate(8941)
                    p.graphic(1577)
                    p.moveTo(xDestination, zDestination)
                }

                6 -> {
                    moveLever(obj.id, obj)
                    break
                }
            }
            ticks++
            wait(1)
        }
        p.unlock()
    }
    return true
}

fun showWarningAndPullLever(player: Player, obj: GameObject, xDestination: Int, zDestination: Int) {
    player.queue {
        messageBox("Warning! Pulling the lever will teleport you deep into the Wilderness.")
        when (options("Yes. I'm brave.", "Eeep! The Wilderness... No thank you.", "Yes please, don't show this message again.")) {
            1, 3 -> pullLever(player, obj, xDestination, zDestination)
            else -> { /* do nothing */ }
        }
    }
}

on_obj_option(obj = Objs.LEVER_1814, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        if (obj.tile.x in listOf(2561, 3090)) {
            showWarningAndPullLever(player, obj, 3154, 3923)
        }
    }
}

on_obj_option(obj = Objs.LEVER_1815, option = "pull", lineOfSightDistance = 1) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        when (obj.tile.x) {
            3153 -> {
                if(player.isBeingAttacked() || player.isLocked() || player.isDead() || player.interfaces.currentModal != -1) {
                    player.message("Your teleport was interrupted!")
                } else {
                    pullLever(player, obj, 2561, 3311)
                }
            }
        }
    }
}