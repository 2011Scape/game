package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.WorldMapCloseMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client

class WorldMapCloseHandler : MessageHandler<WorldMapCloseMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: WorldMapCloseMessage,
    ) {
    }
}
