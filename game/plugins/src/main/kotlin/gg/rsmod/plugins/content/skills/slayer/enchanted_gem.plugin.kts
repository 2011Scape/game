package gg.rsmod.plugins.content.skills.slayer

import gg.rsmod.game.model.attr.SLAYER_AMOUNT
import gg.rsmod.game.model.attr.SLAYER_ASSIGNMENT
import gg.rsmod.game.model.attr.SLAYER_MASTER
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.ENCHANTED_GEM, option = "activate") {
    if(!player.attr.has(SLAYER_MASTER)) {
        player.message("You try to activate the gem; but get no response.")
        return@on_item_option
    }

    val master = player.attr[SLAYER_MASTER]!!
    player.queue {
        chatNpc("Hello there ${Misc.formatForDisplay(player.username)}, what can I help you with?", npc = master)
        when(options("How am I doing so far?", "Who are you?", "Where are you?", "Got any tips for me?", "Nothing really.")) {
            FIRST_OPTION -> {
                chatPlayer("How am I doing so far?")
                if(player.getSlayerAssignment() == null) {
                    chatNpc("You need something new to hunt. Come and see me", "when you can and I'll give you a new task.", npc = master)
                } else {
                    chatNpc("You're currently assigned to kill ${player.getSlayerAssignment()!!.identifier},", "only ${player.attr[SLAYER_AMOUNT]} more to go.", npc = master)
                }
            }
        }
    }
}

on_item_option(item = Items.ENCHANTED_GEM, option = "kills-left") {
    if(player.getSlayerAssignment() == null) {
        player.message("You currently do not have a task; speak to a Slayer Master to receive one.")
        return@on_item_option
    }
    player.message("You're assigned to kill ${player.getSlayerAssignment()!!.identifier.lowercase()}, only ${player.attr[SLAYER_AMOUNT]} more to go.")
}