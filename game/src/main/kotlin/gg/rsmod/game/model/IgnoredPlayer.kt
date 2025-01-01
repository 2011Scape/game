package gg.rsmod.game.model

data class IgnoredPlayer(
    val username: String,
    val usernameUnfiltered: String? = null,
    val oldUsername: String? = null,
    val oldUsernameUnfiltered: String? = null,
)
