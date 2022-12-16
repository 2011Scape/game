package gg.rsmod.plugins.content.skills.woodcutting

import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class AxeType(val item: Int, val level: Int, val animation: Int, val ratio: Double) {
    BRONZE(item = Items.BRONZE_HATCHET, level = 1, animation = 879, ratio = 1.0),
    IRON(item = Items.IRON_HATCHET, level = 1, animation = 877, ratio = 1.5),
    STEEL(item = Items.STEEL_HATCHET, level = 6, animation = 875, ratio = 2.0),
    BLACK(item = Items.BLACK_HATCHET, level = 11, animation = 873, ratio = 2.25),
    MITHRIL(item = Items.MITHRIL_HATCHET, level = 21, animation = 871, ratio = 2.5),
    ADAMANT(item = Items.ADAMANT_HATCHET, level = 31, animation = 869, ratio = 3.0),
    RUNE(item = Items.RUNE_HATCHET, level = 41, animation = 867, ratio = 3.5),
    DRAGON(item = Items.DRAGON_HATCHET, level = 61, animation = 2846, ratio = 3.75),
    INFERNO_ADZE(item = Items.INFERNO_ADZE, level = 61, animation = 10251, ratio = 3.75);

    companion object {
        val values = enumValues<AxeType>()
    }
}