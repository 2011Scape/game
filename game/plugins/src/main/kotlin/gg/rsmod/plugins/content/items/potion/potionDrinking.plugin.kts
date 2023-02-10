package gg.rsmod.plugins.content.items.potion

Potion.values.forEach { potion ->
    on_item_option(item = potion.item, option = "Drink") {
        if (!Potions.canDrink(player, potion)) {
            return@on_item_option
        }
        val inventorySlot = player.getInteractingItemSlot()
        if (player.inventory.remove(item = potion.item, beginSlot = inventorySlot).hasSucceeded()) {
            Potions.drink(player, potion)
            if (potion.replacement != -1) {
                player.inventory.add(item = potion.replacement, beginSlot = inventorySlot)
            }
        }
    }
}