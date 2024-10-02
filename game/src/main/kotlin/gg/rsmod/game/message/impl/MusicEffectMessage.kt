package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Harley <github.com/HarleyGilpin>
 */
data class MusicEffectMessage(
    val id: Int,
    val unknownByte: Int = 0,
    val volume: Int = 255,
) : Message
