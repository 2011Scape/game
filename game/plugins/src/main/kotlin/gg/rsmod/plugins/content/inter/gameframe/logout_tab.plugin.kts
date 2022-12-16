package gg.rsmod.plugins.content.inter.gameframe

import gg.rsmod.game.message.impl.LogoutFullMessage
import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER

on_button(interfaceId = 182, component = 13) {
    if (!player.timers.has(ACTIVE_COMBAT_TIMER)) {
        player.requestLogout()
        player.write(LogoutFullMessage())
        player.channelClose()
    } else {
        player.message("You can't log out until 10 seconds after the end of combat.")
    }
}

// TODO: come back to this when/if lobby is enabled
on_button(interfaceId = 182, component = 6) {
    if (!player.timers.has(ACTIVE_COMBAT_TIMER)) {
        player.requestLogout()
        player.write(LogoutFullMessage())
        player.channelClose()
    } else {
        player.message("You can't log out until 10 seconds after the end of combat.")
    }
}