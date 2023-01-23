package gg.rsmod.plugins.content

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR

/**
 * Closing main modal for players.
 */
set_modal_close_logic {
    val modal = player.interfaces.getModal()
    if (modal != -1) {
        player.closeInterface(modal)
        player.interfaces.setModal(-1)
    }
}

/**
 * Check if the player has a menu opened.
 */
set_menu_open_check {
    player.getInterfaceAt(dest = InterfaceDestination.MAIN_SCREEN) != -1
}

/**
 * Execute when a player logs in.
 */
on_login {

    // Skill-related logic.
    if (player.getSkills().getMaxLevel(Skills.HITPOINTS) < 10) {
        player.getSkills().setBaseLevel(Skills.HITPOINTS, 10)
    }

     player.calculateAndSetCombatLevel()
     player.sendWeaponComponentInformation()


    // Interface-related logic.
    player.openOverlayInterface(player.interfaces.displayMode)
    InterfaceDestination.values.filter { pane -> pane.interfaceId != -1 }.forEach { pane ->
        player.openInterface(pane.interfaceId, pane)
    }

    player.sendOption("Follow", 3)
    player.sendOption("Trade with", 4)
    player.sendOption("Req Assist", 5)

    player.setVarp(281, 1000) // unlocks tutorial settings
    player.lifepointsDirty = true
    player.openChatboxInterface(interfaceId = 137, child = 9, dest = InterfaceDestination.CHAT_BOX_PANE)
    player.message("Welcome to ${world.gameContext.name}.", ChatMessageType.GAME_MESSAGE)
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