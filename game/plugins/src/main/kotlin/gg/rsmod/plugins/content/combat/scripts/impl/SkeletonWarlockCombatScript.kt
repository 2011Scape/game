package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy

object SkeletonWarlockCombatScript {

    val SKELETON_WARLOCK = intArrayOf(Npcs.SKELETON_WARLOCK)
    suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return

        /**
         *     EARTH_STRIKE(
         *         uniqueId = 19,
         *         componentId = 30,
         *         maxHit = 6,
         *         castGfx = Graphic(2713, -4),
         *         castAnimation = arrayOf(14209, 14222),
         *         projectile = 2718,
         *         impactGfx = Graphic(id = 2723, height = 60),
         *         autoCastId = 7,
         *         experience = 9.5,
         *     ),
         */

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 8, projectile = true) && npc.isAttackDelayReady()) {
                npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.NONE)
                if (target is Player) {
                    val player = target
                    val HIT_DIRECTION = npc.faceDirection.faceNpc
                    val EARTH_STRIKE_PROJECTILE = npc.createProjectile(player, 2718, type = ProjectileType.MAGIC)
                    val EARTH_STRIKE_HIT = Graphic(2723, 120, delay = EARTH_STRIKE_PROJECTILE.lifespan, rotation = HIT_DIRECTION)
                    player.message("Direction: ${HIT_DIRECTION}")
                    npc.animate(npc.combatDef.attackAnimation)
                    npc.graphic(2713, -4)
                    player.world.spawn(EARTH_STRIKE_PROJECTILE)
                    player.graphic(EARTH_STRIKE_HIT)
                    val hitDelay = MagicCombatStrategy.getHitDelay(npc.getCentreTile(), target.getCentreTile())
                    npc.dealHit(target = target, formula = MagicCombatFormula, delay = hitDelay, type = HitType.MAGIC)
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