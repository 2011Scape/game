package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR

on_interface_open(Bank.BANK_INTERFACE_ID) {
    Bank.cleanEmptySlots(player)
}

on_interface_close(Bank.BANK_INTERFACE_ID) {
    player.closeInterface(dest = InterfaceDestination.TAB_AREA)
    player.closeInputDialog()
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 44) {
    player.openInterface(Bank.BANK_HELP_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)

    player.setComponentHidden(Bank.BANK_HELP_INTERFACE_ID, 54, false)
}

on_button(interfaceId = Bank.BANK_HELP_INTERFACE_ID, component = 54) {
    Bank.open(player)
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 33) {
    Bank.depositInventory(player)
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 35) {
    Bank.depositEquipment(player)
}

on_button(interfaceId = Bank.DEPOSIT_BOX_INTERFACE_ID, component = 18) {
    Bank.depositInventory(player)
}

on_button(interfaceId = Bank.DEPOSIT_BOX_INTERFACE_ID, component = 20) {
    Bank.depositEquipment(player)
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 17) {
    player.setVarp(190, 1)
    player.runClientScript(1472)
}

on_button(interfaceId = Bank.DEPOSIT_BOX_INTERFACE_ID, component = 17) p@{
    val slot = player.getInteractingSlot()
    val opcode = player.getInteractingOpcode()
    val item = player.inventory[slot] ?: return@p

    if (opcode == 25) {
        world.sendExamine(player, item.id, ExamineEntityType.ITEM)
        return@p
    }

    var amount =
        when (opcode) {
            61 -> 1
            64 -> 5
            4 -> 10
            52 -> player.getVarp(Bank.LAST_X_INPUT)
            81 -> -1 // X
            91 -> 0 // All
            else -> return@p
        }

    if (amount == 0) {
        amount = player.inventory.getItemCount(item.id)
    } else if (amount == -1) {
        player.queue(TaskPriority.WEAK) {
            amount = inputInt("How many would you like to bank?")
            if (amount > 0) {
                player.setVarp(Bank.LAST_X_INPUT, amount)
                Bank.deposit(player, player.inventory, slot, amount)
            }
        }
        return@p
    }

    Bank.deposit(player, player.inventory, slot, amount)
}

on_button(interfaceId = Bank.INV_INTERFACE_ID, component = Bank.INV_INTERFACE_CHILD) p@{
    val slot = player.getInteractingSlot()
    val opcode = player.getInteractingOpcode()
    val item = player.inventory[slot] ?: return@p

    if (opcode == 25) {
        world.sendExamine(player, item.id, ExamineEntityType.ITEM)
        return@p
    }

    var amount =
        when (opcode) {
            61 -> 1
            64 -> 5
            4 -> 10
            52 -> player.getVarp(Bank.LAST_X_INPUT)
            81 -> -1 // X
            91 -> 0 // All
            else -> return@p
        }

    if (amount == 0) {
        amount = player.inventory.getItemCount(item.id)
    } else if (amount == -1) {
        player.queue(TaskPriority.WEAK) {
            amount = inputInt("How many would you like to bank?")
            if (amount > 0) {
                player.setVarp(Bank.LAST_X_INPUT, amount)
                Bank.deposit(player, player.inventory, slot, amount)
            }
        }
        return@p
    }

    Bank.deposit(player, player.inventory, slot, amount)
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 93) p@{
    val slot = player.getInteractingSlot()
    val opcode = player.getInteractingOpcode()

    val item = player.bank[slot] ?: return@p

    if (opcode == 25) {
        world.sendExamine(player, item.id, ExamineEntityType.ITEM)
        return@p
    }

    var amount: Int

    amount =
        when (opcode) {
            61 -> 1
            64 -> 5
            4 -> 10
            52 -> player.getVarp(Bank.LAST_X_INPUT)
            81 -> -1 // X
            91 -> item.amount
            18 -> item.amount - 1
            else -> return@p
        }

    if (amount == -1) {
        /**
         * Placeholders' "release" option uses the same option
         * as "withdraw-x" would.
         */
        if (item.amount == 0) {
            player.bank[slot] = null
            return@p
        }
        player.queue(TaskPriority.WEAK) {
            amount = inputInt("How many would you like to withdraw?")
            if (amount > 0) {
                player.setVarp(Bank.LAST_X_INPUT, amount)
                Bank.withdraw(player, item.id, amount, slot)
            }
        }
        return@p
    }

    amount = Math.max(0, amount)
    if (amount > 0) {
        Bank.withdraw(player, item.id, amount, slot)
    }
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 15) {
    player.toggleVarp(Bank.REARRANGE_MODE_VARP)
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 19) {
    player.toggleVarp(Bank.WITHDRAW_AS_VARB)
}

/**
 * Swap items in bank inventory interface.
 */
on_component_to_component_item_swap(
    srcInterfaceId = Bank.INV_INTERFACE_ID,
    srcComponent = Bank.INV_INTERFACE_CHILD,
    dstInterfaceId = Bank.INV_INTERFACE_ID,
    dstComponent = Bank.INV_INTERFACE_CHILD,
) {
    val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    val dstSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    val container = player.inventory

    if (srcSlot in 0 until container.capacity && dstSlot in 0 until container.capacity) {
        container.swap(srcSlot, dstSlot)
    }
    container.dirty = true
}

/**
 * Swap items in main bank tab.
 */
on_component_to_component_item_swap(
    srcInterfaceId = Bank.BANK_INTERFACE_ID,
    srcComponent = Bank.BANK_MAINTAB_COMPONENT,
    dstInterfaceId = Bank.BANK_INTERFACE_ID,
    dstComponent = Bank.BANK_MAINTAB_COMPONENT,
) {
    val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    val dstSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    val container = player.bank

    if (srcSlot in 0 until container.occupiedSlotCount && dstSlot in 0 until container.occupiedSlotCount) {
        val insertMode = player.getVarp(Bank.REARRANGE_MODE_VARP) == 1
        if (!insertMode) {
            Bank.swap(player, srcSlot, dstSlot)
        } else {
            Bank.tabSafeInsert(player, srcSlot, dstSlot)
        }
    } else {
        // Sync the container on the client
        container.dirty = true
    }
}
