package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.api.cfg.Items

/**
 * Emptying a vial of juju water.
 * Should empty the vial, leaving the player with an empty vial.
 */

on_item_option(item = Items.JUJU_VIAL_OF_WATER, option = "empty") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.JUJU_VIAL)
        player.message("You empty the vial.")
    }
}
