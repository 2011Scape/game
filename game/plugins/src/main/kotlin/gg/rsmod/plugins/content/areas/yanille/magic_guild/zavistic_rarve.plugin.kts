package gg.rsmod.plugins.content.areas.yanille.magic_guild
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

on_npc_option(npc = Npcs.ZAVISTIC_RARVE, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}

suspend fun mainDialogue(
    it: QueueTask,
    skipStart: Boolean,
) {
    if (!skipStart) {
        it.chatNpc("I don't have time to talk!")
    }
}
// TO-DO
// Add ring bell functions when its needed for the quests.
