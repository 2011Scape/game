package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.TileGraphic
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import java.util.*

object CanifisCitizensCombatScript : CombatScript() {

    override val ids = intArrayOf(
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
        Npcs.NIKITA
    )

    val male_ids = intArrayOf(
        Npcs.EDUARD,
        Npcs.LEV,
        Npcs.YURI,
        Npcs.BORIS,
        Npcs.GEORGY,
        Npcs.JOSEPH,
        Npcs.NIKOLAI,
        Npcs.IMRE
    )

    val female_ids = intArrayOf(
        Npcs.VERA,
        Npcs.MILLA,
        Npcs.SOFIYA,
        Npcs.IRINA,
        Npcs.SVETLANA,
        Npcs.ZOJA,
        Npcs.YADVIGA,
        Npcs.NIKITA
    )

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                // Check if the target is a player and cast it as a player instance
                if (target is Player) {
                    val player = target
                    if (!player.hasEquipped(EquipmentType.WEAPON, Items.WOLFBANE)) {
                        it.wait(1)
                        it.npc.animate(6554, priority = true)
                        it.wait(2)
                        it.npc.setTransmogId(npc.id - 20)
                        it.npc.animate(6543, priority = true)
                        it.wait(2)
                        world.remove(npc)
                        val spawnWerewolf = Npc(npc.id - 20, npc.tile, world)
                        world.spawn(spawnWerewolf)
                        spawnWerewolf.animate(-1, priority = true)
                        }
                        npc.prepareAttack(CombatClass.MELEE, StyleType.SLASH, WeaponStyle.NONE)
                        npc.animate(npc.combatDef.attackAnimation)
                        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                        npc.postAttackLogic(target)
                }
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }
}
