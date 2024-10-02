package gg.rsmod.plugins.content.areas.wilderness

val wildernessCheckTimer = TimerKey()

val INTERFACE_ID = 381

on_login {
    player.timers[wildernessCheckTimer] = 1
}

on_timer(wildernessCheckTimer) {
    checkWildernessLevel(player)
    player.timers[wildernessCheckTimer] = 1
}

fun checkWildernessLevel(player: Player) {
    if (player.tile.getWildernessLevel() > 0) {
        player.openInterface(dest = InterfaceDestination.PVP_OVERLAY, interfaceId = INTERFACE_ID)
        player.sendOption("Attack", 2)
    } else {
        player.closeInterface(dest = InterfaceDestination.PVP_OVERLAY)
        player.removeOption(2)
    }
}
