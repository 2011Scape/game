package gg.rsmod.plugins.content.areas.varrock

on_npc_option(Npcs.GUILDMASTER, "Talk-to") {
    player.queue {
        chatNpc("Greetings!",
            facialExpression = FacialExpression.HAPPY_TALKING,
            wrap = true)
        when (options(
            "What is this place?",
            "Can I have a quest?"
        )) {
            FIRST_OPTION -> {
                chatPlayer("What is this place?",
                    facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                chatNpc("This is the Champions' Guild. Only adventurers who have proved themselves worthy by gaining " +
                    "influence from quests are allowed in here.",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
            }
            SECOND_OPTION -> {
                chatPlayer("Can I have a quest?",
                    facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                // TODO: Change once Dragon Slayer quest is released
                chatNpc("Not right now.",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
            }
        }
    }
}
