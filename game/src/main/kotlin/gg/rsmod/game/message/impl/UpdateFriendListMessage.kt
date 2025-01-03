package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.Friend

data class UpdateFriendListMessage(
    val friends: List<Friend>,
) : Message
