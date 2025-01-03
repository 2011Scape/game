package gg.rsmod.game.model

data class Friend(
    val added: Boolean = false,
    val username: String,
    val world: Int = 0,
    val friendChatRank: Int = 0,
    val oldUsername: String = "",
    val referred: Boolean = false,
    val referrer: Boolean = false,
) {
    fun getAddedAsInt(): Int {
        return if (added) 1 else 0
    }

    fun getReferredAsInt(): Int {
        return if (referred) 1 else 0
    }

    fun getReferrerAsInt(): Int {
        return if (referrer) 1 else 0
    }
}
