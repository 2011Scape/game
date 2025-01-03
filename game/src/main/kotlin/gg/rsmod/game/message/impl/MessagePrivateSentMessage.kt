package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.World

data class MessagePrivateSentMessage(
    val username: String,
    val message: String,
    val world: World,
) : Message
