package gg.rsmod.plugins.content.areas.falador

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.STAIRS_30944, option = "climb-down") {
    player.moveTo(Tile(3058, 9776, 0))
}

on_obj_option(obj = Objs.STAIRS_30943, option = "climb-up") {
    player.moveTo(Tile(3061, 3376, 0))
}