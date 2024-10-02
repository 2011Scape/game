package gg.rsmod.plugins.content.areas.yanille.magic_guild
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.ext.*


on_npc_option(npc = Npcs.WIZARD_DISTENTOR, option = "talk-to") {
    player.queue {
        mainDialogue(this, false)
    }
}
on_npc_option(npc = Npcs.WIZARD_DISTENTOR, option = "teleport") {
    essenceTeleport(player, targetTile = Tile(2911, 4832))
}

suspend fun mainDialogue(
    it: QueueTask,
    skipStart: Boolean,
) {
    if (!skipStart) {
        it.chatNpc("Welcome to the Magicians Guild!")
    }
    it.chatPlayer("Hello there.")
    it.chatNpc("What can I do for you?")
    when (it.options("Can you teleport me to the Rune Essence?.", "Nothing thanks, I'm just looking around.")) {
        1 -> {
            it.chatPlayer("Yes, please.")
            essenceTeleport(it.player, dialogue = "Sparanti morduo calmentor!", Tile(2911, 4832, 0))
        }
        2 -> {
            it.chatPlayer("No, thank you.")
        }
    }
}
