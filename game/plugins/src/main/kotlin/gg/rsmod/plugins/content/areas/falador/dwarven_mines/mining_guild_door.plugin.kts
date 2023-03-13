package gg.rsmod.plugins.content.areas.falador.dwarven_mines

val OPEN_DOOR_SFX = 62
val CLOSE_DOOR_SFX = 60
val MINING_LEVEL_REQ = 60
on_obj_option(obj = Objs.DOOR_2112, option = "open") {
    if(player.tile.z > 9756 && player.getSkills().getMaxLevel(Skills.MINING) < MINING_LEVEL_REQ) {
        player.queue {
            chatNpc("Sorry, but you're not experienced enough to go in there.", npc = Npcs.DWARF_382)
            messageBox("You need a Mining level of 60 to access the Mining Guild.")
        }
        return@on_obj_option
    }
    handleDoor(player)
}

fun handleDoor(player: Player) {
    val closedDoor = DynamicObject(id = 2112, type = 0, rot = 1, tile = Tile(x = 3046, z = 9756))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    val door = DynamicObject(id = 2112, type = 0, rot = 2, tile = Tile(x = 3046, z = 9757))
    player.playSound(id = OPEN_DOOR_SFX)
    world.spawn(door)

    player.queue {
        val x = 3046
        val z = if (player.tile.z == 9756) 9757 else 9756
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(CLOSE_DOOR_SFX)
    }
}