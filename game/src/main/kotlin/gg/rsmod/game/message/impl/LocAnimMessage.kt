package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.entity.GameObject

data class LocAnimMessage(
    val gameObject: GameObject,
    val animation: Int,
) : Message
