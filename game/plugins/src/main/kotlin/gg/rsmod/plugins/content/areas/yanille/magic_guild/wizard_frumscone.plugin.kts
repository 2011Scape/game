package gg.rsmod.plugins.content.areas.yanille.magic_guild


on_npc_option(npc = Npcs.WIZARD_FRUMSCONE, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}

suspend fun mainDialogue(it: QueueTask, skipStart: Boolean) {
    if (!skipStart)
        it.chatNpc("Do you like my magic Zombies?",
            "Feel free to kill them, there's plenty more where these came from!")
 }
//TO-DO
//Add Questing dialogues
