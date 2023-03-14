package gg.rsmod.plugins.content.areas.draynor

spawn_npc(npc = Npcs.CHICKEN, x = 3110, z = 9573, walkRadius = 5, direction = Direction.SOUTH)
spawn_npc(npc = Npcs.WIZARD, x = 3108, z = 9558, walkRadius = 5, direction = Direction.NORTH_WEST)
spawn_npc(npc = Npcs.SEDRIDOR, x = 3105, z = 9569, walkRadius = 5, direction = Direction.NORTH_WEST)

on_obj_option(obj = Objs.LADDER_32015, option = "climb-up") {
    if (player.tile.x == 2884 && player.tile.z == 9796) {
        player.queue {
            player.animate(828)
            wait(world.getAnimationDelay(828))
            player.moveTo(Tile(2884, 3398, 0))
        }
    }
    else {
        player.queue {
            player.animate(828)
            wait(world.getAnimationDelay(828))
            player.moveTo(Tile(3104, 3161, 0))
        }
    }
}

on_obj_option(obj = Objs.LADDER_2147, option = "climb-down") {
    player.queue {
        player.animate(827)
        wait(world.getAnimationDelay(827))
        player.moveTo(Tile(3104, 9576, 0))
    }
}