package gg.rsmod.plugins.content.mechanics.trading.impl

import getInterfaceHash
import gg.rsmod.game.model.container.ContainerStackType
import gg.rsmod.game.model.container.ItemContainer
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.mechanics.trading.TRADE_ACCEPTED_ATTR
import gg.rsmod.plugins.content.mechanics.trading.getTradeSession
import gg.rsmod.plugins.content.mechanics.trading.hasAcceptedTrade
import gg.rsmod.plugins.content.mechanics.trading.removeTradeSession
import gg.rsmod.util.Misc

/**
 * @author Triston Plummer ("Dread")
 *
 * Represents a trading session between two players
 *
 * @param player    The player this trade session belongs to
 * @param partner   The partner of this trade session
 */
class TradeSession(private val player: Player, private val partner: Player) {

    /**
     * A copy of this player's inventory, so we don't interfere with the player's real inventory unless necessary
     */
    val inventory = ItemContainer(player.inventory)

    /**
     * The trade container for this trade session, in the current player's context
     */
    val container = ItemContainer(player.world.definitions, player.inventory.capacity, ContainerStackType.NORMAL)


    /**
     * The current 'stage' of the trade session
     */
    private var stage : TradeStage = TradeStage.TRADE_SCREEN

    /**
     * An extension function for retrieving the value of each item in an [ItemContainer]]
     */
    private fun ItemContainer.getItemValues() : Array<Int> = rawItems.map { if (it == null) 0 else it.getDef(player.world.definitions).cost * it.amount }.toTypedArray()

    /**
     * An extension function for retrieving the sum of each item's value in an [ItemContainer]
     */
    private fun ItemContainer.getValue() = getItemValues().sum()

    /**
     * Opens the trade session, and configures the interfaces
     */
    fun open() {

        // Ensure the player isn't still marked as having accepted the trade
        player.attr[TRADE_ACCEPTED_ATTR] = false

        // Configure the trade text
        player.setComponentText(TRADE_INTERFACE, 15, "Trading with: ${Misc.formatforDisplay(partner.username)}")

        // Open the inventory overlay
        player.sendItemContainer(key = PLAYER_INVENTORY_KEY, container = inventory)
        player.openInterface(OVERLAY_INTERFACE, InterfaceDestination.TAB_AREA)

        // Open the trade screen interface
        player.openInterface(TRADE_INTERFACE, InterfaceDestination.MAIN_SCREEN)

        // Clears the "Waiting for other player..." string
        player.setComponentText(TRADE_INTERFACE, 37, "")
        partner.setComponentText(TRADE_INTERFACE, 37, "")

        // Initialise the trade containers
        initTradeContainers()
    }

    /**
     * Refreshes the item containers for both players
     */
    private fun refresh() {

        // Calculate the trade value
        val values = container.getItemValues()
        val containerValue = values.sum()
        val partnerValue = partner.getTradeSession()?.container?.getValue() ?: 0

        // Send the item containers
        player.sendItemContainer(PLAYER_INVENTORY_KEY, inventory)
        player.sendItemContainer(PLAYER_CONTAINER_KEY, container)

        // Send this player's container data to their partner
        partner.sendItemContainerOther(PLAYER_CONTAINER_KEY, container)
        partner.setComponentText(TRADE_INTERFACE, 21, "has ${inventory.freeSlotCount} free inventory slots.")

        player.setVarc(PLAYER_WEALTH_VARC, containerValue)
        player.setVarc(PARTNER_WEALTH_VARC, partnerValue)

        partner.setVarc(PLAYER_WEALTH_VARC, partnerValue)
        partner.setVarc(PARTNER_WEALTH_VARC, containerValue)

      }

    /**
     * Initialises the trade containers and enables the item container components for the player
     */
    private fun initTradeContainers() {
        player.setInterfaceEvents(interfaceId = TRADE_INTERFACE, component = PLAYER_TRADE_CHILD, range = 0..container.capacity, setting = 1278)
        player.setInterfaceEvents(interfaceId = TRADE_INTERFACE, component = PARTNER_TRADE_CHILD, range = 0..container.capacity, setting = 1278)
        player.setInterfaceEvents(interfaceId = OVERLAY_INTERFACE, component = 0, range = 0..container.capacity, setting = 1086)
        player.setInterfaceEvents(interfaceId = TRADE_INTERFACE, component = 0, range = 0..container.capacity, setting = 2360446)

        player.runClientScript(INTERFACE_INV_INIT_BIG, OVERLAY_INTERFACE.getInterfaceHash(), PLAYER_INVENTORY_KEY, 4, 7, 0, -1, "Offer", "Offer-5", "Offer-10", "Offer-All", "Offer-X")
        player.runClientScript(INTERFACE_INV_INIT_BIG, TRADE_INTERFACE.getInterfaceHash(30), PLAYER_CONTAINER_KEY, 4, 7, 0, -1, "Remove", "Remove-5", "Remove-10", "Remove-All", "Remove-X")
        player.runClientScript(695, TRADE_INTERFACE.getInterfaceHash(32), PLAYER_CONTAINER_KEY, 4, 7, 0, -1, "", "", "", "", "")
        refresh()
    }

