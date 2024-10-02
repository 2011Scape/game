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

object AbyssalDemonCombatScript : CombatScript() {
    override val ids = intArrayOf(Npcs.ABYSSAL_DEMON, Npcs.ABYSSAL_DEMON_4230, Npcs.ABYSSAL_DEMON_9086)

    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = false) && npc.isAttackDelayReady()) {
                // Perform the attack
                npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
                npc.animate(npc.combatDef.attackAnimation)
                npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
                npc.postAttackLogic(target)

                // Check for teleportation after a successful hit
                if (world.random(6) == 0) { // 1/6 chance to teleport away
                    val randomNPCTile = getRandomTileWithinArea()
                    if (npc.lock.canMove() && !world.collision.isClipped(randomNPCTile)) {
                        world.spawn(TileGraphic(tile = randomNPCTile, id = 409, height = 0))
                        npc.teleportNpc(randomNPCTile)
                    }
                } else if (world.random(3) == 0) { // 1/3 chance to teleport the player if the demon didn't teleport
                    val randomPlayerTile = getRandomTileWithinArea()
                    if (!world.collision.isClipped(randomPlayerTile)) {
                        world.spawn(TileGraphic(tile = randomPlayerTile, id = 409, height = 0))
                        target.teleportTo(randomPlayerTile)
                        target.fullInterruption(interactions = true) // Player stops attacking
                        // If Auto Retaliate is on, the player will automatically start attacking again
                    }
                }

                it.wait(4)
            } else {
                it.wait(1) // Wait for a tick before re-evaluating conditions
            }
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    private val validTiles: List<Tile> by lazy { generateValidTiles() }

    private fun generateValidTiles(): List<Tile> {
        // Define the boundaries of the teleportation area
        val minX = 3409
        val maxX = 3430
        val minY = 3555
        val maxY = 3576
        val plane = 2

        // Define the boundaries of the exclusion area
        val excludeMinX = 3421
        val excludeMaxX = 3430
        val excludeMinY = 3555
        val excludeMaxY = 3561

        val tiles = mutableListOf<Tile>()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                // Check if the coordinates are not within the exclusion zone
                if (x !in excludeMinX..excludeMaxX || y !in excludeMinY..excludeMaxY) {
                    tiles.add(Tile(x, y, plane))
                }
            }
        }

        return tiles
    }

    private fun getRandomTileWithinArea(): Tile {
        // Select a random tile from the precomputed list of valid tiles
        return validTiles.random()
    }
}
