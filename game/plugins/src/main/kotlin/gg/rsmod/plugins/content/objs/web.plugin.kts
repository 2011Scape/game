package gg.rsmod.plugins.content.objs

fun slashWeb(player: Player, obj: GameObject) {
    val successThreshold = 1.0 - (0.3333 * player.webFatigue)

    if ((0..3).random() < successThreshold * 4) {
        val newObj = DynamicObject(734, obj.type, obj.rot, obj.tile)
        world.spawnTemporaryObject(newObj, 10, obj)
        player.animate(390)
        player.message("You slash the web apart!")
        player.webFatigue++
    } else {
        // Increase fatigue with each failed attempt
        if (player.webFatigue > 5) {
            player.webFatigue = 0
        } else {
            player.webFatigue++
        }
        // Reset the fatigue counter when it reaches 10
        player.animate(390)
        player.message("You fail to cut through the web.")
    }
}

on_obj_option(obj = Objs.WEB, option = "slash") {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}

on_item_on_obj(obj = Objs.WEB, item = Items.KNIFE) {
    val obj = player.getInteractingGameObj()
    if (obj.isSpawned(world)) {
        player.faceTile(obj.tile)
        slashWeb(player, obj)
    }
}