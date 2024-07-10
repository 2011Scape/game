package gg.rsmod.plugins.content.areas.yanille.magic_guild

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

on_npc_option(npc = Npcs.PROFESSOR_IMBLEWYN, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}

suspend fun mainDialogue(
    it: QueueTask,
    skipStart: Boolean,
) {
    if (!skipStart) {
        it.chatPlayer("I didn't realise gnomes were interested in magic.")
    }
    it.chatNpc("Gnomes are interested in everything.")
    it.chatPlayer("Of course.")
    it.chatNpc(
        "We have a saying, 'Curiosity killed the cat'. But cats aren't clever enough to understand what they find. Gnomes are.",
        wrap = true,
    )
    it.chatPlayer("Hey! Some of my best friends are cats!")
    it.chatNpc("I think that says more about you than cats.")
}
