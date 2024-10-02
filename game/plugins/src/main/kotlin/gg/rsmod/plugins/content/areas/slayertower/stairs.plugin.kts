package gg.rsmod.plugins.content.areas.slayertower

on_obj_option(obj = Objs.STAIRCASE_4493, option = "climb-up") {
    when (player.tile.height) {
        0 -> { // Second Floor
            player.moveTo(3433, player.tile.z, height = 1)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_4494, option = "climb-down") {
    when (player.tile.height) {
        1 -> { // Second Floor
            player.moveTo(3438, player.tile.z, height = 0)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_4495, option = "climb-up") {
    when (player.tile.height) {
        1 -> { // Second Floor
            player.moveTo(3417, player.tile.z, height = 2)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_4496, option = "climb-down") {
    when (player.tile.height) {
        2 -> { // Second Floor
            player.moveTo(3412, player.tile.z, height = 1)
        }
    }
}
