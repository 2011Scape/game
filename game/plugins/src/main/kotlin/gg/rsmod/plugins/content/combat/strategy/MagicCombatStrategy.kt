package gg.rsmod.plugins.content.combat.strategy

import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.hasWeaponType
import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell
import gg.rsmod.plugins.content.magic.MagicSpells

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

        val spell = pawn.attr[Combat.CASTING_SPELL]!!
        val projectile = pawn.createProjectile(target, gfx = spell.projectile, type = ProjectileType.MAGIC)


        spell.castGfx?.let { gfx -> pawn.graphic(gfx) }
        var animation = spell.castAnimation[0]
        if(pawn is Player) {
            if (pawn.hasWeaponType(WeaponType.STAFF) || pawn.hasWeaponType(WeaponType.MAGIC_STAFF)) {
                animation = spell.castAnimation[1]
            }
        }
        pawn.animate(animation)
        spell.impactGfx?.let { gfx -> target.graphic(Graphic(gfx.id, gfx.height, projectile.lifespan)) }
        if(spell.projectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.projectile, type = ProjectileType.MAGIC))
        }
        if(spell.secondProjectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.secondProjectile, type = ProjectileType.MAGIC))
        }
        if(spell.thirdProjectile > -1) {
            world.spawn(pawn.createProjectile(target, gfx = spell.thirdProjectile, type = ProjectileType.MAGIC))
        }

        if (pawn is Player) {
            MagicSpells.getMetadata(spell.uniqueId)?.let { requirement -> MagicSpells.removeRunes(pawn, requirement.runes) }
        }

        val formula = MagicCombatFormula
        val accuracy = formula.getAccuracy(pawn, target)
        val maxHit = formula.getMaxHit(pawn, target)
        val landHit = accuracy >= world.randomDouble()

        val hitDelay = getHitDelay(pawn.getCentreTile(), target.getCentreTile())
        val damage = pawn.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = hitDelay, hitType = HitType.MAGIC).hit.hitmarks.sumBy { it.damage }

        if (damage >= 0 && pawn.entityType.isPlayer) {
            addCombatXp(pawn as Player, target, damage, spell)
        }

    }

    fun getHitDelay(start: Tile, target: Tile): Int {
        val distance = start.getDistance(target)
        return 2 + Math.floor((1.0 + distance) / 3.0).toInt()
    }

    private fun addCombatXp(player: Player, target: Pawn, damage: Int, spell: CombatSpell) {
        val modDamage = if (target.entityType.isNpc) Math.min(target.getCurrentHp(), damage) else damage
        val multiplier = if (target is Npc) Combat.getNpcXpMultiplier(target) else 1.0
        val baseXp = spell.experience

        val defensive = player.getVarbit(Combat.SELECTED_AUTOCAST_VARP) != 0 && player.getVarbit(Combat.DEFENSIVE_CAST_VARP) != 0
        var experience = baseXp + ((modDamage / 5) * multiplier)
        val sharedExperience = ((modDamage / 7.5) * multiplier)

        player.addXp(Skills.HITPOINTS, sharedExperience)

        if(defensive) {
            player.addXp(Skills.DEFENCE, sharedExperience)
            experience -= sharedExperience
            player.addXp(Skills.MAGIC, experience)
        } else {
            player.addXp(Skills.MAGIC, experience)
        }
    }
}