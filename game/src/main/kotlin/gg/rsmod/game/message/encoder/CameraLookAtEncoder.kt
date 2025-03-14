package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraLookAtMessage

class CameraLookAtEncoder : MessageEncoder<CameraLookAtMessage>() {
    override fun extract(
        message: CameraLookAtMessage,
        key: String,
    ): Number =
        when (key) {
            "x" -> message.x
            "z" -> message.z
            "height" -> message.height
            "step" -> message.step
            "speed" -> message.speed
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: CameraLookAtMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
