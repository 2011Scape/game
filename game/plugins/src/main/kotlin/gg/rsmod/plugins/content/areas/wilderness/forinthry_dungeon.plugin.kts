package gg.rsmod.plugins.content.areas.wilderness

on_obj_option(obj = Objs.CAVE_ENTRANCE_20599, option = "enter") {
    player.moveTo(x = 3037, z = 10171)
}

on_obj_option(obj = Objs.CAVE_ENTRANCE_20600, option = "enter") {
    player.moveTo(x = 3077, z = 10058)
}

on_obj_option(obj = Objs.EXIT_18341, option = "leave") {
    player.moveTo(x = 3039, z = 3765)
}

on_obj_option(obj = Objs.EXIT_18342, option = "leave") {
    player.moveTo(x = 3071, z = 3649)
}
