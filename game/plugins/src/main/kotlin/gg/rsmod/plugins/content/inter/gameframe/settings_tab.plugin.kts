package gg.rsmod.plugins.content.inter.gameframe

import gg.rsmod.plugins.api.ext.openInterface
import gg.rsmod.plugins.api.ext.player

on_button(interfaceId = 261, component = 14) {
   player.openInterface(interfaceId = 742, dest = InterfaceDestination.MAIN_SCREEN)
}

on_button(interfaceId = 261, component = 16) {
   player.openInterface(interfaceId = 743, dest = InterfaceDestination.MAIN_SCREEN)
}