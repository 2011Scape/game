package gg.rsmod.plugins.content.areas.edgeville

on_obj_option(obj = Objs.LADDER_29355, option = "climb-up") {
    player.handleLadder(height = 0, underground = true)
}

on_obj_option(obj = Objs.TRAPDOOR_26933, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_26934, option = "close") {
    close(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_26934, option = "climb-down") {
    player.moveTo(3096, 9867)
}

fun close(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_26933))
}

fun open(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_26934))
}
