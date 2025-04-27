package gg.rsmod.plugins.content.areas.varrock

on_obj_option(Objs.TRAPDOOR_10558, "Open") {
    // TODO Add opening when Champion's Challenge is released
    player.lockingQueue(lockState = LockState.FULL) {
        wait(1)
        messageBox("The Champion's Challenge is not currently available.")
    }
}

on_obj_option(Objs.CLOSED_CHEST_24203, "Open") {
    player.lockingQueue(lockState = LockState.FULL) {
        val closedChest = player.getInteractingGameObj()
        wait(1)
        player.animate(Anims.REACH_FORWARD)
        wait(2)
        world.spawn(DynamicObject(Objs.OPEN_CHEST_24204, closedChest.type, closedChest.rot, closedChest.tile))
    }
}

on_obj_option(Objs.OPEN_CHEST_24204, "Search") {
    player.lockingQueue(lockState = LockState.FULL) {
        wait(1)
        player.message("You search the chest but find nothing.")
        wait(1)
    }
}

on_obj_option(Objs.OPEN_CHEST_24204, "Shut") {
    player.lockingQueue(lockState = LockState.FULL) {
        val closedChest = player.getInteractingGameObj()
        wait(1)
        player.animate(Anims.REACH_FORWARD)
        wait(2)
        world.spawn(DynamicObject(Objs.CLOSED_CHEST_24203, closedChest.type, closedChest.rot, closedChest.tile))
    }
}

on_obj_option(Objs.DOOR_1805, "Open") {
    if (!player.completedAllQuests()) {
        // TODO: Change once there are at least 33 quest points to the correct message
        player.queue {
            messageBox("You must complete all quests before you can enter the Champions Guild.")
        }
        return@on_obj_option
    }

    val closedDoor = DynamicObject(Objs.DOOR_1805, 0, 3, Tile(3191, 3363))
    val blockingObj = DynamicObject(0, 0, 0, Tile(3191, 3363))
    val openDoor = DynamicObject(Objs.DOOR_23637, 0, 0, Tile(3191, 3362))
    val start = player.tile
    player.lockingQueue(lockState = LockState.FULL) {
        wait(2)
        world.remove(closedDoor)
        world.spawn(blockingObj)
        world.spawn(openDoor)
        player.playSound(Sfx.DOOR_OPEN)
        val newZ = if (start.z >= 3363) 3362 else 3363
        player.walkTo(tile = Tile(start.x, newZ), stepType = MovementQueue.StepType.FORCED_WALK)
        wait(2)
        world.remove(openDoor)
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
        player.lock = LockState.NONE
        if (start.z >= 3363) {
            chatNpc(npc = Npcs.GUILDMASTER,
                message = arrayOf("Greetings bold adventurer. Welcome to the guild of Champions."),
                facialExpression = FacialExpression.HAPPY_TALKING,
                wrap = true
            )
        }
    }
}
