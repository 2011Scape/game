package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateIgnoreListMessage
import gg.rsmod.net.packet.DataType
import gg.rsmod.net.packet.GamePacketBuilder

class UpdateIgnoreListEncoder : MessageEncoder<UpdateIgnoreListMessage>() {
    override fun extractBytes(
        message: UpdateIgnoreListMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "ignores" -> {
                val buf = GamePacketBuilder()
                buf.put(DataType.BYTE, message.ignoredPlayers.count())

                message.ignoredPlayers.forEach { player ->
                    buf.putString(player.username)
                    buf.putString(player.usernameUnfiltered ?: "")
                    buf.putString(player.oldUsername ?: "")
                    buf.putString(player.oldUsernameUnfiltered ?: "")
                }

                val data = ByteArray(buf.byteBuf.readableBytes())
                buf.byteBuf.readBytes(data)
                data
            }
            else -> throw Exception("Unhandled value key.")
        }

    override fun extract(
        message: UpdateIgnoreListMessage,
        key: String,
    ): Number {
        TODO("Not yet implemented")
    }
}
