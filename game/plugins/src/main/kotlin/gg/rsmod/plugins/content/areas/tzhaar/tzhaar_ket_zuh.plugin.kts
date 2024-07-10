package gg.rsmod.plugins.content.areas.tzhaar

import gg.rsmod.plugins.content.inter.bank.openBank

/**
 * @author Alycia <https://github.com/alycii>
 */

on_npc_option(npc = Npcs.TZHAARKETZUH, option = "bank") {
    player.openBank()
}
