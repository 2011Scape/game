package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.ChatFilterType

data class ChatFilterMessage(
    val publicSetting: ChatFilterType,
    val privateSetting: ChatFilterType,
    val tradeSetting: ChatFilterType,
) : Message
