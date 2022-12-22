package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.VarbitLargeMessage

/**
 * @author Alycia
 */
class VarbitLargeEncoder : MessageEncoder<VarbitLargeMessage>() {

    override fun extract(message: VarbitLargeMessage, key: String): Number = when (key) {
        "id" -> message.id
        "value" -> message.value
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: VarbitLargeMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}