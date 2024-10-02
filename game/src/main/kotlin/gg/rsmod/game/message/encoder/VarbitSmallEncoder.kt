package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.VarbitSmallMessage

/**
 * @author Alycia
 */
class VarbitSmallEncoder : MessageEncoder<VarbitSmallMessage>() {

    override fun extract(message: VarbitSmallMessage, key: String): Number = when (key) {
        "id" -> message.id
        "value" -> message.value
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: VarbitSmallMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}