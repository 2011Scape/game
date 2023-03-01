package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.action.EquipAction
import gg.rsmod.game.model.World
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.item.ItemAttribute
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.ext.*
import mu.KLogging

/**
 * @author Tom <rspsmods@gmail.com>
 */
class Bank {
    companion object: KLogging() {
        const val TAB_COUNT = 9

        const val BANK_INTERFACE_ID = 762
        const val BANK_MAINTAB_COMPONENT = 93
        const val INV_INTERFACE_ID = 763
        const val INV_INTERFACE_CHILD = 0

        const val REARRANGE_MODE_VARP = 304
        const val WITHDRAW_AS_VARB = 115
        const val LAST_X_INPUT = 1249

        /**
         * Clean any empty slots in the bank. Shift tabs as needed.
         */
        fun cleanEmptySlots(player: Player) {
            val indicesToCheck = (0 until player.bank.capacity).toList().dropLastWhile { player.bank[it] == null }
            for (index in indicesToCheck) {
                if (player.bank[index] == null) {
                    val tab = BankTabs.getCurrentTab(player, index)
                    if (tab != 0) {
                        BankTabs.updateTabSize(player, tab, delta = -1)
                    }
                }
            }

            player.bank.shift()
            BankTabs.shiftTabs(player)
        }

        fun depositInventory(p: Player) {
            p.inventory.forEachIndexed { index, item ->
                item?.let { deposit(p, p.inventory, index, item.amount) }
            }
        }

        fun depositEquipment(p: Player) {
            p.equipment.forEachIndexed { index, item ->
                item?.let {
                    if (deposit(p, p.equipment, index, item.amount)) {
                        EquipAction.onItemUnequip(p, item.id, index)
                    }
                }
            }
        }

        fun deposit(p: Player, from: ItemContainer, fromSlot: Int, amt: Int): Boolean {
            val to = p.bank
            val item = from[fromSlot] ?: return false
            val amount = item.amount.coerceAtMost(amt)
            val currentTab = BankTabs.selectedTab(p)

            var deposited = 0
            var transferFailed = false
            var bankFull = false
            while (deposited < amount && !transferFailed && !bankFull) {
                val slot = determineDepositSlot(p, item)
                if (slot == -1) {
                    bankFull = true
                } else {
                    val isEmptySlot = to[slot] == null
                    val copy = Item(item, amount - deposited)
                    val transfer = from.transfer(to, item = copy, fromSlot = fromSlot, toSlot = slot, note = false, unnote = true)
                    if (transfer == null) {
                        transferFailed = true
                    } else {
                        deposited += transfer.completed
                        if (currentTab > 0 && isEmptySlot) {
                            BankTabs.dropToTab(p, currentTab, slot)
                        }
                    }
                }
            }

            if (bankFull) {
                p.filterableMessage("Bank full.")
            }

            return !transferFailed && !bankFull
        }

        private fun determineDepositSlot(p: Player, item: Item): Int {
            val placeholderSlot = p.bank.removePlaceholder(p.world, item)
            return if (placeholderSlot >= 0) {
                placeholderSlot
            } else {
                p.bank.indexOfFirst {
                    it != null && it.id == item.id && it.amount < Int.MAX_VALUE
                }.takeUnless { it == -1 } ?: p.bank.nextFreeSlot
            }
        }

        fun withdraw(p: Player, id: Int, amt: Int, fromSlot: Int) {
            val from = p.bank
            val to = p.inventory
            val toSlot = p.inventory.indexOfFirst { it != null && it.id == id }

            val amount = from.getItemCount(id).coerceAtMost(amt)
            val note = p.getVarp(WITHDRAW_AS_VARB) == 1

            val copy = Item(from[fromSlot]!!, amount)
            copy.removeAttr(ItemAttribute.BANK_TAB)
            val withdrawn = from.transfer(to, item = copy, fromSlot = fromSlot, toSlot = toSlot, note = note, unnote = !note)?.completed ?: 0
            if (withdrawn > 0) {
                val tab = BankTabs.getCurrentTab(p, fromSlot)
                if (from[fromSlot] == null && tab != 0) {
                    BankTabs.updateTabSize(p, tab, -1)
                    if (BankTabs.getTabSize(p, tab) == 0) {
                        cleanEmptySlots(p)
                        BankTabs.viewTab(p, 0)
                    }
                }
            }

            if (withdrawn == 0) {
                p.filterableMessage("You don't have enough inventory space.")
            } else if (withdrawn != amount) {
                p.filterableMessage("You don't have enough inventory space to withdraw that many.")
            }
        }

        fun open(p: Player) {
            p.openInterface(BANK_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
            p.openInterface(INV_INTERFACE_ID, InterfaceDestination.TAB_AREA)
            p.inventory.dirty = true
            p.bank.dirty = true
            p.setInterfaceEvents(interfaceId = BANK_INTERFACE_ID, component = 93, from = 0, to = 516, setting = 0x2804FE)
            p.setInterfaceEvents(interfaceId = INV_INTERFACE_ID, component = 0, from = 0, to = 27, setting = 0x25047E)
            p.setVarp(WITHDRAW_AS_VARB, 0)
            if(p.getVarbit(4893) == 0) {
                p.setVarbit(4893, 1)
            }
            p.sendTempVarbit(190, 1) // resets search
        }

        fun ItemContainer.removePlaceholder(world: World, item: Item): Int {
            val def = item.toUnnoted(world.definitions).getDef(world.definitions)
            val slot = if (def.placeholderLink > 0) indexOfFirst { it?.id == def.placeholderLink && it.amount == 0 } else -1
            if (slot != -1) {
                this[slot] = null
            }
            return slot
        }

        fun ItemContainer.insert(from: Int, to: Int) {
            val fromItem = this[from]!! // Shouldn't be null

            this[from] = null

            if (from < to) {
                for (i in from until to) {
                    this[i] = this[i + 1]
                }
            } else {
                for (i in from downTo to + 1) {
                    this[i] = this[i - 1]
                }
            }
            this[to] = fromItem
        }
    }
}