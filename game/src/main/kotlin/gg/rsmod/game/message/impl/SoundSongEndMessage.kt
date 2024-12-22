package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class SoundSongEndMessage(
    val songId: Int,
) : Message
