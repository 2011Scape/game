package gg.rsmod.plugins.content.areas.taverley

val stairs = arrayOf(
    Objs.LADDER_28652,
    Objs.LADDER_35125,

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
