package gg.rsmod.plugins.content.skills.runecrafting

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.interpolate

/**
 * @author Triston Plummer ("Dread")
 *
 * @param id        The id of the rune that is to be crafted
 * @param essence   An array containing the valid essence ids
 * @param level     The level required to craft the rune
 * @param xp        The xp granted per crafted rune
 */
enum class Rune(
    val id: Int,
    val essence: IntArray = intArrayOf(Items.PURE_ESSENCE),
    val level: Int,
    val xp: Double,
) {
    AIR(id = Items.AIR_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 1, xp = 5.0),
    MIND(id = Items.MIND_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 2, xp = 5.5),
    WATER(id = Items.WATER_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 5, xp = 6.0),
    EARTH(id = Items.EARTH_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 9, xp = 6.5),
    FIRE(id = Items.FIRE_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 14, xp = 7.0),
    BODY(id = Items.BODY_RUNE, essence = intArrayOf(Items.RUNE_ESSENCE, Items.PURE_ESSENCE), level = 20, xp = 7.5),
    COSMIC(id = Items.COSMIC_RUNE, level = 27, xp = 8.0),
    CHAOS(id = Items.CHAOS_RUNE, level = 35, xp = 8.5),
    ASTRAL(id = Items.ASTRAL_RUNE, level = 40, xp = 8.7),
    NATURE(id = Items.NATURE_RUNE, level = 44, xp = 9.0),
    LAW(id = Items.LAW_RUNE, level = 54, xp = 9.5),
    DEATH(id = Items.DEATH_RUNE, level = 65, xp = 10.0),
    BLOOD(id = Items.BLOOD_RUNE, level = 77, xp = 10.5),
    ;

    /**
     * Gets the rune count multiplier for the player's Runecrafting level.
     * Taken from https://github.com/apollo-rsps/apollo/blob/kotlin-experiments/game/plugin/skills/runecrafting/src/Rune.kt
     *
     * @param level The runecrafting level
     *
     * @return The number of runes per essence.
     */
    private fun getBonusMultiplier(level: Int): Int =
        when (this) {
            AIR -> level / 11 + 1
            MIND -> level / 14 + 1
            WATER -> level / 19 + 1
            EARTH -> level / 26 + 1
            FIRE -> level / 35 + 1
            BODY -> level / 46 + 1
            COSMIC -> level / 59 + 1
            CHAOS -> level / 74 + 1
            NATURE -> level / 91 + 1
            else -> 1
        }

    private fun getLevelForMultiplier(multiplier: Int): Int =
        when (this) {
            AIR -> 11 * (multiplier - 1)
            MIND -> 14 * (multiplier - 1)
            WATER -> 19 * (multiplier - 1)
            EARTH -> 26 * (multiplier - 1)
            FIRE -> 35 * (multiplier - 1)
            BODY -> 46 * (multiplier - 1)
            COSMIC -> 59 * (multiplier - 1)
            CHAOS -> 74 * (multiplier - 1)
            NATURE -> 91 * (multiplier - 1)
            else -> 1
        }

    /**
     * Gets the amount of runes for the player's Runecrafting level, given the amount of essence used.
     * For each rune there is a chance to return the next multiplier (i.e., one more rune than the level suggests).
     * The chance for this is 0-60%, interpolated from the level for the current multiplier and the level of the next multiplier
     */
    fun getAmount(
        level: Int,
        player: Player,
        essUsed: Int,
    ): Int {
        val baseMultiplier = getBonusMultiplier(level)
        val maxMultiplier = getBonusMultiplier(99)
        return if (baseMultiplier == maxMultiplier) {
            baseMultiplier * essUsed
        } else {
            val currentStart = getLevelForMultiplier(baseMultiplier)
            val nextStart = getLevelForMultiplier(baseMultiplier + 1)
            val percentageChance = level.interpolate(0.0, 60.0, currentStart, nextStart)
            return (1..essUsed).sumOf {
                if (player.world.percentChance(percentageChance)) baseMultiplier + 1 else baseMultiplier
            }
        }
    }

    companion object {
        val values = enumValues<Rune>()
    }
}
