package gg.rsmod.plugins.content.areas.lumbridge

val stairs = arrayOf(
    Objs.STAIRCASE_36778,
    Objs.STAIRCASE_36777,
    Objs.STAIRCASE_36776,
    Objs.STAIRCASE_36775,
    Objs.STAIRCASE_36774,
    Objs.STAIRCASE_36773,
)

stairs.forEach { stairs ->
    if (if_obj_has_option(obj = stairs, option = "climb")) {
        on_obj_option(obj = stairs, option = "climb") {
            player.queue {
                when (options("Climb up the stairs.", "Climb down the stairs.")) {
                    1 -> player.moveTo(player.tile.x, player.tile.z, player.tile.height + 1)
                    2 -> player.moveTo(player.tile.x, player.tile.z, player.tile.height - 1)
                }
            }
        }
    }
    if (if_obj_has_option(obj = stairs, option = "climb-up")) {
        on_obj_option(obj = stairs, option = "climb-up") {
            player.moveTo(player.tile.x, player.tile.z, player.tile.height + 1)
        }
    }
    if (if_obj_has_option(obj = stairs, option = "climb-down")) {
        on_obj_option(obj = stairs, option = "climb-down") {
            player.moveTo(player.tile.x, player.tile.z, player.tile.height - 1)
        }
    }
}