package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.HANS, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

fun mainChat(
    mainchat: QueueTask,
    player: Player,
) {
    val formattedTime = FormatTimePlayed.getTimePlayed(player)
    mainchat.player.queue {
        chatNpc("Hello. What are you doing here?")
        when (
            options(
                "I'm looking for whoever is in charge of this place.",
                "I have come to kill everyone in this castle!",
                "I don't know. I'm lost. Where am I?",
                "Can you tell me how long I've been here?",
                "Nothing.",
            )
        ) {
            FIRST_OPTION -> {
                chatPlayer("I'm looking for whoever is in charge of this place.")
                chatNpc("Who, the Duke? He's in his study, on the first floor.")
            }
            SECOND_OPTION -> {
                chatPlayer("I have come to kill everyone in this castle!")
                // TODO: add hans running away screaming, saying "Help! Help!"
            }
            THIRD_OPTION -> {
                chatPlayer("I don't know. I'm lost. Where am I?")
                chatNpc(
                    "You are in Lumbridge Castle.",
                )
            }
            FOURTH_OPTION -> {
                chatPlayer("Can you tell me how long I've been here?")
                chatNpc(
                    "Ahh, I see all the newcomers arriving in Lumbridge,",
                    "fresh-faced and eager for adventure. I remember you...",
                )
                chatNpc(
                    "You've spent $formattedTime",
                    "in the world since you arrived.",
                )
            }
        }
    }
}
