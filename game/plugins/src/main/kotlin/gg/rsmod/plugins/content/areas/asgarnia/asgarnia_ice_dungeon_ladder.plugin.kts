package gg.rsmod.plugins.content.areas.asgarnia

on_obj_option(obj = Objs.TRAPDOOR_9472, option = "climb-down") {
    player.handleLadder(climbUp = false, endTile = Tile(x = 3009, z = 9550, height = 0))
}