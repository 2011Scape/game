package gg.rsmod.plugins.content.inter.gameframe

on_button(interfaceId = 261, component = 14) {
    player.openInterface(interfaceId = 742, dest = InterfaceDestination.MAIN_SCREEN)
}

on_button(interfaceId = 261, component = 16) {
    player.openInterface(interfaceId = 743, dest = InterfaceDestination.MAIN_SCREEN)
}

on_button(261, 5) {
    player.openInterface(982, InterfaceDestination.SETTINGS_TAB)
}

on_button(982, 5) {
    player.openInterface(261, InterfaceDestination.SETTINGS_TAB)
}
