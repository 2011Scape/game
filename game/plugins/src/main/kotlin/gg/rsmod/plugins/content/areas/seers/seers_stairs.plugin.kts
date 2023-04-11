package gg.rsmod.plugins.content.areas.seers

val stairs = arrayOf(
    Objs.LADDER_25938, //ladders for flax house
    Objs.LADDER_25939,)

stairs.forEach { stairs ->
    if (if_obj_has_option(obj = stairs, option = "climb-up")) {
        on_obj_option(obj = stairs, option = "climb-up") {
            player.handleLadder(true, endTile = Tile(player.tile.x, player.tile.z, 1))
        }
    }
    if (if_obj_has_option(obj = stairs, option = "climb-down")) {
        on_obj_option(obj = stairs, option = "climb-down") {
            player.handleLadder(false, endTile = Tile(player.tile.x, player.tile.z, 0))
        }
    }
}
