package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.attr.OTHER_SWAP_COMPONENT
import gg.rsmod.plugins.content.inter.bank.Bank.BANK_INTERFACE_ID
import gg.rsmod.plugins.content.inter.bank.Bank.BANK_MAINTAB_COMPONENT
import gg.rsmod.plugins.content.inter.bank.Bank.insert
import gg.rsmod.plugins.content.inter.bank.BankTabs.BANK_TAB_ROOT_VARBIT
import gg.rsmod.plugins.content.inter.bank.BankTabs.SELECTED_TAB_VARBIT
import gg.rsmod.plugins.content.inter.bank.BankTabs.dropToTab
import gg.rsmod.plugins.content.inter.bank.BankTabs.insertionPoint
import gg.rsmod.plugins.content.inter.bank.BankTabs.numTabsUnlocked
import gg.rsmod.plugins.content.inter.bank.BankTabs.shiftTabs
import gg.rsmod.plugins.content.inter.bank.BankTabs.startPoint

val slots = arrayOf(46, 48, 50, 52, 54, 56, 58, 60, 62)
val mainSlots = arrayOf(75, 74, 73, 72, 71, 70, 69)

/**
 * Handles setting the current selected tab varbit on tab selection
 */
slots.forEach {
    on_button(BANK_INTERFACE_ID, it) {
        val tab = getTabForSlot(it)
        when (player.getInteractingOpcode()) {

            /**
             * Handle collapsing a tab
             */
            64 -> {
                val container = player.bank
                val item = startPoint(player, tab)
                var end = insertionPoint(player, tab)
                while (item != end) {
                    container.insert(item, container.nextFreeSlot - 1)
                    end--
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + tab, player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) - 1)

                    println("debug $tab, ${numTabsUnlocked(player)}")
                    if (player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) == 0 && tab <= numTabsUnlocked(player)) {
                        shiftTabs(player, tab)
                    }
                }
            }

            /**
             * Handle viewing a tab
             */
            61 -> {
                if (tab <= numTabsUnlocked(player)) {
                    player.setVarbit(SELECTED_TAB_VARBIT, tab + 1)
                }
            }
        }
    }
}

/**
 * Moving items to tabs via the top tabs bar
 */
slots.forEach {
    on_component_to_component_item_swap(
        srcInterfaceId = BANK_INTERFACE_ID, srcComponent = BANK_MAINTAB_COMPONENT,
        dstInterfaceId = BANK_INTERFACE_ID, dstComponent = it
    ) {
        val component = getTabForSlot(player.attr[OTHER_SWAP_COMPONENT]!!)
        dropToTab(player, component)
    }
}

/**
 * Moving items within the "main" components
 */
mainSlots.forEach {
    on_component_to_component_item_swap(
        srcInterfaceId = BANK_INTERFACE_ID, srcComponent = BANK_MAINTAB_COMPONENT,
        dstInterfaceId = BANK_INTERFACE_ID, dstComponent = it
    ) {
        val component = getTabForSlot(player.attr[OTHER_SWAP_COMPONENT]!!)
        dropToTab(player, component)
    }
}

fun getTabForSlot(slot: Int): Int {
    return if (slot >= 74) 8 - (82 - slot) else 9 - (slot - 44) / 2
}