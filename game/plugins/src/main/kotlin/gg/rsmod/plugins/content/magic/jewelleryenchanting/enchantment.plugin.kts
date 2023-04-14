package gg.rsmod.plugins.content.magic.jewelleryenchanting

import gg.rsmod.plugins.content.magic.MagicSpells
import gg.rsmod.plugins.content.magic.SpellbookData
import gg.rsmod.plugins.content.magic.jewelleryenchanting.EnchantmentData.Companion.product

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
 * Performs an enchantment spell on an item.
 *
 * @param player The player casting the spell.
 * @param spell The enchantment spell being cast.
 * @param experience The amount of experience to give to the player for casting the spell.
 * @return True if the spell was successfully cast, false otherwise.
 */
fun performEnchantment(player: Player, spell: SpellbookData, experience: Double): Boolean {
    // Get the item the player is attempting to enchant
    val item = player.getInteractingItem()

    // Get the product of the enchantment spell
    val product = EnchantmentData.values().find { it.raw == item.id }

    // Switch to the spellbook tab
    player.runClientScript(115, GameframeTab.SPELLBOOK.id)

    // Check if the item can be enchanted
    if (product == null || product.spell != spell) {
        player.message("You can't use this spell on this item.")
        return false
    }

    // Determine the animation and graphic to play based on the spell being cast
    val animation = when (spell) {
        SpellbookData.LVL_1_ENCHANT -> 719
        SpellbookData.LVL_2_ENCHANT -> 720
        else -> 721
    }
    val graphic = when (spell) {
        SpellbookData.LVL_1_ENCHANT -> Graphic(114, 92)
        SpellbookData.LVL_2_ENCHANT -> Graphic(115, 92)
        else -> Graphic(116, 92)
    }

    // Play the sound depending on product
    if (product == EnchantmentData.SAPPHIRE_AMULET || product == EnchantmentData.SAPPHIRE_NECKLACE) player.playSound(136)
    if (product == EnchantmentData.SAPPHIRE_BRACELET || product == EnchantmentData.SAPPHIRE_RING) player.playSound(147)
    if (product == EnchantmentData.EMERALD_AMULET || product == EnchantmentData.EMERALD_NECKLACE) player.playSound(141)
    if (product == EnchantmentData.EMERALD_BRACELET || product == EnchantmentData.EMERALD_RING) player.playSound(142)
    if (product == EnchantmentData.RUBY_AMULET || product == EnchantmentData.RUBY_NECKLACE) player.playSound(145)
    if (product == EnchantmentData.RUBY_BRACELET || product == EnchantmentData.RUBY_RING) player.playSound(146)
    if (product == EnchantmentData.DIAMOND_AMULET || product == EnchantmentData.DIAMOND_NECKLACE) player.playSound(137)
    if (product == EnchantmentData.DIAMOND_BRACELET || product == EnchantmentData.DIAMOND_RING) player.playSound(138)
    if (product == EnchantmentData.DRAGONSTONE_AMULET || product == EnchantmentData.DRAGONSTONE_NECKLACE) player.playSound(139)
    if (product == EnchantmentData.DRAGONSTONE_BRACELET || product == EnchantmentData.DRAGONSTONE_RING) player.playSound(140)
    if (product == EnchantmentData.ONYX_AMULET || product == EnchantmentData.ONYX_NECKLACE) player.playSound(143)
    if (product == EnchantmentData.ONYX_BRACELET || product == EnchantmentData.ONYX_RING) player.playSound(144)

    // Play the animation and graphic
    player.animate(animation)
    player.graphic(graphic)

    // Remove the item from the player's inventory and add the enchanted product
    if (player.inventory.remove(Item(item.id, 1)).hasSucceeded()) {
        player.inventory.add(product.product)
        player.addXp(Skills.MAGIC, experience)
    }

    return true
}