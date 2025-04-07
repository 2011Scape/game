package gg.rsmod.plugins.content.items

on_item_option(item = Items.BUCKET_OF_WATER, option = "empty") {
    val itemSlot = player.getInteractingItemSlot()
    val itemHasBeenRemoved =
        player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    if (itemHasBeenRemoved) {
        player.inventory[itemSlot] = Item(Items.BUCKET)
        player.message("You empty the contents of the bucket on the floor.")
    }
}
