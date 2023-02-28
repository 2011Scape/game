package gg.rsmod.plugins.content.areas.alkharid

val OPEN_DOOR_SFX = 62
val CLOSE_DOOR_SFX = 60
val PRINCE_ALI_RESCUE_VARBIT = 273
val COMPLETED_QUEST = 110

arrayOf(10565, 10566).forEach {
    on_obj_option(obj = it, option = "open") {
        if (player.getVarp(PRINCE_ALI_RESCUE_VARBIT) >= COMPLETED_QUEST) {
            handleKharidGate(player)
        } else {
            player.queue {
                chat(this)
            }
        }
    }

    on_obj_option(obj = it, option = "pay-toll(10gp)") {
        if (payToll(player)) {
            handleKharidGate(player)
            player.filterableMessage("You pay the guards and pass through the gate.")
        } else {
            player.queue { chat(this) }
        }
    }
}

on_npc_option(Npcs.BORDER_GUARD, "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    val player = it.player
    it.chatPlayer("Can I come through this gate?")
    it.chatNpc("You must pay a toll of ten gold coins to pass.", npc = Npcs.BORDER_GUARD)
    when (it.options("Yes, okay.", "Who does my money go to?", "No thank you, I'll walk around.")) {
        1 -> {
            it.chatPlayer("Yes, okay.")
            if (payToll(player)) {
                handleKharidGate(player)
                player.filterableMessage("You pay the guards and pass through the gate.")
            } else {
                it.chatPlayer("Oh dear, I don't actually seem to have enough money.")
            }
        }
        2 -> {
            it.chatPlayer("Who does my money go to?")
            it.chatNpc("The money goes to the city of Al Kharid.", npc = Npcs.BORDER_GUARD)
        }
        3 -> {
            it.chatPlayer("No thank you, I'll walk around.")
            it.chatNpc("Okay, suit yourself.", npc = Npcs.BORDER_GUARD)
        }
    }
}

fun payToll(player: Player): Boolean {
    val toll = Item(Items.COINS_995, amount = 10)
    return (player.inventory.remove(item = toll, assureFullRemoval = true).hasSucceeded())
}

fun handleKharidGate(player: Player) {
    val southOriginalGate = DynamicObject(id = 35549, type = 0, rot = 0, tile = Tile(x = 3268, z = 3227))
    val northOriginalGate = DynamicObject(id = 35551, type = 0, rot = 0, tile = Tile(x = 3268, z = 3228))

    player.lock = LockState.DELAY_ACTIONS

    world.remove(southOriginalGate)
    world.remove(northOriginalGate)

    val northGate = DynamicObject(id = 35552, type = 0, rot = 1, tile = Tile(x = 3267, z = 3228))
    val southGate = DynamicObject(id = 35550, type = 0, rot = 3, tile = Tile(x = 3267, z = 3227))

    player.playSound(id = OPEN_DOOR_SFX)
    world.spawn(northGate)
    world.spawn(southGate)

    player.queue() {
        if (player.tile.x <= 3267) {
            if (player.tile.z == 3228)
                player.walkTo(tile = Tile(x = 3268, z = 3228), detectCollision = false)
            else
                player.walkTo(tile = Tile(x = 3268, z = 3227), detectCollision = false)
        } else {
            if (player.tile.z == 3228)
                player.walkTo(tile = Tile(x = 3267, z = 3228), detectCollision = false)
            else
                player.walkTo(tile = Tile(x = 3267, z = 3227), detectCollision = false)
        }
        wait(3)
        world.remove(northGate)
        world.remove(southGate)
        player.lock = LockState.NONE
        world.spawn(northOriginalGate)
        world.spawn(southOriginalGate)
        player.playSound(CLOSE_DOOR_SFX)
    }
}