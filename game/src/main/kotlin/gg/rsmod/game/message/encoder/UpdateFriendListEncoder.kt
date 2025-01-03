package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.UpdateFriendListMessage
import io.netty.buffer.Unpooled

class UpdateFriendListEncoder : MessageEncoder<UpdateFriendListMessage>() {
    override fun extractBytes(
        message: UpdateFriendListMessage,
        key: String,
    ): ByteArray =
        when (key) {
            "friends" -> {
                val buf = Unpooled.buffer()
                message.friends.forEach { friend ->
                    // Building the string bytes for username
                    val usernameData = ByteArray(friend.username.length + 1)
                    System.arraycopy(friend.username.toByteArray(), 0, usernameData, 0, usernameData.size - 1)
                    usernameData[usernameData.size - 1] = 0

                    // Building the stream bytes for oldUsername
                    val oldUsernameData = ByteArray(friend.oldUsername.length + 1)
                    System.arraycopy(friend.oldUsername.toByteArray(), 0, oldUsernameData, 0, oldUsernameData.size - 1)
                    oldUsernameData[oldUsernameData.size - 1] = 0

                    // Building the string bytes for worldName
                    val worldNameString = "World ${friend.world}"
                    val worldNameData = ByteArray("World ${friend.world}".length + 1)
                    System.arraycopy(worldNameString.toByteArray(), 0, worldNameData, 0, worldNameData.size - 1)
                    worldNameData[worldNameData.size - 1] = 0

                    buf.writeByte(friend.getAddedAsInt())
                    buf.writeBytes(usernameData)
                    buf.writeBytes(oldUsernameData)
                    buf.writeShort(friend.world)
                    buf.writeByte(friend.friendChatRank)
                    buf.writeByte(friend.getReferredAsInt())
                    if (friend.world > 0) {
                        buf.writeBytes(worldNameData)
                        buf.writeByte(friend.getReferrerAsInt())
                    }
                }

                val data = ByteArray(buf.readableBytes())
                buf.readBytes(data)
                data
            }
            else -> throw Exception("Unhandled value key.")
        }

    override fun extract(
        message: UpdateFriendListMessage,
        key: String,
    ): Number {
        TODO("Not yet implemented")
    }
}
