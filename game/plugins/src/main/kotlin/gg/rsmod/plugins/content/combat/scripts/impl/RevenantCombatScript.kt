package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Gfx
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.getBonus
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.mechanics.prayer.Prayer
import gg.rsmod.plugins.content.mechanics.prayer.Prayers

object RevenantCombatScript {

    val ids =
        intArrayOf(
            Npcs.REVENANT_IMP,
            Npcs.REVENANT_GOBLIN
        )

    suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (target is Player) {
                val player = target
                var attack = "magic"
                if (Prayers.isActive(player, Prayer.PROTECT_FROM_MAGIC)) {
                    var meleeDef = player.getBonus(BonusSlot.DEFENCE_STAB)
                    var rangedDef = player.getBonus(BonusSlot.DEFENCE_RANGED)
                    if (meleeDef <= rangedDef) {
                        attack = "melee"
                    }
                    else {
                        attack = "ranged"
                    }
                }
                val distance = if (attack == "melee") 1 else 6
                if (npc.moveToAttackRange(it, target, distance = distance, projectile = if (attack == "melee") false
                        else true) && npc
                    .isAttackDelayReady
                        ()) {
                    when (attack) {
                        "melee" -> meleeAttack(it, target)
                        "magic" -> magicAttack(it, target)
                        "ranged" -> rangedAttack(it, target)
                    }
                    npc.postAttackLogic(target)
                }
                it.wait(4)
                target = npc.getCombatTarget() ?: break
            }
        }
        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    fun rangedAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.RANGED, StyleType.RANGED, WeaponStyle.NONE)
        it.npc.animate(getRangedAttackAnim(it.npc.id))
        val rangedAttack = it.npc.createProjectile(target, Gfx.GFX_1278, type = ProjectileType.MAGIC)
        target.world.spawn(rangedAttack)
        val hitDelay = RangedCombatStrategy.getHitDelay(it.npc.getCentreTile(), target.getCentreTile())
        it.npc.dealHit(
            target = target,
            formula = RangedCombatFormula,
            delay = hitDelay,
            type = HitType.RANGE,
        )
    }

    fun magicAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.NONE)
        it.npc.animate(getMagicAttackAnim(it.npc.id))
        val magicAttack = it.npc.createProjectile(target, Gfx.GFX_1276, type = ProjectileType.MAGIC)
        target.world.spawn(magicAttack)
        val hitDelay = MagicCombatStrategy.getHitDelay(it.npc.getCentreTile(), target.getCentreTile())
        it.npc.dealHit(
            target = target,
            formula = MagicCombatFormula,
            delay = hitDelay,
            type = HitType.MAGIC,
        )
    }

    fun meleeAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
        it.npc.animate(it.npc.combatDef.attackAnimation)
        it.npc.dealHit(
            target = target,
            formula = MeleeCombatFormula,
            delay = 1,
            type = HitType.MELEE,
        )
    }

    fun getRangedAttackAnim(id: Int): Int {
        when (id) {
            Npcs.REVENANT_IMP -> return Anims.REVENANT_IMP_RANGED_ATTACK
            else -> return Anims.RESET
        }
    }


    fun getMagicAttackAnim(id: Int): Int {
        when (id) {
            Npcs.REVENANT_IMP -> return Anims.REVENANT_IMP_MAGE_ATTACK
            else -> return Anims.RESET
        }

    }
}
