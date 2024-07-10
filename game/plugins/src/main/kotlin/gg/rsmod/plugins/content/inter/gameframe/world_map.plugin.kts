package gg.rsmod.plugins.content.inter.gameframe

val RESIZABLE_GAME_FRAME = 746
val FIXED_GAME_FRAME = 548
val WORLD_MAP_INTERFACE = 755

on_button(interfaceId = FIXED_GAME_FRAME, component = 180) {
    handleWorldMapButton(player)
    player.animate(840)
}

on_button(interfaceId = RESIZABLE_GAME_FRAME, component = 182) {
    handleWorldMapButton(player)
    player.animate(840)
}

on_button(interfaceId = WORLD_MAP_INTERFACE, component = 44) {
    player.closeFullscreenInterface()
    player.animate(-1)
}

fun handleWorldMapButton(player: Player) {
    if (player.getInteractingOpcode() == 61) {
        player.openFullscreenInterface(interfaceId = WORLD_MAP_INTERFACE)
        val tile = player.tile
        val tileId = (tile.z and 0x3fff) + ((tile.x and 0x3fff) shl 14) + ((tile.height and 0x3) shl 28)
        player.setVarc(622, tileId)
        player.setVarc(674, tileId)
    }
}
