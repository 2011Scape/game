package gg.rsmod.plugins.content.magic.jewelleryenchanting

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.SpellbookData

/**
 * @author Alycia <https://github.com/alycii>
 */
enum class EnchantmentData(
    val raw: Int,
    val product: Int,
    val spell: SpellbookData,
    val sound: Int,
) {
    SAPPHIRE_RING(
        raw = Items.SAPPHIRE_RING,
        product = Items.RING_OF_RECOIL,
        spell = SpellbookData.LVL_1_ENCHANT,
        sound = 147,
    ),
    EMERALD_RING(
        raw = Items.EMERALD_RING,
        product = Items.RING_OF_DUELLING_8,
        spell = SpellbookData.LVL_2_ENCHANT,
        sound = 142,
    ),
    RUBY_RING(
        raw = Items.RUBY_RING,
        product = Items.RING_OF_FORGING,
        spell = SpellbookData.LVL_3_ENCHANT,
        sound = 146,
    ),
    DIAMOND_RING(
        raw = Items.DIAMOND_RING,
        product = Items.RING_OF_LIFE,
        spell = SpellbookData.LVL_4_ENCHANT,
        sound = 138,
    ),
    DRAGONSTONE_RING(
        raw = Items.DRAGONSTONE_RING,
        product = Items.RING_OF_WEALTH,
        spell = SpellbookData.LVL_5_ENCHANT,
        sound = 140,
    ),
    ONYX_RING(
        raw = Items.ONYX_RING_6575,
        product = Items.RING_OF_STONE,
        spell = SpellbookData.LVL_6_ENCHANT,
        sound = 144,
    ),
    SAPPHIRE_NECKLACE(
        raw = Items.SAPPHIRE_NECKLACE,
        product = Items.GAMES_NECKLACE_8,
        spell = SpellbookData.LVL_1_ENCHANT,
        sound = 136,
    ),
    EMERALD_NECKLACE(
        raw = Items.EMERALD_NECKLACE,
        product = Items.BINDING_NECKLACE,
        spell = SpellbookData.LVL_2_ENCHANT,
        sound = 141,
    ),
    RUBY_NECKLACE(
        raw = Items.RUBY_NECKLACE,
        product = Items.DIGSITE_PENDANT_5,
        spell = SpellbookData.LVL_3_ENCHANT,
        sound = 145,
    ),
    DIAMOND_NECKLACE(
        raw = Items.DIAMOND_NECKLACE,
        product = Items.PHOENIX_NECKLACE,
        spell = SpellbookData.LVL_4_ENCHANT,
        sound = 137,
    ),
    DRAGONSTONE_NECKLACE(
        raw = Items.DRAGON_NECKLACE,
        product = Items.SKILLS_NECKLACE_4,
        spell = SpellbookData.LVL_5_ENCHANT,
        sound = 139,
    ),
    ONYX_NECKLACE(
        raw = Items.ONYX_NECKLACE_6577,
        product = Items.BERSERKER_NECKLACE,
        spell = SpellbookData.LVL_6_ENCHANT,
        sound = 143,
    ),
    SAPPHIRE_AMULET(
        raw = Items.SAPPHIRE_AMULET_1694,
        product = Items.AMULET_OF_MAGIC,
        spell = SpellbookData.LVL_1_ENCHANT,
        sound = 136,
    ),
    EMERALD_AMULET(
        raw = Items.EMERALD_AMULET_1696,
        product = Items.AMULET_OF_DEFENCE,
        spell = SpellbookData.LVL_2_ENCHANT,
        sound = 141,
    ),
    RUBY_AMULET(
        raw = Items.RUBY_AMULET_1698,
        product = Items.AMULET_OF_STRENGTH,
        spell = SpellbookData.LVL_3_ENCHANT,
        sound = 145,
    ),
    DIAMOND_AMULET(
        raw = Items.DIAMOND_AMULET_1700,
        product = Items.AMULET_OF_POWER,
        spell = SpellbookData.LVL_4_ENCHANT,
        sound = 137,
    ),
    DRAGONSTONE_AMULET(
        raw = Items.DRAGONSTONE_AMMY_1702,
        product = Items.AMULET_OF_GLORY_4,
        spell = SpellbookData.LVL_5_ENCHANT,
        sound = 139,
    ),
    ONYX_AMULET(
        raw = Items.ONYX_AMULET_6581,
        product = Items.AMULET_OF_FURY,
        spell = SpellbookData.LVL_6_ENCHANT,
        sound = 143,
    ),
    SAPPHIRE_BRACELET(
        raw = Items.SAPPHIRE_BRACELET,
        product = Items.BRACELET_OF_CLAY,
        spell = SpellbookData.LVL_1_ENCHANT,
        sound = 147,
    ),
    EMERALD_BRACELET(
        raw = Items.EMERALD_BRACELET,
        product = Items.CASTLE_WARS_BRACE_3,
        spell = SpellbookData.LVL_2_ENCHANT,
        sound = 142,
    ),
    RUBY_BRACELET(
        raw = Items.RUBY_BRACELET,
        product = Items.INOCULATION_BRACE,
        spell = SpellbookData.LVL_3_ENCHANT,
        sound = 146,
    ),
    DIAMOND_BRACELET(
        raw = Items.DIAMOND_BRACELET,
        product = Items.FORINTHRY_BRACE_5,
        spell = SpellbookData.LVL_4_ENCHANT,
        sound = 138,
    ),
    DRAGONSTONE_BRACELET(
        raw = Items.DRAGON_BRACELET,
        product = Items.COMBAT_BRACELET_4,
        spell = SpellbookData.LVL_5_ENCHANT,
        sound = 140,
    ),
    ONYX_BRACELET(
        raw = Items.ONYX_BRACELET,
        product = Items.REGEN_BRACELET,
        spell = SpellbookData.LVL_6_ENCHANT,
        sound = 144,
    ),
}
