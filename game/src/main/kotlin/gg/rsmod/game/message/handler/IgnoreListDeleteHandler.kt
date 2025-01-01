package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.IgnoreListDeleteMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.DELETED_IGNORE
import gg.rsmod.game.model.entity.Client
import gg.rsmod.util.Misc

class IgnoreListDeleteHandler : MessageHandler<IgnoreListDeleteMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: IgnoreListDeleteMessage,
    ) {
        client.attr[DELETED_IGNORE] = Misc.formatForDisplay(message.username)
        client.world.plugins.executeDeleteIgnore(client)
    }
}
