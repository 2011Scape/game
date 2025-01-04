package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraShakeMessage

class CameraShakeEncoder : MessageEncoder<CameraShakeMessage>() {
    override fun extract(
        message: CameraShakeMessage,
        key: String,
    ): Number =
        when (key) {
            "frequency" -> message.frequency
            "index" -> message.index
            "time" -> message.time
            "center" -> message.center
            "amplitude" -> message.amplitude
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: CameraShakeMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
