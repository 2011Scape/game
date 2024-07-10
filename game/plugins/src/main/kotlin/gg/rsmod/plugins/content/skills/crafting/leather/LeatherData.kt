package gg.rsmod.plugins.content.skills.crafting.leather

import gg.rsmod.plugins.api.cfg.Items

enum class LeatherData(
    val raw: Int,
    val products: Array<LeatherItem>,
) {
    LEATHER(
        raw = Items.LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.LEATHER_GLOVES, levelRequired = 1, experience = 13.8),
                LeatherItem(resultItem = Items.LEATHER_BOOTS, levelRequired = 7, experience = 16.2),
                LeatherItem(resultItem = Items.LEATHER_COWL, levelRequired = 9, experience = 18.5),
                LeatherItem(resultItem = Items.LEATHER_VAMBRACES, levelRequired = 11, experience = 22.0),
                LeatherItem(resultItem = Items.LEATHER_BODY, levelRequired = 14, experience = 25.0),
                LeatherItem(resultItem = Items.LEATHER_CHAPS, levelRequired = 18, experience = 27.0),
                LeatherItem(resultItem = Items.COIF, levelRequired = 38, experience = 37.0),
            ),
    ),
    HARD_LEATHER(
        raw = Items.HARD_LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.HARDLEATHER_BODY, levelRequired = 28, experience = 35.0),
            ),
    ),
    SNAKESKIN(
        raw = Items.SNAKESKIN,
        products =
            arrayOf(
                LeatherItem(
                    resultItem = Items.SNAKESKIN_BOOTS,
                    amountRequired = 6,
                    levelRequired = 45,
                    experience = 30.0,
                ),
                LeatherItem(
                    resultItem = Items.SNAKESKIN_VAMBRACES,
                    amountRequired = 8,
                    levelRequired = 47,
                    experience = 35.0,
                ),
                LeatherItem(
                    resultItem = Items.SNAKESKIN_BANDANA,
                    amountRequired = 5,
                    levelRequired = 48,
                    experience = 45.0,
                ),
                LeatherItem(
                    resultItem = Items.SNAKESKIN_CHAPS,
                    amountRequired = 12,
                    levelRequired = 51,
                    experience = 50.0,
                ),
                LeatherItem(
                    resultItem = Items.SNAKESKIN_BODY,
                    amountRequired = 15,
                    levelRequired = 53,
                    experience = 55.0,
                ),
            ),
    ),
    YAK_HIDE(
        raw = Items.CURED_YAKHIDE,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.YAKHIDE_ARMOUR_10824, levelRequired = 43, experience = 32.0),
                LeatherItem(
                    resultItem = Items.YAKHIDE_ARMOUR,
                    amountRequired = 2,
                    levelRequired = 46,
                    experience = 32.0,
                ),
            ),
    ),
    GREEN_DRAGONHIDE(
        raw = Items.GREEN_DRAGON_LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.GREEN_DHIDE_VAMBRACES, levelRequired = 57, experience = 62.0),
                LeatherItem(
                    resultItem = Items.GREEN_DHIDE_CHAPS,
                    amountRequired = 2,
                    levelRequired = 60,
                    experience = 124.0,
                ),
                LeatherItem(
                    resultItem = Items.GREEN_DHIDE_BODY,
                    amountRequired = 3,
                    levelRequired = 63,
                    experience = 186.0,
                ),
            ),
    ),
    BLUE_DRAGONHIDE(
        raw = Items.BLUE_DRAGON_LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.BLUE_DHIDE_VAMBRACES, levelRequired = 66, experience = 70.0),
                LeatherItem(
                    resultItem = Items.BLUE_DHIDE_CHAPS,
                    amountRequired = 2,
                    levelRequired = 68,
                    experience = 140.0,
                ),
                LeatherItem(
                    resultItem = Items.BLUE_DHIDE_BODY,
                    amountRequired = 3,
                    levelRequired = 71,
                    experience = 210.0,
                ),
            ),
    ),
    RED_DRAGONHIDE(
        raw = Items.RED_DRAGON_LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.RED_DHIDE_VAMBRACES, levelRequired = 73, experience = 78.0),
                LeatherItem(
                    resultItem = Items.RED_DHIDE_CHAPS,
                    amountRequired = 2,
                    levelRequired = 75,
                    experience = 156.0,
                ),
                LeatherItem(
                    resultItem = Items.RED_DHIDE_BODY,
                    amountRequired = 3,
                    levelRequired = 77,
                    experience = 234.0,
                ),
            ),
    ),
    BLACK_DRAGONHIDE(
        raw = Items.BLACK_DRAGON_LEATHER,
        products =
            arrayOf(
                LeatherItem(resultItem = Items.BLACK_DHIDE_VAMBRACES, levelRequired = 79, experience = 86.0),
                LeatherItem(
                    resultItem = Items.BLACK_DHIDE_CHAPS,
                    amountRequired = 2,
                    levelRequired = 82,
                    experience = 172.0,
                ),
                LeatherItem(
                    resultItem = Items.BLACK_DHIDE_BODY,
                    amountRequired = 3,
                    levelRequired = 84,
                    experience = 258.0,
                ),
            ),
    ),
    ;

    companion object {
        val values = enumValues<LeatherData>()
        val leatherDefinitions = values.associateBy { it.raw }

        fun getLeatherItemForId(item: Int): LeatherItem? {
            for (product in LeatherData.values()) {
                val data = product.products
                for (leatherItem in data) {
                    if (leatherItem.resultItem == item) {
                        return leatherItem
                    }
                }
            }
            return null
        }

        fun getLeatherDataForId(item: Int): LeatherData? {
            for (product in LeatherData.values()) {
                val data = product.products
                for (leatherItem in data) {
                    if (leatherItem.resultItem == item) {
                        return product
                    }
                }
            }
            return null
        }
    }
}
