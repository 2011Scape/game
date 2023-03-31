package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.MapBuildCompleteMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.region.Chunk.Companion.REGION_SIZE
import gg.rsmod.game.model.region.ChunkCoords
import gg.rsmod.game.service.GameService
import gg.rsmod.game.sync.block.UpdateBlockType
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet

/**
 * @author Tom <rspsmods@gmail.com>
 */
class MapBuildCompleteHandler : MessageHandler<MapBuildCompleteMessage> {

    override fun handle(client: Client, world: World, message: MapBuildCompleteMessage) {
        client.lastMapBuildTime = world.currentCycle

        val player = client as Player
        val newChunk = player.world.chunks.get(player.tile.chunkCoords, createIfNeeded = false)
        if (newChunk != null) {
            val newSurroundings = newChunk.coords.getSurroundingCoords(chunkRadius = REGION_SIZE)
            newSurroundings.forEach { coords ->
                val chunk = player.world.chunks.get(coords, createIfNeeded = false) ?: return@forEach
                chunk.sendUpdates(player)
            }
        }
    }
}