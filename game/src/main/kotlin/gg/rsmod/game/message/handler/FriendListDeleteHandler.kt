package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.FriendListDeleteMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.DELETED_FRIEND
import gg.rsmod.game.model.entity.Client

class FriendListDeleteHandler : MessageHandler<FriendListDeleteMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: FriendListDeleteMessage,
    ) {
        client.attr[DELETED_FRIEND] = message.username
        client.world.plugins.executeDeleteFriend(client)
    }
}
