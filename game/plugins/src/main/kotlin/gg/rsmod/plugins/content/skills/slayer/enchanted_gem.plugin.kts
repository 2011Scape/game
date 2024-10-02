package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.game.model.attr.SLAYER_AMOUNT
import gg.rsmod.game.model.attr.SLAYER_MASTER
import gg.rsmod.plugins.content.skills.slayer.data.SlayerMaster
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.ENCHANTED_GEM, option = "activate") {
    if (!player.attr.has(SLAYER_MASTER)) {
        player.message("You try to activate the gem; but get no response.")
        return@on_item_option
    }

    val master = SlayerMaster.values().firstOrNull { it.id == player.attr[SLAYER_MASTER] } ?: return@on_item_option
    player.queue {
        chatNpc("Hello there ${Misc.formatForDisplay(player.username)}, what can I help you with?", npc = master.id)
        when (options("How am I doing so far?", "Who are you?", "Where are you?", "Nothing really.")) {
            FIRST_OPTION -> {
                chatPlayer("How am I doing so far?")
                if (player.getSlayerAssignment() == null) {
                    chatNpc(
                        "You need something new to hunt. Come and see me",
                        "when you can and I'll give you a new task.",
                        npc = master.id,
                    )
                } else {
                    chatNpc(
                        "You're currently assigned to kill ${player.getSlayerAssignment()!!.identifier.lowercase()}; only ${player.attr[SLAYER_AMOUNT]}",
                        "more to go.",
                        npc = master.id,
                    )
                }
            }
            SECOND_OPTION -> {
                chatPlayer("Who are you?")
                chatNpc("My name's ${master.identifier}; I'm a Slayer Master.", npc = master.id)
            }
            THIRD_OPTION -> {
                // TODO: Add more options for this when adding more slayer masters
                chatPlayer("Where are you?")
                chatNpc("You'll find me in Burthorpe. I'll be here when you need a", "new task.", npc = master.id)
            }
            FOURTH_OPTION -> {
                chatPlayer("Nothing really.")
            }
        }
    }
}

on_item_option(item = Items.ENCHANTED_GEM, option = "kills-left") {
    if (player.getSlayerAssignment() == null) {
        player.message("You currently do not have a task; speak to a Slayer Master to receive one.")
        return@on_item_option
    }
    player.message(
        "Your current assignment is ${player.getSlayerAssignment()!!.identifier.lowercase()}; only ${player.attr[SLAYER_AMOUNT]} more to go.",
    )
}