    /**
     * Declines the trade session for both players
     */
    fun decline(forced: Boolean = false) {
        if (partner.getTradeSession() != null) {

            // Remove the trade sessions from both players
            player.removeTradeSession()
            partner.removeTradeSession()

            // Inform the player that they've declined the trade, and close the trade window
            if (!forced) player.message("You declined the trade")
            player.closeInterface(InterfaceDestination.MAIN_SCREEN)
            player.closeInterface(OVERLAY_INTERFACE)

            // Inform the partner that the player has declined the trade, and close their window
            if (!forced) partner.message(TRADE_DECLINED_MESSAGE)
            partner.closeInterface(InterfaceDestination.MAIN_SCREEN)
            partner.closeInterface(OVERLAY_INTERFACE)
            player.inventory.dirty = true
            partner.inventory.dirty = true
        }
    }

    /**
     * Offers an item to this [Player]'s trade [ItemContainer]
     *
     * @param slot      The slot in the temporary inventory
     * @param amount    The amount to offer in trade
     */
    fun offer(slot: Int, amount: Int) {
        if (stage != TradeStage.TRADE_SCREEN) return

        val item = inventory[slot]?: return
        val count = Math.min(amount, inventory.getItemCount(item.id))

        val transaction = inventory.remove(item.id, count, assureFullRemoval = true, beginSlot = slot)
        if (transaction.hasSucceeded())
            container.add(item.id, count)

        refresh()
        progress(false)
    }

    /**
     * Removes an item from this [Player]'s trade [ItemContainer]
     *
     * @param slot      The slot in the trade container
     * @param amount    The amount to remove from the trade container
     */
    fun remove(slot: Int, amount: Int) {
        if (stage != TradeStage.TRADE_SCREEN) return

        val item = container[slot] ?: return
        val count = Math.min(amount, container.getItemCount(item.id))

        val transaction = container.remove(item.id, count, assureFullRemoval = true)
        if (transaction.hasSucceeded()) {
            inventory.add(item.id, count)

            // Loop over the remove items
            transaction.items.forEach {
                player.runClientScript(TRADE_MODIFIED_SCRIPT, TRADE_INTERFACE.getInterfaceHash(31), 4, 7, it.slot)
                partner.runClientScript(TRADE_MODIFIED_SCRIPT, TRADE_INTERFACE.getInterfaceHash(33), 4, 7, it.slot)
            }

        }

        refresh()
        progress(false)
    }

    /**
     * Progresses this [TradeSession] instance. If both players accept the trade, it will either
     * progress to the accept screen, or complete the trade and give each player the traded items.
     *
     * @param accepted  If the player accepted this trade session
     */
    fun progress(accepted: Boolean = true) {
        player.attr[TRADE_ACCEPTED_ATTR] = accepted

        // If the current trade session is on the trade screen
        if (stage == TradeStage.TRADE_SCREEN) {

            // If the player revoked their acceptation of the trade offer
            if (!player.hasAcceptedTrade()) {

                // Set the partner's option to revoked also
                partner.attr[TRADE_ACCEPTED_ATTR] =  false

                // Reset the component text
                player.setComponentText(TRADE_INTERFACE, 37, "")
                partner.setComponentText(TRADE_INTERFACE, 37, "")
                return
            }

            // If the other player has not accepted, send the confirmation text
            if (player.hasAcceptedTrade() && !partner.hasAcceptedTrade()) {
                player.setComponentText(TRADE_INTERFACE, 37, "Waiting for other player...")
                partner.setComponentText(TRADE_INTERFACE, 37, "Other player has accepted.")
            } else if (player.hasAcceptedTrade() && partner.hasAcceptedTrade()) {

                // Open the accept screen
                openAcceptScreen()
                partner.getTradeSession()?.openAcceptScreen()
            }
        }

        // If the current trade session is on the progress screen
        if (stage == TradeStage.ACCEPT_SCREEN) {

            // If the player revoked their acceptation of the trade offer
            if (!player.hasAcceptedTrade()) {

                // Set the partner's option to revoked also
                partner.attr[TRADE_ACCEPTED_ATTR] =  false

                // Reset the component text
                player.setComponentText(ACCEPT_INTERFACE, 34, "Are you sure you want to make this trade?")
                partner.setComponentText(ACCEPT_INTERFACE, 34, "Are you sure you want to make this trade?")

                player.setVarcString(203, Misc.formatforDisplay(partner.username).toString())
                partner.setVarcString(203, Misc.formatforDisplay(player.username).toString())
                return
            }

            // If the other player has not accepted, send the confirmation text
            if (player.hasAcceptedTrade() && !partner.hasAcceptedTrade()) {
                player.setComponentText(ACCEPT_INTERFACE, 34, "Waiting for other player...")
                partner.setComponentText(ACCEPT_INTERFACE, 34, "Other player has accepted.")
            } else if (player.hasAcceptedTrade() && partner.hasAcceptedTrade()) {

                // Complete the trade
                complete()
            }
        }
    }

