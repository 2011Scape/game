package gg.rsmod.plugins.content.inter

val destroyableItems = mutableListOf<Int>()
val destroyItemAction = mutableMapOf<Int, (Player) -> Unit>()

// define destroy item action
destroyItemAction[553] = { player -> player.setVarbit(2130, 0) }

// Loop through each item in the specified range
for (itemId in 1..20653) {
    // Check if item is in cache
    if (itemId < world.definitions.getCount(ItemDef::class.java)) {
        // Get the item definition
        val def = world.definitions.get(ItemDef::class.java, itemId)

        // Check if the destroy option exists
        if (def.inventoryMenu.getOrNull(4)?.lowercase() == "destroy") {
            // Add the item id to the destroyableItems list
            destroyableItems.add(itemId)
        }
    }
}
// Then, bind the "destroy" option to each item
destroyableItems.forEach {
    on_item_option(it, 10) {
        val itemId = it
        player.queue(TaskPriority.WEAK) {
            destroyItemAction[itemId]?.invoke(player)
            destroyItem(itemId)
        }
    }
}
