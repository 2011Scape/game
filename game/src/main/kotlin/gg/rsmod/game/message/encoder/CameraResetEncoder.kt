package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraResetMessage

class CameraResetEncoder : MessageEncoder<CameraResetMessage>() {
    override fun extract(
        message: CameraResetMessage,
        key: String,
    ): Number {
        TODO("Not yet implemented")
    }

    override fun extractBytes(
        message: CameraResetMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
