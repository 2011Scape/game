package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.SoundSongEndMessage

class SoundSongEndDecoder : MessageDecoder<SoundSongEndMessage>() {
    override fun decode(
        opcode: Int,
        opcodeIndex: Int,
        values: HashMap<String, Number>,
        stringValues: HashMap<String, String>,
    ): SoundSongEndMessage {
        val songId = values["songId"]!!
        return SoundSongEndMessage(songId.toInt())
    }
}
