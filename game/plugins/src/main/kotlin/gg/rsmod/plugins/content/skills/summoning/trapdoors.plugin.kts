import gg.rsmod.plugins.content.quests.getCurrentStage

// Taverley obelisk
on_obj_option(Objs.LADDER_28714, "Climb") {
    player.handleLadder(2926, 3444, underground = true)
}

on_obj_option(Objs.TRAPDOOR_28676, "Climb-down") {
    player.handleLadder(2209, 5348)
}

on_obj_option(Objs.TRAPDOOR_28675, "Open") {
    player.setVarbit(4303, 2)
}

on_obj_option(Objs.TRAPDOOR_28676, "Close") {
    player.setVarbit(4303, 1)
}

// Desert obelisk
on_obj_option(Objs.LADDER_28740, "Climb-up") {
    player.handleLadder(3303, 2897, underground = true)
}

on_obj_option(Objs.DESERT_LADDER, "Climb-down") {
    player.handleLadder(3299, 9317)
}

// Piscatoris obelisk
on_obj_option(Objs.LADDER_28743, "Climb-up") {
    player.handleLadder(2329, 3645, underground = true)
}

on_obj_option(Objs.TRAPDOOR_28742, "Climb-down") {
    player.handleLadder(2333, 10015)
}

on_obj_option(Objs.TRAPDOOR_28741, "Open") {
    val obj = player.getInteractingGameObj()
    open(player, obj)
}

on_obj_option(Objs.TRAPDOOR_28742, "Close") {
    val obj = player.getInteractingGameObj()
    close(player, obj)
}

fun close(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, obj.id - 1))
}

fun open(
    p: Player,
    obj: GameObject,
) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, obj.id + 1))
}
