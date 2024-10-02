package gg.rsmod.plugins.content.areas.morytania

on_npc_option(npc = Npcs.ULIZIUS, option = "talk-to") {
    player.queue {
        mainChat(this)
    }
}

// Before Nature Spirit
suspend fun mainChat(it: QueueTask) {
    it.player.queue {
        chatPlayer("Hello there.")
        chatNpc(
            "What... Oh, don't creep up on me like that...",
            "I thought you were a Ghast!",
            facialExpression = FacialExpression.AFRAID,
        )
        chatPlayer(
            "Can I go through the gate please?",
        )
        chatNpc(
            "Absolutely not! I've been given strict instructions",
            "not to let anyone through. It's just too dangerous.",
            "No one gets in without Drezels say so!",
        )
        chatPlayer(
            "Where is Drezel?",
        )
        chatNpc(
            "Oh, he is in the temple, just go back over the",
            "bridge, down the ladder and along the hallway,",
            "you can't miss him.",
        )
    }
}
