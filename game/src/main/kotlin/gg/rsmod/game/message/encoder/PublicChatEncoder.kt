package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.PublicChatMessage
import gg.rsmod.net.packet.GamePacketBuilder

/**
 * @author Alycia
 */
class PublicChatEncoder : MessageEncoder<PublicChatMessage>() {

    override fun extract(message: PublicChatMessage, key: String): Number = when (key) {
        "index" -> message.index
        "icon" -> message.icon
        "effects" -> message.effects
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: PublicChatMessage, key: String): ByteArray = when (key) {
        "text" -> {
            val buf = GamePacketBuilder()
            val compressed = ByteArray(256)
            val offset = message.world.huffman.compress(message.text, compressed)

            buf.putSmart(message.text.length)
            buf.putBytes(compressed, 0, offset)

            val data = ByteArray(buf.byteBuf.readableBytes())
            buf.byteBuf.readBytes(data)
            data
        }
        else -> throw Exception("Unhandled value key.")
    }
}