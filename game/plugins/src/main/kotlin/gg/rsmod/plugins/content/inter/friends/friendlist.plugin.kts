package gg.rsmod.plugins.content.inter.friends

import gg.rsmod.game.message.impl.FriendListLoadedMessage
import gg.rsmod.game.message.impl.SetPrivateChatFilterMessage
import gg.rsmod.game.message.impl.SetPublicTradeChatFilterMessage
import gg.rsmod.plugins.api.ext.getAddedFriend
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.util.Misc

/**
 * Initialize friend list and chat filter settings on login
 */
on_login {
    player.write(FriendListLoadedMessage())
    player.updateFriendList()
    player.write(
        SetPrivateChatFilterMessage(
            player.privateFilterSetting.settingId,
        ),
    )
    player.write(
        SetPublicTradeChatFilterMessage(
            player.publicFilterSetting.settingId,
            player.tradeFilterSetting.settingId,
        ),
    )
}

/**
 * When adding a friend, add them to the player's friend list array if they exist and are not already on
 * the list and then update the player's friendlist in the client.
 */
on_add_friend {
    val newFriend = Misc.formatForDisplay(player.getAddedFriend())
    val playerExists = world.characterExists(newFriend)

    if (!playerExists) {
        player.message("Unable to add friend - unknown player.")
        return@on_add_friend
    }

    if (!player.friends.contains(newFriend)) player.friends.add(newFriend)
    player.updateFriendList()
    player.updateOthersFriendLists()
}

/**
 * When deleting a friend, remove them from the player's friend list array, if they exist on the list.
 */
on_delete_friend {
    val deletedFriend = Misc.formatForDisplay(player.getDeletedFriend())

    player.friends.remove(deletedFriend)
    player.updateFriendList()
    player.updateOthersFriendLists()
}

/**
 * Update friend lists of other players who have this player as a friend when this player logs in
 */
on_login {
    player.updateOthersFriendLists()
}

/**
 * Update friend lists of other players who have this player as a friend when this player logs out
 */
on_logout {
    player.updateOthersFriendLists()
}
