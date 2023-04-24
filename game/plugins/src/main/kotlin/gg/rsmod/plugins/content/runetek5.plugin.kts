package gg.rsmod.plugins.content

import gg.rsmod.game.model.attr.DISPLAY_MODE_CHANGE_ATTR
import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.entity.Player.Companion.PRAYER_VARBIT
import gg.rsmod.game.model.interf.DisplayMode
import gg.rsmod.game.model.timer.DAILY_TIMER
import gg.rsmod.game.model.timer.PRAYER_INITIALIZATION_TIMER
import gg.rsmod.game.model.timer.SAVE_TIMER
import gg.rsmod.game.model.timer.TIME_ONLINE
import gg.rsmod.game.service.serializer.PlayerSerializerService

/**
 * Closing main modal for players.
 */
set_modal_close_logic {
    val modal = player.interfaces.getModal()
    if (modal != -1) {
        player.closeInterface(modal)
        player.closeInterface(InterfaceDestination.TAB_AREA)
        player.interfaces.setModal(-1)
    }
}

/**
 * Check if the player has a menu opened.
 */
set_menu_open_check {
    player.getInterfaceAt(dest = InterfaceDestination.MAIN_SCREEN) != -1 || player.getInterfaceAt(dest = InterfaceDestination.MAIN_SCREEN_FULL) != -1
}

set_window_status_logic {
    val mode = when (player.attr[DISPLAY_MODE_CHANGE_ATTR]) {
        2 -> DisplayMode.RESIZABLE_NORMAL
        else -> DisplayMode.FIXED
    }
    player.toggleDisplayInterface(mode)
}

/**
 * Execute when a player logs in.
 */
on_login {

    // Skill-related logic.
    if (player.skills.getMaxLevel(Skills.HITPOINTS) < 10) {
        player.skills.setBaseLevel(Skills.HITPOINTS, 10)
    }

     player.calculateAndSetCombatLevel()
     player.sendWeaponComponentInformation()

    // Interface-related logic.
    player.openOverlayInterface(player.interfaces.displayMode)

    // Sends the player tabs
    player.sendTabs()

    player.sendOption("Follow", 3)
    player.sendOption("Trade with", 4)
    player.sendOption("Req Assist", 5)

    player.setVarp(281, 1000) // unlocks tutorial settings
    player.setVarp(1160, -1) // Unlocks summoning orb
    player.setVarbit(4893, 1) // resets bank tab view index
    player.sendTemporaryVarbit(7198, player.lifepoints)
    player.openChatboxInterface(interfaceId = 137, child = 9, dest = InterfaceDestination.CHAT_BOX_PANE)

    // send the active bonus experience weekend
    // message only if bonus experience is active
    if(world.gameContext.bonusExperience) {
        player.message("Bonus XP Weekend is now active!")
    }

    player.message("Welcome to ${world.gameContext.name}.", ChatMessageType.GAME_MESSAGE)

    player.checkEquipment()
    if(!player.timers.has(TIME_ONLINE)) {
        player.timers[TIME_ONLINE] = 0
    }
    player.timers[SAVE_TIMER] = 200

    if(!player.timers.exists(DAILY_TIMER)) {
        player.timers[DAILY_TIMER] = 1
    }

    player.timers[PRAYER_INITIALIZATION_TIMER] = 1
}

/**
 * Saves the player everytime the save timer
 * reaches 0. This is done for each individual player
 * as opposed to all the players at once to save
 * processing time.
 */
on_timer(key = SAVE_TIMER) {
    player.world.getService(PlayerSerializerService::class.java, searchSubclasses = true)?.saveClientData(player as Client)
    player.timers[SAVE_TIMER] = 200
}

/**
 * Sets the prayer varbit to its own value again. This triggers a visual update in the client.
 */
on_timer(key = PRAYER_INITIALIZATION_TIMER) {
    player.setVarbit(PRAYER_VARBIT, player.getVarbit(PRAYER_VARBIT))
}

/**
 * Logic for swapping items in inventory.
 */
on_component_to_component_item_swap(
    srcInterfaceId = 679, srcComponent = 0,
    dstInterfaceId = 679, dstComponent = 0) {
    val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    val dstSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    val container = player.inventory

    if (srcSlot in 0 until container.capacity && dstSlot in 0 until container.capacity) {
        container.swap(srcSlot, dstSlot)
    } else {
        // Sync the container on the client
        container.dirty = true
    }
}


// TODO: Implement proper pathing and opening/closing
// Notes: handles border guards, a temporary solution
// also handles basic things like global object spawns, etc
on_world_init {
    val tiles = arrayOf(
        Tile(3070, 3277, 0), Tile(3070, 3275), // Draynor -> Falador
        Tile(3147, 3336, 0), Tile(3145, 3336), Tile(3147, 3337, 0), Tile(3145, 3337), // Draynor -> Barbarian Village, East
        Tile(3076, 3333, 0), Tile(3077, 3333), Tile(3078, 3333, 0), Tile(3079, 3333), // Draynor -> Barbarian Village, West
        Tile(3109, 3421, 0), Tile(3109, 3419), // Edgeville
        Tile(3261, 3172, 0), Tile(3261, 3174), Tile(3261, 3173), // Al-kharid, south-west
        Tile(3282, 3330, 0), Tile(3284, 3330), Tile(3283, 3329), Tile(3284, 3329), // Al-kharid, north
        Tile(3273, 3429, 0), Tile(3273, 3428), // Varrock east doors
        Tile(3293, 3385, 0), Tile(3291, 3385), // Varrock east guards
    )

    tiles.forEach {
        val obj = world.getObject(it, ObjectType.INTERACTABLE)
        if(obj != null) {
            world.remove(obj)
            world.spawn(DynamicObject(obj.id - 1, obj.type, obj.rot, obj.tile))
        }

        val wall = world.getObject(it, ObjectType.LENGTHWISE_WALL)
        if(wall != null) {
            world.remove(wall)
        }
    }

    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2898, 4808, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2886, 4848, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2933, 4820, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2923, 4854, 0)))
}