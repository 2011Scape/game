package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class UpdateFriendListMessage(
    val added: Boolean = false,
    val username: String,
    val oldUsername: String = "",
    val worldId: Int,
    val rank: Int,
    val referred: Boolean = false,
    val worldName: String,
    val referrer: Boolean = false,
) : Message
