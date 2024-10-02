package gg.rsmod.plugins.content.areas.wilderness

on_obj_option(obj = Objs.LADDER_1765, option = "climb-down") {
    player.handleLadder(x = 3069, z = 10257, height = 0, underground = true)
}
