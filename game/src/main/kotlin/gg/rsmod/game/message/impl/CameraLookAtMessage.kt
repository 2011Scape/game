package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class CameraLookAtMessage(
    val x: Int,
    val z: Int,
    val height: Int,
    val step: Int,
    val speed: Int,
) : Message
