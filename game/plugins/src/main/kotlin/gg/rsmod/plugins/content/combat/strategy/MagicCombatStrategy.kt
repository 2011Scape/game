package gg.rsmod.plugins.content.combat.strategy

import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.*
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.magic.MagicSpells
import kotlin.math.floor

/**
 * @author Tom <rspsmods@gmail.com>
 */
object MagicCombatStrategy : CombatStrategy {
    override fun getAttackRange(pawn: Pawn): Int = 10

    override fun canAttack(
        pawn: Pawn,
        target: Pawn,
    ): Boolean {
        if (pawn is Player) {
            val spell = pawn.attr[Combat.CASTING_SPELL]!!
            val requirements = MagicSpells.getMetadata(spell.uniqueId)
            if (requirements != null && !MagicSpells.canCast(pawn, requirements.lvl, requirements.runes)) {
                return false
            }
        }
        return true
    }

    fun convertToRotation(value: Int): Int {
        return when (value) {
            0 -> 1
            1 -> 0
            2 -> 7
            3 -> 2
            4 -> 6
            5 -> 3
            6 -> 4
            7 -> 5
            else -> throw IllegalArgumentException("Invalid value for direction: $value")
        }
    }

    fun combinedDirection(
        attackDirection: Direction,
        targetDirection: Direction,
    ): Int {
        val lookupTable =
            arrayOf(
                intArrayOf(1, 2, 4, 0, 7, 3, 5, 6), // Player Facing NORTH_WEST
                intArrayOf(0, 1, 2, 3, 4, 5, 6, 7), // Player Facing NORTH
                intArrayOf(3, 0, 1, 5, 2, 6, 7, 4), // Player Facing NORTH_EAST
                intArrayOf(2, 4, 7, 1, 6, 0, 3, 5), // Player Facing WEST
                intArrayOf(5, 3, 0, 6, 1, 7, 4, 2), // Player Facing EAST
                intArrayOf(4, 7, 6, 2, 5, 1, 0, 3), // Player Facing SOUTH_WEST
                intArrayOf(7, 6, 5, 4, 3, 2, 1, 0), // Player Facing SOUTH
                intArrayOf(6, 5, 3, 7, 0, 4, 2, 1), // Player Facing SOUTH_EAST
            )
        return lookupTable[targetDirection.orientationValue][attackDirection.orientationValue]
    }

    override fun attack(
        pawn: Pawn,
        target: Pawn,
    ) {
        val world = pawn.world

        val spell = pawn.attr[Combat.CASTING_SPELL] ?: return
        val attackDirection = Direction.calculateAttackDirection(target.tile, pawn.tile)
        val targetDirection = target.faceDirection
        val impactDirection: Int = combinedDirection(attackDirection, targetDirection)
        val impactGfxRotation: Int = convertToRotation(impactDirection)
        val projectile = pawn.createProjectile(target, gfx = spell.projectile, type = ProjectileType.MAGIC)
        pawn.stopMovement()
        spell.castGfx?.let { gfx -> pawn.graphic(gfx) }
        var animation = spell.castAnimation[0]
        if (pawn is Player && pawn.hasWeaponType(WeaponType.STAFF)) {
            animation = spell.castAnimation[1]
        }
        if (pawn is Npc) {
            animation = spell.castAnimation.getOrNull(2) ?: spell.castAnimation[0]
        }
        pawn.animate(animation)
        spell.impactGfx?.let {
                gfx ->
            target.graphic(Graphic(gfx.id, gfx.height, projectile.lifespan, impactGfxRotation))
        }
        if (spell.projectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.projectile, type = ProjectileType.MAGIC))
        }
        if (spell.secondProjectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.secondProjectile, type = ProjectileType.MAGIC))
        }
        if (spell.thirdProjectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.thirdProjectile, type = ProjectileType.MAGIC))
        }

        if (pawn is Player) {
            MagicSpells
                .getMetadata(spell.uniqueId)
                ?.let { requirement -> MagicSpells.removeRunes(pawn, requirement.runes, spellId = spell.uniqueId) }
        }

        val formula = MagicCombatFormula
        val accuracy = formula.getAccuracy(pawn, target)
        val maxHit = formula.getMaxHit(pawn, target)
        val landHit = accuracy >= world.randomDouble()

        val hitDelay = getHitDelay(pawn.getCentreTile(), target.getCentreTile())
        val damage =
            pawn
                .dealHit(
                    target = target,
                    maxHit = maxHit,
                    landHit = landHit,
                    delay = hitDelay,
                    hitType = HitType.MAGIC,
                ).hit.hitmarks
                .sumOf { it.damage }

        if (damage >= 0 && pawn.entityType.isPlayer) {
            addCombatXp(pawn as Player, target, damage, spell)
        }
    }

    fun getHitDelay(
        start: Tile,
        target: Tile,
    ): Int {
        val distance = start.getDistance(target)
        return 2 + floor((1.0 + distance) / 3.0).toInt()
    }

    private fun addCombatXp(
        player: Player,
        target: Pawn,
        damage: Int,
        spell: CombatSpell,
    ) {
        val modDamage = if (target.entityType.isNpc) target.getCurrentLifepoints().coerceAtMost(damage) else damage
        val multiplier = if (target is Npc) Combat.getNpcXpMultiplier(target) else 1.0
        val baseXp = spell.experience
        val experience = baseXp + (modDamage * 0.2) * multiplier
        val sharedExperience = baseXp + (modDamage * 0.133) * multiplier
        val hitpointsExperience = (modDamage * 0.133) * multiplier
        val defenceExperience = (modDamage * 0.1) * multiplier

        player.addXp(Skills.CONSTITUTION, hitpointsExperience)

        val defensive = player.getVarp(Combat.DEFENSIVE_CAST_VARP) > 0
        if (defensive) {
            player.addXp(Skills.MAGIC, sharedExperience)
            player.addXp(Skills.DEFENCE, defenceExperience)
        } else {
            player.addXp(Skills.MAGIC, experience)
        }
    }
}
