package gg.rsmod.plugins.content.skills.crafting.spinning

import gg.rsmod.plugins.api.cfg.Items

enum class SpinningData(val raw: Array<Int>, val product: Int, val levelRequirement: Int, val experience: Double, val rawName: String) {
    WOOL(raw = arrayOf(Items.WOOL), product = Items.BALL_OF_WOOL, levelRequirement = 1, experience = 2.5, rawName = "Wool"),
    FLAX(raw = arrayOf(Items.FLAX), product = Items.BOW_STRING, levelRequirement = 10, experience = 15.0, rawName = "Flax"),
    CBOW_STRING(raw = arrayOf(Items.SINEW), product = Items.CROSSBOW_STRING, levelRequirement = 10, experience = 15.0, rawName = "Sinew"),
    CBOW_STRING1(raw = arrayOf(Items.OAK_ROOTS, Items.WILLOW_ROOTS, Items.MAPLE_ROOTS, Items.YEW_ROOTS, Items.MAGIC_ROOTS, Items.SPIRIT_ROOTS), product = Items.CROSSBOW_STRING, levelRequirement = 10, experience = 15.0, rawName = "Tree Roots"),
    MAGIC_STRING(raw = arrayOf(Items.MAGIC_ROOTS), product = Items.MAGIC_STRING, levelRequirement = 19, experience = 30.0, rawName = "Magic Roots"),
    ROPE(raw = arrayOf(Items.HAIR), product = Items.ROPE, levelRequirement = 30, experience = 25.0, rawName = "Yak hair")

    ;
    companion object {
        val values = enumValues<SpinningData>()
        val spinningDefinitions = values.associateBy { it.product }
    }
}