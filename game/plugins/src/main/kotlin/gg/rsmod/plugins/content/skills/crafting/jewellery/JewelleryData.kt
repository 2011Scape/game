package gg.rsmod.plugins.content.skills.crafting.jewellery

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.skills.crafting.MouldItems

enum class JewelleryData(
    val gemRequired: Int = -1,
    val products: List<JewelleryItem>,
) {
    GOLD(
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.GOLD_RING,
                    levelRequired = 5,
                    experience = 15.0,
                    modelComponent = 81,
                    optionComponent = 82,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.GOLD_NECKLACE,
                    levelRequired = 6,
                    experience = 20.0,
                    modelComponent = 67,
                    optionComponent = 68,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.GOLD_BRACELET,
                    levelRequired = 7,
                    experience = 25.0,
                    modelComponent = 32,
                    optionComponent = 33,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.GOLD_AMULET,
                    levelRequired = 8,
                    experience = 30.0,
                    modelComponent = 52,
                    optionComponent = 53,
                ),
            ),
    ),
    SAPPHIRE(
        gemRequired = Items.SAPPHIRE,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.SAPPHIRE_RING,
                    levelRequired = 20,
                    experience = 40.0,
                    modelComponent = 83,
                    optionComponent = 84,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.SAPPHIRE_NECKLACE,
                    levelRequired = 22,
                    experience = 55.0,
                    modelComponent = 69,
                    optionComponent = 70,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.SAPPHIRE_BRACELET,
                    levelRequired = 23,
                    experience = 60.0,
                    modelComponent = 34,
                    optionComponent = 35,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.SAPPHIRE_AMULET,
                    levelRequired = 24,
                    experience = 65.0,
                    modelComponent = 54,
                    optionComponent = 55,
                ),
            ),
    ),
    EMERALD(
        gemRequired = Items.EMERALD,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.EMERALD_RING,
                    levelRequired = 27,
                    experience = 55.0,
                    modelComponent = 85,
                    optionComponent = 86,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.EMERALD_NECKLACE,
                    levelRequired = 29,
                    experience = 60.0,
                    modelComponent = 71,
                    optionComponent = 72,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.EMERALD_BRACELET,
                    levelRequired = 30,
                    experience = 65.0,
                    modelComponent = 36,
                    optionComponent = 37,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.EMERALD_AMULET,
                    levelRequired = 31,
                    experience = 70.0,
                    modelComponent = 56,
                    optionComponent = 57,
                ),
            ),
    ),
    RUBY(
        gemRequired = Items.RUBY,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.RUBY_RING,
                    levelRequired = 34,
                    experience = 70.0,
                    modelComponent = 87,
                    optionComponent = 88,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.RUBY_NECKLACE,
                    levelRequired = 40,
                    experience = 75.0,
                    modelComponent = 73,
                    optionComponent = 74,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.RUBY_BRACELET,
                    levelRequired = 42,
                    experience = 80.0,
                    modelComponent = 38,
                    optionComponent = 39,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.RUBY_AMULET,
                    levelRequired = 50,
                    experience = 85.0,
                    modelComponent = 58,
                    optionComponent = 59,
                ),
            ),
    ),
    DIAMOND(
        gemRequired = Items.DIAMOND,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.DIAMOND_RING,
                    levelRequired = 43,
                    experience = 85.0,
                    modelComponent = 89,
                    optionComponent = 90,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.DIAMOND_NECKLACE,
                    levelRequired = 56,
                    experience = 90.0,
                    modelComponent = 75,
                    optionComponent = 76,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.DIAMOND_BRACELET,
                    levelRequired = 58,
                    experience = 95.0,
                    modelComponent = 40,
                    optionComponent = 41,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.DIAMOND_AMULET,
                    levelRequired = 70,
                    experience = 100.0,
                    modelComponent = 60,
                    optionComponent = 61,
                ),
            ),
    ),
    DRAGONSTONE(
        gemRequired = Items.DRAGONSTONE,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.DRAGONSTONE_RING,
                    levelRequired = 55,
                    experience = 100.0,
                    modelComponent = 91,
                    optionComponent = 92,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.DRAGON_NECKLACE,
                    levelRequired = 72,
                    experience = 105.0,
                    modelComponent = 77,
                    optionComponent = 78,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.DRAGON_BRACELET,
                    levelRequired = 74,
                    experience = 110.0,
                    modelComponent = 42,
                    optionComponent = 43,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.DRAGONSTONE_AMMY,
                    levelRequired = 80,
                    experience = 150.0,
                    modelComponent = 62,
                    optionComponent = 63,
                ),
            ),
    ),
    ONYX(
        gemRequired = Items.ONYX,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.ONYX_RING_6575,
                    levelRequired = 67,
                    experience = 115.0,
                    modelComponent = 95,
                    optionComponent = 94,
                ),
                JewelleryItem(
                    mould = MouldItems.NECKLACE,
                    resultItem = Items.ONYX_NECKLACE_6577,
                    levelRequired = 82,
                    experience = 120.0,
                    modelComponent = 79,
                    optionComponent = 80,
                ),
                JewelleryItem(
                    mould = MouldItems.BRACELET,
                    resultItem = Items.ONYX_BRACELET,
                    levelRequired = 84,
                    experience = 125.0,
                    modelComponent = 44,
                    optionComponent = 45,
                ),
                JewelleryItem(
                    mould = MouldItems.AMULET,
                    resultItem = Items.ONYX_AMULET_6579,
                    levelRequired = 90,
                    experience = 165.0,
                    modelComponent = 64,
                    optionComponent = 65,
                ),
            ),
    ),
    SLAYER_RING(
        gemRequired = Items.ENCHANTED_GEM,
        products =
            listOf(
                JewelleryItem(
                    mould = MouldItems.RING,
                    resultItem = Items.RING_OF_SLAYING_8,
                    levelRequired = 75,
                    experience = 15.0,
                    modelComponent = 96,
                    optionComponent = 97,
                ),
            ),
    ),
    ;

    companion object {
        val values = enumValues<JewelleryData>()

        fun getJewelleryItemFromOption(option: Int): JewelleryItem? {
            for (data in JewelleryData.values()) {
                for (product in data.products) {
                    if (product.optionComponent == option) {
                        return product
                    }
                }
            }
            return null
        }

        fun getJewelleryDataFromItem(jewelleryItem: JewelleryItem): JewelleryData? {
            for (data in JewelleryData.values()) {
                for (product in data.products) {
                    if (product == jewelleryItem) {
                        return data
                    }
                }
            }
            return null
        }
    }
}
