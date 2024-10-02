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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
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
                MagicSpells.removeRunes(player, data.runes, data.sprite)
            }
        }
    }
}

/**
 * Performs an enchantment spell on an item.
 *
 * @param player The player casting the spell.
 * @param spell The enchantment spell being cast.
 * @param experience The amount of experience to give to the player for casting the spell.
 * @return True if the spell was successfully cast, false otherwise.
 */
fun performEnchantment(
    player: Player,
    spell: SpellbookData,
    experience: Double,
): Boolean {
    // Get the item the player is attempting to enchant
    val item = player.getInteractingItem()

    // Get the product of the enchantment spell
    val product = EnchantmentData.values().find { it.raw == item.id }

    // Switch to the spellbook tab
    player.focusTab(Tabs.SPELLBOOK)

    // Check if the item can be enchanted
    if (product == null || product.spell != spell) {
        player.message("You can't use this spell on this item.")
        return false
    }

    // Determine the animation and graphic to play based on the spell being cast
    val animation =
        when (spell) {
            SpellbookData.LVL_1_ENCHANT -> 719
            SpellbookData.LVL_2_ENCHANT -> 720
            else -> 721
        }
    val graphic =
        when (spell) {
            SpellbookData.LVL_1_ENCHANT -> Graphic(114, 92)
            SpellbookData.LVL_2_ENCHANT -> Graphic(115, 92)
            else -> Graphic(116, 92)
        }

    // Gets the sound associated with product
    val sfx = product.sound

    // Play the animation, graphic and sound
    player.animate(animation)
    player.graphic(graphic)
    player.playSound(sfx)

    // Remove the item from the player's inventory and add the enchanted product
    if (player.inventory.remove(Item(item.id, 1)).hasSucceeded()) {
        player.inventory.add(product.product)
        player.addXp(Skills.MAGIC, experience)
    }

    return true
}
