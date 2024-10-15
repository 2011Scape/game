package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.MusicEffectMessage

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */
class MusicEffectEncoder : MessageEncoder<MusicEffectMessage>() {
    override fun extract(
        message: MusicEffectMessage,
        key: String,
    ): Number =
        when (key) {
            "id" -> message.id
            "unknown_byte" -> message.unknownByte
            "volume" -> message.volume
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: MusicEffectMessage,
        key: String,
    ): ByteArray = throw Exception("Unhandled value key.")
}
