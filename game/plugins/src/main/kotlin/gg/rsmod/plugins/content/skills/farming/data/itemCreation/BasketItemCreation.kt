package gg.rsmod.plugins.content.skills.farming.data.itemCreation

import gg.rsmod.plugins.api.cfg.Items

enum class BasketItemCreation(
    val product: Int,
    val produce: Int,
) {
    Apples(Items.APPLES_5, Items.COOKING_APPLE),
    Bananas(Items.BANANAS_5, Items.BANANA),
    Oranges(Items.ORANGES_5, Items.ORANGE),
    Strawberries(Items.STRAWBERRIES_5, Items.STRAWBERRY),
    Tomatoes(Items.TOMATOES_5, Items.TOMATO),
    ;

    val amount = 5
    val basket = Items.BASKET
    val message = "You need $amount ${name.lowercase()} to do that."
}
