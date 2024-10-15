package gg.rsmod.plugins.content.mechanics.prayer

import gg.rsmod.plugins.api.cfg.Sfx

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
        varbit = 5942,
        qpVarbit = 5971,
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
        varbit = 5943,
        qpVarbit = 5972,
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
        varbit = 5944,
        qpVarbit = 5973,
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
        varbit = 5960,
        qpVarbit = 5989,
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
        varbit = 5961,
        qpVarbit = 5990,
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
        varbit = 5945,
        qpVarbit = 5974,
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
        varbit = 5946,
        qpVarbit = 5975,
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
        varbit = 5947,
        qpVarbit = 5976,
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
        varbit = 5948,
        qpVarbit = 5977,
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
        varbit = 5949,
        qpVarbit = 5978,
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
        varbit = 5950,
        qpVarbit = 5979,
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
        varbit = 5962,
        qpVarbit = 5991,
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
        varbit = 5963,
        qpVarbit = 5992,
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
        varbit = 5951,
        qpVarbit = 5980,
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
        varbit = 5952,
        qpVarbit = 5981,
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
        varbit = 5953,
        qpVarbit = 5982,
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
        varbit = 5966,
        qpVarbit = 5995,
        level = 35,
        sound = Sfx.PROTECT_FROM_SUMMONING,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MAGIC(
        named = "Protect from Magic",
        slot = 17,
        varbit = 5954,
        qpVarbit = 5983,
        level = 37,
        sound = Sfx.PROTECT_FROM_MAGIC,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MISSILES(
        named = "Protect from Missiles",
        slot = 18,
        varbit = 5955,
        qpVarbit = 5984,
        level = 40,
        sound = Sfx.PROTECT_FROM_MISSILES,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    PROTECT_FROM_MELEE(
        named = "Protect from Melee",
        slot = 19,
        varbit = 5956,
        qpVarbit = 5985,
        level = 43,
        sound = Sfx.PROTECT_FROM_MELEE,
        drainEffect = 120,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    EAGLE_EYE(
        named = "Eagle Eye",
        slot = 20,
        varbit = 5964,
        qpVarbit = 5993,
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
        varbit = 5965,
        qpVarbit = 5994,
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
        varbit = 5957,
        qpVarbit = 5986,
        level = 46,
        sound = Sfx.RETRIBUTION,
        drainEffect = 30,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    REDEMPTION(
        named = "Redemption",
        slot = 23,
        varbit = 5958,
        qpVarbit = 5987,
        level = 49,
        sound = Sfx.REDEMPTION,
        drainEffect = 60,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    SMITE(
        named = "Smite",
        slot = 24,
        varbit = 5959,
        qpVarbit = 5988,
        level = 52,
        sound = Sfx.SMITE,
        drainEffect = 180,
        group = PrayerGroup.OVERHEAD,
        curse = false,
    ),

    CHIVALRY(
        named = "Chivalry",
        slot = 25,
        varbit = 5967,
        qpVarbit = 5996,
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
        varbit = 7768,
        qpVarbit = 7770,
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
        varbit = 5968,
        qpVarbit = 5997,
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
        varbit = 7381,
        qpVarbit = 7382,
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
        varbit = 7769,
        qpVarbit = 7771,
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
