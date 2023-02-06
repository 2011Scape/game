package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_ITEM_SLOT_ATTR
import gg.rsmod.plugins.content.inter.bank.Bank.insert
import gg.rsmod.plugins.content.inter.bank.Bank.removePlaceholder
import gg.rsmod.plugins.content.inter.bank.BankTabs.BANK_TAB_ROOT_VARBIT
import gg.rsmod.plugins.content.inter.bank.BankTabs.SELECTED_TAB_VARBIT
import gg.rsmod.plugins.content.inter.bank.BankTabs.dropToTab
import gg.rsmod.plugins.content.inter.bank.BankTabs.numTabsUnlocked
import gg.rsmod.plugins.content.inter.bank.BankTabs.shiftTabs

on_interface_open(Bank.BANK_INTERFACE_ID) {
    var slotOffset = 0
    for (tab in 1..9) {
        val size = player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
        for (slot in slotOffset until slotOffset + size) { // from beginning slot of tab to end
            if (player.bank[slot] == null) {
                player.setVarbit(BANK_TAB_ROOT_VARBIT + tab, player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) - 1)
                // check for empty tab shift
                if (player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) == 0 && tab <= numTabsUnlocked(player))
                    shiftTabs(player, tab)
            }
        }
        slotOffset += size
    }
    player.bank.shift()
}

on_interface_close(Bank.BANK_INTERFACE_ID) {
    player.closeInterface(dest = InterfaceDestination.TAB_AREA)
    player.closeInputDialog()
}


on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 33) {
    val from = player.inventory
    val to = player.bank

    var any = false
    for (i in 0 until from.capacity) {
        val item = from[i] ?: continue

        val total = item.amount

        val curTab = player.getVarbit(SELECTED_TAB_VARBIT) - 1
        val placeholderSlot = to.removePlaceholder(world, item)
        val deposited = from.transfer(to, item, fromSlot = i, toSlot = placeholderSlot, note = false, unnote = true)?.completed ?: 0
        if (total != deposited) {
            // Was not able to deposit the whole stack of [item].
        }
        if (deposited > 0) {
            any = true
            if (curTab != 0) {
                dropToTab(player, curTab, to.nextFreeSlot - 1)
            }
        }
    }

    if (!any && !from.isEmpty) {
        player.message("Bank full.")
    }
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 35) {
    val from = player.equipment
    val to = player.bank

    var any = false
    for (i in 0 until from.capacity) {
        val item = from[i] ?: continue

        val total = item.amount

        val curTab = player.getVarbit(SELECTED_TAB_VARBIT) - 1
        val placeholderSlot = to.removePlaceholder(world, item)
        val deposited = from.transfer(to, item, fromSlot = i, toSlot = placeholderSlot, note = false, unnote = true)?.completed ?: 0
        if (total != deposited) {
            // Was not able to deposit the whole stack of [item].
        }
        if (deposited > 0) {
            any = true
            if (curTab != 0) {
                dropToTab(player, curTab, to.nextFreeSlot - 1)
            }
            EquipAction.onItemUnequip(player, item.id, i)
        }
    }

    if (!any && !from.isEmpty) {
        player.message("Bank full.")
    }
}

on_button(interfaceId = Bank.BANK_INTERFACE_ID, component = 17) {
    player.setVarp(190, 1)
    player.runClientScript(1472)
}

on_button(interfaceId = Bank.INV_INTERFACE_ID, component = Bank.INV_INTERFACE_CHILD) p@ {
    val slot = player.getInteractingSlot()
    val opcode = player.getInteractingOpcode()
    val item = player.inventory[slot] ?: return@p

    if (opcode == 25) {
        world.sendExamine(player, item.id, ExamineEntityType.ITEM)
        return@p
    }

    var amount: Int

    amount = when (opcode) {
            61 -> 1
            64 -> 5
            4 -> 10
            52 -> player.getVarbit(Bank.LAST_X_INPUT)
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
                player.setVarbit(Bank.LAST_X_INPUT, amount)
                Bank.deposit(player, item.id, amount)
            }
        }
        return@p
    }

    Bank.deposit(player, item.id, amount)
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

    amount = when (opcode) {
        61 -> 1
        64 -> 5
        4 -> 10
        52 -> player.getVarbit(Bank.LAST_X_INPUT)
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
                player.setVarbit(Bank.LAST_X_INPUT, amount)
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
        srcInterfaceId = Bank.INV_INTERFACE_ID, srcComponent = Bank.INV_INTERFACE_CHILD,
        dstInterfaceId = Bank.INV_INTERFACE_ID, dstComponent = Bank.INV_INTERFACE_CHILD) {
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
        srcInterfaceId = Bank.BANK_INTERFACE_ID, srcComponent = Bank.BANK_MAINTAB_COMPONENT,
        dstInterfaceId = Bank.BANK_INTERFACE_ID, dstComponent = Bank.BANK_MAINTAB_COMPONENT) {
    val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
    val dstSlot = player.attr[OTHER_ITEM_SLOT_ATTR]!!

    val container = player.bank

    if (srcSlot in 0 until container.occupiedSlotCount && dstSlot in 0 until container.occupiedSlotCount) {
        val insertMode = player.getVarp(Bank.REARRANGE_MODE_VARP) == 1
        if (!insertMode) {
            container.swap(srcSlot, dstSlot)
        } else {
            container.insert(srcSlot, dstSlot)
        }
    } else {
        // Sync the container on the client
        container.dirty = true
    }
}