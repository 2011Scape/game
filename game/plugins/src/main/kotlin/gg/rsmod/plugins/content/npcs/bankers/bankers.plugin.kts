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
    on_npc_option(it, option = "talk-to", lineOfSightDistance = 2) {
        player.queue { chat(this) }
    }
}

/**
 * Banker Dialogue, cross referenced with Runescape 3 and Runescape Wiki dating back to 2016.
 */
suspend fun chat(it: QueueTask) {
    it.chatNpc("Good day. How may I help you?", facialExpression = FacialExpression.HAPPY_TALKING)
    when (
        it.options(
                "I'd like to access my bank account, please.",
                "I'd like to check my PIN settings.",
                "I'd like to see my collection box.",
                "What is this place?",
        )
    ) {
        1 -> {
            it.chatPlayer("I'd like to access my bank account, please.", facialExpression = FacialExpression.HAPPY_TALKING)
            it.player.openBank()
        }
        2 -> {
            // TODO : Ability to set Pins isn't implemented yet
            it.chatNpc("Sorry, it is not implemented yet.", facialExpression = FacialExpression.SAD_2)
        }
        3 -> {
            // TODO : Grand Exchange isn't implemented yet
            it.chatNpc("Sorry, it is not implemented yet.", facialExpression = FacialExpression.SAD_2)
        }
        4 -> {
            it.chatPlayer("What is this place?", facialExpression = FacialExpression.UNCERTAIN)
            it.chatNpc("This is a branch of the Bank of Gielinor. We have branches", "in many towns.", facialExpression = FacialExpression.CHEERFUL)

            when (
                it.options(
                        "And what do you do?",
                        "Didn't you used to be called the Bank of Varrock?",
                )
            ) {
                1 -> {
                    it.chatPlayer("And what do you do?", facialExpression = FacialExpression.DISREGARD)
                    it.chatNpc("We will look after your items and money for you. Leave", "your valuables with us if you want to keep them safe.",
                            facialExpression = FacialExpression.CHEERFUL)
                }
                2 -> {
                    it.chatPlayer("Didn't you used to be called the Bank of Varrock?", facialExpression = FacialExpression.DISREGARD)
                    it.chatNpc("Yes we did, but people kept on coming into our branches", "outside of Varrock and telling us that our signs were", "wrong. They acted as if we didn't know what town we", "were in or something.",
                            facialExpression = FacialExpression.TALKING)
                }

            }
        }
    }
}