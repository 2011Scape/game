package gg.rsmod.plugins.content.combat.strategy.magic

import gg.rsmod.game.model.Graphic

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class CombatSpell(
    val uniqueId: Int,
    val componentId: Int,
    val maxHit: Int,
    val castGfx: Graphic?,
    val castAnimation: Array<Int>,
    val projectile: Int,
    val secondProjectile: Int = -1,
    val thirdProjectile: Int = -1,
    val impactGfx: Graphic?,
    val autoCastId: Int,
    val experience: Double = 0.0,
) {
    /**
     * Standard.
     */

    WIND_RUSH(
        uniqueId = 3759,
        componentId = 98,
        maxHit = 1,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 458,
        impactGfx = Graphic(id = 463, height = 32),
        autoCastId = 143,
        experience = 0.2,
    ),

    WIND_STRIKE(
        uniqueId = 15,
        componentId = 25,
        maxHit = 2,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 459,
        impactGfx = Graphic(id = 464, height = 32),
        autoCastId = 3,
        experience = 5.5,
    ),

    WATER_STRIKE(
        uniqueId = 17,
        componentId = 28,
        maxHit = 4,
        castGfx = Graphic(2701, 22),
        castAnimation = arrayOf(10542, 14220),
        projectile = 2703,
        impactGfx = Graphic(id = 2708, height = 32),
        autoCastId = 5,
        experience = 7.5,
    ),

    EARTH_STRIKE(
        uniqueId = 19,
        componentId = 30,
        maxHit = 6,
        castGfx = Graphic(2713, -4),
        castAnimation = arrayOf(14209, 14222),
        projectile = 2718,
        impactGfx = Graphic(id = 2723, height = 60),
        autoCastId = 7,
        experience = 9.5,
    ),

    FIRE_STRIKE(
        uniqueId = 71,
        componentId = 32,
        maxHit = 8,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2729,
        impactGfx = Graphic(id = 2737, height = 32),
        autoCastId = 9,
        experience = 11.5,
    ),

    WIND_BOLT(
        uniqueId = 73,
        componentId = 34,
        maxHit = 9,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 460,
        impactGfx = Graphic(id = 464, height = 32),
        autoCastId = 11,
        experience = 13.5,
    ),

    WATER_BOLT(
        uniqueId = 76,
        componentId = 39,
        maxHit = 10,
        castGfx = Graphic(2701, 22),
        castAnimation = arrayOf(10542, 14220),
        projectile = 2704,
        impactGfx = Graphic(id = 2709, height = 32),
        autoCastId = 13,
        experience = 16.5,
    ),

    EARTH_BOLT(
        uniqueId = 79,
        componentId = 42,
        maxHit = 11,
        castGfx = Graphic(2714, 22),
        castAnimation = arrayOf(14209, 14222),
        projectile = 2719,
        impactGfx = Graphic(id = 2724, height = 32),
        autoCastId = 15,
        experience = 19.5,
    ),

    FIRE_BOLT(
        uniqueId = 82,
        componentId = 45,
        maxHit = 12,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2730,
        impactGfx = Graphic(id = 2738, height = 32),
        autoCastId = 17,
        experience = 22.5,
    ),

    WIND_BLAST(
        uniqueId = 85,
        componentId = 49,
        maxHit = 13,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 461,
        impactGfx = Graphic(id = 1863, height = 32),
        autoCastId = 19,
        experience = 25.5,
    ),

    WATER_BLAST(
        uniqueId = 88,
        componentId = 52,
        maxHit = 14,
        castGfx = Graphic(2701, 22),
        castAnimation = arrayOf(10542, 14220),
        projectile = 2705,
        impactGfx = Graphic(id = 2706, height = 32),
        autoCastId = 21,
        experience = 28.5,
    ),

    EARTH_BLAST(
        uniqueId = 90,
        componentId = 58,
        maxHit = 15,
        castGfx = Graphic(2715, 22),
        castAnimation = arrayOf(14209, 14222),
        projectile = 2720,
        impactGfx = Graphic(id = 2425, height = 32),
        autoCastId = 23,
        experience = 31.5,
    ),

    FIRE_BLAST(
        uniqueId = 95,
        componentId = 63,
        maxHit = 16,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2731,
        secondProjectile = 2732,
        impactGfx = Graphic(id = 2739, height = 32),
        autoCastId = 25,
        experience = 34.5,
    ),

    SARADOMIN_STRIKE(
        uniqueId = 501,
        componentId = 66,
        maxHit = 20,
        castGfx = Graphic(-1, 0),
        castAnimation = arrayOf(811, 811),
        projectile = 0,
        secondProjectile = 0,
        impactGfx = Graphic(id = 76, height = 100),
        autoCastId = 26,
        experience = 34.5,
    ),

    CLAWS_OF_GUTHIX(
        uniqueId = 502,
        componentId = 67,
        maxHit = 20,
        castGfx = Graphic(-1, 0),
        castAnimation = arrayOf(811, 811, 211),
        projectile = 0,
        secondProjectile = 0,
        impactGfx = Graphic(id = 77, height = 100),
        autoCastId = 28,
        experience = 34.5,
    ),

    FLAMES_OF_ZAMORAK(
        uniqueId = 503,
        componentId = 68,
        maxHit = 20,
        castGfx = Graphic(-1, 0),
        castAnimation = arrayOf(811, 811),
        projectile = 0,
        secondProjectile = 0,
        impactGfx = Graphic(id = 78, height = 0),
        autoCastId = 30,
        experience = 34.5,
    ),

    WIND_WAVE(
        uniqueId = 96,
        componentId = 70,
        maxHit = 17,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 462,
        impactGfx = Graphic(id = 2699, height = 32),
        autoCastId = 27,
        experience = 36.0,
    ),

    WATER_WAVE(
        uniqueId = 98,
        componentId = 73,
        maxHit = 18,
        castGfx = Graphic(2701, 22),
        castAnimation = arrayOf(10542, 14220),
        projectile = 2706,
        impactGfx = Graphic(id = 2709, height = 32),
        autoCastId = 29,
        experience = 37.5,
    ),

    EARTH_WAVE(
        uniqueId = 101,
        componentId = 77,
        maxHit = 19,
        castGfx = Graphic(2716, 22),
        castAnimation = arrayOf(14209, 14222),
        projectile = 2721,
        impactGfx = Graphic(id = 2726, height = 32),
        autoCastId = 31,
        experience = 40.0,
    ),

    FIRE_WAVE(
        uniqueId = 102,
        componentId = 80,
        maxHit = 20,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2733,
        secondProjectile = 2734,
        thirdProjectile = 2734,
        impactGfx = Graphic(id = 2740, height = 32),
        autoCastId = 33,
        experience = 42.5,
    ),

    WIND_SURGE(
        uniqueId = 815,
        componentId = 84,
        maxHit = 21,
        castGfx = Graphic(id = 457, height = 22),
        castAnimation = arrayOf(10546, 14221),
        projectile = 462,
        impactGfx = Graphic(id = 2700, height = 96),
        autoCastId = 47,
        experience = 44.5,
    ),

    WATER_SURGE(
        uniqueId = 816,
        componentId = 87,
        maxHit = 22,
        castGfx = Graphic(2701, 22),
        castAnimation = arrayOf(10542, 14220),
        projectile = 2707,
        impactGfx = Graphic(id = 2712, height = 32),
        autoCastId = 49,
        experience = 46.5,
    ),

    EARTH_SURGE(
        uniqueId = 817,
        componentId = 89,
        maxHit = 23,
        castGfx = Graphic(2717, 22),
        castAnimation = arrayOf(14209, 14222),
        projectile = 2722,
        impactGfx = Graphic(id = 2727, height = 32),
        autoCastId = 51,
        experience = 48.5,
    ),

    FIRE_SURGE(
        uniqueId = 818,
        componentId = 91,
        maxHit = 24,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2735,
        secondProjectile = 2736,
        thirdProjectile = 2736,
        impactGfx = Graphic(id = 2741, height = 32),
        autoCastId = 53,
        experience = 50.5,
    ),

    /**
     * NPC Spells
     */

    WEAK_FIRE_BLAST(
        uniqueId = 995,
        componentId = -1,
        maxHit = 8,
        castGfx = Graphic(2728, 22),
        castAnimation = arrayOf(2791, 14223),
        projectile = 2731,
        secondProjectile = 2732,
        impactGfx = Graphic(id = 2739, height = 32),
        autoCastId = -1,
        experience = 0.0,
    ),
    WARLOCK_SKELETON_EARTH_STRIKE(
        uniqueId = 996,
        componentId = -1,
        maxHit = 6,
        castGfx = Graphic(2713, -4),
        castAnimation = arrayOf(724),
        projectile = 2718,
        impactGfx = Graphic(id = 2723, height = 60),
        autoCastId = 7,
        experience = 9.5,
    ),

    ;

    companion object {
        val values = enumValues<CombatSpell>()
        val definitions = CombatSpell.values().associateBy { it.componentId }
    }
}
