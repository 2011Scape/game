package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.WorldMapCloseMessage

class WorldMapCloseDecoder : MessageDecoder<WorldMapCloseMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): WorldMapCloseMessage {
        return WorldMapCloseMessage()
    }
}
