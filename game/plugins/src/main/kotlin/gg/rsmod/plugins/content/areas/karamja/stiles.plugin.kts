package gg.rsmod.plugins.content.areas.karamja

import gg.rsmod.plugins.content.skills.fishing.Fish

/**
 * @author Alycia <https://github.com/alycii>
 */

on_npc_option(npc = Npcs.STILES, option = "talk-to") {
    player.queue {
        chatNpc(
            *"Ay-uh, 'tis a grand day for the fishin'. Will ye be wantin' to exchange yer fish for banknotes?"
                .splitForDialogue(),
        )
        when (
            options(
                "Okay, exchange all my fish for banknotes.",
                "Who are you and why are you here?",
                "Which fish can you exchange?",
                "No thanks.",
            )
        ) {
            FIRST_OPTION -> {
                chatPlayer("Okay, exchange all my fish for banknotes.")
                noteFish(this)
            }
            SECOND_OPTION -> {
                chatPlayer("Who are you and why are you here?")
                chatNpc(
                    *"Ahhh, when I were a young'un my name were Nigel but, these days, folks mostly call me Stiles."
                        .splitForDialogue(),
                )
                chatNpc(
                    *"Long time ago, in Draynor Village, there were three brothers who'd exchange yer stuff for bitty bits o' paper, like these new-fangled banknotes we've got today. Niles, Miles an' Giles they called themselves."
                        .splitForDialogue(),
                )
                chatNpc(
                    *"They be long gone, like the golden days, but they were an inspiration to me, so I took this trade myself, an' I changed my name to Stiles."
                        .splitForDialogue(),
                )
                chatPlayer("But why are you here, in this place?")
                chatNpc(
                    *"The smell of yon bananas were drivin' me scatty, so I can't go too near the fishing spots."
                        .splitForDialogue(),
                )
                chatNpc(
                    *"A tough-lookin' geezer callin' himself a slayer master tried to give me a nosepeg once, but I bain't wearin' one o' them things. Ol' Stiles has a tender nose."
                        .splitForDialogue(),
                )
                chatNpc("So, would ye like me to exchange yer fish now?")
                when (options("Yes please.", "No thanks.")) {
                    FIRST_OPTION -> {
                        noteFish(this)
                    }
                    SECOND_OPTION -> {
                        chatPlayer("No thanks.")
                    }
                }
            }
            THIRD_OPTION -> {
                chatNpc(
                    *"Ahhh, ol' Stiles has banknotes for yer lobbies, yer swordies and yer tuna. 'Tis a grand service I be offerin' here, and nary a penny do I ask in return."
                        .splitForDialogue(),
                )
                chatNpc("So, would ye like me to exchange yer fish now?")
                when (options("Yes please.", "No thanks.")) {
                    FIRST_OPTION -> {
                        noteFish(this)
                    }
                    SECOND_OPTION -> {
                        chatPlayer("No thanks.")
                    }
                }
            }
            FOURTH_OPTION -> {
                chatPlayer("No thanks.")
            }
        }
    }
}

on_npc_option(npc = Npcs.STILES, option = "exchange") {
    player.queue {
        noteFish(this)
    }
}

/**
 * Notes lobsters, swordfish, and tuna by exchanging them for banknotes with Stiles.
 *
 * @param it The [QueueTask] containing the player and task information.
 */
suspend fun noteFish(it: QueueTask) {
    val player = it.player

    // Filter for the fish items to be exchanged
    val data = Fish.values().filter { it == Fish.LOBSTER || it == Fish.SWORDFISH || it == Fish.TUNA }

    var exchanged = false

    // Iterate through each fish item to be exchanged
    data.forEach {
        val id = it.id
        val amount = player.inventory.getItemCount(id)
        val item = Item(id, amount)

        // If the player has at least one of the fish item, exchange it for a banknote
        if (amount > 0) {
            player.inventory.remove(id, amount)
            player.inventory.add(item.toNoted(player.world.definitions))
            exchanged = true
        }
    }

    // If at least one fish item was exchanged, notify the player
    if (exchanged) {
        it.chatNpc("There ye goes.")
    }
    // Otherwise, notify the player that they have nothing to exchange
    else {
        it.chatNpc(
            *"Ahhh, ye've nothing that ol' Stiles can exchange. I'll do yer lobbies, yer swordies, an' yer tuna, that's all."
                .splitForDialogue(),
        )
    }
}
