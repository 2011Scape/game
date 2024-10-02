package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.game.model.LockState
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player

on_obj_option(obj = Objs.DOOR_15653, option = "open") {
    if (getCombinedLevels(player) < 130) {
        player.message("Your attack and strength must total 130 together or higher for you to enter.")
    } else {
        handleMainDoor(player)
    }
}

fun getCombinedLevels(player: Player): Int {
    val strengthLevel = player.skills.getCurrentLevel(Skills.STRENGTH)
    val attackLevel = player.skills.getCurrentLevel(Skills.ATTACK)
    return strengthLevel + attackLevel
}

fun handleMainDoor(player: Player) {
    val closedDoor = DynamicObject(id = 15653, type = 0, rot = 0, tile = Tile(x = 2877, z = 3542))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    val door = DynamicObject(id = 15653, type = 0, rot = 1, tile = Tile(x = 2876, z = 3542))
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = if (player.tile.x == 2877) 2876 else 2877
        val z = 3542
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}
