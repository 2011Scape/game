package gg.rsmod.plugins.content.areas.taverley

val ladders =
    listOf(
        Objs.LADDER_28652, // ladders for sanfew house
        Objs.LADDER_35125,
        Objs.LADDER_28653,
        Objs.LADDER_28572,
    )

ladders.forEach { ladder ->
    if (if_obj_has_option(ladder, option = "climb-up")) {
        on_obj_option(ladder, option = "climb-up") {
            player.handleLadder(height = 1)
        }
    }
    if (if_obj_has_option(ladder, option = "climb-down")) {
        on_obj_option(ladder, option = "climb-down") {
            player.handleLadder(height = 0)
        }
    }
}
