package gg.rsmod.plugins.content.inter.bank

import gg.rsmod.game.model.attr.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.item.ItemAttribute
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.content.inter.bank.Bank.Companion.insert
import mu.KLogging

/**
 * @author bmyte <bmytescape@gmail.com>
 */
class BankTabs {
    companion object: KLogging() {
        const val SELECTED_TAB_VARBIT = 4893
        const val BANK_TAB_ROOT_VARBIT = 4884
        const val MAX_BANK_TABS = 8

        private fun isTabUnlocked(player: Player, tab: Int) = numTabsUnlocked(player) > tab

        fun viewTab(player: Player, tab: Int) {
            if (isTabUnlocked(player, tab)) {
                player.setVarbit(SELECTED_TAB_VARBIT, tab + 1)
            }
        }

        fun collapseTab(player: Player, tab: Int) {
            if (isTabUnlocked(player, tab)) {
                Bank.cleanEmptySlots(player)
                val bank = player.bank
                val item = startPoint(player, tab)
                var end = insertionPoint(player, tab)
                while (item != end) {
                    insert(player, item, bank.nextFreeSlot - 1)
                    end--
                    updateTabSize(player, tab, -1)
                }

                shiftTabs(player)
                viewTab(player, 0)
            }
        }

        /**
         * Handles the dropping of items into the specified tab of the player's [Bank].
         *
         * @param player
         * The acting [Player] for which the [INTERACTING_ITEM_SLOT] [Item] should
         * be dropped into the specified [dstTab]
         *
         * @param dstTab
         * The bank tab number for which the [INTERACTING_ITEM_SLOT] [Item] is
         * to be dropped into.
         */
        fun dropToTab(player: Player, dstTab: Int) {
            val container = player.bank
            val srcSlot = player.attr[INTERACTING_ITEM_SLOT]!!
            val item = player.bank[srcSlot]!!
            val curTab = getCurrentTab(player, srcSlot)

            if (dstTab == curTab) return

            when {
                dstTab == 0 -> {
                    container.insert(srcSlot, container.nextFreeSlot - 1)
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + curTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) - 1)
                    if (player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) == 0 && curTab <= numTabsUnlocked(player))
                        shiftTabs(player)
                    item.attr[ItemAttribute.BANK_TAB] = 0
                }
                dstTab < curTab || curTab == 0 -> {
                    container.insert(srcSlot, insertionPoint(player, dstTab))
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + dstTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + dstTab) + 1)
                    item.attr[ItemAttribute.BANK_TAB] = BANK_TAB_ROOT_VARBIT + dstTab
                }
                else -> {
                    container.insert(srcSlot, insertionPoint(player, dstTab) - 1)
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + dstTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + dstTab) + 1)
                    item.attr[ItemAttribute.BANK_TAB] = BANK_TAB_ROOT_VARBIT + dstTab
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + curTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) - 1)
                    if (player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) == 0 && curTab <= numTabsUnlocked(player))
                        shiftTabs(player)
                }
            }
        }


        /**
         * Handles the dropping of items into the specified tab of the player's [Bank] from a source slot.
         */
        fun dropToTab(player: Player, dstTab: Int, srcSlot: Int) {
            val container = player.bank
            val curTab = getCurrentTab(player, srcSlot)

            if (dstTab == curTab) {
                return
            } else {
                if (dstTab == 0) { //add to main tab don't insert
                    container.insert(srcSlot, container.nextFreeSlot - 1)
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + curTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) - 1)
                    // check for empty tab shift
                    if (player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) == 0 && curTab <= numTabsUnlocked(player))
                        shiftTabs(player)
                } else {
                    if (dstTab < curTab || curTab == 0)
                        container.insert(srcSlot, insertionPoint(player, dstTab))
                    else
                        container.insert(srcSlot, insertionPoint(player, dstTab) - 1)
                    player.setVarbit(BANK_TAB_ROOT_VARBIT + dstTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + dstTab) + 1)
                    if (curTab != 0) {
                        player.setVarbit(BANK_TAB_ROOT_VARBIT + curTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) - 1)
                        // check for empty tab shift
                        if (player.getVarbit(BANK_TAB_ROOT_VARBIT + curTab) == 0 && curTab <= numTabsUnlocked(player))
                            shiftTabs(player)
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
        fun getCurrentTab(player: Player, slot: Int): Int {
            var current = 0
            for (tab in 1..9) {
                current += player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
                if (slot < current) {
                    return tab
                }
            }
            return 0
        }

        fun getTabSize(player: Player, tab: Int) = player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)

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
            for (tab in 1..9)
                if (player.getVarbit(BANK_TAB_ROOT_VARBIT + tab) > 0)
                    tabsUnlocked++
            return tabsUnlocked
        }

        /**
         * Determines the insertion point for an item being added to
         * a tab based on the tab order and number of items in it and
         * previous tabs.
         *
         * @param player
         * The acting [Player] to find the insertion point for placing
         * an [Item] in the bank tab specified by [tabIndex]
         *
         * @param tabIndex
         * The tab for which the insertion point is desired
         *
         * @return -> Int
         * The insertion index for inserting into the desired tab
         */
        fun insertionPoint(player: Player, tabIndex: Int = 0): Int {
            if (tabIndex == 0)
                return player.bank.nextFreeSlot
            var prevDex = 0
            var dex = 0
            for (tab in 1..tabIndex) {
                prevDex = dex
                dex += player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
            }

            // truncate empty spots, but stay in current tab
            while (dex != 0 && player.bank[dex - 1] == null && dex > prevDex) {
                dex--
            }
            return dex
        }

        /**
         * Determines the beginning index for a specified bank tab
         * based on the tab order and number of items in previous tabs.
         *
         * @param player
         * The acting [Player] to find the start point for the bank tab
         * specified by [tabIndex]
         *
         * @param tabIndex
         * The tab for which the start point is desired
         *
         * @return -> Int
         * The start index for the beginning of the desired tab
         */
        fun startPoint(player: Player, tabIndex: Int = 0): Int {
            var dex = 0
            if (tabIndex == 0) {
                for (tab in 1..9)
                    dex += player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
            } else {
                for (tab in 1 until tabIndex)
                    dex += player.getVarbit(BANK_TAB_ROOT_VARBIT + tab)
            }
            return dex
        }

        fun shiftTabs(player: Player) {
            val tabSizes = (1..MAX_BANK_TABS)
                    .map { player.getVarbit(BANK_TAB_ROOT_VARBIT + it) }
                    .dropLastWhile { it == 0 }

            for (tabIndex in tabSizes.indices.reversed()) {
                val tabSize = tabSizes[tabIndex]
                if (tabSize == 0) {
                    for (tab in tabIndex .. tabSizes.size) {
                        player.setVarbit(BANK_TAB_ROOT_VARBIT + tab + 1, player.getVarbit(BANK_TAB_ROOT_VARBIT + tab + 2))
                    }
                }
            }
        }

        fun insert(player: Player, from: Int, to: Int) {
            val targetTab = getCurrentTab(player, to)
            val sourceTab = getCurrentTab(player, from)
            player.bank.insert(from, to)
            if (targetTab != 0) {
                player.setVarbit(BANK_TAB_ROOT_VARBIT + targetTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + targetTab) + 1)
            }

            if (sourceTab != 0) {
                player.setVarbit(BANK_TAB_ROOT_VARBIT + sourceTab, player.getVarbit(BANK_TAB_ROOT_VARBIT + sourceTab) - 1)
                if (player.getVarbit(BANK_TAB_ROOT_VARBIT + sourceTab) == 0) {
                    shiftTabs(player)
                }
            }
        }

        fun updateTabSize(player: Player, tab: Int, delta: Int): Boolean {
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

        fun selectedTab(player: Player) = player.getVarbit(SELECTED_TAB_VARBIT) - 1
    }
}