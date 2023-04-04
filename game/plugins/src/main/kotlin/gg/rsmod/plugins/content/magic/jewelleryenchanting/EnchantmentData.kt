package gg.rsmod.plugins.content.magic.jewelleryenchanting

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class EnchantmentData(val raw: Int, val product: Int, val spell: SpellbookData) {
    SAPPHIRE_RING(
        raw = Items.SAPPHIRE_RING,
        product = Items.RING_OF_RECOIL,
        spell = SpellbookData.LVL_1_ENCHANT
    ),
    EMERALD_RING(
        raw = Items.EMERALD_RING,
        product = Items.RING_OF_DUELLING_8,
        spell = SpellbookData.LVL_2_ENCHANT
    ),
    RUBY_RING(
        raw = Items.RUBY_RING,
        product = Items.RING_OF_FORGING,
        spell = SpellbookData.LVL_3_ENCHANT
    ),
    DIAMOND_RING(
        raw = Items.DIAMOND_RING,
        product = Items.RING_OF_LIFE,
        spell = SpellbookData.LVL_4_ENCHANT
    ),
    DRAGONSTONE_RING(
        raw = Items.DRAGONSTONE_RING,
        product = Items.RING_OF_WEALTH,
        spell = SpellbookData.LVL_5_ENCHANT
    ),
    ONYX_RING(
        raw = Items.ONYX_RING,
        product = Items.RING_OF_STONE,
        spell = SpellbookData.LVL_6_ENCHANT
    ),
    SAPPHIRE_NECKLACE(
        raw = Items.SAPPHIRE_NECKLACE,
        product = Items.GAMES_NECKLACE_8,
        spell = SpellbookData.LVL_1_ENCHANT
    ),
    EMERALD_NECKLACE(
        raw = Items.EMERALD_NECKLACE,
        product = Items.BINDING_NECKLACE,
        spell = SpellbookData.LVL_2_ENCHANT
    ),
    RUBY_NECKLACE(
        raw = Items.RUBY_NECKLACE,
        product = Items.DIGSITE_PENDANT_5,
        spell = SpellbookData.LVL_3_ENCHANT
    ),
    DIAMOND_NECKLACE(
        raw = Items.DIAMOND_NECKLACE,
        product = Items.PHOENIX_NECKLACE,
        spell = SpellbookData.LVL_4_ENCHANT
    ),
    DRAGONSTONE_NECKLACE(
        raw = Items.DRAGON_NECKLACE,
        product = Items.SKILLS_NECKLACE_4,
        spell = SpellbookData.LVL_5_ENCHANT
    ),
    ONYX_NECKLACE(
        raw = Items.ONYX_NECKLACE,
        product = Items.BERSERKER_NECKLACE,
        spell = SpellbookData.LVL_6_ENCHANT
    ),
    SAPPHIRE_AMULET(
        raw = Items.SAPPHIRE_AMULET_1694,
        product = Items.AMULET_OF_MAGIC,
        spell = SpellbookData.LVL_1_ENCHANT
    ),
    EMERALD_AMULET(
        raw = Items.EMERALD_AMULET_1696,
        product = Items.AMULET_OF_DEFENCE,
        spell = SpellbookData.LVL_2_ENCHANT
    ),
    RUBY_AMULET(
        raw = Items.RUBY_AMULET_1698,
        product = Items.AMULET_OF_STRENGTH,
        spell = SpellbookData.LVL_3_ENCHANT
    ),
    DIAMOND_AMULET(
        raw = Items.DIAMOND_AMULET_1700,
        product = Items.AMULET_OF_POWER,
        spell = SpellbookData.LVL_4_ENCHANT
    ),
    DRAGONSTONE_AMULET(
        raw = Items.DRAGONSTONE_AMMY_1702,
        product = Items.AMULET_OF_GLORY_4,
        spell = SpellbookData.LVL_5_ENCHANT
    ),
    ONYX_AMULET(
        raw = Items.ONYX_AMULET_6581,
        product = Items.AMULET_OF_FURY,
        spell = SpellbookData.LVL_6_ENCHANT
    ),
    SAPPHIRE_BRACELET(
        raw = Items.SAPPHIRE_BRACELET,
        product = Items.BRACELET_OF_CLAY,
        spell = SpellbookData.LVL_1_ENCHANT
    ),
    EMERALD_BRACELET(
        raw = Items.EMERALD_BRACELET,
        product = Items.CASTLE_WARS_BRACE_3,
        spell = SpellbookData.LVL_2_ENCHANT
    ),
    RUBY_BRACELET(
        raw = Items.RUBY_BRACELET,
        product = Items.INOCULATION_BRACE,
        spell = SpellbookData.LVL_3_ENCHANT
    ),
    DIAMOND_BRACELET(
        raw = Items.DIAMOND_BRACELET,
        product = Items.FORINTHRY_BRACE_5,
        spell = SpellbookData.LVL_4_ENCHANT
    ),
    DRAGONSTONE_BRACELET(
        raw = Items.DRAGON_BRACELET,
        product = Items.COMBAT_BRACELET_4,
        spell = SpellbookData.LVL_5_ENCHANT
    ),
    ONYX_BRACELET(
        raw = Items.ONYX_BRACELET,
        product = Items.REGEN_BRACELET,
        spell = SpellbookData.LVL_6_ENCHANT
    )
}