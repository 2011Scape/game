package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.MessagePrivateReceivedMessage
import gg.rsmod.net.packet.GamePacketBuilder
import gg.rsmod.util.Misc

class MessagePrivateReceivedEncoder : MessageEncoder<MessagePrivateReceivedMessage>() {
    override fun extract(
        message: MessagePrivateReceivedMessage,
        key: String,
    ): Number =
        when (key) {
            "username_has_tags" -> if (message.privilege.id > 0) 1 else 0
            "message_count_id" -> message.messageId
            "world_id" -> message.worldId
            "player_privilege_id" -> message.privilege.id
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: MessagePrivateReceivedMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "username" -> {
                val icon =
                    when (message.privilege.id) {
                        1 -> "<img=0>"
                        2, 3 -> "<img=1>"
                        else -> ""
                    }
                val username = "$icon${Misc.formatForDisplay(message.username)}"

                val buf = GamePacketBuilder()
                buf.putString(username)

                val data = ByteArray(buf.byteBuf.readableBytes())
                buf.byteBuf.readBytes(data)
                data
            }
            "username_without_tags" -> {
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
