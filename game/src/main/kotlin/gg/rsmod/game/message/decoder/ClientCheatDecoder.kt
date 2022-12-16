package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.ClientCheatMessage

/**
 * @author Tom <rspsmods@gmail.com>
 */
class ClientCheatDecoder : MessageDecoder<ClientCheatMessage>() {

    override fun decode(opcode: Int, opcodeIndex: Int, values: HashMap<String, Number>, stringValues: HashMap<String, String>): ClientCheatMessage {
        val additionalInformation = values["additional_information"]!!.toShort()
        return ClientCheatMessage(additionalInformation, stringValues["command"]!!)
    }
}
