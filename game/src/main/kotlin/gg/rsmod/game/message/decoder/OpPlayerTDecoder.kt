package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.OpPlayerTMessage

/**
 * @author Alycia <https://github.com/alycii>
 */
class OpPlayerTDecoder : MessageDecoder<OpPlayerTMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): OpPlayerTMessage {
        val playerIndex = values["player_index"]!!.toInt()
        val componentHash = values["component_hash"]!!.toInt()
        val componentSlot = values["component_slot"]!!.toInt()
        val movementType = values["movement_type"]!!.toInt()
        return OpPlayerTMessage(playerIndex, componentHash, componentSlot, movementType)
    }
}
