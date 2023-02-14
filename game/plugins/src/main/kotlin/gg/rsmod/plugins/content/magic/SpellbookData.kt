package gg.rsmod.plugins.content.magic

import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items

enum class SpellbookData(
    val interfaceId: Int,
    val component: Int,
    val sprite: Int,
    val spellType: SpellType,
    val spellName: String,
    val level: Int,
    val runes: List<Item> = emptyList()
)  {
    HOME_TELEPORT(
        interfaceId = 192,
        component = 24,
        sprite = 356,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Lumbridge Home Teleport",
        level = 1,
    ),
    MOBILISING_ARMIES(
        interfaceId = 192,
        component = 37,
        sprite = 1564,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Mobilising Armies Teleport",
        level = 10,
        runes = listOf(Item(Items.WATER_RUNE), Item(Items.AIR_RUNE, 3), Item(Items.LAW_RUNE))
    ),
    VARROCK_TELEPORT(
        interfaceId = 192,
        component = 40,
        sprite = 77,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Varrock Teleport",
        level = 25,
        runes = listOf(Item(Items.FIRE_RUNE), Item(Items.AIR_RUNE, 3), Item(Items.LAW_RUNE))
    ),
    LUMBRIDGE_TELEPORT(
        interfaceId = 192,
        component = 43,
        sprite = 80,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Lumbridge Teleport",
        level = 31,
        runes = listOf(Item(Items.EARTH_RUNE), Item(Items.AIR_RUNE, 3), Item(Items.LAW_RUNE))
    ),
    FALADOR_TELEPORT(
        interfaceId = 192,
        component = 46,
        sprite = 83,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Falador Teleport",
        level = 37,
        runes = listOf(Item(Items.WATER_RUNE), Item(Items.AIR_RUNE, 3), Item(Items.LAW_RUNE))
    ),
    CAMELOT_TELEPORT(
        interfaceId = 192,
        component = 51,
        sprite = 87,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Camelot Teleport",
        level = 45,
        runes = listOf(Item(Items.AIR_RUNE, 5), Item(Items.LAW_RUNE))
    ),
    ARDOUGNE_TELEPORT(
        interfaceId = 192,
        component = 57,
        sprite = 104,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Ardougne Teleport",
        level = 51,
        runes = listOf(Item(Items.WATER_RUNE, 2), Item(Items.LAW_RUNE, 2))
    ),
    WATCHTOWER_TELEPORT(
        interfaceId = 192,
        component = 62,
        sprite = 105,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Watchtower Teleport",
        level = 58,
        runes = listOf(Item(Items.EARTH_RUNE, 2), Item(Items.LAW_RUNE, 2))
    ),
    TROLLHEIM_TELEPORT(
        interfaceId = 192,
        component = 69,
        sprite = 373,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Trollheim Teleport",
        level = 61,
        runes = listOf(Item(Items.FIRE_RUNE, 2), Item(Items.LAW_RUNE, 2))
    ),
    APE_ATOLL_TELEPORT(
        interfaceId = 192,
        component = 72,
        sprite = 407,
        spellType = SpellType.TELEPORT_SPELL_TYPE,
        spellName = "Teleport to Ape Atoll",
        level = 64,
        runes = listOf(Item(Items.FIRE_RUNE, 2), Item(Items.WATER_RUNE, 2), Item(Items.LAW_RUNE, 2), Item(Items.BANANA))
    ),
}