package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.TileGraphic
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import java.util.*

object ImpCombatScript : CombatScript() {

    override val ids = intArrayOf(Npcs.IMP, Npcs.IMP_709)

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                if (world.random(10) > 2) {
                    world.spawn(TileGraphic(tile = npc.tile, id = 74, height = 0))
                    it.wait(1)
                    var randomTile = getRandomTile(npc.tile)
                    while (world.collision.isClipped(randomTile)) {
                        randomTile = getRandomTile(npc.tile)
                    }
//                    npc.moveTo(randomTile)
                    println("Moved to x: ${randomTile.x} z: ${randomTile.z}")
                } else {
                    npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
                    npc.animate(npc.combatDef.attackAnimation)
                    npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                }
                npc.postAttackLogic(target)
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    private fun getRandomTile(tile: Tile) : Tile {
        return tile.transform(x = tile.x + Random().nextInt(7), z = tile.z + Random().nextInt(7))
    }
}