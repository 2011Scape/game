package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.MessagePrivateReceivedMessage
import gg.rsmod.net.packet.GamePacketBuilder
import gg.rsmod.util.Misc
import kotlin.math.min

class MessagePrivateReceivedEncoder : MessageEncoder<MessagePrivateReceivedMessage>() {
    override fun extract(
        message: MessagePrivateReceivedMessage,
        key: String,
    ): Number =
        when (key) {
            "filtered" -> 1
            "id_hi" -> message.messageId
            "id_lo" -> message.worldId
            "player_rank" -> min(message.privilege.id, 2)
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: MessagePrivateReceivedMessage,
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
            "username_unfiltered" -> {
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
