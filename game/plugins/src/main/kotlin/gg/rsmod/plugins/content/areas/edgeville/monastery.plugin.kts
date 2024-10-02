package gg.rsmod.plugins.content.areas.edgeville

import gg.rsmod.game.model.attr.JOINED_MONASTERY


/**
 * @author Alycia <https://github.com/alycii>
 */

val monks = arrayOf(Npcs.MONK_7727, Npcs.ABBOT_LANGLEY)
monks.forEach {
    on_npc_option(npc = it, option = "talk-to") {
        player.queue {
            monkDialogue(this)
        }
    }
}

on_npc_option(npc = Npcs.BROTHER_ALTHRIC, option = "talk-to") {
    player.queue {
        chatPlayer("Very nice rosebushes you have here.")
        chatNpc(
            "Yes, it has taken me many long hours in this garden to",
            "bring them to this state of near-perfection.",
        )
    }
}

// handle the first-level ladder
on_obj_option(obj = Objs.LADDER_2641, option = "climb-up") {
    if (!player.attr.has(JOINED_MONASTERY)) {
        player.queue {
            joinOrderDialogue(this)
        }
    } else {
        handleLadder(player, climb = true)
    }
}

// handle the second-level ladder
on_obj_option(obj = Objs.LADDER_30863, option = "climb-down") {
    handleLadder(player, climb = false)
}

fun handleLadder(
    player: Player,
    climb: Boolean,
) {
    player.queue {
        player.animate(828)
        wait(2)
        val height =
            when (climb) {
                true -> 1
                false -> 0
            }
        player.moveTo(player.tile.x, player.tile.z, height)
    }
}

val roseBushes = arrayOf(Objs.ROSES_30806, Objs.ROSES_9261, Objs.ROSES_9262)
roseBushes.forEach {
    on_obj_option(obj = it, option = "take-seed") {
        player.message("There doesn't seem to be any seeds on this rosebush.")
    }
}

suspend fun healDialogue(it: QueueTask) {
    val player = it.player
    val npc = player.getInteractingNpc()
    it.chatPlayer("Can you heal me? I'm injured.")
    it.chatNpc("Ok.")
    npc.animate(710)
    npc.graphic(84)
    player.heal(40 + ((it.player.skills.getCurrentLevel(Skills.CONSTITUTION) * 0.12) * 10).toInt())
    player.message("You feel a little better.")
}

suspend fun joinOrderDialogue(it: QueueTask) {
    val player = it.player
    it.chatNpc(
        "I'm sorry but only members of our order are allowed",
        "in the second level of the monastery.",
        npc = Npcs.ABBOT_LANGLEY,
    )
    when (it.options("Well can I join your order?", "Oh, sorry.")) {
        FIRST_OPTION -> {
            it.chatPlayer("Well can I join your order?")
            if (it.player.skills.getCurrentLevel(Skills.PRAYER) < 31) {
                it.chatNpc("No. I am sorry, but I feel you are not devout enough.", npc = Npcs.ABBOT_LANGLEY)
                player.message("You need a prayer level of 31 to join the order.")
            } else {
                it.chatNpc(
                    "Ok, I see you are someone suitable for our order. You",
                    "may join.",
                    npc = Npcs.ABBOT_LANGLEY,
                )
                player.attr[JOINED_MONASTERY] = true
            }
        }
        SECOND_OPTION -> {
            it.chatPlayer("Oh, sorry.")
        }
    }
}

suspend fun monkDialogue(it: QueueTask) {
    val player = it.player
    val npc = player.getInteractingNpc()
    val thirdOption =
        when {
            !player.attr.has(JOINED_MONASTERY) -> "How do I get further into the monastery?"
            else -> ""
        }
    it.chatNpc("Greetings traveller.")
    when (it.options("Can you heal me? I'm injured.", "Isn't this place built a bit out of the way?", thirdOption)) {
        FIRST_OPTION -> {
            healDialogue(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("Isn't this place built a bit out of the way?")
            it.chatNpc(
                "We like it that way actually! We get disturbed less. We",
                "still get rather a large amount of travellers looking for",
                "sanctuary and healing here as it is!",
            )
        }
        THIRD_OPTION -> {
            if (npc.id == Npcs.ABBOT_LANGLEY) {
                it.chatPlayer("How do I get further into the monastery?")
                joinOrderDialogue(it)
            } else {
                it.chatPlayer("How do I get further into the monastery?")
                it.chatNpc(
                    "You'll need to talk to Abbot Langley about that. He's",
                    "usually to be found walking the halls of the monastery.",
                )
            }
        }
    }
}
