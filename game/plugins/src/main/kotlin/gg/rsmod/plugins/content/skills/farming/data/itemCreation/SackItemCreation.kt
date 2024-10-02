package gg.rsmod.plugins.content.skills.farming.data.itemCreation

import gg.rsmod.plugins.api.cfg.Items

enum class SackItemCreation(
    val product: Int,
    val produce: Int,
) {
    Potatoes(Items.POTATOES_10, Items.POTATO),
    Onions(Items.ONIONS_10, Items.ONION),
    Cabbages(Items.CABBAGES_10, Items.CABBAGE),
    ;

    val amount = 10
    val message = "You need $amount ${name.lowercase()} to do that."
    val sack = Items.EMPTY_SACK
}
