package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.IgnoreListAddMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.ADDED_IGNORE
import gg.rsmod.game.model.entity.Client
import gg.rsmod.util.Misc

class IgnoreListAddHandler : MessageHandler<IgnoreListAddMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: IgnoreListAddMessage,
    ) {
        client.attr[ADDED_IGNORE] = Misc.formatForDisplay(message.username)
        client.world.plugins.executeAddIgnore(client)
    }
}
