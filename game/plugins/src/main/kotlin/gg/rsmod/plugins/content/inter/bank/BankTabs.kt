package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.insert
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.swap
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.tabSafeInsert
import mu.KLogging

/**
 * @author bmyte <bmytescape@gmail.com>
 */
class BankTabs {
    companion object : KLogging() {
        const val SELECTED_TAB_VARBIT = 4893
        const val BANK_TAB_ROOT_VARBIT = 4884
        const val MAX_BANK_TABS = 8

        private fun isTabUnlocked(
            player: Player,
            tab: Int,
        ) = tab <= numTabsUnlocked(player)

        fun viewTab(
            player: Player,
            tab: Int,
        ) {
            if (isTabUnlocked(player, tab)) {
                player.setVarbit(SELECTED_TAB_VARBIT, tab + 1)
            }
        }

        fun collapseTab(
            player: Player,
            tab: Int,
        ) {
            if (isTabUnlocked(player, tab)) {
                val bank = player.bank
                val tabIndices = tabIndices(player, tab)
                val from = tabIndices.first
                val to = bank.nextFreeSlot - 1
                repeat(tabIndices.count()) { tabSafeInsert(player, from, to) }
                viewTab(player, 0)
            }
        }

        /**
         * Returns the range of slots that fall within this tab
         */
        private fun tabIndices(
            player: Player,
            tab: Int,
        ): IntRange {
            return if (tab == 0) {
                (1..MAX_BANK_TABS).sumOf { getTabSize(player, it) } until player.bank.capacity
            } else {
                val start = (1 until tab).sumOf { getTabSize(player, it) }
                val end = start + getTabSize(player, tab)
                start until end
            }
        }

        /**
         * Handles the dropping of items into the specified tab of the player's [Bank] from a source slot.
         */
        fun dropToTab(
            player: Player,
            dstTab: Int,
            srcSlot: Int,
        ) {
            val bank = player.bank
            val curTab = getCurrentTab(player, srcSlot)

            if (dstTab != curTab) {
                if (dstTab == 0) { // add to main tab
                    val to = (bank.nextFreeSlot - 1).takeUnless { it == -1 } ?: bank.capacity
                    bank.insert(srcSlot, to)
                    updateTabSize(player, curTab, -1)
                    if (getTabSize(player, curTab) == 0) {
                        shiftTabs(player)
                        viewTab(player, 0)
                    }
                } else if (getTabSize(player, dstTab) == 0) { // create a new tab
                    val to = tabIndices(player, 0).first
                    tabSafeInsert(player, srcSlot, to)
                    if (curTab != 0) {
                        swap(player, to, to - 1) // inserting put it in the second slot of the main tab
                    }
                    updateTabSize(player, dstTab, 1)
                    if (getTabSize(player, curTab) == 0) {
                        shiftTabs(player)
                    }
                } else { // add to existing tab
                    val to = tabIndices(player, dstTab).last
                    tabSafeInsert(player, srcSlot, to)
                    if (to < srcSlot) {
                        swap(player, to, to + 1) // inserting put it in the second-to-last slot of the target tab
                    }
                }
            }
        }

        /**
         * Determines the tab a given slot falls into based on
         * associative varbit analysis.
         *
         * @param player
         * The acting [Player] whose [Bank] tabs are to be checked
         *
         * @param slot
         * The associated slot for a given [Item] within the player's [Bank]
         *
         * @return -> Int
         * The tab which the specified [slot] resides
         */
        fun getCurrentTab(
            player: Player,
            slot: Int,
        ): Int {
            var current = 0
            for (tab in 1..MAX_BANK_TABS) {
                current += player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
                if (slot < current) {
                    return tab
                }
            }
            return 0
        }

        fun getTabSize(
            player: Player,
            tab: Int,
        ) = player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)

        /**
         * Tabulates the number of tabs the [player] is currently using
         * based off associative varbit analysis.
         *
         * @param player
         * The acting [Player] to get the number of in-use tabs for
         *
         * @return -> Int
         * The number of tabs the player has in-use/unlocked
         */
        fun numTabsUnlocked(player: Player): Int {
            var tabsUnlocked = 0
            for (tab in 1..MAX_BANK_TABS) {
                if (player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) > 0) {
                    tabsUnlocked++
                }
            }
            return tabsUnlocked
        }

        /**
         * Removes any empty tabs
         */
        fun shiftTabs(player: Player) {
            val tabSizes =
                (1..MAX_BANK_TABS)
                    .map { getTabSize(player, it) }
                    .dropLastWhile { it == 0 }

            for (tabIndex in tabSizes.indices.reversed()) {
                val tabSize = tabSizes[tabIndex]
                if (tabSize == 0) {
                    for (tab in (tabIndex until tabSizes.size - 1).map { it + 1 }) {
                        setTabSize(player, tab, getTabSize(player, tab + 1))
                    }
                    setTabSize(player, tabSizes.size, 0)
                }
            }
        }

        /**
         * Updates the size of a tab, by adding the passed delta argument
         */
        fun updateTabSize(
            player: Player,
            tab: Int,
            delta: Int,
        ): Boolean {
            if (tab == 0) {
                return true
            }
            val newValue = player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) + delta
            if (newValue < 0) {
                Bank.logger.error("Illegal new tab size of $newValue for tab [$tab] of player [${player.username}]")
                return false
            }
            player.setVarbit(BANK_TAB_ROOT_VARBIT + tab, newValue)
            return true
        }

        /**
         * Directly sets the size of a tab with the passed newValue argument
         */
        fun setTabSize(
            player: Player,
            tab: Int,
            newValue: Int,
        ) {
            player.setVarbit(BANK_TAB_ROOT_VARBIT + tab, newValue)
        }

        fun selectedTab(player: Player) = player.getVarbit(SELECTED_TAB_VARBIT) - 1
    }
}
