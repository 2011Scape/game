package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula

object BansheeCombatScript {
    val ids = intArrayOf(Npcs.BANSHEE, Npcs.BANSHEE_MISTRESS, Npcs.BANSHEE_MISTRESS_7794)
    private val skills =
        intArrayOf(
            Skills.ATTACK,
            Skills.STRENGTH,
            Skills.DEFENCE,
            Skills.RANGED,
            Skills.MAGIC,
            Skills.AGILITY,
            Skills.PRAYER,
        )

    suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
                if (target is Player) {
                    val player = target
                    val protectionItems = listOf("EARMUFFS", "SLAYER HELMET")
                    if (!player.hasEquippedWithName(protectionItems.toTypedArray())) {
                        npc.animate(npc.combatDef.attackAnimation)
                        npc.dealHit(target = player, maxHit = 7.8, landHit = true, delay = 1, hitType = HitType.MELEE)
                        skills.forEach {
                            val drain = player.skills.getMaxLevel(it) * 0.25
                            player.skills.decrementCurrentLevel(it, drain.toInt(), capped = false)
                        }
                    } else {
                        npc.animate(npc.combatDef.attackAnimation)
                        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                    }
                }
                npc.postAttackLogic(target)
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }
        npc.resetFacePawn()
        npc.removeCombatTarget()
    }
}
