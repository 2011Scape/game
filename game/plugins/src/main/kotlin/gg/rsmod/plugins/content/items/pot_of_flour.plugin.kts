package gg.rsmod.plugins.content.items

/**
 * @author Ilwyd <https://github.com/William1Gray>
 */

/**
 * Emptying a flour pot.
 * Should empty the pot of flour, leaving the player with an empty pot.
 */
on_item_option(item = Items.POT_OF_FLOUR, option = "empty") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.EMPTY_POT)
    }
}
