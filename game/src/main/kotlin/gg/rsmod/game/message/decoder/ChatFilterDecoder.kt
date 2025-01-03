package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.ChatFilterMessage
import gg.rsmod.game.model.ChatFilterType

class ChatFilterDecoder : MessageDecoder<ChatFilterMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): ChatFilterMessage {
        val publicSetting = ChatFilterType.getSettingById(values["public_setting"]!!.toInt())!!
        val privateSetting = ChatFilterType.getSettingById(values["private_setting"]!!.toInt())!!
        val tradeSetting = ChatFilterType.getSettingById(values["trade_setting"]!!.toInt())!!

        return ChatFilterMessage(publicSetting, privateSetting, tradeSetting)
    }
}
