package gg.rsmod.game.sync.task

import gg.rsmod.game.message.impl.RebuildNormalMessage
import gg.rsmod.game.message.impl.RebuildRegionMessage
import gg.rsmod.game.model.Coordinate
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.region.Chunk
import gg.rsmod.game.sync.SynchronizationTask

/**
 * @author Tom <rspsmods@gmail.com>
 */
object PlayerPreSynchronizationTask : SynchronizationTask<Player> {

    override fun run(pawn: Player) {
        pawn.movementQueue.cycle()

        val last = pawn.lastKnownRegionBase
        val currentTile = pawn.tile
        val currentRegion = pawn.tile.regionId
        val world = pawn.world

        if (last == null || shouldRebuildRegion(last, currentTile)) {
            val regionX = ((currentTile.x shr 3) - (Chunk.MAX_VIEWPORT shr 4)) shl 3
            val regionZ = ((currentTile.z shr 3) - (Chunk.MAX_VIEWPORT shr 4)) shl 3

            pawn.lastKnownRegionBase = Coordinate(regionX, regionZ, currentTile.height)

            val xteaService = pawn.world.xteaKeyService
            val instance = pawn.world.instanceAllocator.getMap(currentTile)
            val rebuildMessage = when {
                instance != null -> RebuildRegionMessage(currentTile.x shr 3, currentTile.z shr 3, 1, instance.getCoordinates(pawn.tile), xteaService)
                else -> RebuildNormalMessage(pawn.mapSize, if(pawn.forceMapRefresh) 1 else 0, currentTile.x shr 3, currentTile.z shr 3, xteaService)
            }
            pawn.write(rebuildMessage)

            // Retrieve and iterate over surrounding region IDs from the current tile's region.
            currentTile.getSurroundingRegions(currentRegion).forEach { id ->

                // Iterate over each chunk in the region, deriving x, z coordinates from the region ID.
                for (cx in 0 until 8) {
                    for (cz in 0 until 8) {
                        // Calculate the base coordinates for each chunk within the region.
                        val chunkBaseX = ((id shr 8) * 64) + (cx * 8)
                        val chunkBaseZ = ((id and 0xFF) * 64) + (cz * 8)

                        // Allocate collision data for each height level in the chunk.
                        for (level in 0 until 4) {
                            world.collision.allocateIfAbsent(chunkBaseX, chunkBaseZ, level)
                        }
                    }
                }
            }
        }
    }

    private fun shouldRebuildRegion(old: Coordinate, new: Tile): Boolean {
        val dx = new.x - old.x
        val dz = new.z - old.z

        return dx <= Player.NORMAL_VIEW_DISTANCE || dx >= Chunk.MAX_VIEWPORT - Player.NORMAL_VIEW_DISTANCE - 1
                || dz <= Player.NORMAL_VIEW_DISTANCE || dz >= Chunk.MAX_VIEWPORT - Player.NORMAL_VIEW_DISTANCE - 1
    }
}