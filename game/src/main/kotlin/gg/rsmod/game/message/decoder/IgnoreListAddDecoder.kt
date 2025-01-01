package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.IgnoreListAddMessage

class IgnoreListAddDecoder : MessageDecoder<IgnoreListAddMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): IgnoreListAddMessage {
        val username = stringValues["username"]!!
        val untilLogout = values["until_logout"]!! == 1

        return IgnoreListAddMessage(username, untilLogout)
    }
}
