package gg.rsmod.plugins.content.areas.draynor

spawn_npc(npc = Npcs.CHICKEN, x = 3110, z = 9573, walkRadius = 5, direction = Direction.SOUTH)
spawn_npc(npc = Npcs.WIZARD, x = 3108, z = 9558, walkRadius = 5, direction = Direction.NORTH_WEST)
spawn_npc(npc = Npcs.SEDRIDOR, x = 3105, z = 9569, walkRadius = 5, direction = Direction.NORTH_WEST)

on_obj_option(obj = Objs.LADDER_32015, option = "climb-up") {
    val obj = player.getInteractingGameObj()
    if (player.tile.x == 2884 && player.tile.z == 9796) { //adds a check for taverley dungeon, as it shares the same ladder ID with the ladder there.
        player.handleLadder(climbUp = true, endTile = Tile(2884, 3398, 0))
    } else if (obj.tile == Tile(3008, 9550)) { //Asgarnia Ice dungeon
        player.handleLadder(climbUp = true, endTile = Tile(3009, 3150))
    } else {
        player.handleLadder(climbUp = true, endTile = Tile(3104, 3161, 0))
    }
}

on_obj_option(obj = Objs.LADDER_2147, option = "climb-down") {
    player.handleLadder(climbUp = false, endTile = Tile(3104, 9576, 0))
}