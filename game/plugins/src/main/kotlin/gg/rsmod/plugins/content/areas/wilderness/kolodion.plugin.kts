package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.COMPLETED_MAGE_ARENA
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

val kolodion = Npcs.KOLODION

on_npc_option(kolodion, option = "talk-to") {
    player.queue {
        if (player.attr.has(COMPLETED_MAGE_ARENA)) {
            mageArenaCompletedDialogue(this)
        } else {
            kolodionDialogue(this)
        }
    }
}

suspend fun kolodionDialogue(it: QueueTask) {
    it.chatPlayer("Hello there. What is this place?")
    it.chatNpc(
        "I am the great Kolodion, master of battle magic,",
        " and this is my battle arena. Top wizards travel ",
        "from all over Gielinor to fight here.",
    )
    val option = it.options("Can I fight here?", "What's the point of that?", "That's barbaric!")
    when (option) {
        1 -> canIFightHere(it)
        2 -> whatsThePoint(it)
        3 -> thatsBarbaric(it)
    }
}

suspend fun mageArenaCompletedDialogue(it: QueueTask) {
    it.chatPlayer("Hello, Kolodion.")
    it.chatNpc(
        "Well done, young adventurer;",
        "you truly are a worthy battle mage.",
    )
    it.chatPlayer("What now?")
    it.chatNpc(
        "Step into the magic pool.",
        "It will take you to a chamber. There, you must decide",
        "which god you will represent in the arena.",
    )
    it.chatPlayer("Thanks, Kolodion.")
    it.chatNpc("That's what I'm here for.")
}

suspend fun canIFightHere(it: QueueTask) {
    it.chatPlayer("Can I fight here?")
    it.chatNpc(
        "My arena is open to any high level wizard, but this",
        "is no game. Many wizards fall in this arena, never",
        "to rise again. The strongest mages have been destroyed.",
    )
    it.chatNpc("If you're sure you want in?")
    val option2 = it.options("Yes indeedy.", "No I don't.")
    when (option2) {
        1 -> yesIndeedy(it)
        2 -> noIDont(it)
    }
}

suspend fun yesIndeedy(it: QueueTask) {
    it.chatPlayer("Yes indeedy.")
    it.chatNpc("Good, good. You have a healthy sense of competition.")
    it.chatNpc(
        "Remember, traveller - in my arena, hand-to-hand combat",
        "is useless. Your strength will diminish as you enter the",
        "arena, but the spells you can learn are amongst the most",
        "powerful in all of Gielinor.",
    )
    it.chatNpc("Before I can accept you in, we must duel.")
    val option3 = it.options("Okay, let's fight.", "No thanks.")
    when (option3) {
        1 -> okayLetsFight(it)
        2 -> noThanks(it)
    }
}

suspend fun okayLetsFight(it: QueueTask) {
    val playerSpawnTile = Tile(x = 3104, z = 3933)
    it.chatPlayer("Okay, let's fight.")
    it.chatNpc("I must first check that you are up to scratch.")
    it.chatPlayer("You don't need to worry about that.")
    it.chatNpc(
        "Not just any magician can enter - only the most powerful",
        "and most feared. Before you can use the power of this arena,",
        "you must prove yourself against me.",
    )
    // The player is teleported to the Mage Arena to fight against Kolodion
    it.player.teleport(playerSpawnTile, TeleportType.MODERN)
    // (End of dialogue)
}

suspend fun noThanks(it: QueueTask) {
    it.chatPlayer("No thanks.")
    // (End of dialogue)
}

suspend fun noIDont(it: QueueTask) {
    it.chatPlayer("No I don't.")
    it.chatNpc("Your loss.")
    // (End of dialogue)
}

suspend fun whatsThePoint(it: QueueTask) {
    it.chatPlayer("What's the point of that?")
    it.chatNpc(
        "We learn how to use our magic to its fullest and how",
        "to channel the forces of the cosmos into our world...",
    )
    it.chatNpc("But mainly, I just like blasting people into dust.")
    val option4 = it.options("Can I fight here?", "That's barbaric!")
    when (option4) {
        1 -> canIFightHere(it)
        2 -> thatsBarbaric(it)
    }
}

suspend fun thatsBarbaric(it: QueueTask) {
    it.chatPlayer("That's barbaric!")
    it.chatNpc(
        "Nope, it's magic. But I know what you mean.",
        "So do you want to join us?",
    )
    val option5 = it.options("Yes indeedy.", "No I don't.")
    when (option5) {
        1 -> yesIndeedy(it)
        2 -> noIDont(it)
    }
}
