package gg.rsmod.plugins.content.areas.canifis

import gg.rsmod.plugins.content.combat.isAttacking
import kotlin.random.Random

val citizens =
    listOf(
        Npcs.EDUARD,
        Npcs.LEV,
        Npcs.YURI,
        Npcs.BORIS,
        Npcs.GEORGY,
        Npcs.JOSEPH,
        Npcs.NIKOLAI,
        Npcs.IMRE,
        Npcs.VERA,
        Npcs.MILLA,
        Npcs.SOFIYA,
        Npcs.IRINA,
        Npcs.SVETLANA,
        Npcs.ZOJA,
        Npcs.YADVIGA,
        Npcs.NIKITA,
        Npcs.LILIYA,
        Npcs.ALEXIS,
        Npcs.KSENIA,
        Npcs.GALINA,
    )

citizens.forEach { citizen ->
    on_npc_option(citizen, option = "talk-to") {
        if (!player.isAttacking()) {
            player.queue { CanifisCitizenChat(this) }
        }
    }
}

fun CanifisCitizenChat(canifisCitizenChat: QueueTask) {
    canifisCitizenChat.player.queue {
        when (Random.nextInt(1, 10)) {
            1 -> chatNpc("If I were as ugly as you I would not dare", "to show my face in public!")
            2 -> chatNpc("Out of my way, punk.")
            3 -> {
                chatNpc("Hmm... you smell strange...")
                chatPlayer("Strange how?")
                chatNpc("Like a human!")
                chatPlayer("Oh! Er... I just ate one is why!")
            }
            4 -> chatNpc("Leave me alone.")
            5 -> chatNpc("Don't talk to me again if you value your life!")
            6 -> chatNpc("Get lost!")
            7 -> chatNpc("I don't have anything to give you so leave me alone,", "mendicant.")
            8 -> chatNpc("Have you no manners?")
            9 -> chatNpc("I don't have time for this right now.")
            10 -> chatNpc("I have no interest in talking to a pathetic", "meat bag like yourself.")
        }
    }
}
