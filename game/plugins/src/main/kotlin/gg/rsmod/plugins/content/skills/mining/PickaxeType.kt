package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class PickaxeType(val item: Int, val level: Int, val animation: Int, val ticksBetweenRolls: Int) {

    BRONZE(
        item = Items.BRONZE_PICKAXE, level = 1, animation = 625, ticksBetweenRolls = 8
    ),

    IRON(
        item = Items.IRON_PICKAXE, level = 1, animation = 626, ticksBetweenRolls = 7
    ),

    STEEL(
        item = Items.STEEL_PICKAXE, level = 6, animation = 627, ticksBetweenRolls = 6
    ),

    MITHRIL(
        item = Items.MITHRIL_PICKAXE, level = 21, animation = 629, ticksBetweenRolls = 5
    ),

    ADAMANT(
        item = Items.ADAMANT_PICKAXE, level = 31, animation = 628, ticksBetweenRolls = 4
    ),

    RUNE(
        item = Items.RUNE_PICKAXE, level = 41, animation = 624, ticksBetweenRolls = 3
    ),

    DRAGON(
        item = Items.DRAGON_PICKAXE, level = 61, animation = 4766, ticksBetweenRolls = 3
    ),

    GILDED_BRONZE(
        item = Items.GILDED_BRONZE_PICKAXE, level = 1, animation = 251, ticksBetweenRolls = 8
    ),

    GILDED_IRON(
        item = Items.GILDED_IRON_PICKAXE, level = 1, animation = 252, ticksBetweenRolls = 7
    ),

    GILDED_STEEL(
        item = Items.GILDED_STEEL_PICKAXE, level = 1, animation = 253, ticksBetweenRolls = 6
    ),

    GILDED_MITHRIL(
        item = Items.GILDED_MITHRIL_PICKAXE, level = 21, animation = 269, ticksBetweenRolls = 5
    ),

    GILDED_ADAMANT(
        item = Items.GILDED_ADAMANT_PICKAXE, level = 1, animation = 270, ticksBetweenRolls = 4
    ),

    GILDED_RUNE(
        item = Items.GILDED_RUNE_PICKAXE, level = 1, animation = 271, ticksBetweenRolls = 3
    ),

    GILDED_DRAGON(
        item = Items.GILDED_DRAGON_PICKAXE, level = 61, animation = 272, ticksBetweenRolls = 3
    );

    companion object {
        val values = enumValues<PickaxeType>()
    }

}