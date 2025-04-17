package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.LAST_MAP_BUILD_TIME
import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.cfg.Gfx
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.getBonus
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.items.jewellery.ForinthryBracelet
import gg.rsmod.plugins.content.mechanics.prayer.Prayer
import gg.rsmod.plugins.content.mechanics.prayer.Prayers
import kotlin.math.abs

class Revenants(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    init {
        /**
         *  Refill available healing pool for revenants when they respawn and override their aggressiveness
         *  to be unaggressive if player's forinthry bracelet is active
         */
        ids.forEach {
            on_global_npc_spawn {
                healsLeft[it] = 10
                npc.queue {
                    // Wait 1 cycle to avoid a race condition with the global aggro plugin to set it to normal
                    wait(1)
                    npc.aggroCheck = revenantAggressiveness
                }
            }
        }
    }

    val revenantAggressiveness: (Npc, Player) -> Boolean = boolean@{ n, p ->
        if (p.attr.has(ForinthryBracelet.FORINTHRY_CHARGES)) {
            return@boolean false
        }

        if (n.combatDef.aggressiveTimer == Int.MAX_VALUE) {
            return@boolean true
        } else if (n.combatDef.aggressiveTimer == Int.MIN_VALUE) {
            return@boolean false
        }

        if (abs(world.currentCycle - (p.attr[LAST_MAP_BUILD_TIME] ?: 0)) > n.combatDef.aggressiveTimer) {
            return@boolean false
        }

        val npcLvl = n.def.combatLevel
        return@boolean p.combatLevel <= npcLvl * 2
    }

    companion object {
        val ids =
            intArrayOf(
                Npcs.REVENANT_IMP,
                Npcs.REVENANT_GOBLIN,
                Npcs.REVENANT_GOBLIN_13468,
                Npcs.REVENANT_GOBLIN_13469,
                Npcs.REVENANT_ICEFIEND,
                Npcs.REVENANT_PYREFIEND,
                Npcs.REVENANT_HOBGOBLIN,
                Npcs.REVENANT_VAMPYRE,
                Npcs.REVENANT_WEREWOLF,
                Npcs.REVENANT_CYCLOPS,
                Npcs.REVENANT_HELLHOUND,
                Npcs.REVENANT_DEMON,
                Npcs.REVENANT_ORK,
                Npcs.REVENANT_DARK_BEAST,
                Npcs.REVENANT_KNIGHT,
                Npcs.REVENANT_DRAGON
            )

        val healsLeft: HashMap<Int, Int> = HashMap()

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
                    if (!healsLeft.containsKey(it.npc.id)) {
                        healsLeft[it.npc.id] = 10
                    }
                    if (it.npc.getCurrentLifepoints() < it.npc.getMaximumLifepoints() / 2 && healsLeft[it.npc.id]!! > 0) {
                        attack = "heal"
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
                            "heal" -> heal(it, target)
                        }
                        npc.postAttackLogic(target)
                    }
                    it.wait(5)
                    target = npc.getCombatTarget() ?: break
                }
            }
            npc.resetFacePawn()
            npc.removeCombatTarget()
        }

        fun rangedAttack(it: QueueTask, target: Player) {
            it.npc.prepareAttack(CombatClass.RANGED, StyleType.RANGED, WeaponStyle.NONE)
            val rev = Revenant.forId(it.npc.id)
            it.npc.animate(rev.rangedAnim)
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
            val rev = Revenant.forId(it.npc.id)
            it.npc.animate(rev.mageAnim)
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

        fun heal(it: QueueTask, target: Player) {
            val rev = Revenant.forId(it.npc.id)
            it.npc.animate(rev.healAnim)
            if (it.npc.isPoisoned()) {
                target.playSound(Sfx.DRINK)
                it.npc.attr[POISON_TICKS_LEFT_ATTR] = 0
            }
            else {
                target.playSound(Sfx.EAT)
                val healAmt = it.npc.getMaximumLifepoints() / 6
                it.npc.setCurrentLifepoints(it.npc.getCurrentLifepoints() + healAmt)
                val leftNow = healsLeft[it.npc.id]!! - 1
                healsLeft[it.npc.id] = leftNow
            }
        }
    }
}
