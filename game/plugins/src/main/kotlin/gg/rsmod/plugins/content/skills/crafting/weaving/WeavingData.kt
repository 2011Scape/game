package gg.rsmod.plugins.content.skills.crafting.weaving

import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items

enum class WeavingData(
    val resourceItem: Item,
    val resultItem: Int,
    val levelRequired: Int,
    val experience: Double,
    val rawName: String,
) {
    STRIP_OF_CLOTH(
        resourceItem = Item(Items.BALL_OF_WOOL, amount = 4),
        resultItem = Items.STRIP_OF_CLOTH,
        levelRequired = 10,
        experience = 12.0,
        rawName = "4 balls of wool",
    ),
    EMPTY_SACK(
        resourceItem = Item(Items.JUTE_FIBRE, amount = 4),
        resultItem = Items.EMPTY_SACK,
        levelRequired = 21,
        experience = 38.0,
        rawName = "4 jute fibres",
    ),
    EMPTY_BASKET(
        resourceItem = Item(Items.WILLOW_BRANCH, amount = 6),
        resultItem = Items.BASKET,
        levelRequired = 36,
        experience = 56.0,
        rawName = "6 willow branches",
    ),
    UNFINISHED_NET(
        resourceItem = Item(Items.FLAX, amount = 5),
        resultItem = Items.UNFINISHED_NET,
        levelRequired = 52,
        experience = 0.0,
        rawName = "5 flax",
    ),
    MILESTONE_CAPES(
        resourceItem = Item(-1),
        resultItem = Items.MILESTONE_CAPE_10,
        levelRequired = 10,
        experience = 0.0,
        rawName = "balls of wool",
    ),
    ;

    companion object {
        val values = enumValues<WeavingData>()
        val weavingDefinitions = values.associateBy { it.resultItem }
    }
}
