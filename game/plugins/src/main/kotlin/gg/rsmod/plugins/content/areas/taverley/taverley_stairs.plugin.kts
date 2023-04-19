package gg.rsmod.plugins.content.areas.taverley

val ladders = listOf(
    Objs.LADDER_28652, //ladders for sanfew house
    Objs.LADDER_35125,

)

ladders.forEach { ladder ->
    if (if_obj_has_option(ladder, option = "climb-up")) {
        on_obj_option(ladder, option = "climb-up") {
            player.handleLadder(true, Tile(player.tile.x, player.tile.z, player.tile.height + 1))
        }
    }
    if (if_obj_has_option(ladder, option = "climb-down")) {
        on_obj_option(ladder, option = "climb-down") {
            player.handleLadder(false, Tile(player.tile.x, player.tile.z, player.tile.height - 1))
        }
    }
}
