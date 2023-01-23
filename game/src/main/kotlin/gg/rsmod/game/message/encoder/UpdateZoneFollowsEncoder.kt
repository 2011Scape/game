package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateZoneFollowsMessage

/**
 * @author Tom <rspsmods@gmail.com>
 */
class UpdateZoneFollowsEncoder : MessageEncoder<UpdateZoneFollowsMessage>() {

    override fun extract(message: UpdateZoneFollowsMessage, key: String): Number = when (key) {
        "x" -> message.x
        "z" -> message.z
        "height" -> message.height
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: UpdateZoneFollowsMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}