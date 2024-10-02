package gg.rsmod.plugins.content

import gg.rsmod.game.model.attr.DISPLAY_MODE_CHANGE_ATTR
import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR
import gg.rsmod.game.model.collision.ObjectType
import gg.rsmod.game.model.interf.DisplayMode
import gg.rsmod.game.model.timer.*
import gg.rsmod.game.service.serializer.PlayerSerializerService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File

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

fun updatePlayerCountInJson() {
    val filePath = "./plugins/configs/player_count.json"
    val file = File(filePath)

    // Check if the file exists; if not, initialize with default structure
    if (!file.exists()) {
        file.parentFile.mkdirs()
        file.writeText("""{"playerCount": 0}""")
    }

    // Read the existing content
    val content = file.readText()
    val json = Json.parseToJsonElement(content) as JsonObject

    // Calculate the new count
    val count = world.players.count()

    // Update the count in the JSON structure
    val updatedJson =
        buildJsonObject {
            put("playerCount", count)
            json.keys.filter { it != "playerCount" }.forEach { key ->
                put(key, json[key]!!)
            }
        }

    // Write the updated content back to the file
    file.writeText(updatedJson.toString())
}

/**
 * Check if the player has a menu opened.
 */
set_menu_open_check {
    player.getInterfaceAt(dest = InterfaceDestination.MAIN_SCREEN) != -1 ||
        player.getInterfaceAt(dest = InterfaceDestination.MAIN_SCREEN_FULL) != -1
}

set_window_status_logic {
    val mode =
        when (player.attr[DISPLAY_MODE_CHANGE_ATTR]) {
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
    if (player.skills.getMaxLevel(Skills.CONSTITUTION) < 10) {
        player.skills.setBaseLevel(Skills.CONSTITUTION, 10)
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
    player.setVarp(678, 3) // recipe for disaster chest

    player.setVarbit(4893, 1) // resets bank tab view index
    player.setVarbit(4221, 0) // unlock incubator
    player.setVarbit(1766, 1) // unlock killerwatt portal
    player.setVarbit(6471, 45) // chaos dwarf area
    player.setVarbit(532, 4) // lumbridge underground
    player.setVarbit(2869, 1) // balloon (castle wars)
    player.setVarbit(2871, 1) // balloon (crafting guild)
    player.setVarbit(2870, 1) // balloon (grand tree)
    player.setVarbit(2867, 3) // balloon (entrana) (3 empty, 2 full, 1 half built with fire lit)
    player.setVarbit(2868, 1) // balloon (taverly)
    player.setVarbit(2872, 1) // balloon (varrock)

    player.openChatboxInterface(interfaceId = 137, child = 9, dest = InterfaceDestination.CHAT_BOX_PANE)

    // send the active bonus experience weekend
    // message only if bonus experience is active
    if (world.gameContext.bonusExperience) {
        player.message("Bonus XP Weekend is now active!")
    }

    player.message("Welcome to ${world.gameContext.name}.", ChatMessageType.GAME_MESSAGE)

    player.checkEquipment()

    // Updates the players lifepoints
    if (player.getVarbit(player.skills.LIFEPOINTS_VARBIT) == 0) {
        player.setVarbit(player.skills.LIFEPOINTS_VARBIT, player.skills.getMaxLevel(Skills.CONSTITUTION) * 10)
    }

    val timersToInitialize = listOf(TIME_ONLINE, DAILY_TIMER, SAVE_TIMER, STAT_RESTORE)
    timersToInitialize.forEach { timer ->
        if (!player.timers.exists(timer)) {
            player.timers[timer] = 1
        }
    }
    updatePlayerCountInJson()
}

on_logout {
    world.queue {
        wait(5)
        updatePlayerCountInJson()
    }
}

/**
 * Saves the player everytime the save timer
 * reaches 0. This is done for each individual player
 * as opposed to all the players at once to save
 * processing time.
 */
on_timer(key = SAVE_TIMER) {
    player.world
        .getService(PlayerSerializerService::class.java, searchSubclasses = true)
        ?.saveClientData(player as Client)
    player.timers[SAVE_TIMER] = 200
}

/**
 * Logic for swapping items in inventory.
 */
on_component_to_component_item_swap(
    srcInterfaceId = 679,
    srcComponent = 0,
    dstInterfaceId = 679,
    dstComponent = 0,
) {
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

// Notes: handles border guards, a temporary solution
// also handles basic things like global object spawns, etc
on_world_init {
    val tiles =
        arrayOf(
            Tile(3070, 3277, 0),
            Tile(3070, 3275), // Draynor -> Falador
            Tile(3147, 3336, 0),
            Tile(3145, 3336),
            Tile(3147, 3337, 0),
            Tile(3145, 3337), // Draynor -> Barbarian Village, East
            Tile(3076, 3333, 0),
            Tile(3077, 3333),
            Tile(3078, 3333, 0),
            Tile(3079, 3333), // Draynor -> Barbarian Village, West
            Tile(3109, 3421, 0),
            Tile(3109, 3419), // Edgeville
            Tile(3261, 3172, 0),
            Tile(3261, 3174),
            Tile(3261, 3173), // Al-kharid, south-west
            Tile(3282, 3330, 0),
            Tile(3284, 3330),
            Tile(3283, 3329),
            Tile(3284, 3329), // Al-kharid, north
            Tile(3273, 3429, 0),
            Tile(3273, 3428), // Varrock east doors
            Tile(3293, 3385, 0),
            Tile(3291, 3385), // Varrock east guards
        )

    tiles.forEach {
        val obj = world.getObject(it, ObjectType.INTERACTABLE)
        if (obj != null) {
            world.remove(obj)
            world.spawn(DynamicObject(obj.id - 1, obj.type, obj.rot, obj.tile))
        }

        val wall = world.getObject(it, ObjectType.LENGTHWISE_WALL)
        if (wall != null) {
            world.remove(wall)
        }
    }

    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2898, 4808, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2886, 4848, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2933, 4820, 0)))
    world.spawn(DynamicObject(id = Objs.PORTAL_7352, type = 10, rot = 0, tile = Tile(2923, 4854, 0)))
}
