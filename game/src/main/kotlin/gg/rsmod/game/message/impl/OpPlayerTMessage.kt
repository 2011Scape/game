package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Alycia <https://github.com/alycii>
 */
data class OpPlayerTMessage(
    val playerIndex: Int,
    val componentHash: Int,
    val componentSlot: Int,
    val movementType: Int,
) : Message
