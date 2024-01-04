package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.ChatTypeMessage

class ChatTypeDecoder : MessageDecoder<ChatTypeMessage>() {

	override fun decode(
		opcode: Int, opcodeIndex: Int, values: HashMap<String, Number>, stringValues: HashMap<String, String>
	): ChatTypeMessage {
		val type = values["type"]!!.toInt()
		return ChatTypeMessage(type)
	}
}