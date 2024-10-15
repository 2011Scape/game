package gg.rsmod.plugins.content.mechanics.run

import gg.rsmod.plugins.content.mechanics.resting.Resting

on_login {
    player.timers[RunEnergy.RUN_DRAIN] = 1
    player.sendRunEnergy(player.runEnergy.toInt())
    player.setVarp(RunEnergy.RUN_ENABLED_VARP, if (player.isRunning()) 1 else 0)
}

on_timer(RunEnergy.RUN_DRAIN) {
    player.timers[RunEnergy.RUN_DRAIN] = 1
    RunEnergy.drain(player)
}

/**
 * Button by minimap.
 */
on_button(interfaceId = 750, component = 1) {
    when (player.getInteractingOpcode()) {
        61 -> RunEnergy.toggle(player)
        64 -> Resting.rest(player, musician = false)
    }
}
