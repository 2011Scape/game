package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.OpLoc1Message

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpLoc1Decoder : MessageDecoder<OpLoc1Message>() {

    override fun decode(opcode: Int, opcodeIndex: Int, values: HashMap<String, Number>, stringValues: HashMap<String, String>): OpLoc1Message {
        val movementType = values["movement_type"]!!.toInt()
        val x = values["x"]!!.toInt()
        val id = values["id"]!!.toInt()
        val z = values["z"]!!.toInt()
        return OpLoc1Message(movementType, x, id, z)
    }
}