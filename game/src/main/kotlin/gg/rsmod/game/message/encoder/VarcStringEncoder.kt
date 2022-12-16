package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.VarcStringMessage

/**
 * @author Alycia
 */
class VarcStringEncoder : MessageEncoder<VarcStringMessage>() {

    override fun extract(message: VarcStringMessage, key: String): Number = when (key) {
        "id" -> message.id
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: VarcStringMessage, key: String): ByteArray = when (key) {
        "text" -> {
            val data = ByteArray(message.text.length + 1)
            System.arraycopy(message.text.toByteArray(), 0, data, 0, data.size - 1)
            data[data.size - 1] = 0
            data
        }
        else -> throw Exception("Unhandled value key.")
    }
}