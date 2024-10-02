package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheKnightsSword

val knightsSword = TheKnightsSword

/**
 * Binds the option for "talk-to" for npc [Npcs.RELDO]
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_npc_option(npc = Npcs.RELDO, option = "talk-to") {
    player.queue {
        chatNpc("Hello stranger.")
        /**
         * TODO Add support for when Shield of Arrav is added.
         * When it is added, add extra option for "I'm in search of a Quest"
         */

        // If Knights sword is started, show the "What do you know about the Imcando dwarves option.
        if (player.getCurrentStage(knightsSword) == 1) {
            when (
                options(
                    "I'm in search of a quest.",
                    "Do you have anything to trade?",
                    "What do you do?",
                    "What do you know about the Imcando dwarves?",
                )
            ) {
                1 -> getChat(this, option = 1)
                2 -> getChat(this, option = 2)
                3 -> getChat(this, option = 3)
                4 -> getChat(this, option = 4)
            }
            // If no quest is started, show regular options.
        } else {
            when (options("I'm in search of a quest", "Do you have anything to trade?", "What do you do?")) {
                1 -> getChat(this, option = 1)
                2 -> getChat(this, option = 2)
                3 -> getChat(this, option = 3)
            }
        }
    }
}

/**
 * Returns the chats depending on the option.
 * Allows easy modification of options to quests and what-not.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
suspend fun getChat(
    task: QueueTask,
    option: Int,
) = when (option) {
    1 -> { // Start for Shield of Arrav (Quest not added yet.)
        task.chatPlayer("I'm in search of a quest.")
        task.chatNpc("Hmmm. I don't... believe there are any here...")
        task.chatNpc("Let me think actually...")
        task.chatNpc(
            *"Ah yes. I know. If you look in a book called 'The Shield of Arrav', you'll find a quest in there."
                .splitForDialogue(),
        )
        task.chatNpc(
            *"I'm not sure where the book is mind you... but I'm sure it's around here somewhere.".splitForDialogue(),
        )
        task.chatPlayer("Thank you.")
    }

    2 -> { // Anything to trade
        task.chatPlayer("Do you have anything to trade?")
        task.chatNpc("Only knowledge.")
        task.chatPlayer("How much do you want for that then?")
        task.chatNpc("No, sorry, that was just my little joke. I'm not the trading type.")
        task.chatPlayer("Ah well.")
    }

    3 -> { // What does Reldo do?
        task.chatPlayer("What do you do?")
        task.chatNpc("I am the palace librarian.")
        task.chatPlayer("Ah. That's why you're in the library then.")
        task.chatNpc("Yes.")
        task.chatNpc(
            *"Although I would probably be in here even if I didn't work here. I like reading. Someday I hope to catalogue all of the information stored in these books so all may read it."
                .splitForDialogue(),
        )
    }

    4 -> { // If player has started The Knight's Sword quest, add the according chat.
        val player = task.player
        task.chatPlayer("What do you know about the Imcando dwarves?")
        task.chatNpc("The Imcando dwarves, you say?")
        task.chatNpc(
            *"Ah yes... for many hundred years they were the world's most skilled smiths. They used secret smithing knowledge passed down from generation to generation."
                .splitForDialogue(),
        )
        task.chatNpc(
            *"Unfortunately, about a century ago, the once thriving race was wiped out during the barbarian invasions of that time."
                .splitForDialogue(),
        )
        task.chatPlayer("So are there any Imcando left at all?")
        task.chatNpc(
            *"I believe a few of them survived, but with the bulk of their population destroyed their numbers have dwindled even further."
                .splitForDialogue(),
        )
        task.chatNpc(
            *"They tend to keep to themselves, and they tend not to tell people they're descendants of the Imcando, which is why people think the tribe is extinct. However..."
                .splitForDialogue(),
        )
        task.chatNpc(
            *"... you could try taking them some redberry pie. They REALLY like redberry pie. I believe I remember a couple living in Asgarnia near the cliffs on the Asgarnian southern peninsula."
                .splitForDialogue(),
        )
        player.advanceToNextStage(knightsSword)
    }

    else -> throw Exception("Unhandled dialogue option in Reldo.plugin.kts")
}
