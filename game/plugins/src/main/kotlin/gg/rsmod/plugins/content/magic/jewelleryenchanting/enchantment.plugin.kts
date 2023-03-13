package gg.rsmod.plugins.content.magic.jewelleryenchanting

import gg.rsmod.plugins.content.magic.MagicSpells
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */


// Sapphire enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 29) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_1_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 17.5)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

// Emerald enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 41) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_2_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 37.0)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

// Ruby enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 53) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_3_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 59.0)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

// Diamond enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 61) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_4_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 67.0)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

// Dragonstone enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 76) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_5_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 78.0)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

// Onyx enchantment
on_spell_on_item(fromInterface = 192, fromComponent = 88) {
    player.queue(TaskPriority.STRONG) {
        val spell = SpellbookData.LVL_6_ENCHANT
        val data = MagicSpells.getMetadata(spellId = spell.uniqueId) ?: return@queue
        if (MagicSpells.canCast(player, data.lvl, data.runes)) {
            if (performEnchantment(player, spell = spell, experience = 97.0)) {
                MagicSpells.removeRunes(player, data.runes)
            }
        }
    }
}

/**
 * Performs a high or low alchemy on the item the player is interacting with, and adds coins and experience
 * to the player's inventory if successful.
 *
 * @param player The player performing the alchemy.
 * @param highAlchemy Whether to perform a high alchemy. If false, a low alchemy will be performed.
 * @return true if the alchemy was successful, false otherwise.
 */
fun performEnchantment(player: Player, spell: SpellbookData, experience: Double): Boolean {
    val item = player.getInteractingItem()
    val product = EnchantmentData.values().find {it.raw == item.id}

    player.runClientScript(115, GameframeTab.SPELLBOOK.id)

    if(product == null || product.spell != spell) {
        player.message("You can't use this spell on this item.")
        return false
    }

    val animation = when(spell) {
        SpellbookData.LVL_1_ENCHANT -> 719
        SpellbookData.LVL_2_ENCHANT -> 720
        else -> 721
    }

    val graphic = when(spell) {
        SpellbookData.LVL_1_ENCHANT -> Graphic(114, 92)
        SpellbookData.LVL_2_ENCHANT -> Graphic(115, 92)
        else -> Graphic(116, 92)
    }

    player.animate(animation)
    player.graphic(graphic)
    if (player.inventory.remove(Item(item.id, 1)).hasSucceeded()) {
        player.inventory.add(product.product)
        player.addXp(Skills.MAGIC, experience)
    }
    return true
}