package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class CameraForceAngleMessage(
    val pitch: Int,
    val yaw: Int,
) : Message
