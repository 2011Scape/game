package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.IgnoreListDeleteMessage

class IgnoreListDeleteDecoder : MessageDecoder<IgnoreListDeleteMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): IgnoreListDeleteMessage {
        val username = stringValues["username"]!!

        return IgnoreListDeleteMessage(username)
    }
}
