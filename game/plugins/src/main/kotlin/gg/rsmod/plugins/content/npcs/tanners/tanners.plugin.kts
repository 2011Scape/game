package gg.rsmod.plugins.content.npcs.tanners

val TANNERS = listOf(Npcs.ELLIS, Npcs.TANNER, Npcs.TANNER_2320, Npcs.SBOTT)

TANNERS.forEach {
    on_npc_option(it, option = "trade") {
        openTanningInterface(player)
    }

    on_npc_option(it, option = "talk-to") {
        player.queue {
            chatNpc(
                "Greetings friend. I am a manufacturer of leather.",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            when (options("Can I buy some leather then?", "Leather is rather weak stuff.")) {
                1 -> {
                    chatPlayer("Can I buy some leather then?", facialExpression = FacialExpression.CONFUSED)
                    chatNpc(
                        "I make leather from animal hides. Bring me some",
                        "cowhides and one gold coin per hide, and I'll tan them",
                        "into soft leather for you.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                    )
                }
                2 -> {
                    chatPlayer("Leather is rather weak stuff.", facialExpression = FacialExpression.CONFUSED)
                    chatNpc(
                        "Normal leather may be quite weak, but it's very cheap `",
                        "I make it from cowhides for only 1 gp per hide ` and",
                        "it's so easy to craft that anyone can work with it.",
                        facialExpression = FacialExpression.TALKING_ALOT,
                    )
                    chatNpc(
                        "Alternatively you could try hard leather. It's not so",
                        "easy to craft, but I only charge 3 gp per cowhide to",
                        "prepare it, and it makes much sturdier armour.",
                        facialExpression = FacialExpression.TALKING_ALOT,
                    )
                    chatNpc(
                        "I can also tan snake hides and dragonhides, suitable for",
                        "crafting into the highest quality armour for rangers.",
                        facialExpression = FacialExpression.TALKING_ALOT,
                    )
                    chatPlayer("Thanks, I'll bear it in mind.", facialExpression = FacialExpression.HAPPY_TALKING)
                }
            }
        }
    }
}
