package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.IfSetScrollVerticalMessage

/**
 * @author Alycia <https://github.com/alycii>
 */
class IfSetScrollVerticalEncoder : MessageEncoder<IfSetScrollVerticalMessage>() {
    override fun extract(
        message: IfSetScrollVerticalMessage,
        key: String,
    ): Number =
        when (key) {
            "hash" -> message.hash
            "height" -> message.height
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: IfSetScrollVerticalMessage,
        key: String,
    ): ByteArray = throw Exception("Unhandled value key.")
}
