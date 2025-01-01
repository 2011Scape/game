package gg.rsmod.game.sync.task

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.sync.SynchronizationTask

/**
 * @author Tom <rspsmods@gmail.com>
 */
object PlayerPostSynchronizationTask : SynchronizationTask<Player> {
    override fun run(pawn: Player) {
        val oldTile = pawn.lastTile
        val changedHeight = oldTile?.height != pawn.tile.height
        val moved = oldTile == null || !oldTile.sameAs(pawn.tile) || changedHeight

        if (moved) {
            val oldRegion = oldTile?.regionId ?: -1
            val currentRegion = pawn.tile.regionId
            if (oldRegion != currentRegion) {
                if (oldRegion != -1) {
                    pawn.world.plugins.executeRegionExit(pawn, oldRegion)
                }
                pawn.world.plugins.executeRegionEnter(pawn, currentRegion)
            }
            pawn.lastTile = Tile(pawn.tile)
        }
        pawn.moved = false
        pawn.steps = null
        pawn.blockBuffer.clean()

        if (moved) {
            val oldChunk =
                if (oldTile !=
                    null
                ) {
                    pawn.world.chunks.get(oldTile.chunkCoords, createIfNeeded = false)
                } else {
                    null
                }
            val newChunk = pawn.world.chunks.get(pawn.tile.chunkCoords, createIfNeeded = false)
            if (newChunk != null) {
                val newSurroundings = newChunk.coords.getSurroundingCoords()
                newSurroundings.forEach { coords ->
                    val chunk = pawn.world.chunks.get(coords, createIfNeeded = false) ?: return@forEach
                    chunk.sendUpdates(pawn)
                }
                if (!changedHeight) {
                    if (oldChunk != null) {
                        pawn.world.plugins.executeChunkExit(pawn, oldChunk.coords.hashCode())
                    }
                    pawn.world.plugins.executeChunkEnter(pawn, newChunk.coords.hashCode())
                }
            }
            pawn.world.plugins.simplePolygonAreas.forEach {
                if (pawn.tile.regionId in it.associatedRegionIds && it.containsTile(pawn.tile)) {
                    pawn.world.plugins.executeSimplePolygonAreaEnter(pawn, it.hashCode())
                }
            }
        }
    }
}
