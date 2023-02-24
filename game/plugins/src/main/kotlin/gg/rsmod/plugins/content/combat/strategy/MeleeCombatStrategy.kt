package gg.rsmod.plugins.content.combat.strategy

import gg.rsmod.game.model.combat.XpMode
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.ext.hasWeaponType
import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.CombatConfigs
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import kotlin.math.min

/**
 * @author Tom <rspsmods@gmail.com>
 */
object MeleeCombatStrategy : CombatStrategy {

    override fun getAttackRange(pawn: Pawn): Int {
        var baseDistance = 1

        if (pawn is Player) {
            val halberd = pawn.hasWeaponType(WeaponType.HALBERD)
            if (halberd) baseDistance = 2
        }

        return baseDistance
    }

    override fun canAttack(pawn: Pawn, target: Pawn): Boolean {
        return true
    }

    override fun attack(pawn: Pawn, target: Pawn) {
        val world = pawn.world

        val animation = CombatConfigs.getAttackAnimation(pawn)
        pawn.animate(animation)

        val blockAnimation = CombatConfigs.getBlockAnimation(target)
        target.animate(blockAnimation, priority = false)

        val formula = MeleeCombatFormula
        val accuracy = formula.getAccuracy(pawn, target)
        val maxHit = formula.getMaxHit(pawn, target)
        val landHit = accuracy >= world.randomDouble()

        val damage = pawn.dealHit(
            target = target,
            maxHit = maxHit,
            landHit = landHit,
            delay = 1,
            hitType = HitType.MELEE
        ).hit.hitmarks.sumOf { it.damage }

        if (damage > 0 && pawn.entityType.isPlayer) {
            addCombatXp(pawn as Player, target, damage)
        }
    }

    private fun addCombatXp(player: Player, target: Pawn, damage: Int) {
        val modDamage = if (target.entityType.isNpc) min(target.getCurrentHp(), damage) else damage
        val mode = CombatConfigs.getXpMode(player)
        val multiplier = if (target is Npc) Combat.getNpcXpMultiplier(target) else 1.0

        val hitpointsExperience = (modDamage / 7.5) * multiplier
        val combatExperience = (modDamage / 2.5) * multiplier
        val sharedExperience = ((modDamage / 2.5) / 3) * multiplier

        when (mode) {
            XpMode.ATTACK -> {
                player.addXp(Skills.ATTACK, combatExperience)
                player.addXp(Skills.HITPOINTS, hitpointsExperience)
            }

            XpMode.STRENGTH -> {
                player.addXp(Skills.STRENGTH, combatExperience)
                player.addXp(Skills.HITPOINTS, hitpointsExperience)
            }

            XpMode.DEFENCE -> {
                player.addXp(Skills.DEFENCE, combatExperience)
                player.addXp(Skills.HITPOINTS, hitpointsExperience)
            }

            XpMode.SHARED -> {
                player.addXp(Skills.ATTACK, sharedExperience)
                player.addXp(Skills.STRENGTH, sharedExperience)
                player.addXp(Skills.DEFENCE, sharedExperience)
                player.addXp(Skills.HITPOINTS, hitpointsExperience)
            }

            XpMode.RANGED -> TODO()
            XpMode.MAGIC -> TODO()
        }
    }
}