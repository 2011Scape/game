package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.inter.bank.openBank

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.TRAPDOOR_7257, option = "enter") {
    player.moveTo(Tile(x = 3061, z = 4985, height = 1))
}

on_obj_option(obj = Objs.PASSAGEWAY_7258, option = "enter") {
    player.moveTo(Tile(x = 2906, z = 3537, height = 0))
}

on_npc_option(npc = Npcs.EMERALD_BENEDICT, option = "bank") {
    player.openBank()
}
