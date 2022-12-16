package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.VarbitLargeMessage
import gg.rsmod.game.message.impl.VarbitSmallMessage
import gg.rsmod.game.message.impl.VarcLargeMessage

/**
 * @author Alycia
 */
class VarcLargeEncoder : MessageEncoder<VarcLargeMessage>() {

    override fun extract(message: VarcLargeMessage, key: String): Number = when (key) {
        "id" -> message.id
        "value" -> message.value
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: VarcLargeMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}