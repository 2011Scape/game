package gg.rsmod.plugins.content.skills.farming.constants

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.content.skills.farming.logic.FarmingManager
import java.time.ZoneOffset
import java.time.ZonedDateTime

object Constants {
    val playerFarmingTimer =
        TimerKey(
            persistenceKey = "farming_timer",
            tickOffline = false,
            resetOnDeath = false,
            tickForward = false,
            removeOnZero = false,
        )
    val worldFarmingTimer = TimerKey()

    val worldFarmTick = AttributeKey<Int>()
    val farmingManagerAttr = AttributeKey<FarmingManager>()

    const val playerFarmingTickLength = 500
    const val worldFarmingTickLength = 500

    val firstFarmTickDate: ZonedDateTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
}
