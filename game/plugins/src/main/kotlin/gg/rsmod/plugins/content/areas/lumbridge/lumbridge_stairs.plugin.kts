import gg.rsmod.plugins.api.cfg.Objs

on_obj_option(Objs.STAIRS_45483, "climb-up") {
    val objPosition = player.getInteractingGameObj().tile

    when (objPosition) {
        // House north of musician
        Tile(3232, 3239) -> {
            player.handleStairs(3232, 3241, 1)
        }
        // House north of Bob
        Tile(3230, 3209) -> {
            player.handleStairs(3229, 3209, 1)
        }
        // Bob's axes
        Tile(3230, 3205) -> {
            player.handleStairs(3229, 3205, 1)
        }
        // East farm
        Tile(3225, 3288) -> {
            player.handleStairs(3225, 3287, 1)
        }
        else ->
            player.message("Nothing interesting happens.")
    }
}

on_obj_option(Objs.STAIRS_45484, "climb-down") {
    val objPosition = player.getInteractingGameObj().tile

    when (objPosition) {
        // House north of musician
        Tile(3232, 3239, 1) -> {
            player.handleStairs(3232, 3238, 0)
        }
        // House north of Bob
        Tile(3230, 3209, 1) -> {
            player.handleStairs(3232, 3209, 0)
        }
        // Bob's axes
        Tile(3230, 3205, 1) -> {
            player.handleStairs(3232, 3205, 0)
        }
        // East farm
        Tile(3225, 3288, 1) -> {
            player.handleStairs(3225, 3290, 0)
        }
        else ->
            player.message("Nothing interesting happens.")
    }
}

on_obj_option(Objs.STAIRS_45481, "climb-up") {
    val objPosition = player.getInteractingGameObj().tile

    when (objPosition) {
        // General store
        Tile(3215, 3239) -> {
            player.handleStairs(3214, 3239, 1)
        }
        // Explorer Jack's house
        Tile(3200, 3243) -> {
            player.handleStairs(3200, 3242, 1)
        }
        // Hank's fishing shop
        Tile(3193, 3255) -> {
            player.handleStairs(3195, 3255, 1)
        }
        // Cordero's house
        Tile(3237, 3196) -> {
            player.handleStairs(3237, 3195, 1)
        }
        // Combat training building
        Tile(3212, 3256) -> {
            player.handleStairs(3214, 3256, 1)
        }
        // Thieves guild house
        Tile(3225, 3265) -> {
            player.handleStairs(3225, 3264, 1)
        }
        else ->
            player.message("Nothing interesting happens.")
    }
}

on_obj_option(Objs.STAIRS_45482, "climb-down") {
    val objPosition = player.getInteractingGameObj().tile

    when (objPosition) {
        // General store
        Tile(3215, 3239, 1) -> {
            player.handleStairs(3217, 3239, 0)
        }
        // Explorer Jack's house
        Tile(3200, 3243, 1) -> {
            player.handleStairs(3200, 3245, 0)
        }
        // Hank's fishing shop
        Tile(3193, 3255, 1) -> {
            player.handleStairs(3192, 3255, 0)
        }
        // Cordero's house
        Tile(3237, 3196, 1) -> {
            player.handleStairs(3237, 3198, 0)
        }
        // Combat training building
        Tile(3212, 3256, 1) -> {
            player.handleStairs(3211, 3256, 0)
        }
        // Thieves guild house
        Tile(3225, 3265, 1) -> {
            player.handleStairs(3225, 3267, 0)
        }
        else ->
            player.message("Nothing interesting happens.")
    }
}


