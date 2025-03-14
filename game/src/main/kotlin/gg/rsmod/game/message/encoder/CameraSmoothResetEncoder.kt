package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.CameraSmoothResetMessage

class CameraSmoothResetEncoder : MessageEncoder<CameraSmoothResetMessage>() {
    override fun extract(
        message: CameraSmoothResetMessage,
        key: String,
    ): Number {
        TODO("Not yet implemented")
    }

    override fun extractBytes(
        message: CameraSmoothResetMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
