package gg.rsmod.plugins.content.skills.crafting.pottery

import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Items

enum class PotteryData(
    vararg val products: PotteryItem,
    val urn: Boolean = true,
    val runeRequired: Int? = null,
) {
    CLAY_RING(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CLAY_RING_UNFIRED,
                    fired = Items.CLAY_RING,
                    levelRequired = 4,
                    formingExperience = 11.3,
                    firingExperience = 11.0,
                ),
            ),
        urn = false,
    ),
    NOT_URNS(
        products =
            arrayOf(
                PotteryItem(unfired = Items.CRACKED_FISHING_URN_UNF, fired = -1),
                PotteryItem(
                    unfired = Items.POT_UNFIRED,
                    fired = Items.EMPTY_POT,
                    levelRequired = 1,
                    formingExperience = 6.3,
                    firingExperience = 6.3,
                ),
                PotteryItem(
                    unfired = Items.PIE_DISH_UNFIRED,
                    fired = Items.PIE_DISH,
                    levelRequired = 7,
                    formingExperience = 15.0,
                    firingExperience = 10.0,
                ),
                PotteryItem(
                    unfired = Items.BOWL_UNFIRED,
                    fired = Items.BOWL,
                    levelRequired = 8,
                    formingExperience = 18.0,
                    firingExperience = 15.0,
                ),
                PotteryItem(
                    unfired = Items.UNFIRED_PLANT_POT,
                    fired = Items.PLANT_POT,
                    levelRequired = 19,
                    formingExperience = 20.0,
                    firingExperience = 17.5,
                ),
                PotteryItem(
                    unfired = Items.UNFIRED_POT_LID,
                    fired = Items.POT_LID,
                    levelRequired = 25,
                    formingExperience = 20.0,
                    firingExperience = 20.0,
                ),
            ),
        urn = false,
    ),
    MINING_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CRACKED_MINING_URN_UNF,
                    fired = Items.CRACKED_MINING_URN_NR,
                    levelRequired = 1,
                    formingExperience = 11.8,
                    firingExperience = 16.8,
                    addRuneAnimation = Anims.CRACKED_MINING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FRAGILE_MINING_URN_UNF,
                    fired = Items.FRAGILE_MINING_URN_NR,
                    levelRequired = 17,
                    formingExperience = 21.2,
                    firingExperience = 31.8,
                    addRuneAnimation = Anims.FRAGILE_MINING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.MINING_URN_UNF,
                    fired = Items.MINING_URN_NR,
                    levelRequired = 32,
                    formingExperience = 27.2,
                    firingExperience = 40.8,
                    addRuneAnimation = Anims.MINING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.STRONG_MINING_URN_UNF,
                    fired = Items.STRONG_MINING_URN_NR,
                    levelRequired = 48,
                    formingExperience = 30.8,
                    firingExperience = 49.2,
                    addRuneAnimation = Anims.STRONG_MINING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.DECORATED_MINING_URN_UNF,
                    fired = Items.DECORATED_MINING_URN_NR,
                    levelRequired = 59,
                    formingExperience = 38.0,
                    firingExperience = 57.0,
                    addRuneAnimation = Anims.DECORATED_MINING_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.EARTH_RUNE,
    ),
    COOKING_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CRACKED_COOKING_URN_UNF,
                    fired = Items.CRACKED_COOKING_URN_NR,
                    levelRequired = 2,
                    formingExperience = 12.0,
                    firingExperience = 18.0,
                    addRuneAnimation = Anims.CRACKED_COOKING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FRAGILE_COOKING_URN_UNF,
                    fired = Items.FRAGILE_COOKING_URN_NR,
                    levelRequired = 12,
                    formingExperience = 16.0,
                    firingExperience = 24.0,
                    addRuneAnimation = Anims.FRAGILE_COOKING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.COOKING_URN_UNF,
                    fired = Items.COOKING_URN_NR,
                    levelRequired = 36,
                    formingExperience = 28.6,
                    firingExperience = 42.8,
                    addRuneAnimation = Anims.COOKING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.STRONG_COOKING_URN_UNF,
                    fired = Items.STRONG_COOKING_URN_NR,
                    levelRequired = 51,
                    formingExperience = 35.0,
                    firingExperience = 52.5,
                    addRuneAnimation = Anims.STRONG_COOKING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.DECORATED_COOKING_URN_UNF,
                    fired = Items.DECORATED_COOKING_URN_NR,
                    levelRequired = 81,
                    formingExperience = 52.0,
                    firingExperience = 78.0,
                    addRuneAnimation = Anims.DECORATED_COOKING_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.FIRE_RUNE,
    ),
    FISHING_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CRACKED_FISHING_URN_UNF,
                    fired = Items.CRACKED_FISHING_URN_NR,
                    levelRequired = 2,
                    formingExperience = 12.0,
                    firingExperience = 18.0,
                    addRuneAnimation = Anims.CRACKED_FISHING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FRAGILE_FISHING_URN_UNF,
                    fired = Items.FRAGILE_FISHING_URN_NR,
                    levelRequired = 15,
                    formingExperience = 20.0,
                    firingExperience = 30.0,
                    addRuneAnimation = Anims.FRAGILE_FISHING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FISHING_URN_UNF,
                    fired = Items.FISHING_URN_NR,
                    levelRequired = 41,
                    formingExperience = 31.2,
                    firingExperience = 46.8,
                    addRuneAnimation = Anims.FISHING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.STRONG_FISHING_URN_UNF,
                    fired = Items.STRONG_FISHING_URN_NR,
                    levelRequired = 53,
                    formingExperience = 36.0,
                    firingExperience = 54.0,
                    addRuneAnimation = Anims.STRONG_FISHING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.DECORATED_FISHING_URN_UNF,
                    fired = Items.DECORATED_FISHING_URN_NR,
                    levelRequired = 76,
                    formingExperience = 48.0,
                    firingExperience = 72.0,
                    addRuneAnimation = Anims.DECORATED_FISHING_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.WATER_RUNE,
    ),
    WOODCUTTING_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CRACKED_WOODCUTTING_URN_UNF,
                    fired = Items.CRACKED_WOODCUTTING_URN_NR,
                    levelRequired = 4,
                    formingExperience = 15.4,
                    firingExperience = 23.1,
                    addRuneAnimation = Anims.CRACKED_WOODCUTTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FRAGILE_WOODCUTTING_URN_UNF,
                    fired = Items.FRAGILE_WOODCUTTING_URN_NR,
                    levelRequired = 15,
                    formingExperience = 20.0,
                    firingExperience = 30.0,
                    addRuneAnimation = Anims.FRAGILE_WOODCUTTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.WOODCUTTING_URN_UNF,
                    fired = Items.WOODCUTTING_URN_NR,
                    levelRequired = 44,
                    formingExperience = 32.0,
                    firingExperience = 48.0,
                    addRuneAnimation = Anims.WOODCUTTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.STRONG_WOODCUTTING_URN_UNF,
                    fired = Items.STRONG_WOODCUTTING_URN_NR,
                    levelRequired = 61,
                    formingExperience = 38.8,
                    firingExperience = 58.2,
                    addRuneAnimation = Anims.STRONG_WOODCUTTING_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.EARTH_RUNE,
    ),
    SMELTING_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.CRACKED_SMELTING_URN_UNF,
                    fired = Items.CRACKED_SMELTING_URN_NR,
                    levelRequired = 4,
                    formingExperience = 15.4,
                    firingExperience = 23.8,
                    addRuneAnimation = Anims.CRACKED_SMELTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.FRAGILE_SMELTING_URN_UNF,
                    fired = Items.FRAGILE_SMELTING_URN_NR,
                    levelRequired = 17,
                    formingExperience = 21.2,
                    firingExperience = 31.8,
                    addRuneAnimation = Anims.FRAGILE_SMELTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.SMELTING_URN_UNF,
                    fired = Items.SMELTING_URN_NR,
                    levelRequired = 35,
                    formingExperience = 28.0,
                    firingExperience = 42.0,
                    addRuneAnimation = Anims.SMELTING_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.STRONG_SMELTING_URN_UNF,
                    fired = Items.STRONG_SMELTING_URN_NR,
                    levelRequired = 49,
                    formingExperience = 33.8,
                    firingExperience = 50.8,
                    addRuneAnimation = Anims.STRONG_SMELTING_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.FIRE_RUNE,
    ),
    PRAYER_URNS(
        products =
            arrayOf(
                PotteryItem(
                    unfired = Items.IMPIOUS_URN_UNF,
                    fired = Items.IMPIOUS_URN_NR,
                    levelRequired = 2,
                    formingExperience = 12.0,
                    firingExperience = 18.0,
                    addRuneAnimation = Anims.IMPIOUS_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.ACCURSED_URN_UNF,
                    fired = Items.ACCURSED_URN_NR,
                    levelRequired = 26,
                    formingExperience = 25.0,
                    firingExperience = 37.5,
                    addRuneAnimation = Anims.ACCURSED_URN_ADDRUNE,
                ),
                PotteryItem(
                    unfired = Items.INFERNAL_URN_UNF,
                    fired = Items.INFERNAL_URN_NR,
                    levelRequired = 62,
                    formingExperience = 40.0,
                    firingExperience = 60.0,
                    addRuneAnimation = Anims.INFERNAL_URN_ADDRUNE,
                ),
            ),
        runeRequired = Items.AIR_RUNE,
    ),
    ;

    companion object {
        val values = enumValues<PotteryData>()

        fun getAllFiredUrns(): MutableList<Int> {
            val temp = mutableListOf<Int>()
            for (data in PotteryData.values()) {
                if (data.urn) {
                    for (product in data.products) {
                        if (!temp.contains(product.fired)) {
                            temp.add(product.fired)
                        }
                    }
                }
            }
            return temp
        }

        fun findPotteryDataByItem(item: Int): PotteryData? {
            for (data in PotteryData.values()) {
                for (product in data.products) {
                    if (product.unfired == item || product.fired == item) {
                        return data
                    }
                }
            }
            return null
        }

        fun findPotteryItemByItem(item: Int): PotteryItem? {
            for (data in PotteryData.values()) {
                for (product in data.products) {
                    if (product.unfired == item || product.fired == item) {
                        return product
                    }
                }
            }
            return null
        }
    }
}
