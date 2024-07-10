package gg.rsmod.plugins.content.skills.fletching.arrows

import gg.rsmod.plugins.api.cfg.Items

enum class ArrowData(
    val tips: Int,
    val product: Int,
    val amount: Int = 15,
    val levelRequirement: Int,
    val experience: Double,
) {
    BRONZE(tips = Items.BRONZE_ARROWTIPS, product = Items.BRONZE_ARROW, levelRequirement = 1, experience = 19.5),

    IRON(tips = Items.IRON_ARROWTIPS, product = Items.IRON_ARROW, levelRequirement = 15, experience = 37.5),

    STEEL(tips = Items.STEEL_ARROWTIPS, product = Items.STEEL_ARROW, levelRequirement = 30, experience = 75.0),

    MITHRIL(tips = Items.MITHRIL_ARROWTIPS, product = Items.MITHRIL_ARROW, levelRequirement = 45, experience = 112.5),

    ADAMANT(tips = Items.ADAMANT_ARROWTIPS, product = Items.ADAMANT_ARROW, levelRequirement = 60, experience = 150.0),

    RUNE(tips = Items.RUNE_ARROWTIPS, product = Items.RUNE_ARROW, levelRequirement = 75, experience = 187.5),

    BROAD(tips = Items.BROAD_ARROW_HEADS, product = Items.BROAD_ARROW, levelRequirement = 52, experience = 150.0),

    DRAGON(tips = Items.DRAGON_ARROWTIPS, product = Items.DRAGON_ARROW, levelRequirement = 90, experience = 225.0),
    ;

    companion object {
        val arrowDefinitions = values().associateBy { it.product }
    }
}
