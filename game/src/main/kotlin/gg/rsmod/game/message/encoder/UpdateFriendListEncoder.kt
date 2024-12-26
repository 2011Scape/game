package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateFriendListMessage

class UpdateFriendListEncoder : MessageEncoder<UpdateFriendListMessage>() {
    override fun extract(
        message: UpdateFriendListMessage,
        key: String,
    ): Number =
        when (key) {
            "added" -> if (message.added) 1 else 0
            "world_id" -> message.worldId
            "rank" -> message.rank
            "referred" -> if (message.referred) 1 else 0
            "referrer" -> if (message.referrer) 1 else 0
            else -> throw Exception("Unhandled value key.")
        }

    override fun extractBytes(
        message: UpdateFriendListMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "username" -> {
                val data = ByteArray(message.username.length + 1)
                System.arraycopy(message.username.toByteArray(), 0, data, 0, data.size - 1)
                data[data.size - 1] = 0
                data
            }
            "old_username" -> {
                val data = ByteArray(message.oldUsername.length + 1)
                System.arraycopy(message.oldUsername.toByteArray(), 0, data, 0, data.size - 1)
                data[data.size - 1] = 0
                data
            }
            "world_name" -> {
                val data = ByteArray(message.worldName.length + 1)
                System.arraycopy(message.worldName.toByteArray(), 0, data, 0, data.size - 1)
                data[data.size - 1] = 0
                data
            }
            else -> throw Exception("Unhandled value key.")
        }
}
