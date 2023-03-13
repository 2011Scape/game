package gg.rsmod.plugins.content.areas.edgeville

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.DOOR_1804, option = "open") {
    if(!player.inventory.contains(Items.BRASS_KEY)) {
        player.message("The door is locked.")
        return@on_obj_option
    }
    handleDoor(player)
}

on_obj_option(obj = Objs.LADDER_12389, option = "Climb-Down") {
    player.handleBasicLadder(player, climbUp = false)
}

on_obj_option(obj = Objs.LADDER_29358, option = "Climb-Down") {
    player.handleBasicLadder(player, climbUp = false)
}


fun handleDoor(player: Player) {
    val obj = DynamicObject(id = 1804, type = 0, rot = 1, tile = Tile(x = 3115, z = 3449))
    val moveX = obj.tile.x
    val moveZ = if (player.tile.z == 3450) 3449 else 3450
    val changedDoorId = -1
    val doorX = obj.tile.x
    val doorZ = obj.tile.z + 1
    val rotation = 2
    val wait = 3
    player.handleTemporaryDoor(obj, moveX, moveZ, changedDoorId, doorX, doorZ, rotation, wait)
}