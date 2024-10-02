package gg.rsmod.plugins.content.areas.edgeville

on_obj_option(obj = Objs.LADDER_26982, option = "climb-up") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.LADDER_26983, option = "climb-down") {
    player.handleLadder(height = 0)
}
