package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.attr.OTHER_SWAP_COMPONENT
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.BANK_INTERFACE_ID
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.BANK_MAINTAB_COMPONENT
import gg.rsmod.plugins.content.inter.bank.BankTabs.Companion.dropToTab

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
                BankTabs.collapseTab(player, tab)
            }

            /**
             * Handle viewing a tab
             */
            61 -> {
                BankTabs.viewTab(player, tab)
            }
        }
    }
}

/**
 * Moving items to tabs via the top tabs bar
 */
slots.forEach {
    on_component_to_component_item_swap(
        srcInterfaceId = BANK_INTERFACE_ID,
        srcComponent = BANK_MAINTAB_COMPONENT,
        dstInterfaceId = BANK_INTERFACE_ID,
        dstComponent = it,
    ) {
        val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
        val dstTab = getTabForSlot(player.attr[OTHER_SWAP_COMPONENT]!!)
        dropToTab(player, dstTab, srcSlot)
    }
}

fun getTabForSlot(slot: Int): Int {
    return if (slot >= 74) 8 - (82 - slot) else 9 - (slot - 44) / 2
}
