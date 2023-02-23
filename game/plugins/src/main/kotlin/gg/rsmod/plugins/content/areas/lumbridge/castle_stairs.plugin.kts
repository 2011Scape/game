package gg.rsmod.plugins.content.areas.lumbridge

val stairs = arrayOf(
    Objs.STAIRCASE_36778,
    Objs.STAIRCASE_36777,
    Objs.STAIRCASE_36776,
    Objs.STAIRCASE_36775,
    Objs.STAIRCASE_36774,
    Objs.STAIRCASE_36773,
    Objs.STAIRCASE_12538,
    Objs.STAIRCASE_12537,
    Objs.STAIRCASE_12536,
    Objs.LADDER_36795,
    Objs.LADDER_36796,
    Objs.LADDER_36797,
)

stairs.forEach { stairs ->
    val name = world.definitions.get(ObjectDef::class.java, stairs).name.lowercase()
    if (if_obj_has_option(obj = stairs, option = "climb")) {
        on_obj_option(obj = stairs, option = "climb") {
            player.queue {
                when (options("Climb up the $name.", "Climb down the $name.")) {
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