import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy

object AberrantSpectreCombatScript {
    val ids =
        intArrayOf(
            Npcs.ABERRANT_SPECTRE,
            Npcs.ABERRANT_SPECTRE_1605,
            Npcs.ABERRANT_SPECTRE_1607,
            Npcs.ABERRANT_SPECTRE_7801,
            Npcs.ABERRANT_SPECTRE_7802,
            Npcs.ABERRANT_SPECTRE_7803,
            Npcs.ABERRANT_SPECTRE_7804,
        )
    private val skills =
        intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC, Skills.PRAYER)

    suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 8, projectile = true) && npc.isAttackDelayReady()) {
                npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.NONE)
                if (target is Player) {
                    val player = target
                    val protectionItems = listOf("NOSE PEG", "SLAYER HELMET")
                    val NOXIOUS_GAS = npc.createProjectile(player, 335, type = ProjectileType.MAGIC)
                    val NOXIOUS_HIT_GFX = Graphic(336, 110, NOXIOUS_GAS.lifespan)
                    if (!player.hasEquippedWithName(protectionItems.toTypedArray())) {
                        npc.animate(npc.combatDef.attackAnimation)
                        player.world.spawn(NOXIOUS_GAS)
                        player.graphic(NOXIOUS_HIT_GFX)
                        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getCentreTile(), target.getCentreTile())
                        npc.dealHit(
                            target = player,
                            minHit = 14.0,
                            maxHit = 14.01,
                            landHit = true,
                            delay = hitDelay,
                            hitType = HitType.CRIT_MAGIC,
                        )
                        skills.forEach {
                            val drain = player.skills.getMaxLevel(it) * 0.25
                            player.skills.decrementCurrentLevel(it, drain.toInt(), capped = false)
                        }
                    } else {
                        npc.animate(npc.combatDef.attackAnimation)
                        player.world.spawn(NOXIOUS_GAS)
                        player.graphic(NOXIOUS_HIT_GFX)
                        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getCentreTile(), target.getCentreTile())
                        npc.dealHit(
                            target = target,
                            formula = MagicCombatFormula,
                            delay = hitDelay,
                            type = HitType.MAGIC,
                        )
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
