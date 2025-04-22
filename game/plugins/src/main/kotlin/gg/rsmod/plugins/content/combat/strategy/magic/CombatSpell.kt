package gg.rsmod.plugins.content.combat.strategy.magic

import gg.rsmod.game.model.Graphic
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Gfx

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
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_RUSH_PROJ,
        impactGfx = Graphic(Gfx.WIND_RUSH_IMPACT, 32),
        autoCastId = 143,
        experience = 0.2,
    ),

    WIND_STRIKE(
        uniqueId = 15,
        componentId = 25,
        maxHit = 2,
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_STRIKE_PROJ,
        impactGfx = Graphic(Gfx.WIND_STRIKE_IMPACT, 32),
        autoCastId = 3,
        experience = 5.5,
    ),

    WATER_STRIKE(
        uniqueId = 17,
        componentId = 28,
        maxHit = 4,
        castGfx = Graphic(Gfx.WATER_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WATER_SPELL, Anims.WATER_SPELL_WITH_STAFF),
        projectile = Gfx.WATER_STRIKE_PROJ,
        impactGfx = Graphic(Gfx.WATER_STRIKE_IMPACT, 32),
        autoCastId = 5,
        experience = 7.5,
    ),

    EARTH_STRIKE(
        uniqueId = 19,
        componentId = 30,
        maxHit = 6,
        castGfx = Graphic(Gfx.EARTH_SPELL_CAST, -4),
        castAnimation = arrayOf(Anims.EARTH_SPELL, Anims.EARTH_SPELL_WITH_STAFF),
        projectile = Gfx.EARTH_STRIKE_PROJ,
        impactGfx = Graphic(Gfx.EARTH_STRIKE_IMPACT, 60),
        autoCastId = 7,
        experience = 9.5,
    ),

    FIRE_STRIKE(
        uniqueId = 71,
        componentId = 32,
        maxHit = 8,
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_STRIKE_PROJ,
        impactGfx = Graphic(Gfx.FIRE_STRIKE_IMPACT, 32),
        autoCastId = 9,
        experience = 11.5,
    ),

    WIND_BOLT(
        uniqueId = 73,
        componentId = 34,
        maxHit = 9,
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_BOLT_PROJ,
        impactGfx = Graphic(Gfx.WIND_BLAST_IMPACT, 32),
        autoCastId = 11,
        experience = 13.5,
    ),

    WATER_BOLT(
        uniqueId = 76,
        componentId = 39,
        maxHit = 10,
        castGfx = Graphic(Gfx.WATER_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WATER_SPELL, Anims.WATER_SPELL_WITH_STAFF),
        projectile = Gfx.WATER_BOLT_PROJ,
        impactGfx = Graphic(Gfx.WATER_BOLT_IMPACT, 32),
        autoCastId = 13,
        experience = 16.5,
    ),

    EARTH_BOLT(
        uniqueId = 79,
        componentId = 42,
        maxHit = 11,
        castGfx = Graphic(Gfx.EARTH_BOLT_CAST, 22),
        castAnimation = arrayOf(Anims.EARTH_SPELL, Anims.EARTH_SPELL_WITH_STAFF),
        projectile = Gfx.EARTH_BOLT_PROJ,
        impactGfx = Graphic(Gfx.EARTH_BOLT_IMPACT, 32),
        autoCastId = 15,
        experience = 19.5,
    ),

    FIRE_BOLT(
        uniqueId = 82,
        componentId = 45,
        maxHit = 12,
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_BOLT_PROJ,
        impactGfx = Graphic(Gfx.FIRE_BOLT_IMPACT, 32),
        autoCastId = 17,
        experience = 22.5,
    ),

    WIND_BLAST(
        uniqueId = 85,
        componentId = 49,
        maxHit = 13,
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_BLAST_PROJ,
        impactGfx = Graphic(Gfx.WIND_BLAST_IMPACT, 32),
        autoCastId = 19,
        experience = 25.5,
    ),

    WATER_BLAST(
        uniqueId = 88,
        componentId = 52,
        maxHit = 14,
        castGfx = Graphic(Gfx.WATER_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WATER_SPELL, Anims.WATER_SPELL_WITH_STAFF),
        projectile = Gfx.WATER_BLAST_PROJ,
        impactGfx = Graphic(Gfx.WATER_BLAST_IMPACT, 32),
        autoCastId = 21,
        experience = 28.5,
    ),

    EARTH_BLAST(
        uniqueId = 90,
        componentId = 58,
        maxHit = 15,
        castGfx = Graphic(Gfx.EARTH_BLAST_CAST, 22),
        castAnimation = arrayOf(Anims.EARTH_SPELL, Anims.EARTH_SPELL_WITH_STAFF),
        projectile = Gfx.EARTH_BLAST_PROJ,
        impactGfx = Graphic(Gfx.EARTH_BLAST_IMPACT, 32),
        autoCastId = 23,
        experience = 31.5,
    ),

    FIRE_BLAST(
        uniqueId = 95,
        componentId = 63,
        maxHit = 16,
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_BLAST_PROJ,
        secondProjectile = Gfx.FIRE_BLAST_PROJ_2,
        impactGfx = Graphic(Gfx.FIRE_BLAST_IMPACT, 32),
        autoCastId = 25,
        experience = 34.5,
    ),

    SARADOMIN_STRIKE(
        uniqueId = 501,
        componentId = 66,
        maxHit = 20,
        castGfx = Graphic(Gfx.RESET, 0),
        castAnimation = arrayOf(Anims.SPELL_GODSTAVE, Anims.SPELL_GODSTAVE),
        projectile = Gfx.GFX_0,
        secondProjectile = Gfx.GFX_0,
        impactGfx = Graphic(Gfx.SARADOMIN_STRIKE_IMPACT, 100),
        autoCastId = 26,
        experience = 34.5,
    ),

    CLAWS_OF_GUTHIX(
        uniqueId = 502,
        componentId = 67,
        maxHit = 20,
        castGfx = Graphic(Gfx.RESET, 0),
        castAnimation = arrayOf(Anims.SPELL_GODSTAVE, Anims.SPELL_GODSTAVE, Anims.CLAWS_OF_GUTHIX),
        projectile = Gfx.GFX_0,
        secondProjectile = Gfx.GFX_0,
        impactGfx = Graphic(Gfx.CLAWS_OF_GUTHIX_IMPACT, 100),
        autoCastId = 28,
        experience = 34.5,
    ),

    FLAMES_OF_ZAMORAK(
        uniqueId = 503,
        componentId = 68,
        maxHit = 20,
        castGfx = Graphic(Gfx.RESET, 0),
        castAnimation = arrayOf(Anims.SPELL_GODSTAVE, Anims.SPELL_GODSTAVE),
        projectile = Gfx.GFX_0,
        secondProjectile = Gfx.GFX_0,
        impactGfx = Graphic(Gfx.FLAMES_OF_ZAMORAK_IMPACT, 0),
        autoCastId = 30,
        experience = 34.5,
    ),

    WIND_WAVE(
        uniqueId = 96,
        componentId = 70,
        maxHit = 17,
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_WAVE_PROJ,
        impactGfx = Graphic(Gfx.WIND_WAVE_IMPACT, height = 32),
        autoCastId = 27,
        experience = 36.0,
    ),

    WATER_WAVE(
        uniqueId = 98,
        componentId = 73,
        maxHit = 18,
        castGfx = Graphic(Gfx.WATER_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WATER_SPELL, Anims.WATER_SPELL_WITH_STAFF),
        projectile = Gfx.WATER_WAVE_PROJ,
        impactGfx = Graphic(Gfx.WATER_WAVE_IMPACT, 32),
        autoCastId = 29,
        experience = 37.5,
    ),

    EARTH_WAVE(
        uniqueId = 101,
        componentId = 77,
        maxHit = 19,
        castGfx = Graphic(Gfx.EARTH_WAVE_CAST, 22),
        castAnimation = arrayOf(Anims.EARTH_SPELL, Anims.EARTH_SPELL_WITH_STAFF),
        projectile = Gfx.EARTH_WAVE_PROJ,
        impactGfx = Graphic(Gfx.EARTH_WAVE_IMPACT, 32),
        autoCastId = 31,
        experience = 40.0,
    ),

    FIRE_WAVE(
        uniqueId = 102,
        componentId = 80,
        maxHit = 20,
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_WAVE_PROJ,
        secondProjectile = Gfx.FIRE_WAVE_PROJ_2,
        thirdProjectile = Gfx.FIRE_WAVE_PROJ_2,
        impactGfx = Graphic(Gfx.FIRE_WAVE_IMPACT, 32),
        autoCastId = 33,
        experience = 42.5,
    ),

    WIND_SURGE(
        uniqueId = 815,
        componentId = 84,
        maxHit = 21,
        castGfx = Graphic(Gfx.WIND_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WIND_SPELL, Anims.WIND_SPELL_WITH_STAFF),
        projectile = Gfx.WIND_WAVE_PROJ,
        impactGfx = Graphic(Gfx.WIND_SURGE_IMPACT, 96),
        autoCastId = 47,
        experience = 44.5,
    ),

    WATER_SURGE(
        uniqueId = 816,
        componentId = 87,
        maxHit = 22,
        castGfx = Graphic(Gfx.WATER_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.WATER_SPELL, Anims.WATER_SPELL_WITH_STAFF),
        projectile = Gfx.WATER_SURGE_PROJ,
        impactGfx = Graphic(Gfx.WATER_SURGE_IMPACT, 32),
        autoCastId = 49,
        experience = 46.5,
    ),

    EARTH_SURGE(
        uniqueId = 817,
        componentId = 89,
        maxHit = 23,
        castGfx = Graphic(Gfx.EARTH_SURGE_CAST, 22),
        castAnimation = arrayOf(Anims.EARTH_SPELL, Anims.EARTH_SPELL_WITH_STAFF),
        projectile = Gfx.EARTH_SURGE_PROJ,
        impactGfx = Graphic(Gfx.EARTH_SURGE_IMPACT, 32),
        autoCastId = 51,
        experience = 48.5,
    ),

    FIRE_SURGE(
        uniqueId = 818,
        componentId = 91,
        maxHit = 24,
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_SURGE_PROJ,
        secondProjectile = Gfx.FIRE_SURGE_PROJ_2,
        thirdProjectile = Gfx.FIRE_SURGE_PROJ_2,
        impactGfx = Graphic(Gfx.FIRE_SURGE_IMPACT, 32),
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
        castGfx = Graphic(Gfx.FIRE_SPELL_CAST, 22),
        castAnimation = arrayOf(Anims.FIRE_SPELL, Anims.FIRE_SPELL_WITH_STAFF),
        projectile = Gfx.FIRE_BLAST_PROJ,
        secondProjectile = Gfx.FIRE_BLAST_PROJ_2,
        impactGfx = Graphic(Gfx.FIRE_BLAST_IMPACT, 32),
        autoCastId = -1,
        experience = 0.0,
    ),
    WARLOCK_SKELETON_EARTH_STRIKE(
        uniqueId = 996,
        componentId = -1,
        maxHit = 6,
        castGfx = Graphic(Gfx.EARTH_SPELL_CAST, -4),
        castAnimation = arrayOf(Anims.WARLOCK_WEAK_EARTH_STRIKE),
        projectile = Gfx.EARTH_STRIKE_PROJ,
        impactGfx = Graphic(Gfx.EARTH_STRIKE_IMPACT, 60),
        autoCastId = 7,
        experience = 9.5,
    ),

    ;

    companion object {
        val values = enumValues<CombatSpell>()
        val definitions = CombatSpell.values().associateBy { it.componentId }
    }
}
