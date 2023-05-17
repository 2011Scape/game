package gg.rsmod.plugins.content.areas.morytania


on_obj_option(obj = Objs.TRAPDOOR_30571, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30572, option = "close") {
    close(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30573, option = "open") {
    open(player, player.getInteractingGameObj())
}

on_obj_option(obj = Objs.TRAPDOOR_30572, option = "climb-down") {
    val obj = player.getInteractingGameObj()
    when (obj.tile.x) {
        3405 -> player.handleLadder(height = 0, underground = true)
        3422 -> player.handleLadder(x = 3440, z = 9887, height = 0, underground = true)
    }
}

on_obj_option(obj = Objs.LADDER_30575, option = "climb-up") {
    player.handleLadder(height = 0, underground = true)
}

on_obj_option(obj = Objs.HOLY_BARRIER, option = "pass-through") {
    player.moveTo(Tile(x = 3423, z = 3484, height = 0))
}

on_obj_option(obj = Objs.GATE_3444, option = "open") {
    handleDoor(player)
}

fun handleDoor(player: Player) {
    val closedDoor = DynamicObject(id = Objs.GATE_3444, type = 0, rot = 3, tile = Tile(x = 3405, z = 9895))
    val door = DynamicObject(id = Objs.GATE_3444, type = 0, rot = 2, tile = Tile(x = 3405, z = 9895))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = 3405
        val z = if (player.tile.z >= 9895) 9893 else 9896
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.GATE_3445, option = "open") {
    handleDoor2(player)
}

fun handleDoor2(player: Player) {
    val closedDoor = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 2, tile = Tile(x = 3431, z = 9897))
    val door = DynamicObject(id = Objs.GATE_3445, type = 0, rot = 1, tile = Tile(x = 3431, z = 9897))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val x = if (player.tile.x >= 3431) 3430 else 3433
        val z = 9897
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

fun close(p: Player, obj: GameObject) {
    p.playSound(Sfx.DOOR_CLOSE)
    p.filterableMessage("You close the trapdoor.")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30571))
}

fun open(p: Player, obj: GameObject) {
    p.playSound(Sfx.DOOR_OPEN)
    p.filterableMessage("The trapdoor opens...")
    world.spawn(DynamicObject(obj, Objs.TRAPDOOR_30572))
}