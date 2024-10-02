package gg.rsmod.plugins.content.mechanics.ladders

on_obj_option(obj = Objs.LADDER_1747, option = "climb-up") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2895, 2890 -> player.handleLadder(player.tile.x, player.tile.z, 2)
    }
}

on_obj_option(obj = Objs.LADDER_1746, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2895, 2890 -> player.handleLadder(player.tile.x, player.tile.z, 1)
    }
}

on_obj_option(obj = Objs.LADDER_1754, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2594 -> player.handleLadder(2594, 9486, 0) // Wizards' Tower Basement
        2892 -> player.handleLadder(2893, 9907, 0) // Heroes' Guild Basement
    }
}

on_obj_option(obj = Objs.LADDER_1757, option = "climb-up") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        2594 -> player.handleLadder(2594, 3086, 0) // Wizards' Tower Basement
        2892 -> player.handleLadder(2892, 3508, 0) // Heroes' Guild Basement
    }
}
