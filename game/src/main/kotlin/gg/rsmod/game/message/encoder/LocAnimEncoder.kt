package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.LocAnimMessage
import java.lang.Exception

class LocAnimEncoder : MessageEncoder<LocAnimMessage>() {
    override fun extract(
        message: LocAnimMessage,
        key: String,
    ): Number =
        when (key) {
            "tile" -> message.gameObject.tile.get30BitsLocationHash
            "animation" -> message.animation
            "unknown" -> (message.gameObject.type shl 2) + (message.gameObject.rot and 0x3)
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: LocAnimMessage,
        key: String,
    ): ByteArray = throw Exception("Unhandled value key.")
}
