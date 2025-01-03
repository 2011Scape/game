package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.MessagePrivateSentMessage
import gg.rsmod.net.packet.GamePacketBuilder
import gg.rsmod.util.Misc

class MessagePrivateSentEncoder : MessageEncoder<MessagePrivateSentMessage>() {
    override fun extract(
        message: MessagePrivateSentMessage,
        key: String,
    ): Number {
        TODO("Not yet implemented")
    }

    override fun extractBytes(
        message: MessagePrivateSentMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "username" -> {
                val buf = GamePacketBuilder()
                buf.putString(Misc.formatForDisplay(message.username))

                val data = ByteArray(buf.byteBuf.readableBytes())
                buf.byteBuf.readBytes(data)
                data
            }

            "message" -> {
                val buf = GamePacketBuilder()
                val compressed = ByteArray(256)
                val offset = message.world.huffman.compress(Misc.formatSentence(message.message), compressed)

                buf.putSmart(message.message.length)
                buf.putBytes(compressed, 0, offset)

                val data = ByteArray(buf.byteBuf.readableBytes())
                buf.byteBuf.readBytes(data)
                data
            }

            else -> throw Exception("Unhandled value key.")
        }
}
