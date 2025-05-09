package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.plugins.api.cfg.Items

/**
 * Describes the possible compost states a patch can have
 */
enum class CompostState(
    val itemId: Int,
    val diseaseChanceFactor: Double,
    val varbitValue: Int,
    val xp: Double,
    val lives: Int,
) {
    None(itemId = -1, diseaseChanceFactor = 1.0, varbitValue = 0, xp = 0.0, lives = 0),
    Compost(itemId = Items.COMPOST, diseaseChanceFactor = 0.28, varbitValue = 1, 18.0, lives = 1),
    SuperCompost(itemId = Items.SUPERCOMPOST, diseaseChanceFactor = 0.14, varbitValue = 2, 26.0, lives = 2),
    ;

    val replacement = Items.BUCKET

    companion object {
        fun fromVarbit(varbit: Int) = values().firstOrNull { it.varbitValue == varbit }

        fun fromId(id: Int) = values().firstOrNull { it.itemId == id }

        val itemIds = values().map { it.itemId }.filterNot { it == -1 }
    }
}
