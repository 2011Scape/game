package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.FriendListAddMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.ADDED_FRIEND
import gg.rsmod.game.model.entity.Client
import gg.rsmod.util.Misc

class FriendListAddHandler : MessageHandler<FriendListAddMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: FriendListAddMessage,
    ) {
        client.attr[ADDED_FRIEND] = Misc.formatForDisplay(message.username)
        client.world.plugins.executeAddFriend(client)
    }
}
