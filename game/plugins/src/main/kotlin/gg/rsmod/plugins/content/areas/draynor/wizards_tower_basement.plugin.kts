package gg.rsmod.plugins.content.areas.draynor

spawn_npc(npc = Npcs.CHICKEN, x = 3110, z = 9573, walkRadius = 5, direction = Direction.SOUTH)
spawn_npc(npc = Npcs.WIZARD, x = 3108, z = 9558, walkRadius = 5, direction = Direction.NORTH_WEST)
spawn_npc(npc = Npcs.SEDRIDOR, x = 3105, z = 9569, walkRadius = 5, direction = Direction.NORTH_WEST)

on_obj_option(obj = Objs.LADDER_32015, option = "climb-up") {
    when (player.tile.regionId) {
        12181 -> { // Asgarnia Ice Dungeon
            player.handleLadder(x = 3009, z = 3150)
        }
        11673 -> { // Taverly
            player.handleLadder(x = 2884, z = 3398)
        }
        11417 -> { // Taverly Dungeon
            player.handleLadder(x = 2842, z = 3423)
        }
        12192 -> { // King Black Dragon's Lair
            player.handleLadder(x = 3017, z = 3850)
        }
        else -> player.handleLadder(x = 3104, z = 3161)
    }
}

on_obj_option(obj = Objs.LADDER_2147, option = "climb-down") {
    player.handleLadder(x = 3104, z = 9576)
}

on_obj_option(obj = Objs.LADDER_1756, option = "climb-down") {
    player.handleLadder(height = 0, underground = true)
}
