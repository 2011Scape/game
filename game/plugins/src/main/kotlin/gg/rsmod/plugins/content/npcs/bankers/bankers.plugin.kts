package gg.rsmod.plugins.content.npcs.bankers

import gg.rsmod.plugins.content.inter.bank.openBank

val bankers = arrayOf(Npcs.BANKER, Npcs.BANKER_45, Npcs.BANKER_494, Npcs.BANKER_4907)

bankers.forEach {
    on_npc_option(it, option = "Bank", lineOfSightDistance = 2) {
        player.openBank()
    }
}