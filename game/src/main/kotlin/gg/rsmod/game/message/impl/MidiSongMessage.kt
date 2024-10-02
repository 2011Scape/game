package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class MidiSongMessage(
    val delay: Int,
    val id: Int,
    val volume: Int,
) : Message
