package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.World
import gg.rsmod.game.model.priv.Privilege

data class MessagePrivateReceivedMessage(
    val username: String,
    val privilege: Privilege,
    val messageId: Int,
    val worldId: Int,
    val message: String,
    val world: World,
) : Message
