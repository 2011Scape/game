package gg.rsmod.plugins.content.areas.seers

val stairs =
    listOf(
        Objs.LADDER_25938, // ladders for flax house
        Objs.LADDER_25939,
    )

stairs.forEach { stairs ->
    if (if_obj_has_option(obj = stairs, option = "climb-up")) {
        on_obj_option(obj = stairs, option = "climb-up") {
            player.handleLadder(height = 1)
        }
    }
    if (if_obj_has_option(obj = stairs, option = "climb-down")) {
        on_obj_option(obj = stairs, option = "climb-down") {
            player.handleLadder(height = 0)
        }
    }
}
