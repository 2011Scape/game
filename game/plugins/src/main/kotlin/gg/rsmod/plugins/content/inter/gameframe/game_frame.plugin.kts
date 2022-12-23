package gg.rsmod.plugins.content.inter.gameframe

val GAME_FILTER_VARBIT = 6161
val CHAT_OPTIONS_INTERFACE = 751

on_button(interfaceId = CHAT_OPTIONS_INTERFACE, component = 31) {
    when(player.getInteractingOpcode()) {
        52 -> {
            player.toggleVarbit(GAME_FILTER_VARBIT)
        }
    }
}