package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.SetPublicTradeChatFilterMessage

class SetPublicTradeChatFilterEncoder : MessageEncoder<SetPublicTradeChatFilterMessage>() {
    override fun extract(
        message: SetPublicTradeChatFilterMessage,
        key: String,
    ): Number =
        when (key) {
            "public_setting" -> message.publicSetting
            "trade_setting" -> message.tradeSetting
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: SetPublicTradeChatFilterMessage,
        key: String,
    ): ByteArray {
        TODO("Not yet implemented")
    }
}
