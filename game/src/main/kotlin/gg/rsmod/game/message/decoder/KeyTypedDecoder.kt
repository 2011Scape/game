package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.KeyTypedMessage

/**
 * @author Alycia <https://github.com/alycii>
 */
class KeyTypedDecoder : MessageDecoder<KeyTypedMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): KeyTypedMessage {
        val keycode = values["keycode"]!!.toInt()
        return KeyTypedMessage(keycode)
    }
}
