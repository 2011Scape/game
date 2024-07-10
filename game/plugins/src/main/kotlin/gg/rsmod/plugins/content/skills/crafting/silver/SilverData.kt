package gg.rsmod.plugins.content.skills.crafting.silver

import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.crafting.MouldItems

enum class SilverData(
    val resultItem: Item,
    val extraItems: List<Int>? = null,
    val mould: MouldItems,
    val levelRequired: Int,
    val experience: Double,
    val componentArray: IntArray,
) {
    HOLY_SYMBOL(
        resultItem = Item(Items.UNSTRUNG_SYMBOL),
        mould = MouldItems.HOLY,
        levelRequired = 16,
        experience = 50.0,
        componentArray = intArrayOf(15, 16, 17, 18, 19, 20, 21),
    ),
    UNHOLY_SYMBOL(
        resultItem = Item(Items.UNSTRUNG_EMBLEM),
        mould = MouldItems.UNHOLY,
        levelRequired = 17,
        experience = 50.0,
        componentArray = intArrayOf(22, 23, 24, 25, 26, 27, 28),
    ),
    SILVER_SICKLE(
        resultItem = Item(Items.SILVER_SICKLE),
        mould = MouldItems.SICKLE,
        levelRequired = 18,
        experience = 50.0,
        componentArray = intArrayOf(29, 30, 31, 32, 33, 34, 35),
    ),
    CONDUCTOR_ROD(
        resultItem = Item(Items.CONDUCTOR),
        mould = MouldItems.LIGHTNING_ROD,
        levelRequired = 20,
        experience = 50.0,
        componentArray = intArrayOf(36, 37, 38, 39, 40, 41, 42),
    ),
    SILVER_BOLTS(
        resultItem = Item(Items.SILVER_BOLTS_UNF, amount = 10),
        mould = MouldItems.BOLT,
        levelRequired = 21,
        experience = 50.0,
        componentArray = intArrayOf(65, 66, 67, 68, 69, 70, 71),
    ),
    TIARA(
        resultItem = Item(Items.TIARA),
        mould = MouldItems.TIARA,
        levelRequired = 23,
        experience = 52.5,
        componentArray = intArrayOf(43, 44, 45, 46, 47, 48, 49),
    ),
    SILVTHRILL_ROD(
        resultItem = Item(Items.SILVTHRILL_ROD_7637),
        extraItems = listOf(Items.MITHRIL_BAR, Items.SAPPHIRE),
        mould = MouldItems.SILVTHRIL_ROD,
        levelRequired = 25,
        experience = 55.0,
        componentArray = intArrayOf(50, 53, 52, 54, 55, 56, 57),
    ),
    SILVTHRILL_CHAIN(
        resultItem = Item(Items.SILVTHRIL_CHAIN),
        extraItems = listOf(Items.MITHRIL_BAR),
        mould = MouldItems.SILVTHRIL_CHAIN,
        levelRequired = 47,
        experience = 100.0,
        componentArray = intArrayOf(72, 76, 73, 75, 77, 82, 83),
    ),
    DEMONIC_SIGIL(
        resultItem = Item(Items.DEMONIC_SIGIL),
        mould = MouldItems.DEMONIC_SIGIL,
        levelRequired = 30,
        experience = 50.0,
        componentArray = intArrayOf(58, 59, 60, 61, 62, 63, 64),
    ),
    ;

    companion object {
        val values = enumValues<SilverData>()

        fun getDataByComponent(component: Int): SilverData? {
            for (data in values) {
                if (data.componentArray.contains(component)) {
                    return data
                }
            }
            return null
        }
    }
}
