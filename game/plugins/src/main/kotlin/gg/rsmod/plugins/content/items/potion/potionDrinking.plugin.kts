package gg.rsmod.plugins.content.items.potion

val potionValues = Potion.values()

potionValues.forEach { potion ->
    on_item_option(item = potion.item, option = "Drink") {
        Potions.drink(player, potion)
    }
}
