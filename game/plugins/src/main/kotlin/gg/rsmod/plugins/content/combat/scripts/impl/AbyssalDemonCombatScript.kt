package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.LockState
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


object AbyssalDemonCombatScript : CombatScript() {

    override val ids = intArrayOf(Npcs.ABYSSAL_DEMON, Npcs.ABYSSAL_DEMON_4230, Npcs.ABYSSAL_DEMON_9086)

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                if (world.random(2) == 1) {
                    world.spawn(TileGraphic(tile = npc.tile, id = 409, height = 0))
                    it.wait(1)
                    var randomNPCTile = getRandomTile(npc.tile)
                    var randomPlayerTile = getRandomTile(target.tile)
                    while (world.collision.isClipped(randomPlayerTile)) {
                        randomPlayerTile = getRandomTile(target.tile)
                    }
                    while (world.collision.isClipped(randomNPCTile)) {
                        randomNPCTile = getRandomTile(npc.tile)
                    }
                    // Teleport NPC or Player based on a random decision
                    if (Random.nextBoolean() && npc.lock.canMove()) {
                        target.fullInterruption(interactions = true)
                        npc.invisible = true
                        npc.walkMask = npc.def.walkMask
                        npc.walkTo(randomPlayerTile, MovementQueue.StepType.FORCED_WALK) // Teleport NPC
                        it.wait(2)
                        npc.invisible = false
                        npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
                        npc.animate(npc.combatDef.attackAnimation)
                        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                    } else {
                        target.moveTo(randomNPCTile, forceTeleport = true) // Teleport Player
                    }
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
        var deltaX: Int
        var deltaZ: Int
        do {
            deltaX = Random.nextInt(-1, 2)
            deltaZ = Random.nextInt(-1, 2)
        } while (deltaX == 0 && deltaZ == 0)
        return tile.transform(x = deltaX, z = deltaZ)
    }
}