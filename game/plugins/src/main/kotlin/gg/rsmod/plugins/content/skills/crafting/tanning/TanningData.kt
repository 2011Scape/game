package gg.rsmod.plugins.content.skills.crafting.tanning

import gg.rsmod.plugins.api.cfg.Items

enum class TanningData(
    val componentId: Int,
    val price: Int,
    val rawItemId: Int,
    val tannedId: Int,
) {
    SOFT_LEATHER(componentId = 1, price = 1, rawItemId = Items.COWHIDE, tannedId = Items.LEATHER),
    HARD_LEATHER(componentId = 2, price = 3, rawItemId = Items.COWHIDE, tannedId = Items.HARD_LEATHER),
    SNAKESKIN(componentId = 3, price = 15, rawItemId = Items.SNAKE_HIDE, tannedId = Items.SNAKESKIN),
    SNAKESKIN_SWAMP(componentId = 4, price = 20, rawItemId = Items.SNAKE_HIDE_7801, tannedId = Items.SNAKESKIN),
    GREEN_DRAGONHIDE(
        componentId = 5,
        price = 20,
        rawItemId = Items.GREEN_DRAGONHIDE,
        tannedId = Items.GREEN_DRAGON_LEATHER,
    ),
    BLUE_DRAGONHIDE(
        componentId = 6,
        price = 20,
        rawItemId = Items.BLUE_DRAGONHIDE,
        tannedId = Items.BLUE_DRAGON_LEATHER,
    ),
    RED_DRAGONHIDE(componentId = 7, price = 20, rawItemId = Items.RED_DRAGONHIDE, tannedId = Items.RED_DRAGON_LEATHER),
    BLACK_DRAGONHIDE(
        componentId = 8,
        price = 20,
        rawItemId = Items.BLACK_DRAGONHIDE,
        tannedId = Items.BLACK_DRAGON_LEATHER,
    ),
    ;

    companion object {
        val values = enumValues<TanningData>()
        val tanningDefinitions = values.associateBy { it.componentId }
    }
}
