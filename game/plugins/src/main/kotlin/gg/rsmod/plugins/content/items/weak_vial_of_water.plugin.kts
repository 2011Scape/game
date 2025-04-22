package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.api.cfg.Items

/**
 * Emptying a vial of water.
 * Should empty the vial, leaving the player with an empty vial.
 */

on_item_option(item = Items.VIAL_OF_WATER_17492, option = "empty") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.VIAL_17490)
        player.message("You empty the vial.")
    }
}
