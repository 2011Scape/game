package gg.rsmod.plugins.content.areas.ardougne

val legendsGuildGates = listOf(Objs.GATE_2391, Objs.GATE_2392)
val legendsGuildDoors = listOf(Objs.LEGENDS_GUILD_DOOR, Objs.LEGENDS_GUILD_DOOR_2897)

val notFullCombatBracelets = listOf(Items.COMBAT_BRACELET, Items.COMBAT_BRACELET_1,
    Items.COMBAT_BRACELET_2, Items.COMBAT_BRACELET_3)
val notFullSkillsNecklaces = listOf(Items.SKILLS_NECKLACE, Items.SKILLS_NECKLACE_1,
    Items.SKILLS_NECKLACE_2, Items.SKILLS_NECKLACE_3)

legendsGuildGates.forEach {
    on_obj_option(it, "Search") {
        player.lockingQueue {
            wait(1)
            player.message("You don't find anything interesting.")
        }
    }

    on_obj_option(it, "Open") {
        if (player.getInteractingGameObj().tile.z != 3349) return@on_obj_option
        if (!player.completedAllQuests()) {
            player.queue {
                messageBox("You must complete all quests before you can access the Legends Guild.")
            }
            return@on_obj_option
        }

        player.lockingQueue {
            wait(2)
            val westGate = DynamicObject(id = Objs.GATE_2391, type = 0, rot = 1, tile = Tile(x = 2728, z = 3349))
            val eastGate = DynamicObject(id = Objs.GATE_2392, type = 0, rot = 1, tile = Tile(x = 2729, z = 3349))

            val westBlock = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = 2728, z = 3349))
            val eastBlock = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = 2729, z = 3349))

            val westOpenGate = DynamicObject(id = Objs.GATE_2391, type = 0, rot = 0, tile = Tile(x = 2728, z = 3350))
            val eastOpenGate = DynamicObject(id = Objs.GATE_2392, type = 0, rot = 2, tile = Tile(x = 2729, z = 3350))

            world.remove(westGate)
            world.remove(eastGate)
            world.spawn(westBlock)
            world.spawn(eastBlock)
            world.spawn(westOpenGate)
            world.spawn(eastOpenGate)
            player.playSound(Sfx.GATE_OPEN)
            val prevTile = player.tile
            val x = if (player.tile.x > 2728) 2729 else 2728
            val y = if (player.tile.z > 3349) 3349 else 3350
            player.walkTo(x, y, MovementQueue.StepType.FORCED_WALK)
            wait(1)
            player.faceTile(prevTile)
            wait(1)
            world.remove(westOpenGate)
            world.remove(eastOpenGate)
            world.spawn(westGate)
            world.spawn(eastGate)
            player.playSound(Sfx.GATE_CLOSE)
            wait(1)
        }
    }
}

legendsGuildDoors.forEach {
    on_obj_option(it, "Open") {
        if (player.getInteractingGameObj().tile.z != 3373) return@on_obj_option

        player.lockingQueue {
            wait(2)
            val westGate = DynamicObject(id = Objs.LEGENDS_GUILD_DOOR, type = 0, rot = 1, tile = Tile(x = 2728, z =
                3373))
            val eastGate = DynamicObject(id = Objs.LEGENDS_GUILD_DOOR_2897, type = 0, rot = 1, tile = Tile(x = 2729, z =
                3373))

            val westBlock = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = 2728, z = 3373))
            val eastBlock = DynamicObject(id = 0, type = 0, rot = 0, tile = Tile(x = 2729, z = 3373))

            val westOpenGate = DynamicObject(id = Objs.LEGENDS_GUILD_DOOR, type = 0, rot = 0,
                tile = Tile(x = 2728, z = 3374))
            val eastOpenGate = DynamicObject(id = Objs.LEGENDS_GUILD_DOOR_2897, type = 0, rot = 2,
                tile = Tile(x = 2729, z = 3374))

            world.remove(westGate)
            world.remove(eastGate)
            world.spawn(westBlock)
            world.spawn(eastBlock)
            world.spawn(westOpenGate)
            world.spawn(eastOpenGate)
            player.playSound(Sfx.GATE_OPEN)
            val prevTile = player.tile
            val x = if (player.tile.x > 2728) 2729 else 2728
            val y = if (player.tile.z > 3373) 3373 else 3374
            player.walkTo(x, y, MovementQueue.StepType.FORCED_WALK)
            wait(1)
            player.faceTile(prevTile)
            wait(1)
            world.remove(westOpenGate)
            world.remove(eastOpenGate)
            world.spawn(westGate)
            world.spawn(eastGate)
            player.playSound(Sfx.GATE_CLOSE)
            wait(1)
        }
    }
}

