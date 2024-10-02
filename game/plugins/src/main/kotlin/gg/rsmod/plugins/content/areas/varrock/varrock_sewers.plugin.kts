package gg.rsmod.plugins.content.areas.varrock

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.LADDER_24366, option = "climb-up") {
    player.handleLadder(height = 0, underground = true)
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

on_obj_option(obj = Objs.HOLE_24264, option = "climb-down") {
    player.handleLadder()
}

fun close(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You place the cover back over the manhole.")
    world.spawn(DynamicObject(obj, 881))
}

fun open(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("You pull back the cover from over the manhole.")
    world.spawn(DynamicObject(obj, Objs.MANHOLE_882))
}
