package gg.rsmod.plugins.content.areas.falador

on_obj_option(obj = Objs.LADDER_11727, option = "climb-up") {
    player.handleLadder(x = player.tile.x, z = player.tile.z, height = player.tile.transform(height = 1).height)
}

on_obj_option(obj = Objs.LADDER_11728, option = "climb-down") {
    player.handleLadder(x = player.tile.x, z = player.tile.z, height = player.tile.transform(height = -1).height)
}

on_obj_option(obj = Objs.STAIRCASE_11734, option = "climb-up") {
    player.moveTo(Tile(x = 2984, z = 3340, height = 2))
}

on_obj_option(obj = Objs.STAIRCASE_35783, option = "climb-down") {
    player.moveTo(Tile(x = 2984, z = 3336, height = 1))
}
