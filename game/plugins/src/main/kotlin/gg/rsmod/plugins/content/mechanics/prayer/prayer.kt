package gg.rsmod.plugins.content.mechanics.prayer

import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.cfg.Varbits

/**
 * @authors
 * Tom <rspsmods@gmail.com>
 * Harley <github.com/HarleyGilpin>
 */

enum class Prayer(
    val named: String,
    val slot: Int,
    val varbit: Int,
    val qpVarbit: Int,
    val level: Int,
    val sound: Int,
    val drainEffect: Int,
    val group: PrayerGroup?,
    val curse: Boolean,
    vararg val overlap: PrayerGroup? = arrayOf(),
) {
    THICK_SKIN(
        "Thick Skin",
        slot = 0,
        varbit = Varbits.THICK_SKIN,
        qpVarbit = Varbits.THICK_SKIN_QUICK_PRAYER,
        level = 1,
        sound = Sfx.THICK_SKIN,
        drainEffect = 30,
        group = PrayerGroup.DEFENCE,
        curse = false,
        overlap = arrayOf(PrayerGroup.MELEE_COMBAT),
    ),

    BURST_OF_STRENGTH(
        named = "Burst of Strength",
        slot = 1,
        varbit = Varbits.BURST_OF_STRENGTH,
        qpVarbit = Varbits.BURST_OF_STRENGTH_QUICK_PRAYER,
        level = 4,
        sound = Sfx.STRENGTH_BURST,
        drainEffect = 30,
        group = PrayerGroup.STRENGTH,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    CLARITY_OF_THOUGHT(
        named = "Clarity of Thought",
        slot = 2,
        varbit = Varbits.CLARITY_OF_THOUGHT,
        qpVarbit = Varbits.CLARITY_OF_THOUGHT_QUICK_PRAYER,
        level = 7,
        sound = Sfx.CLARITY,
        drainEffect = 30,
        group = PrayerGroup.ATTACK,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    SHARP_EYE(
        named = "Sharp Eye",
        slot = 3,
        varbit = Varbits.SHARP_EYE,
        qpVarbit = Varbits.SHARP_EYE_QUICK_PRAYER,
        level = 8,
        sound = Sfx.SHARP_EYE,
        drainEffect = 30,
        group = PrayerGroup.RANGED,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    MYSTIC_WILL(
        named = "Mystic Will",
        slot = 4,
        varbit = Varbits.MYSTIC_WILL,
        qpVarbit = Varbits.MYSTIC_WILL_QUICK_PRAYER,
        level = 9,
        sound = Sfx.MYSTIC_WILL,
        drainEffect = 30,
        group = PrayerGroup.MAGIC,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.RANGED, PrayerGroup.MELEE_COMBAT),
    ),

    ROCK_SKIN(
        named = "Rock Skin",
        slot = 5,
        varbit = Varbits.ROCK_SKIN,
        qpVarbit = Varbits.ROCK_SKIN_QUICK_PRAYER,
        level = 10,
        sound = Sfx.ROCK_SKIN,
        drainEffect = 60,
        group = PrayerGroup.DEFENCE,
        curse = false,
        overlap = arrayOf(PrayerGroup.MELEE_COMBAT),
    ),

    SUPERHUMAN_STRENGTH(
        named = "Superhuman Strength",
        slot = 6,
        varbit = Varbits.SUPERHUMAN_STRENGTH,
        qpVarbit = Varbits.SUPERHUMAN_STRENGTH_QUICK_PRAYER,
        level = 13,
        sound = Sfx.SUPERHUMAN_STRENGTH,
        drainEffect = 60,
        group = PrayerGroup.STRENGTH,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    IMPROVED_REFLEXES(
        named = "Improved Reflexes",
        slot = 7,
        varbit = Varbits.IMPROVED_REFLEXES,
        qpVarbit = Varbits.IMPROVED_REFLEXES_QUICK_PRAYER,
        level = 16,
        sound = Sfx.IMPROVED_REFLEXES,
        drainEffect = 60,
        group = PrayerGroup.ATTACK,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    RAPID_RESTORE(
        named = "Rapid Restore",
        slot = 8,
        varbit = Varbits.RAPID_RESTORE,
        qpVarbit = Varbits.RAPID_RESTORE_QUICK_PRAYER,
        level = 19,
        sound = Sfx.RAPID_RESTORE,
        drainEffect = 10,
        group = PrayerGroup.RESTORATION,
        curse = false,
        overlap = arrayOf(PrayerGroup.DUNGEONEERING_RESTORATION),
    ),

    RAPID_HEAL(
        named = "Rapid Heal",
        slot = 9,
        varbit = Varbits.RAPID_HEAL,
        qpVarbit = Varbits.RAPID_HEAL_QUICK_PRAYER,
        level = 22,
        sound = Sfx.RAPID_HEAL,
        drainEffect = 20,
        group = PrayerGroup.RESTORATION,
        curse = false,
        overlap = arrayOf(PrayerGroup.DUNGEONEERING_RESTORATION),
    ),

    PROTECT_ITEM(
        named = "Protect Item",
        slot = 10,
        varbit = Varbits.PROTECT_ITEM,
        qpVarbit = Varbits.PROTECT_ITEM_QUICK_PRAYER,
        level = 25,
        sound = Sfx.PROTECT_ITEMS,
        drainEffect = 20,
        group = null,
        curse = false,
        overlap = arrayOf(),
    ),

    HAWK_EYE(
        named = "Hawk Eye",
        slot = 11,
        varbit = Varbits.HAWK_EYE,
        qpVarbit = Varbits.HAWK_EYE_QUICK_PRAYER,
        level = 26,
        sound = Sfx.HAWK_EYE,
        drainEffect = 60,
        group = PrayerGroup.RANGED,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    MYSTIC_LORE(
        named = "Mystic Lore",
        slot = 12,
        varbit = Varbits.MYSTIC_LORE,
        qpVarbit = Varbits.MYSTIC_LORE_QUICK_PRAYER,
        level = 27,
        sound = Sfx.MYSTIC_LORE,
        drainEffect = 60,
        group = PrayerGroup.MAGIC,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.RANGED, PrayerGroup.MELEE_COMBAT),
    ),

    STEEL_SKIN(
        named = "Steel Skin",
        slot = 13,
        varbit = Varbits.STEEL_SKIN,
        qpVarbit = Varbits.STEEL_SKIN_QUICK_PRAYER,
        level = 28,
        sound = Sfx.STEEL_SKIN,
        drainEffect = 120,
        group = PrayerGroup.DEFENCE,
        curse = false,
        overlap = arrayOf(PrayerGroup.MELEE_COMBAT),
    ),

    ULTIMATE_STRENGTH(
        named = "Ultimate Strength",
        slot = 14,
        varbit = Varbits.ULTIMATE_STRENGTH,
        qpVarbit = Varbits.ULTIMATE_STRENGTH_QUICK_PRAYER,
        level = 31,
        sound = Sfx.ULTIMATE_STRENGTH,
        drainEffect = 120,
        group = PrayerGroup.STRENGTH,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    INCREDIBLE_REFLEXES(
        named = "Incredible Reflexes",
        slot = 15,
        varbit = Varbits.INCREDIBLE_REFLEXES,
        qpVarbit = Varbits.INCREDIBLE_REFLEXES_QUICK_PRAYER,
        level = 34,
        sound = Sfx.INCREDIBLE_REFLEXES,
        drainEffect = 120,
        group = PrayerGroup.ATTACK,
        curse = false,
        overlap = arrayOf(PrayerGroup.RANGED, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    PROTECT_FROM_SUMMONING(
        named = "Protect from Summoning",
        slot = 16,
        varbit = Varbits.PROTECT_FROM_SUMMONING,
        qpVarbit = Varbits.PROTECT_FROM_SUMMONING_QUICK_PRAYER,
        level = 35,
        sound = Sfx.PROTECT_FROM_SUMMONING,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MAGIC(
        named = "Protect from Magic",
        slot = 17,
        varbit = Varbits.PROTECT_FROM_MAGIC,
        qpVarbit = Varbits.PROTECT_FROM_MAGIC_QUICK_PRAYER,
        level = 37,
        sound = Sfx.PROTECT_FROM_MAGIC,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MISSILES(
        named = "Protect from Missiles",
        slot = 18,
        varbit = Varbits.PROTECT_FROM_MISSILES,
        qpVarbit = Varbits.PROTECT_FROM_MISSILES_QUICK_PRAYER,
        level = 40,
        sound = Sfx.PROTECT_FROM_MISSILES,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MELEE(
        named = "Protect from Melee",
        slot = 19,
        varbit = Varbits.PROTECT_FROM_MELEE,
        qpVarbit = Varbits.PROTECT_FROM_MELEE_QUICK_PRAYER,
        level = 43,
        sound = Sfx.PROTECT_FROM_MELEE,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    EAGLE_EYE(
        named = "Eagle Eye",
        slot = 20,
        varbit = Varbits.EAGLE_EYE,
        qpVarbit = Varbits.EAGLE_EYE_QUICK_PRAYER,
        level = 44,
        sound = Sfx.EAGLE_EYE,
        drainEffect = 120,
        group = PrayerGroup.RANGED,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.MAGIC, PrayerGroup.MELEE_COMBAT),
    ),

    MYSTIC_MIGHT(
        named = "Mystic Might",
        slot = 21,
        varbit = Varbits.MYSTIC_MIGHT,
        qpVarbit = Varbits.MYSTIC_MIGHT_QUICK_PRAYER,
        level = 45,
        sound = Sfx.MYSTIC_MIGHT,
        drainEffect = 120,
        group = PrayerGroup.MAGIC,
        curse = false,
        overlap = arrayOf(PrayerGroup.ATTACK, PrayerGroup.STRENGTH, PrayerGroup.RANGED, PrayerGroup.MELEE_COMBAT),
    ),

    RETRIBUTION(
        named = "Retribution",
        slot = 22,
        varbit = Varbits.RETRIBUTION,
        qpVarbit = Varbits.RETRIBUTION_QUICK_PRAYER,
        level = 46,
        sound = Sfx.RETRIBUTION,
        drainEffect = 30,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    REDEMPTION(
        named = "Redemption",
        slot = 23,
        varbit = Varbits.REDEMPTION,
        qpVarbit = Varbits.REDEMPTION_QUICK_PRAYER,
        level = 49,
        sound = Sfx.REDEMPTION,
        drainEffect = 60,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    SMITE(
        named = "Smite",
        slot = 24,
        varbit = Varbits.SMITE,
        qpVarbit = Varbits.SMITE_QUICK_PRAYER,
        level = 52,
        sound = Sfx.SMITE,
        drainEffect = 180,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    CHIVALRY(
        named = "Chivalry",
        slot = 25,
        varbit = Varbits.CHIVALRY,
        qpVarbit = Varbits.CHIVALRY_QUICK_PRAYER,
        level = 60,
        sound = 3826,
        drainEffect = 240,
        group = PrayerGroup.MELEE_COMBAT,
        curse = false,
        overlap =
            arrayOf(
                PrayerGroup.ATTACK,
                PrayerGroup.STRENGTH,
                PrayerGroup.DEFENCE,
                PrayerGroup.RANGED,
                PrayerGroup.MAGIC,
            ),
    ),

    RAPID_RENEWAL(
        named = "Rapid Renewal",
        slot = 26,
        varbit = Varbits.RAPID_RENEWAL,
        qpVarbit = Varbits.RAPID_RENEWAL_QUICK_PRAYER,
        level = 60,
        sound = -1 /*TODO*/,
        drainEffect = 240,
        group = PrayerGroup.DUNGEONEERING_RESTORATION,
        curse = false,
        overlap = arrayOf(PrayerGroup.RESTORATION),
    ),

    PIETY(
        named = "Piety",
        slot = 27,
        varbit = Varbits.PIETY,
        qpVarbit = Varbits.PIETY_QUICK_PRAYER,
        level = 70,
        sound = Sfx.KR_PIETY,
        drainEffect = 240,
        group = PrayerGroup.MELEE_COMBAT,
        curse = false,
        overlap =
            arrayOf(
                PrayerGroup.ATTACK,
                PrayerGroup.STRENGTH,
                PrayerGroup.DEFENCE,
                PrayerGroup.RANGED,
                PrayerGroup.MAGIC,
            ),
    ),

    RIGOUR(
        named = "Rigour",
        slot = 28,
        varbit = Varbits.RIGOUR,
        qpVarbit = Varbits.RIGOUR_QUICK_PRAYER,
        level = 74,
        sound = -1 /*TODO*/,
        drainEffect = 240, // TODO MAKE SURE FINE
        group = PrayerGroup.RANGED,
        curse = false,
        overlap =
            arrayOf(
                PrayerGroup.ATTACK,
                PrayerGroup.STRENGTH,
                PrayerGroup.DEFENCE,
                PrayerGroup.MAGIC,
                PrayerGroup.MELEE_COMBAT,
            ),
    ),

    AUGURY(
        named = "Augury",
        slot = 29,
        varbit = Varbits.AUGURY,
        qpVarbit = Varbits.AUGURY_QUICK_PRAYER,
        level = 77,
        sound = -1 /*TODO*/,
        drainEffect = 240, // TODO MAKE SURE FINE
        group = PrayerGroup.MAGIC,
        curse = false,
        overlap =
            arrayOf(
                PrayerGroup.ATTACK,
                PrayerGroup.STRENGTH,
                PrayerGroup.DEFENCE,
                PrayerGroup.RANGED,
                PrayerGroup.MELEE_COMBAT,
            ),
    ),

    ;

    companion object {
        val values = enumValues<Prayer>()
    }
}
