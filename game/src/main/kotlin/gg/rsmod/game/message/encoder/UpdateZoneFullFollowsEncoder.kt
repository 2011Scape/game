package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateZoneFullFollowsMessage

/**
 * @author Tom <rspsmods@gmail.com>
 */
class UpdateZoneFullFollowsEncoder : MessageEncoder<UpdateZoneFullFollowsMessage>() {

    override fun extract(message: UpdateZoneFullFollowsMessage, key: String): Number = when (key) {
        "x" -> message.x
        "z" -> message.z
        "height" -> message.height
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: UpdateZoneFullFollowsMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}