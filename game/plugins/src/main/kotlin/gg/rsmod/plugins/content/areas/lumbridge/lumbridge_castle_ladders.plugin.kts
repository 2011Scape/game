package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(Objs.LADDER_36771, "Climb-up") {
    player.handleLadder(3207, 3222, 3)
}

on_obj_option(Objs.LADDER_36772, "Climb-down") {
    player.handleLadder(3207, 3224, 2)
}
