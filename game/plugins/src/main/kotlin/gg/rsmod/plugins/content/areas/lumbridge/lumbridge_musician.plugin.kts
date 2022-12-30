package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.run.RunEnergy


on_npc_option(npc = Npcs.MUSICIAN_LUMBRIDGE, option = "talk-to") {
    player.queue { mainChat(this) }
}

on_npc_option(npc = Npcs.MUSICIAN_LUMBRIDGE, option = "listen-to") {
    RunEnergy.rest(player, musician = true)
}

suspend fun mainChat(it: QueueTask) {
    // TODO: finish chat options, there's quite a lot of dialogue..
    when (it.options("Who are you?", "Can I ask you some questions about resting?", "Can I ask you some questions about running?", "That's all for now.")) {
        1 -> {
            it.chatNpc("Me? I'm a musician! Let me help you relax: sit down, rest", "your weary limbs and allow me to wash away the troubles", "of the day. After a long trek, what could be better than", "some music to give you the energy to continue? Did you")
            it.chatNpc("know music has curative properties? Music stimulates the", "healing humours in your body, so they say.")
            it.chatPlayer("Who says that, then?")
            it.chatNpc("I was told by a travelling medical practitioner, selling oil", "extracted from snakes. It's a commonly known fact, so he", "said. After resting to some music, you will be able to run", "longer and your life points will increase noticeably.")
            it.chatNpc("A panacea, if you will. Ah, the power of music.")
            it.chatPlayer("So, just listening to some music will cure me of all my ills?")
            it.chatNpc("Well, not quite. Poison, lack of faith and dismembered", "limbs are all a bit beyond even the most rousing of", "harmonies, but I guarantee you will feel refreshed, and", "better equipped to take on the challenges of the day.")
            it.chatPlayer("Does this cost me anything?")
            it.chatNpc("Oh, no! My reward is the pleasure I bring to the masses.", "Just remember me and tell your friends, and that is", "payment enough. So sit down and enjoy!")
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