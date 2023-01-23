package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateZoneClearMessage

/**
 * @author Tom <rspsmods@gmail.com>
 */
class UpdateZoneClearEncoder : MessageEncoder<UpdateZoneClearMessage>() {

    override fun extract(message: UpdateZoneClearMessage, key: String): Number = when (key) {
        "x" -> message.x
        "z" -> message.z
        "height" -> message.height
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: UpdateZoneClearMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}