package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class CameraMoveToMessage(
    val rate: Int,
    val x: Int,
    val z: Int,
    val height: Int,
    val step: Int,
) : Message
