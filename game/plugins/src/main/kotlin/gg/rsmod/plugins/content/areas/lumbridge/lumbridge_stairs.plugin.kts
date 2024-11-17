import gg.rsmod.plugins.api.cfg.Objs

// House north of musician
on_obj_option(Objs.STAIRS_45483, "climb-up") {
    player.teleportTo(3232, 3241, 1)
}

on_obj_option(Objs.STAIRS_45484, "climb-down") {
    player.teleportTo(3232, 3238, 0)
}

on_obj_option(Objs.STAIRS_45481, "climb-up") {
    val objPosition = player.getInteractingGameObj().tile

    // General store
    if (objPosition == Tile(3215, 3239)) {
        player.teleportTo(3214, 3239, 1)
    }

    // Explorer Jack's house
    if (objPosition == Tile(3200, 3243)) {
        player.teleportTo(3200, 3242, 1)
    }

    // Hank's fishing shop
    if (objPosition == Tile(3193, 3255)) {
        player.teleportTo(3195, 3255, 1)
    }
}

on_obj_option(Objs.STAIRS_45482, "climb-down") {
    val objPosition = player.getInteractingGameObj().tile

    // General store
    if (objPosition == Tile(3215, 3239, 1)) {
        player.teleportTo(3217, 3239, 0)
    }

    // Explorer Jack's house
    if (objPosition == Tile(3200, 3243, 1)) {
        player.teleportTo(3200, 3245, 0)
    }

    // Hank's fishing shop
    if (objPosition == Tile(3193, 3255, 1)) {
        player.teleportTo(3192, 3255, 0)
    }
}


