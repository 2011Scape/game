package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.VarbitSmallMessage
import gg.rsmod.game.message.impl.VarcSmallMessage

/**
 * @author Alycia
 */
class VarcSmallEncoder : MessageEncoder<VarcSmallMessage>() {

    override fun extract(message: VarcSmallMessage, key: String): Number = when (key) {
        "id" -> message.id
        "value" -> message.value
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: VarcSmallMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}