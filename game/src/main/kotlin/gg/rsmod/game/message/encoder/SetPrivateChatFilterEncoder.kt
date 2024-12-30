package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.SetPrivateChatFilterMessage

class SetPrivateChatFilterEncoder : MessageEncoder<SetPrivateChatFilterMessage>() {
    override fun extract(
        message: SetPrivateChatFilterMessage,
        key: String,
    ): Number =
        when(key) {
            "private_setting" -> message.privateSetting
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: SetPrivateChatFilterMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
