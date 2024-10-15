package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author Alycia <https://github.com/alycii>
 */
data class KeyTypedMessage(
    val keycode: Int,
) : Message
