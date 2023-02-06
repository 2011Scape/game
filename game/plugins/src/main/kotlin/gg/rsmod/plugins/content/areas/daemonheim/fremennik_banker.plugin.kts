package gg.rsmod.plugins.content.areas.daemonheim

import gg.rsmod.plugins.content.inter.bank.openBank

on_npc_option(npc = Npcs.FREMENNIK_BANKER, option = "Bank") {
    player.openBank()
}