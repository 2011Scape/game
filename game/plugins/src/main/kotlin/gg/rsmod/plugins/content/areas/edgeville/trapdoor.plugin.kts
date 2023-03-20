package gg.rsmod.plugins.content.areas.edgeville

val OPEN_SFX = 62
val CLOSE_SFX = 60

on_obj_option(obj = Objs.LADDER_29355, option = "climb-up") {
    player.handleBasicLadder(climbUp = true)
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

fun close(p: Player, obj: GameObject) {
    p.playSound(CLOSE_SFX)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_26933))
}

fun open(p: Player, obj: GameObject) {
    p.playSound(OPEN_SFX)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_26934))
}