package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items
/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class PickaxeType(val item: Int, val level: Int, val animation: Int) {
    BRONZE(item = Items.BRONZE_PICKAXE, level = 1, animation = 625),
    IRON(item = Items.IRON_PICKAXE, level = 1, animation = 626),
    STEEL(item = Items.STEEL_PICKAXE, level = 6, animation = 627),
    MITHRIL(item = Items.MITHRIL_PICKAXE, level = 21, animation = 629),
    ADAMANT(item = Items.ADAMANT_PICKAXE, level = 31, animation = 628),
    RUNE(item = Items.RUNE_PICKAXE, level = 41, animation = 624),
    DRAGON(item = Items.DRAGON_PICKAXE, level = 61, animation = 624); // need to find the correct animation
//    GILDED_BRONZE(item = Items.GILDED_BRONZE_PICKAXE, level = 1, animation = 625),
//    GILDED_IRON(item = Items.GILDED_IRON_PICKAXE, level = 1, animation = 626),
//    GILDED_STEEL(item = Items.GILDED_STEEL_PICKAXE, level = 1, animation = 2846),
//    GILDED_MITHRIL(item = Items.GILDED_MITHRIL_PICKAXE, level = 21, animation = 629),
//    GILDED_ADAMANT(item = Items.GILDED_ADAMANT_PICKAXE, level = 1, animation = 2846),
//    GILDED_RUNE(item = Items.GILDED_RUNE_PICKAXE, level = 1, animation = 2846),
//    GILDED_DRAGON(item = Items.GILDED_DRAGON_PICKAXE, level = 61, animation = 2846);
    companion object {
        val values = enumValues<PickaxeType>()
    }
}