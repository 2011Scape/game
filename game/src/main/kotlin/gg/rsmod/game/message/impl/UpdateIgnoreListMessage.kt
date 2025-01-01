package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.IgnoredPlayer

data class UpdateIgnoreListMessage(
    val ignoredPlayers: List<IgnoredPlayer>,
) : Message
