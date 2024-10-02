package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.api.cfg.Items

/**
 * Removing the legs from a swamp toad
 * Should get rid of the swamp toad and leave the legs in its place
 */

on_item_option(item = Items.SWAMP_TOAD, option = "Remove-legs") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.TOADS_LEGS)
        player.message("You pull the legs off the toad. Poor toad. At least they'll grow back.")
    }
}
