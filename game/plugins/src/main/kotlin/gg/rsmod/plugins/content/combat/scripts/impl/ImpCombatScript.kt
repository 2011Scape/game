package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.MovementQueue
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
import kotlin.random.Random

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
                    it.wait(1)
                    world.spawn(TileGraphic(tile = npc.tile, id = 74, height = 0))
                    var randomTile = getRandomTile(npc.tile)
                    while (world.collision.isClipped(randomTile)) {
                        randomTile = getRandomTile(npc.tile)
                    }
                    if (Random.nextBoolean() && npc.lock.canMove()) {
                        npc.invisible = true
                        npc.walkMask = npc.def.walkMask
                        npc.walkTo(randomTile, MovementQueue.StepType.FORCED_RUN) // Teleport NPC
                        it.wait(2)
                        npc.invisible = false
                        world.spawn(TileGraphic(tile = npc.tile, id = 74, height = 0))
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

    }
}

private fun getRandomTile(tile: Tile) : Tile {
    var deltaX: Int
    var deltaZ: Int
    do {
        deltaX = Random.nextInt(-5, 6)
        deltaZ = Random.nextInt(-5, 6)
    } while (deltaX == 0 && deltaZ == 0)
    return tile.transform(x = deltaX, z = deltaZ)
}
