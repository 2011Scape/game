package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraMoveToMessage

class CameraMoveToEncoder : MessageEncoder<CameraMoveToMessage>() {
    override fun extract(
        message: CameraMoveToMessage,
        key: String,
    ): Number =
        when (key) {
            "rate" -> message.rate
            "x" -> message.x
            "z" -> message.z
            "height" -> message.height
            "step" -> message.step
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: CameraMoveToMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
