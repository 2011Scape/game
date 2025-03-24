package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class MinimapToggleMessage(
    val state: Int
) : Message
