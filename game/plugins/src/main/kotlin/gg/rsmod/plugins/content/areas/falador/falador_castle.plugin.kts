package gg.rsmod.plugins.content.areas.falador

on_obj_option(obj = Objs.LADDER_11727, option = "climb-up") {
    player.handleLadder(x = player.tile.x, z = player.tile.z, height = player.tile.transform(height = 1).height)
}

on_obj_option(obj = Objs.LADDER_11728, option = "climb-down") {
    player.handleLadder(x = player.tile.x, z = player.tile.z, height = player.tile.transform(height = -1).height)
}
