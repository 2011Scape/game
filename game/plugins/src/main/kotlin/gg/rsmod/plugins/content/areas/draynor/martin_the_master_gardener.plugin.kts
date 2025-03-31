package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer

on_npc_option(Npcs.MARTIN_THE_MASTER_GARDENER, "Talk-to") {
    player.queue {
        when(options(
            "Skillcape of Farming.",
            "General Chat."
        )) {
            FIRST_OPTION -> {
                chatPlayer("What is that cape that you're wearing?", facialExpression = FacialExpression.CONFUSED,
                    wrap = true)
                chatNpc("This is a Skillcape of Farming, isn't it incredible? It's a symbol of my ability as the " +
                    "finest farmer in the land!",
                    facialExpression = FacialExpression.HAPPY_TALKING,
                    wrap = true)
            }
            SECOND_OPTION -> {
                if (player.finishedQuest(VampyreSlayer)) {
                    chatNpc("I'm very grateful to you for ridding the village of that vampyre. It's been so nice to " +
                        "sit outdoors in the evenings and not worry.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                        wrap = true)
                    chatPlayer("Glad to help.", facialExpression = FacialExpression.HAPPY_TALKING)
                }
                else {
                    chatNpc("I can't chat now, I have too many things to worry about.", facialExpression =
                        FacialExpression.WORRIED, wrap = true)
                }
            }
        }
    }
}
