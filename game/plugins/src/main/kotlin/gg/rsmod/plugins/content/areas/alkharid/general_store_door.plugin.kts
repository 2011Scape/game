package gg.rsmod.plugins.content.areas.alkharid

on_obj_option(obj = Objs.DOOR_27988, "open") {
    world.openDoor(obj = player.getInteractingGameObj())
}

