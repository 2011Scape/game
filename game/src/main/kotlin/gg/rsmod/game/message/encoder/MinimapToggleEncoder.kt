package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.MinimapToggleMessage

class MinimapToggleEncoder : MessageEncoder<MinimapToggleMessage>() {
    override fun extract(
        message: MinimapToggleMessage,
        key: String
    ): Number =
        when (key) {
            "state" -> message.state
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: MinimapToggleMessage,
        key: String
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
