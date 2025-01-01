package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.ChatFilterMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client

class ChatFilterHandler : MessageHandler<ChatFilterMessage> {
    override fun handle(
        client: Client,
        world: World,
        message: ChatFilterMessage,
    ) {
        client.publicFilterSetting = message.publicSetting
        client.privateFilterSetting = message.privateSetting
        client.tradeFilterSetting = message.tradeSetting

        client.updateFriendList()
        client.updateOthersFriendLists()
    }
}
