package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Alycia <https://github.com/alycii>
 */
data class IfSetSpriteMessage(
    val hash: Int,
    val sprite: Int,
) : Message
