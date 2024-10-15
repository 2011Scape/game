package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

// Not implemented yet, used for voices
data class SynthSoundRepeatMessage(
    val sound: Int,
    val loops: Int,
    val delay: Int,
    val volume: Int,
) : Message
