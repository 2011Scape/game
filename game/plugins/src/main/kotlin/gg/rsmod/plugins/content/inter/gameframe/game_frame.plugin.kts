package gg.rsmod.plugins.content.inter.gameframe

val GAME_FILTER_VARBIT = 6161
val XP_ORB_COUNTER_VARP = 1801
val RESIZABLE_GAME_FRAME = 746
val CHAT_OPTIONS_INTERFACE = 751

on_button(interfaceId = CHAT_OPTIONS_INTERFACE, component = 31) {
    when(player.getInteractingOpcode()) {
        52 -> {
            player.toggleVarbit(GAME_FILTER_VARBIT)
        }
    }
}

on_button(interfaceId = RESIZABLE_GAME_FRAME, component = 229) {
    when(player.getInteractingOpcode()) {
        10 -> player.setVarp(XP_ORB_COUNTER_VARP,0)
    }
}