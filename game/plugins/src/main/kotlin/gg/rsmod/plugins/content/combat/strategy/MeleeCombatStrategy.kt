package gg.rsmod.plugins.content.combat.strategy

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.combat.XpMode
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.WeaponType
import gg.rsmod.plugins.api.ext.hasWeaponType
import gg.rsmod.plugins.api.ext.playSound
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

    override fun canAttack(
        pawn: Pawn,
        target: Pawn,
    ): Boolean {
        return true
    }

    override fun attack(
        pawn: Pawn,
        target: Pawn,
    ) {
        val world = pawn.world

        val animation = CombatConfigs.getAttackAnimation(pawn)
        pawn.animate(animation)

        if (pawn is Player) {
            val weapon = pawn.equipment[3]
            if (weapon != null && world.definitions.get(ItemDef::class.java, weapon.id).attackAudio > -1) {
                pawn.playSound(world.definitions.get(ItemDef::class.java, weapon.id).attackAudio)
            }
        }

        val blockAnimation = CombatConfigs.getBlockAnimation(target)
        target.animate(blockAnimation, priority = false)

        val formula = MeleeCombatFormula
        val accuracy = formula.getAccuracy(pawn, target)
        val maxHit = formula.getMaxHit(pawn, target)
        val landHit = accuracy >= world.randomDouble()

        val damage =
            pawn
                .dealHit(
                    target = target,
                    maxHit = maxHit,
                    landHit = landHit,
                    delay = 1,
                    hitType = HitType.MELEE,
                ).hit.hitmarks
                .sumOf { it.damage }

        if (damage > 0 && pawn.entityType.isPlayer) {
            addCombatXp(pawn as Player, target, damage)
        }
    }

    private fun addCombatXp(
        player: Player,
        target: Pawn,
        damage: Int,
    ) {
        val modDamage = if (target.entityType.isNpc) min(target.getCurrentLifepoints(), damage) else damage
        val mode = CombatConfigs.getXpMode(player)
        val multiplier = if (target is Npc) Combat.getNpcXpMultiplier(target) else 1.0

        val hitpointsExperience = (modDamage * 0.133) * multiplier
        val combatExperience = (modDamage * 0.4) * multiplier
        val sharedExperience = (modDamage * 0.133) * multiplier

        when (mode) {
            XpMode.ATTACK_XP -> {
                player.addXp(Skills.ATTACK, combatExperience)
                player.addXp(Skills.CONSTITUTION, hitpointsExperience)
            }

            XpMode.STRENGTH_XP -> {
                player.addXp(Skills.STRENGTH, combatExperience)
                player.addXp(Skills.CONSTITUTION, hitpointsExperience)
            }

            XpMode.DEFENCE_XP -> {
                player.addXp(Skills.DEFENCE, combatExperience)
                player.addXp(Skills.CONSTITUTION, hitpointsExperience)
            }

            XpMode.SHARED_XP -> {
                player.addXp(Skills.ATTACK, sharedExperience)
                player.addXp(Skills.STRENGTH, sharedExperience)
                player.addXp(Skills.DEFENCE, sharedExperience)
                player.addXp(Skills.CONSTITUTION, hitpointsExperience)
            }

            XpMode.RANGED_XP -> TODO()
            XpMode.MAGIC_XP -> TODO()
        }
    }
}
