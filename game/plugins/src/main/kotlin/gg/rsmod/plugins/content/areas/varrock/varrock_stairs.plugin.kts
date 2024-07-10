package gg.rsmod.plugins.content.areas.varrock

// Cooking Guild stairs

// Floor 1
on_obj_option(obj = Objs.STAIRCASE_24073, option = "climb-up") {
    player.handleStairs(height = 1)
}
// Floor 2
on_obj_option(obj = Objs.STAIRCASE_24074, option = "climb") {
    player.queue {
        when (options("Climb up the staircase.", "Climb down the staircase.")) {
            1 -> player.moveTo(3144, 3446, 2)
            2 -> player.moveTo(player.tile.x, player.tile.z, player.tile.height - 1)
        }
    }
}

on_obj_option(obj = Objs.STAIRCASE_24074, option = "climb-up") {
    player.handleStairs(3144, 3446, 2)
}
on_obj_option(obj = Objs.STAIRCASE_24074, option = "climb-down") {
    player.handleStairs(height = 0)
}
// Floor 3
on_obj_option(obj = Objs.STAIRCASE_24075, option = "climb-down") {
    player.handleStairs(3144, 3449, 1)
}

// Juliet's House
on_obj_option(obj = Objs.STAIRCASE_24357, option = "climb-up") {
    player.handleStairs(3155, player.tile.z, 1)
}
on_obj_option(obj = Objs.STAIRCASE_24359, option = "climb-down") {
    player.handleStairs(3159, player.tile.z, 0)
}

// Varrock Wall

on_obj_option(obj = Objs.LADDER_24361, option = "climb-up") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.LADDER_24362, option = "climb-down") {
    player.handleLadder(height = 0)
}

// Varrock West Bank
on_obj_option(obj = Objs.STAIRCASE_24360, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        3189 -> player.handleStairs(x = 3190, underground = true)
    }
}

on_obj_option(obj = Objs.STAIRCASE_24365, option = "climb-up") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        3187 -> player.handleStairs(3188, 3433, height = 0)
    }
}

on_obj_option(obj = Objs.LADDER_24354, option = "climb-up") {
    player.handleLadder(height = 1)
}

on_obj_option(obj = Objs.LADDER_24355, option = "climb-down") {
    player.handleLadder(height = 0)
}

// Blue Moon Inn
on_obj_option(obj = Objs.LADDER_24363, option = "climb-down") {
    player.handleLadder(underground = true)
}

on_obj_option(obj = Objs.LADDER_24364, option = "climb-up") {
    player.handleLadder(underground = true)
}
