package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import java.util.Timer
import java.util.TimerTask

object CanifisCitizensCombatScript : CombatScript() {
    override val ids =
        intArrayOf(
            Npcs.EDUARD,
            Npcs.LEV,
            Npcs.YURI,
            Npcs.BORIS,
            Npcs.GEORGY,
            Npcs.JOSEPH,
            Npcs.NIKOLAI,
            Npcs.IMRE,
            Npcs.VERA,
            Npcs.MILLA,
            Npcs.SOFIYA,
            Npcs.IRINA,
            Npcs.SVETLANA,
            Npcs.ZOJA,
            Npcs.YADVIGA,
            Npcs.NIKITA,
            Npcs.LILIYA,
            Npcs.ALEXIS,
            Npcs.KSENIA,
            Npcs.GALINA,
        )

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world
        val nextAnimationTimer = Timer()
        val resetAnimationTimer = Timer()

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            // Check if the target is a player and cast it as a player instance
            if (target is Player) {
                val player = target
                if (!player.hasEquipped(EquipmentType.WEAPON, Items.WOLFBANE)) {
                    npc.stopMovement()
                    val werewolf = Npc(npc.id - 20, npc.tile, world)
                    werewolf.walkRadius = 5
                    it.wait(1)
                    // Start transformation
                    npc.animate(6554, priority = true)
                    nextAnimationTimer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                // Transform into werewolf
                                world.spawn(werewolf)
                                world.remove(npc)
                                werewolf.facePawn(target)
                                werewolf.animate(6543, priority = true)
                                // TODO: ADD Transformation GFX
                                /**
                                 * GFX Ids
                                 * 1079: Werewolf transformation
                                 * 1080: Werewolf transformation
                                 * 1081: Werewolf transformation
                                 * 1082: Werewolf transformation
                                 * 1083: Werewolf transformation
                                 * 1084: Werewolf transformation
                                 * 1085: Werewolf transformation
                                 * 1086: Werewolf transformation
                                 * 1087: Werewolf transformation
                                 * 1088: Werewolf transformation
                                 * 1089: Werewolf transformation
                                 * 1090: Werewolf transformation
                                 * 1091: Werewolf transformation
                                 * 1092: Werewolf transformation
                                 * 1093: Werewolf transformation
                                 * 1094: Werewolf transformation
                                 * 1095: Werewolf transformation
                                 * 1096: Werewolf transformation
                                 * 1097: Werewolf transformation
                                 * 1098: Werewolf transformation
                                 */
                            }
                        },
                        150,
                    ) // Set the delay in milliseconds
                    resetAnimationTimer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                werewolf.resetAnimation()
                            }
                        },
                        2000,
                    ) // Set the delay in milliseconds
                    it.wait(1)
                    // Changes the combat target to the werewolf
                    player.clearActiveCombatTimer()
                    player.setCombatTarget(werewolf)
                    werewolf.setCombatTarget(player)
                    // Attack player
                    werewolf.attack(player)
                }
                if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                    npc.prepareAttack(CombatClass.MELEE, StyleType.SLASH, WeaponStyle.ACCURATE)
                    npc.animate(npc.combatDef.attackAnimation)
                    npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                    npc.postAttackLogic(target)
                }
            } else {
                // Exits the loop if the target is null.
                return
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }
        npc.resetFacePawn()
        npc.removeCombatTarget()
        nextAnimationTimer.cancel() // Cancel the timer when the combat loop is done
        resetAnimationTimer.cancel()
    }
}
