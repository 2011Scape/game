package gg.rsmod.plugins.content.npcs.musicians

import gg.rsmod.plugins.content.mechanics.resting.Resting

val musicians =
    listOf(
        Npcs.MUSICIAN_5439,
        Npcs.MUSICIAN_5442,
        Npcs.MUSICIAN_8698,
        Npcs.MUSICIAN_8699,
        Npcs.MUSICIAN_8700,
        Npcs.MUSICIAN_8701,
        Npcs.MUSICIAN_8702,
        Npcs.MUSICIAN_8703,
        Npcs.MUSICIAN_8704,
        Npcs.MUSICIAN_8705,
        Npcs.MUSICIAN_8706,
        Npcs.MUSICIAN_8707,
        Npcs.MUSICIAN_8708,
        Npcs.MUSICIAN_8710,
        Npcs.MUSICIAN_8711,
        Npcs.MUSICIAN_8716,
        Npcs.MUSICIAN_8717,
        Npcs.MUSICIAN_8718,
        Npcs.MUSICIAN_8721,
        Npcs.MUSICIAN_DRAYNOR_30,
        Npcs.MUSICIAN_LUMBRIDGE,
        Npcs.MUSICIAN_PISCATORIS_3463,
        Npcs.DRUNKEN_MUSICIAN,
        Npcs.ELF_MUSICIAN,
        Npcs.GOBLIN_MUSICIAN,
        Npcs.BILL_BLAKEY,
        Npcs.DRUMMER,
        Npcs.DRUMMER_8720,
        Npcs.GHOSTLY_PIPER,
        Npcs.BORIS_BARRINGTON,
        Npcs.BARD_ROBERTS,
    )

musicians.forEach {
    on_npc_option(npc = it, option = "listen-to") {
        Resting.rest(player, musician = true)
    }
    on_npc_option(npc = it, option = "talk-to") {
        player.queue { mainChat(this) }
    }
}

suspend fun mainChat(it: QueueTask) {
    // TODO: finish chat options, there's quite a lot of dialogue..
    when (
        it.options(
            "Who are you?",
            "Can I ask you some questions about resting?",
            "Can I ask you some questions about running?",
            "That's all for now.",
        )
    ) {
        1 -> {
            it.chatNpc(
                "Me? I'm a musician! Let me help you relax: sit down, rest",
                "your weary limbs and allow me to wash away the troubles",
                "of the day. After a long trek, what could be better than",
                "some music to give you the energy to continue? Did you",
            )
            it.chatNpc(
                "know music has curative properties? Music stimulates the",
                "healing humours in your body, so they say.",
            )
            it.chatPlayer("Who says that, then?")
            it.chatNpc(
                "I was told by a travelling medical practitioner, selling oil",
                "extracted from snakes. It's a commonly known fact, so he",
                "said. After resting to some music, you will be able to run",
                "longer and your life points will increase noticeably.",
            )
            it.chatNpc("A panacea, if you will. Ah, the power of music.")
            it.chatPlayer("So, just listening to some music will cure me of all my ills?")
            it.chatNpc(
                "Well, not quite. Poison, lack of faith and dismembered",
                "limbs are all a bit beyond even the most rousing of",
                "harmonies, but I guarantee you will feel refreshed, and",
                "better equipped to take on the challenges of the day.",
            )
            it.chatPlayer("Does this cost me anything?")
            it.chatNpc(
                "Oh, no! My reward is the pleasure I bring to the masses.",
                "Just remember me and tell your friends, and that is",
                "payment enough. So sit down and enjoy!",
            )
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        2 -> {}
        3 -> {}
        4 -> {
            it.chatNpc("Well, don't forget to have a rest every now and again.")
        }
    }
}
