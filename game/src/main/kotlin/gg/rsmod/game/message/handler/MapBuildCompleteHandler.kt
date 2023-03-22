package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.MapBuildCompleteMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.service.GameService
import gg.rsmod.game.sync.block.UpdateBlockType
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet

/**
 * @author Tom <rspsmods@gmail.com>
 */
class MapBuildCompleteHandler : MessageHandler<MapBuildCompleteMessage> {

    override fun handle(client: Client, world: World, message: MapBuildCompleteMessage) {
        client.lastMapBuildTime = world.currentCycle
        val chunk = client.world.chunks.get(client.tile.chunkCoords, createIfNeeded = false)
        if(chunk != null) {
            client.world.getService(GameService::class.java)?.let { service ->
                val surroundings = chunk.coords.getSurroundingCoords()

                surroundings.forEach { coords ->
                    chunk.sendUpdates(client, service)
                }
            }
        }
    }
}