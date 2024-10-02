package gg.rsmod.plugins.content.areas.falador

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.STAIRS_30943, option = "climb-up") {
    player.moveTo(player.tile.x + 3, player.tile.z - 6400)
}
on_obj_option(obj = Objs.STAIRS_30944, option = "climb-down") {
    player.moveTo(player.tile.x - 3, player.tile.z + 6400)
}
