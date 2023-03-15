package gg.rsmod.plugins.content.npcs.bankers

import gg.rsmod.plugins.content.inter.bank.openBank

val bankers = arrayOf(
        Npcs.BANKER,
        Npcs.BANKER_45, // Various Female Bankers
        Npcs.BANKER_494, // Various Male Bankers
        Npcs.BANKER_495, // Various Female Bankers
        Npcs.BANKER_2759, // Varrock East Female Bankers
        Npcs.BANKER_3293, // GrandExchange Female Bankers
        Npcs.BANKER_3416, // GrandExchange Male Bankers
        Npcs.BANKER_4907, // Lumbridge Castle Banker
        Npcs.BANKER_6200, // Falador Bankers All Share the Same ID
)

bankers.forEach {
    on_npc_option(it, option = "Bank", lineOfSightDistance = 2) {
        player.openBank()
    }
}