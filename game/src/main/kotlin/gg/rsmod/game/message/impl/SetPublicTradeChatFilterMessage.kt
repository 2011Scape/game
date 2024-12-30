package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class SetPublicTradeChatFilterMessage(
    val publicSetting: Int,
    val tradeSetting: Int,
) : Message
