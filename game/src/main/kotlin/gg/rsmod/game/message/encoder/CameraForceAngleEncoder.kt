package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraForceAngleMessage

class CameraForceAngleEncoder : MessageEncoder<CameraForceAngleMessage>() {
    override fun extract(
        message: CameraForceAngleMessage,
        key: String,
    ): Number =
        when (key) {
            "pitch" -> message.pitch
            "yaw" -> message.yaw
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: CameraForceAngleMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
