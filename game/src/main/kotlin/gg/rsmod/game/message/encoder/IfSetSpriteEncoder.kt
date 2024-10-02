package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.IfSetSpriteMessage

/**
 * @author Alycia <https://github.com/alycii>
 */
class IfSetSpriteEncoder : MessageEncoder<IfSetSpriteMessage>() {
    override fun extract(
        message: IfSetSpriteMessage,
        key: String,
    ): Number =
        when (key) {
            "hash" -> message.hash
            "sprite" -> message.sprite
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: IfSetSpriteMessage,
        key: String,
    ): ByteArray = throw Exception("Unhandled value key.")
}
