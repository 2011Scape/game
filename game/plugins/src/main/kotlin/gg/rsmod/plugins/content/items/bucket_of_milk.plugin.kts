package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.api.cfg.Items

/**
 * Emptying a flour pot.
 * Should empty the bucket of milk, leaving the player with an empty pot.
 */

on_item_option(item = Items.BUCKET_OF_MILK, option = "empty") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.BUCKET)
    }
}