    /**
     * Opens the accept screen for each player
     */
    private fun openAcceptScreen() {

        // If we don't have enough inventory space for the partner's container
        if (player.inventory.freeSlotCount < partner.getTradeSession()!!.container.occupiedSlotCount) {
            player.message("You don't have enough inventory space for this trade.")
            partner.message("Other player doesn't have enough inventory space for this trade.")
            decline(forced = true)
            return
        }

        // Set the trade stage
        stage = TradeStage.ACCEPT_SCREEN

        container.shift()
        // Send the item containers
        player.sendItemContainer(ACCEPT_CONTAINER_KEY, container)
        partner.getTradeSession()?.let { player.sendItemContainerOther(ACCEPT_CONTAINER_KEY, it.container) }

        // Open the accept screen interface
        player.openInterface(ACCEPT_INTERFACE, InterfaceDestination.MAIN_SCREEN)

        // Reset the accept state
        player.attr[TRADE_ACCEPTED_ATTR] = false
    }

    /**
     * Completes the trade session, which swaps the player's trade containers, and
     * sets their inventory to the temporary one operated on during the trade.
     */
    private fun complete() {
        if (stage != TradeStage.ACCEPT_SCREEN) return
        stage = TradeStage.COMPLETED

        // Assign the trade containers for this player
        val playerInv = player.inventory
        inventory.forEachIndexed { index, item -> playerInv[index] = item }
        partner.getTradeSession()?.container?.filterNotNull()?.forEach { playerInv.add(it) }

        // Assign the trade containers for the partner
        val partnerInv = partner.inventory
        partner.getTradeSession()?.inventory?.forEachIndexed { index, item -> partnerInv[index] = item }
        container.filterNotNull().forEach { partnerInv.add(it) }

        // Finalise the trade session
        finalise(player)
        finalise(partner)
    }

    /**
     * Finalises the trade session by clearing the item containers, removing
     * the session attribute, and closing the trade screen interface
     *
     * @param player    The player to finalise the trade session for
     */
    private fun finalise(player: Player) {

        // Clear the containers
        container.removeAll()
        inventory.removeAll()

        // Remove the trade session
        player.removeTradeSession()

        // Close the trade interface
        player.closeInterface(InterfaceDestination.MAIN_SCREEN)
        player.closeInterface(OVERLAY_INTERFACE)

        // Inform the player that the trade has been accepted
        player.message("Accepted trade.")
    }

    companion object {
        /**
         * The inventory overlay interface
         */
        const val OVERLAY_INTERFACE = 336

        /**
         * The primary trade screen interface
         */
        const val TRADE_INTERFACE = 335

        /**
         * The child id of this player's trade offer
         */
        const val PLAYER_TRADE_CHILD = 30

        /**
         * The child id of the partner's trade offer
         */
        private const val PARTNER_TRADE_CHILD = 32

        /**
         * The progress trade interface
         */
        const val ACCEPT_INTERFACE = 334

        /**
         * The message that is shown to the partner when a player declined a trade
         */
        const val TRADE_DECLINED_MESSAGE = "Other player declined trade."

        /**
         * The container key for the trade accept screen
         */
        const val ACCEPT_CONTAINER_KEY = 90

        /**
         * The container key for the player's inventory overlay
         */
        const val PLAYER_INVENTORY_KEY = 93

        /**
         * The container key for this player's trade offer
         */
        const val PLAYER_CONTAINER_KEY = 90

        /**
         * The id of the ClientScript used to display the red exclamation marks when
         * an item has been removed from the trade container
         */
        const val TRADE_MODIFIED_SCRIPT = 143

        const val PLAYER_WEALTH_VARC = 729

        const val PARTNER_WEALTH_VARC = 697
    }
}