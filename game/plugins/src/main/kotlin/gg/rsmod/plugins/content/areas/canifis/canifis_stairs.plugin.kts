package gg.rsmod.plugins.content.areas.canifis

on_obj_option(obj = Objs.STAIRCASE_24925, option = "climb-up") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.STAIRCASE_24926, option = "climb-down") {
    player.handleLadder(height = 0)
}

on_obj_option(obj = Objs.LADDER_24918, option = "climb-up") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.LADDER_24919, option = "climb-down") {
    player.handleLadder(height = 0)
}
