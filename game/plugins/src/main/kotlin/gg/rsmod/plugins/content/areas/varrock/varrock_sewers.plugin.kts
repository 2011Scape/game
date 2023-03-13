package gg.rsmod.plugins.content.areas.varrock

/**
 * @author Alycia <https://github.com/alycii>
 */

val OPEN_SFX = 62
val CLOSE_SFX = 60


on_obj_option(obj = Objs.LADDER_24366, option = "climb-up") {
    player.handleBasicLadder(player, climbUp = true)
}

on_obj_option(obj = 881, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.MANHOLE_882, option = "close") {
    close(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.MANHOLE_882, option = "climb-down") {
    player.moveTo(3236, 9858)
    player.filterableMessage("You climb down through the manhole.")
}

fun close(p: Player, obj: GameObject) {
    p.playSound(CLOSE_SFX)
    p.filterableMessage("You place the cover back over the manhole.")
    world.spawn(DynamicObject(obj, 881))
}

fun open(p: Player, obj: GameObject) {
    p.playSound(OPEN_SFX)
    p.filterableMessage("You pull back the cover from over the manhole.")
    world.spawn(DynamicObject(obj, Objs.MANHOLE_882))
}