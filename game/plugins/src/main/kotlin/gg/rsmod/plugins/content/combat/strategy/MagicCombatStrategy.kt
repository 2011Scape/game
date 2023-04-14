package gg.rsmod.plugins.content.combat.strategy

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

    override fun canAttack(pawn: Pawn, target: Pawn): Boolean {
        if (pawn is Player) {
            val spell = pawn.attr[Combat.CASTING_SPELL]!!
            val requirements = MagicSpells.getMetadata(spell.uniqueId)
            if (requirements != null && !MagicSpells.canCast(pawn, requirements.lvl, requirements.runes)) {
                return false
            }
        }
        return true
    }

    override fun attack(pawn: Pawn, target: Pawn) {
        val world = pawn.world

        val spell = pawn.attr[Combat.CASTING_SPELL] ?: return

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
        spell.impactGfx?.let { gfx -> target.graphic(Graphic(gfx.id, gfx.height, projectile.lifespan)) }
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
            MagicSpells.getMetadata(spell.uniqueId)
                ?.let { requirement -> MagicSpells.removeRunes(pawn, requirement.runes) }
            /* ------- SPELL SOUNDS -------- */
            if (spell.uniqueId == 15) pawn.playSound(220) //wind strike
            if (spell.uniqueId == 17) pawn.playSound(211) //water strike
            if (spell.uniqueId == 19) pawn.playSound(132) //earth strike
            if (spell.uniqueId == 71) pawn.playSound(160) //fire strike
            if (spell.uniqueId == 73) pawn.playSound(218) //wind bolt
            if (spell.uniqueId == 76) pawn.playSound(209) //water bolt
            if (spell.uniqueId == 79) pawn.playSound(130) //earth bolt
            if (spell.uniqueId == 82) pawn.playSound(160) //fire bolt
            if (spell.uniqueId == 85) pawn.playSound(216) //wind blast
            if (spell.uniqueId == 88) pawn.playSound(207) //water blast
            if (spell.uniqueId == 90) pawn.playSound(128) //earth blast
            if (spell.uniqueId == 95) pawn.playSound(155) //fire blast
            if (spell.uniqueId == 96) pawn.playSound(222) //wind wave
            if (spell.uniqueId == 98) pawn.playSound(213) //water wave
            if (spell.uniqueId == 101) pawn.playSound(134) //earth wave
            if (spell.uniqueId == 102) pawn.playSound(162) //fire wave
            /* ------- CURRENTLY MISSING SOUNDS ------- */
            //if (spell.uniqueId == 815) pawn.playSound() //wind surge
            //if (spell.uniqueId == 816) pawn.playSound() //water surge
            //if (spell.uniqueId == 817) pawn.playSound() //earth surge
            //if (spell.uniqueId == 818) pawn.playSound() //fire surge
        }

        val formula = MagicCombatFormula
        val accuracy = formula.getAccuracy(pawn, target)
        val maxHit = formula.getMaxHit(pawn, target)
        val landHit = accuracy >= world.randomDouble()

        val hitDelay = getHitDelay(pawn.getCentreTile(), target.getCentreTile())
        val damage =
            pawn.dealHit(
                target = target,
                maxHit = maxHit,
                landHit = landHit,
                delay = hitDelay,
                hitType = HitType.MAGIC
            ).hit.hitmarks.sumOf { it.damage }

        if (damage >= 0 && pawn.entityType.isPlayer) {
            addCombatXp(pawn as Player, target, damage, spell)
        }

    }

    fun getHitDelay(start: Tile, target: Tile): Int {
        val distance = start.getDistance(target)
        return 2 + floor((1.0 + distance) / 3.0).toInt()
    }

    private fun addCombatXp(player: Player, target: Pawn, damage: Int, spell: CombatSpell) {
        val modDamage = if (target.entityType.isNpc) target.getCurrentHp().coerceAtMost(damage) else damage
        val multiplier = if (target is Npc) Combat.getNpcXpMultiplier(target) else 1.0
        val baseXp = spell.experience
        val experience = baseXp + (modDamage * 0.2) * multiplier
        val sharedExperience = baseXp + (modDamage * 0.133) * multiplier
        val hitpointsExperience = (modDamage * 0.133) * multiplier
        val defenceExperience = (modDamage * 0.1) * multiplier

        player.addXp(Skills.HITPOINTS, hitpointsExperience)

        val defensive = player.getVarp(Combat.DEFENSIVE_CAST_VARP) > 0
        if (defensive) {
            player.addXp(Skills.MAGIC, sharedExperience)
            player.addXp(Skills.DEFENCE, defenceExperience)
        } else {
            player.addXp(Skills.MAGIC, experience)
        }
    }
}