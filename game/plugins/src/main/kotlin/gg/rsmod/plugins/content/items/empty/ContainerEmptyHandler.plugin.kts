package gg.rsmod.plugins.content.items.empty

data class Container(
    val item: Int,
    val emptyItem: Int,
    val emptyMessage: String
)

val containerHandler = ContainerInteractionHandler()

val containers = listOf(
    Container(Items.VIAL_OF_WATER, Items.VIAL, "You empty the vial of water."),
    Container(Items.BUCKET_OF_WATER, Items.BUCKET, "You empty the bucket of water."),
    Container(Items.JUG_OF_WATER, Items.EMPTY_JUG, "You empty the jug of water."),
    Container(Items.VIAL_OF_WATER_17492, Items.VIAL_17490, "You empty the dung of water."),
    Container(Items.JUJU_VIAL_OF_WATER, Items.JUJU_VIAL, "You empty the juju vial of water."),
    Container(Items.CUP_OF_WATER, Items.EMPTY_CUP, "You empty the cup of water."),
    Container(Items.FISHBOWL_6668, Items.FISHBOWL, "You empty the fish bow of water."),
    Container(Items.GOLDEN_BOWL_724, Items.GOLD_BOWL, "You empty the golden bowl of water."),
    Container(Items.GOLDEN_BOWL_725, Items.BLESSED_GOLD_BOWL, "You empty the golden bowl of water.")
    // Add more containers if needed
)

containers.forEach { containerHandler.emptyContainer(it) }

class ContainerInteractionHandler {
    fun emptyContainer(container: Container) {
        on_item_option(item = container.item, option = "empty") {
            val itemSlot = player.getInteractingItemSlot()
            val itemHasBeenRemoved = player.inventory.remove(player.getInteractingItem(), beginSlot = player.getInteractingItemSlot()).hasSucceeded()
            if(itemHasBeenRemoved) {
                player.inventory[itemSlot] = Item(container.emptyItem)
                player.filterableMessage(container.emptyMessage)
            }
        }
    }
}