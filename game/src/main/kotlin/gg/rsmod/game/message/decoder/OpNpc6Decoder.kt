package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.OpNpc6Message

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpNpc6Decoder : MessageDecoder<OpNpc6Message>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): OpNpc6Message {
        val index = values["index"]!!.toInt()
        val movement = values["movement_type"]!!.toInt()
        return OpNpc6Message(index, movement)
    }
}
