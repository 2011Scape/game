package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class CameraShakeMessage(
    val frequency: Int,
    val index: Int,
    val time: Int,
    val center: Int,
    val amplitude: Int,
) : Message