on_any_item_on_obj(Objs.TOTEM_POLE_2938) {
    if (player.inventory.count { item -> item != null && (notFullCombatBracelets.contains(item.id) ||
            notFullSkillsNecklaces.contains(item.id)) } == 0) {
        player.message("You don't have any jewellery that the totem can recharge.")
        return@on_any_item_on_obj
    }

    player.lockingQueue(lockState = LockState.FULL) {
        player.animate(Anims.REACH_DOWN)
        var itemType = ""
        var firstItem = Item(Items.COMBAT_BRACELET)
        var extraMessage = ""
        for (i in 0 until player.inventory.capacity) {
            val item = player.inventory[i] ?: continue
            if (notFullCombatBracelets.contains(item.id)) {
                player.inventory.remove(item)
                player.inventory.add(Item(Items.COMBAT_BRACELET_4), beginSlot = i)
                if (itemType.isEmpty()) {
                    itemType = "bracelet"
                    firstItem = item
                    extraMessage = "get information while on a Slayer assignment"
                }
            }
            else if (notFullSkillsNecklaces.contains(item.id)) {
                player.inventory.remove(item)
                player.inventory.add(Item(Items.SKILLS_NECKLACE_4), beginSlot = i)
                if (itemType.isEmpty()) {
                    itemType = "necklace"
                    firstItem = item
                    extraMessage = "get more caskets while big net fishing"
                }
            }
        }
        player.lock = LockState.NONE
        itemMessageBox("You feel a power emanating from the totem pole as it recharges your $itemType. You can now " +
            "rub the $itemType to teleport and wear it to $extraMessage.", item = firstItem.id)
        wait(1)
    }
}

on_obj_option(Objs.TOTEM_POLE_2938, "Look") {
    player.lockingQueue {
        wait(1)
        player.message("It's a gilded totem taken from the Kharazi Jungle.")
    }
}

on_obj_option(Objs.STAIRCASE_41425, "Climb-down") {
    val z = if (player.tile.z >= 3375) 3375 else 3374
    player.lockingQueue {
        wait(2)
        player.teleportTo(Tile(player.tile.x - 3, z + 6400))
    }
}

on_obj_option(Objs.STAIRCASE_32048, "Climb-up") {
    val z = if (player.tile.z >= 9775) 9775 else 9774
    player.lockingQueue {
        wait(2)
        player.teleportTo(Tile(player.tile.x + 3, z - 6400))
    }
}

on_obj_option(Objs.STAIRCASE_41435, "Climb-up") {
    player.lockingQueue {
        wait(2)
        val tile = player.tile
        player.teleportTo(Tile(tile.x, tile.z + 4, 1))
    }
}

on_obj_option(Objs.STAIRCASE_41436, "Climb-down") {
    player.lockingQueue {
        wait(2)
        val tile = player.tile
        player.teleportTo(Tile(tile.x, tile.z - 4, 0))
    }
}

on_obj_option(Objs.LADDER_41458, "Climb-up") {
    val tile = player.tile
    player.handleLadder(tile.x, tile.z, tile.height + 1)
}
