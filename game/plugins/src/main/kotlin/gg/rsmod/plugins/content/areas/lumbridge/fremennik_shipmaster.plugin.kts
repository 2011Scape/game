package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.FREMENNIK_SHIPMASTER, option = "Sail") {
    // TODO: fade camera
    player.moveTo(Tile(x = 3511, z = 3693))
}

on_npc_option(npc = Npcs.FREMENNIK_SHIPMASTER_9708, option = "Sail") {
    // TODO: fade camera
    player.moveTo(Tile(x = 3255, z = 3171))
}