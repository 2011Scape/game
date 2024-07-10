package gg.rsmod.plugins.content.areas.barbarianvillage

/**
 * @author Alycia <https://github.com/alycii>
 */

on_npc_option(Npcs.LITARA, "talk-to") {
    player.queue { chat(this) }
}

suspend fun chat(it: QueueTask) {
    it.chatNpc("Hello there. You look lost, are you okay?")
    when (it.options("I'm looking for a stronghold or something...", "I'm fine, just passing through.")) {
        1 -> {
            it.chatPlayer("I'm looking for a stronghold or something...")
            it.chatNpc("Ahh... the Stronghold of Security. It's down there.")
            it.doubleMessageBox("Litara points to the hole in the ground that looks like you could", "squeeze through.")
            it.chatPlayer("Looks kind of... deep and dark.")
            it.chatNpc("Yeah... tell that to my brother, he still hasn't come back.")
            it.chatPlayer("Your brother?")
            it.chatNpc(
                "He's an explorer too. When the miner fell down that",
                "hole he'd made and came back babbling about doors,",
                "questions and treasure, my brother went to explore.",
                "No-one has seen him since.",
            )
            it.chatPlayer("Oh... that's not good.")
            it.chatNpc(
                "Lots of people have been down there, but none of them",
                "have seen him. Let me know if you do, will you?",
            )
            it.chatPlayer("I'll certainly keep my eyes open.")
        }
        2 -> {
            it.chatPlayer("I'm fine, just passing through.")
        }
    }
}
