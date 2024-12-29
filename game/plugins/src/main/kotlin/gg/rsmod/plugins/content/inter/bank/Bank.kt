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
    companion object : KLogging() {
        const val DEPOSIT_BOX_INTERFACE_ID = 11
        const val BANK_INTERFACE_ID = 762
        const val BANK_HELP_INTERFACE_ID = 767
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
            var hasSeenItem = false
            for (index in player.bank.capacity - 1 downTo 0) {
                if (player.bank[index] == null) {
                    if (hasSeenItem) {
                        val tab = BankTabs.getCurrentTab(player, index)
                        if (tab != 0) {
                            BankTabs.updateTabSize(player, tab, delta = -1)
                        }
                    }
                } else {
                    hasSeenItem = true
                    player.bank[index]!!.removeAttr(ItemAttribute.BANK_TAB)
                }
            }

            player.bank.shift()
            BankTabs.shiftTabs(player)
        }

        fun depositInventory(player: Player) {
            player.inventory.forEachIndexed { index, item ->
                item?.let { deposit(player, player.inventory, index, item.amount) }
            }
        }

        fun depositEquipment(player: Player) {
            player.equipment.forEachIndexed { index, item ->
                item?.let {
                    if (deposit(player, player.equipment, index, item.amount)) {
                        EquipAction.onItemUnequip(player, item.id, index)
                    }
                }
            }
        }

        fun deposit(
            player: Player,
            from: ItemContainer,
            fromSlot: Int,
            amt: Int,
        ): Boolean {
            val to = player.bank
            val item = from[fromSlot] ?: return false
            val amount = from.getItemCount(item.id).coerceAtMost(amt)
            val currentTab = BankTabs.selectedTab(player)

            var deposited = 0
            var transferFailed = false
            var bankFull = false
            while (deposited < amount && !transferFailed && !bankFull) {
                val slot = determineDepositSlot(player, item)
                if (slot == -1) {
                    bankFull = true
                } else {
                    val isEmptySlot = to[slot] == null
                    val copy = Item(item, amount - deposited)
                    val transfer =
                        from.transfer(
                            to,
                            item = copy,
                            fromSlot = fromSlot,
                            toSlot = slot,
                            note = false,
                            unnote = true,
                        )
                    if (transfer == null) {
                        transferFailed = true
                    } else {
                        deposited += transfer.completed
                        if (currentTab > 0 && isEmptySlot) {
                            BankTabs.dropToTab(player, currentTab, slot)
                        }
                    }
                }
            }

            if (bankFull) {
                player.filterableMessage("Bank full.")
            }

            return !transferFailed && !bankFull
        }

        /**
         * Figures out the slot an item should be deposited to
         */
        private fun determineDepositSlot(
            player: Player,
            item: Item,
        ): Int {
            val placeholderSlot = player.bank.removePlaceholder(player.world, item)
            return if (placeholderSlot >= 0) {
                placeholderSlot
            } else {
                player.bank
                    .indexOfFirst {
                        it != null && it.id == item.id && it.amount < Int.MAX_VALUE
                    }.takeUnless { it == -1 } ?: player.bank.nextFreeSlot
            }
        }

        fun withdraw(
            player: Player,
            id: Int,
            amt: Int,
            fromSlot: Int,
        ) {
            val from = player.bank
            val to = player.inventory
            val tab = BankTabs.getCurrentTab(player, fromSlot)

            val amount = from.getItemCount(id).coerceAtMost(amt)
            val note = player.getVarp(WITHDRAW_AS_VARB) == 1

            val copy = Item(from[fromSlot]!!, amount)
            val withdrawn =
                from.transfer(to, item = copy, fromSlot = fromSlot, note = note, unnote = !note)?.completed ?: 0
            if (withdrawn > 0) {
                if (from[fromSlot] == null) {
                    player.bank.shift()

                    if (tab != 0) {
                        BankTabs.updateTabSize(player, tab, -1)

                        if (BankTabs.getTabSize(player, tab) == 0) {
                            BankTabs.shiftTabs(player)
                        }
                    }
                }
            }

            if (withdrawn == 0) {
                player.filterableMessage("You don't have enough inventory space.")
            } else if (withdrawn != amount) {
                player.filterableMessage("You don't have enough inventory space to withdraw that many.")
            }
        }

        fun open(player: Player) {
            player.openInterface(BANK_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
            player.openInterface(INV_INTERFACE_ID, InterfaceDestination.TAB_AREA)
            player.inventory.dirty = true
            player.bank.dirty = true
            player.setEvents(interfaceId = BANK_INTERFACE_ID, component = 93, to = 516, setting = 0x2804FE)
            player.setEvents(interfaceId = INV_INTERFACE_ID, to = 27, setting = 0x25047E)

            // TODO: re-enable equipment stats
            player.setComponentHidden(interfaceId = BANK_INTERFACE_ID, component = 117, hidden = true)
            player.setComponentHidden(interfaceId = BANK_INTERFACE_ID, component = 118, hidden = true)

            player.setVarp(WITHDRAW_AS_VARB, 0)
            if (player.getVarbit(4893) == 0) {
                player.setVarbit(4893, 1)
            }
            player.sendTempVarbit(190, 1) // resets search
        }

        fun openDepositBox(player: Player) {
            player.openInterface(DEPOSIT_BOX_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
            player.openInterface(INV_INTERFACE_ID, InterfaceDestination.TAB_AREA)
            player.runClientScript(
                150,
                11 shl 16 or 17,
                93,
                6,
                5,
                0,
                "Deposit-1",
                "Deposit-5",
                "Deposit-10",
                "Deposit-All",
                "Deposit-X",
            )
            player.setEvents(interfaceId = DEPOSIT_BOX_INTERFACE_ID, component = 17, to = 27, setting = 1150)
        }

        private fun ItemContainer.removePlaceholder(
            world: World,
            item: Item,
        ): Int {
            val def = item.toUnnoted(world.definitions).getDef(world.definitions)
            val slot =
                if (def.placeholderLink >
                    0
                ) {
                    indexOfFirst { it?.id == def.placeholderLink && it.amount == 0 }
                } else {
                    -1
                }
            if (slot != -1) {
                this[slot] = null
            }
            return slot
        }

        fun swap(
            player: Player,
            from: Int,
            to: Int,
        ) {
            player.bank.swap(from, to)
        }

        /**
         * Inserts an item from one slot in the bank to another slot, adjusting tab sizes when necessary
         */
        fun tabSafeInsert(
            player: Player,
            from: Int,
            to: Int,
        ) {
            val targetTab = BankTabs.getCurrentTab(player, to)
            val sourceTab = BankTabs.getCurrentTab(player, from)
            player.bank.insert(from, to)
            if (targetTab != 0) {
                BankTabs.updateTabSize(player, targetTab, 1)
            }

            if (sourceTab != 0) {
                BankTabs.updateTabSize(player, sourceTab, -1)
                if (BankTabs.getTabSize(player, sourceTab) == 0) {
                    BankTabs.shiftTabs(player)
                }
            }
        }

        fun ItemContainer.insert(
            from: Int,
            to: Int,
        ) {
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
