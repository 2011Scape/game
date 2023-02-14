package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.item.Item

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class SpellMetadata(
    val interfaceId: Int, val component: Int, val sprite: Int, val spellType: SpellType,
    val name: String, val lvl: Int, val runes: List<Item>
)