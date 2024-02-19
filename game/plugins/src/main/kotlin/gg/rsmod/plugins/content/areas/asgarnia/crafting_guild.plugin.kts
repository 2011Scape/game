package gg.rsmod.plugins.content.areas.asgarnia

on_obj_option(obj = Objs.STAIRCASE_9582, option = "climb-up") {
    val stairsObj = player.getInteractingGameObj()
    if (stairsObj.tile.z == 3282) {
        player.moveTo(2933, 3282, 1)
    }
    return@on_obj_option
}

on_obj_option(obj = Objs.STAIRCASE_9584, option = "climb-down") {
    val stairsObj = player.getInteractingGameObj()
    if (stairsObj.tile.z == 3282) {
        player.moveTo(2932, 3281, 0)
    }
    return@on_obj_option
}

on_obj_option(obj = Objs.GUILD_DOOR_2647, option = "open") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.CRAFTING) < 40) {
        player.queue {
            messageBox("You need a Crafting level of 40 to enter this guild.")
        }
        return@on_obj_option
    }
    player.queue {
        when (obj.tile.x) {
            2933 -> handleGuildDoor(player, obj)
        }
    }
}

fun handleGuildDoor(player: Player, obj: GameObject) {
    val doorObject = DynamicObject(obj)
    val blockEntrance = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = 2933, z = 3288))
    val doorOpen = DynamicObject(id = obj.id, type = 0, rot = 2, tile = Tile(x = obj.tile.x, z = obj.tile.z))
    val isNorth = player.tile.z >= obj.tile.z
        if (player.hasEquipped(slot = EquipmentType.CHEST, Items.BROWN_APRON)) {
            player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
                world.remove(doorObject)
                player.playSound(Sfx.DOOR_OPEN)
                world.spawn(blockEntrance)
                world.spawn(doorOpen)
                val targetTile = if (isNorth) Tile(obj.tile.x, obj.tile.z - 1) else Tile(obj.tile.x, obj.tile.z + 1)
                player.walkTo(Tile(targetTile), detectCollision = false)
                wait(3)
                world.remove(blockEntrance)
                world.remove(doorOpen)
                world.spawn(doorObject)
                player.playSound(Sfx.DOOR_CLOSE)
                if (isNorth) {
                    chatNpc("Welcome to the Guild of Master Craftsmen.", npc = Npcs.MASTER_CRAFTER)
                }
            }
        } else {
            player.queue {
                chatNpc(
                    "Where's your brown apron? You can't come in here unless you're wearing one.",
                    npc = Npcs.MASTER_CRAFTER, wrap = true
                )
                chatPlayer("Err... I haven't got one.")
            }
        }
    }


