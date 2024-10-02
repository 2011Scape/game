package gg.rsmod.plugins.content.areas.yanille.magic_guild
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

on_npc_option(npc = Npcs.WIZARD_FRUMSCONE, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}

suspend fun mainDialogue(
    it: QueueTask,
    skipStart: Boolean,
) {
    if (!skipStart) {
        it.chatNpc(
            "Do you like my magic Zombies? Feel free to kill them, there's plenty more where these came from!",
            wrap = true,
        )
    }
}
// TO-DO
// Add Questing dialogues
