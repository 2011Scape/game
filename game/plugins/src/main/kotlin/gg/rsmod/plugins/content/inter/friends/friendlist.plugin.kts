package gg.rsmod.plugins.content.inter.friends

import gg.rsmod.game.message.impl.FriendListLoadedMessage
import gg.rsmod.plugins.api.ext.getAddedFriend
import gg.rsmod.plugins.api.ext.player
import java.nio.file.Paths
import kotlin.io.path.exists

/**
 * Initialize friend list on login
 */
on_login {
    player.write(FriendListLoadedMessage())
    player.updateFriendList()
}

/**
 * When adding a friend, add them to the player's friend list array if they exist and are not already on
 * the list and then update the player's friendlist in the client.
 */
on_add_friend {
    val newFriend = player.getAddedFriend()

    if (!doesPlayerExist(newFriend)) {
        player.message("Unable to add friend - unknown player.")
        return@on_add_friend
    }

    if (!player.friends.contains(newFriend)) player.friends.add(newFriend)
    player.updateFriendList()
}

/**
 * Update friend lists of other players who have this player as a friend when this player logs in
 */
on_login {
    world.players.forEach { otherPlayer ->
        if (otherPlayer.friends.contains(player.username)) {
            otherPlayer.updateFriendList()
        }
    }
}

/**
 * Update friend lists of other players who have this player as a friend when this player logs out
 */
on_logout {
    world.players.forEach { otherPlayer ->
        if (otherPlayer.friends.contains(player.username)) {
            otherPlayer.queue {
                wait(1)
                otherPlayer.updateFriendList()
            }
        }
    }
}

/**
 * Checks to see if the player exists in the [World] or if the player is present
 * in the saves folder
 *
 * @param username The username of the player
 *
 * @return If the player exists either in the world players array, or in the server's save files.
 */
fun doesPlayerExist(username: String): Boolean {
    val player = world.getPlayerForName(username)
    if (player != null) return true

    return Paths.get("./data/saves/$username").exists()
}
