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
        pawn.handleFutureRoute()
        pawn.movementQueue.cycle()

        val last = pawn.lastKnownRegionBase
        val current = pawn.tile

        pawn.movedToInstance = last != null && last.x < 6368 && current.x >= 6400
        pawn.movedFromInstance = last != null && last.x >= 6368 && current.x < 6400

        if (last == null || shouldRebuildRegion(last, current)) {
            val regionX = ((current.x shr 3) - (Chunk.MAX_VIEWPORT shr 4)) shl 3
            val regionZ = ((current.z shr 3) - (Chunk.MAX_VIEWPORT shr 4)) shl 3

            pawn.lastKnownRegionBase = Coordinate(regionX, regionZ, current.height)

            val xteaService = pawn.world.xteaKeyService
            val instance = pawn.world.instanceAllocator.getMap(current)
            val rebuildMessage =
                when {
                    instance != null -> {
                        RebuildRegionMessage(
                            current.x shr 3,
                            current.z shr 3,
                            0,
                            instance.getCoordinates(pawn.tile),
                            0,
                            1,
                            xteaService,
                        )
                    }
                    else ->
                        RebuildNormalMessage(
                            pawn.mapSize,
                            if (pawn.forceMapRefresh) 1 else 0,
                            current.x shr 3,
                            current.z shr 3,
                            xteaService,
                        )
                }
            pawn.write(rebuildMessage)
        }
    }

    private fun shouldRebuildRegion(
        old: Coordinate,
        new: Tile,
    ): Boolean {
        val dx = new.x - old.x
        val dz = new.z - old.z

        return dx <= Player.NORMAL_VIEW_DISTANCE ||
            dx >= Chunk.MAX_VIEWPORT - Player.NORMAL_VIEW_DISTANCE - 1 ||
            dz <= Player.NORMAL_VIEW_DISTANCE ||
            dz >= Chunk.MAX_VIEWPORT - Player.NORMAL_VIEW_DISTANCE - 1
    }
}
