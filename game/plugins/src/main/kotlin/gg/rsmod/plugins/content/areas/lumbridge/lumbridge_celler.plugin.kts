package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(obj = Objs.TRAPDOOR_36687, option = "climb-down") {
    player.handleLadder(height = 0, underground = true)
}