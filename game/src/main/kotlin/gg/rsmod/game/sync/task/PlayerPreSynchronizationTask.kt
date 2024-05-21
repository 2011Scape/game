package gg.rsmod.game.sync.task

import gg.rsmod.game.message.impl.RebuildNormalMessage
import gg.rsmod.game.message.impl.RebuildRegionMessage
import gg.rsmod.game.model.Coordinate
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.region.Chunk
import gg.rsmod.game.sync.SynchronizationTask
import net.runelite.cache.IndexType
import net.runelite.cache.definitions.loaders.MapLoader

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


            // Retrieve the surrounding region IDs from the current tile's region.
            val surroundingRegions = currentTile.getSurroundingRegions(currentRegion)

            // Iterate over each region ID in the surrounding regions.
            surroundingRegions.forEach { id ->

                // Extract the X and Z coordinates from the region ID. 'x' is derived by shifting right 8 bits,
                // and 'z' is derived by masking the lower 8 bits.
                val x = id shr 8
                val z = id and 0xFF

                // Calculate the base X and Z coordinates for the region by multiplying the extracted coordinates by 64.
                val baseX = x * 64
                val baseZ = z * 64

                // Iterate over each chunk within the region. There are 8 chunks along each axis in a region.
                for (cx in 0 until 8) {
                    for (cz in 0 until 8) {
                        // Calculate the base coordinates for each chunk by adding the offset multiplied by 8 (chunk size).
                        val chunkBaseX = baseX + cx * 8
                        val chunkBaseZ = baseZ + cz * 8

                        // Iterate over each level of the game's height (assumed to be 4 levels from 0 to 3).
                        for (level in 0 until 4) {
                            // Allocate collision data for each chunk at each level, ensuring no data for the chunk and level
                            // already exists. If absent, this function initializes or allocates necessary data structures
                            // for handling collisions at these coordinates.
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