package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.MessageStructure
import gg.rsmod.game.message.impl.MessagePrivateMessage
import gg.rsmod.net.packet.DataType
import gg.rsmod.net.packet.GamePacketReader

class MessagePrivateDecoder : MessageDecoder<MessagePrivateMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): MessagePrivateMessage {
        throw RuntimeException()
    }

    override fun decode(
        opcode: Int,
        structure: MessageStructure,
        reader: GamePacketReader,
    ): MessagePrivateMessage {
        val username = reader.string
        val length = reader.getUnsigned(DataType.BYTE)

        val data = ByteArray(reader.readableBytes)
        reader.getBytes(data)

        return MessagePrivateMessage(username, data, length.toInt())
    }
}
