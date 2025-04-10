package gg.rsmod.plugins.content.skills.woodcutting

import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class AxeType(
    val item: Int,
    val level: Int,
    val animation: Int,
    val ivyAnimation: Int,
    val ratio: Double,
) {
    BRONZE(item = Items.BRONZE_HATCHET,
        level = 1,
        animation = Anims.CHOP_BRONZE_HATCHET,
        ivyAnimation = Anims.IVY_BRONZE_HATCHET,
        ratio = 1.0),
    IRON(item = Items.IRON_HATCHET,
        level = 1,
        animation = Anims.CHOP_IRON_HATCHET,
        ivyAnimation = Anims.IVY_IRON_HATCHET,
        ratio = 1.5),
    STEEL(item = Items.STEEL_HATCHET,
        level = 6,
        animation = Anims.CHOP_STEEL_HATCHET,
        ivyAnimation = Anims.IVY_STEEL_HATCHET,
        ratio = 2.0),
    BLACK(item = Items.BLACK_HATCHET,
        level = 6,
        animation = Anims.CHOP_BLACK_HATCHET,
        ivyAnimation = Anims.IVY_BLACK_HATCHET,
        ratio = 2.25),
    MITHRIL(item = Items.MITHRIL_HATCHET,
        level = 21,
        animation = Anims.CHOP_MITHRIL_HATCHET,
        ivyAnimation = Anims.IVY_MITHRIL_HATCHET,
        ratio = 2.5),
    ADAMANT(item = Items.ADAMANT_HATCHET,
        level = 31,
        animation = Anims.CHOP_ADAMANT_HATCHET,
        ivyAnimation = Anims.IVY_ADAMANT_HATCHET,
        ratio = 3.0),
    RUNE(item = Items.RUNE_HATCHET,
        level = 41,
        animation = Anims.CHOP_RUNE_HATCHET,
        ivyAnimation = Anims.IVY_RUNE_HATCHET,
        ratio = 3.5),
    DRAGON(item = Items.DRAGON_HATCHET,
        level = 61,
        animation = Anims.CHOP_DRAGON_HATCHET,
        ivyAnimation = Anims.IVY_DRAGON_HATCHET,
        ratio = 3.75),
    INFERNO_ADZE(item = Items.INFERNO_ADZE,
        level = 61,
        animation = Anims.CHOP_INFERNO_ADZE,
        ivyAnimation = Anims.IVY_INFERNO_ADZE,
        ratio = 3.75),
    ;

    companion object {
        val values = enumValues<AxeType>()
    }
}
