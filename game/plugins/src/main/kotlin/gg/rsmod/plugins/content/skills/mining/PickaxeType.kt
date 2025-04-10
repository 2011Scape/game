package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Items

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class PickaxeType(
    val item: Int,
    val level: Int,
    val animation: Int,
    val ticksBetweenRolls: Int,
) {
    BRONZE(
        item = Items.BRONZE_PICKAXE,
        level = 1,
        animation = Anims.MINE_BRONZE_PICKAXE,
        ticksBetweenRolls = 8,
    ),
    IRON(
        item = Items.IRON_PICKAXE,
        level = 1,
        animation = Anims.MINE_IRON_PICKAXE,
        ticksBetweenRolls = 7,
    ),
    STEEL(
        item = Items.STEEL_PICKAXE,
        level = 6,
        animation = Anims.MINE_STEEL_PICKAXE,
        ticksBetweenRolls = 6,
    ),
    MITHRIL(
        item = Items.MITHRIL_PICKAXE,
        level = 21,
        animation = Anims.MINE_MITHRIL_PICKAXE,
        ticksBetweenRolls = 5,
    ),
    ADAMANT(
        item = Items.ADAMANT_PICKAXE,
        level = 31,
        animation = Anims.MINE_ADAMANT_PICKAXE,
        ticksBetweenRolls = 4,
    ),
    RUNE(
        item = Items.RUNE_PICKAXE,
        level = 41,
        animation = Anims.MINE_RUNE_PICKAXE,
        ticksBetweenRolls = 3,
    ),
    INFERNO_ADZE(
        item = Items.INFERNO_ADZE,
        level = 41,
        animation = Anims.MINE_INFERNO_ADZE,
        ticksBetweenRolls = 3,
    ),
    DRAGON(
        item = Items.DRAGON_PICKAXE,
        level = 61,
        animation = Anims.MINE_DRAGON_PICKAXE,
        ticksBetweenRolls = 3,
    ),
    GILDED_BRONZE(
        item = Items.GILDED_BRONZE_PICKAXE,
        level = 1,
        animation = Anims.MINE_GILDED_BRONZE_PICKAXE,
        ticksBetweenRolls = 8,
    ),
    GILDED_IRON(
        item = Items.GILDED_IRON_PICKAXE,
        level = 1,
        animation = Anims.MINE_GILDED_IRON_PICKAXE,
        ticksBetweenRolls = 7,
    ),
    GILDED_STEEL(
        item = Items.GILDED_STEEL_PICKAXE,
        level = 1,
        animation = Anims.MINE_GILDED_STEEL_PICKAXE,
        ticksBetweenRolls = 6,
    ),
    GILDED_MITHRIL(
        item = Items.GILDED_MITHRIL_PICKAXE,
        level = 21,
        animation = Anims.MINE_GILDED_MITHRIL_PICKAXE,
        ticksBetweenRolls = 5,
    ),
    GILDED_ADAMANT(
        item = Items.GILDED_ADAMANT_PICKAXE,
        level = 1,
        animation = Anims.MINE_GILDED_ADAMANT_PICKAXE,
        ticksBetweenRolls = 4,
    ),
    GILDED_RUNE(
        item = Items.GILDED_RUNE_PICKAXE,
        level = 1,
        animation = Anims.MINE_GILDED_RUNE_PICKAXE,
        ticksBetweenRolls = 3,
    ),
    GILDED_DRAGON(
        item = Items.GILDED_DRAGON_PICKAXE,
        level = 61,
        animation = Anims.MINE_GILDED_DRAGON_PICKAXE,
        ticksBetweenRolls = 3,
    ),
    ;

    companion object {
        val values = enumValues<PickaxeType>()
    }
}
