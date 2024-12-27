package gg.rsmod.plugins.content.inter.friends

import gg.rsmod.game.message.impl.FriendListLoadedMessage
import gg.rsmod.game.service.serializer.json.JsonPlayerSerializer
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
    val playerExists = world.getService(JsonPlayerSerializer::class.java)!!.playerExists(newFriend)

    if (!playerExists) {
        player.message("Unable to add friend - unknown player.")
        return@on_add_friend
    }

    if (!player.friends.contains(newFriend)) player.friends.add(newFriend)
    player.updateFriendList()
}

/**
 * When deleting a friend, remove them from the player's friend list array, if they exist on the list.
 */
on_delete_friend {
    val deletedFriend = player.getDeletedFriend()

    player.friends.remove(deletedFriend)
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
                // Need to wait 1 cycle, otherwise the player's friendlist is
                // updated prior to the other player logging off
                wait(1)
                otherPlayer.updateFriendList()
            }
        }
    }
}

fun doesPlayerExist(username: String): Boolean {
    val player = world.getPlayerForName(username)
    if (player != null) return true

    return Paths.get("./data/saves/${username.lowercase()}").exists()
}
