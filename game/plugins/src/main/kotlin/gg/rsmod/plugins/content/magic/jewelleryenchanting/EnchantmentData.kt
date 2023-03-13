package gg.rsmod.plugins.content.magic.jewelleryenchanting

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class EnchantmentData(val raw: Int, val product: Int, val spell: SpellbookData) {
    SAPPHIRE_RING(raw = Items.SAPPHIRE_RING, product = Items.RING_OF_RECOIL, spell = SpellbookData.LVL_1_ENCHANT),
    EMERALD_RING(raw = Items.EMERALD_RING, product = Items.RING_OF_DUELLING_8, spell = SpellbookData.LVL_2_ENCHANT),
    RUBY_RING(raw = Items.RUBY_RING, product = Items.RING_OF_FORGING, spell = SpellbookData.LVL_3_ENCHANT),
    DIAMOND_RING(raw = Items.DIAMOND_RING, product = Items.RING_OF_LIFE, spell = SpellbookData.LVL_4_ENCHANT),
    DRAGONSTONE_RING(raw = Items.DRAGONSTONE_RING, product = Items.RING_OF_WEALTH_4, spell = SpellbookData.LVL_5_ENCHANT),
    ONYX_RING(raw = Items.ONYX_RING, product = Items.RING_OF_STONE, spell = SpellbookData.LVL_6_ENCHANT),
}