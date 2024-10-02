package gg.rsmod.plugins.content.inter.equipmentstats

val EQUIPMENT_BONUS_INTERFACE_ID = 667
val INVENTORY_INTERFACE_ID = 670
val EQUIP_ITEM_SOUND = 2238

on_button(interfaceId = 387, component = 39) {
    when (player.getInteractingOpcode()) {
        61 -> openEquipmentBonuses(player, false)
    }
}

on_interface_close(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID) {
    player.closeInterface(interfaceId = INVENTORY_INTERFACE_ID)
    player.openInterface(dest = InterfaceDestination.INVENTORY_TAB)
    player.inventory.dirty = true
}

fun openEquipmentBonuses(
    player: Player,
    bank: Boolean,
) {
    player.queue {
        player.openInterface(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID, dest = InterfaceDestination.MAIN_SCREEN)
        player.setVarbit(4894, if (bank) 1 else 0)
        player.openInterface(INVENTORY_INTERFACE_ID, dest = InterfaceDestination.TAB_AREA)
        // player.setInterfaceEvents(interfaceId = INVENTORY_INTERFACE_ID, component = 0, from = 0, to = 27, 1538)
        // player.runClientScript(150, INVENTORY_INTERFACE_ID shl 16, 93, 0, 1, 2, 3)
        // player.setInterfaceEvents(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID, component = 7, from = 0, to = 15, 1538)
        player.refreshBonuses()
    }
}

on_button(interfaceId = EQUIPMENT_BONUS_INTERFACE_ID, component = 7) {
    handleOption(player)
}

on_button(interfaceId = INVENTORY_INTERFACE_ID, component = 0) {
    handleOption(player)
}

fun handleOption(player: Player) {
    val opcode = player.getInteractingOpcode()
    val item = player.getInteractingItemId()
    when (opcode) {
        61 -> TODO("Handle equipping/un-equipping here")
        20 -> showStats(player, item)
        25 -> world.sendExamine(player, item, ExamineEntityType.ITEM)
        else -> player.message("Unhandled Equipment Stats interface opcode: $opcode", type = ChatMessageType.CONSOLE)
    }
}

fun showStats(
    player: Player,
    item: Int,
) {
    val def = player.world.definitions.get(ItemDef::class.java, item)
    if (def.equipSlot != -1) {
        TODO("Figure out the proper way to get the 'Stats' section to show up.")
    } else {
        return
    }
}
