package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

data class MessagePrivateMessage(
    val username: String,
    val message: ByteArray,
    val length: Int,
) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessagePrivateMessage

        if (username != other.username) return false
        if (!message.contentEquals(other.message)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + message.contentHashCode()
        return result
    }
}
