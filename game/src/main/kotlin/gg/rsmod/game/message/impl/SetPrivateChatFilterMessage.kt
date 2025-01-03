package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class SetPrivateChatFilterMessage(
    val privateSetting: Int,
) : Message
