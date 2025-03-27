package gg.rsmod.plugins.content.areas.karamja

/**
 * Whether player is employed or not
 */
val EMPLOYED_ATTR = AttributeKey<Boolean>()
/**
 *  How many stored bananas we have in crate
 */
val CRATED_BANANAS_ATTR = AttributeKey<Int>()

on_npc_option(npc = Npcs.LUTHAS, option = "talk-to") {
    player.queue {
        val employed: Boolean = player.attr.has(EMPLOYED_ATTR) && player.attr[EMPLOYED_ATTR]!!
        if (employed) {
            val stored = player.attr.getOrDefault(CRATED_BANANAS_ATTR, 0)
            if (stored >= 10) {
                filledCrateDialogue(this)
            }
            else {
                unfilledCrateDialogue(this)
            }
        }
        else {
            chatNpc("Hello I'm Luthas, I run the banana plantation here.")
            when (options(
                "Could you offer me employment on your plantation?",
                "That customs officer is annoying isn't she?"
            )) {
                FIRST_OPTION -> askForEmploymentDialogue(this)
                SECOND_OPTION -> annoyingCustomsDialogue(this)
            }
        }
    }
}

suspend fun filledCrateDialogue(it: QueueTask) {
    it.chatPlayer("I've filled the crate with bananas")
    it.chatNpc("Well done, here's your payment.")
    val payment = 30
    val added = it.player.inventory.add(Item(Items.COINS_995, payment))
    if (added.hasFailed()) {
        world.spawn(GroundItem(Items.COINS_995, added.getLeftOver(), it.player.tile, it.player))
    }
    it.player.attr[EMPLOYED_ATTR] = false
    when (it.options(
        "Will you pay me for another crate full?",
        "Thank you, I'll be on my way."
    )) {
        FIRST_OPTION -> {
            it.chatNpc("Yes certainly.")
            it.chatNpc(*("If you go outside you should see the old crate has been loaded on to the ship, and there is " +
                "another empty crate in its place.").splitForDialogue())
            it.player.attr[EMPLOYED_ATTR] = true
        }
        SECOND_OPTION -> it.chatPlayer("Thank you, I'll be on my way.")
    }
}

suspend fun unfilledCrateDialogue(it: QueueTask) {
    it.chatNpc("Have you completed your task yet?")
    when (it.options(
        "What did I have to do again?",
        "No, the crate isn't full yet...",
        "So where are these bananas going to be delivered to?",
        "That customs officer is annoying isn't she?"
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("What did I have to do again?")
            it.chatNpc(*("There's a crate ready to be loaded onto the ship. If you could fill it up with bananas, I'll" +
                " pay you 30 gold.").splitForDialogue())
        }
        SECOND_OPTION -> {
            it.chatPlayer("No, the crate isn't full yet...")
            it.chatNpc("Well come back when it is.")
        }
        THIRD_OPTION -> {
            it.chatPlayer(*"So where are these bananas going to be delivered to?".splitForDialogue())
            it.chatNpc(*"I sell them to Wydin who runs a grocery store in Port Sarim.".splitForDialogue())
        }
        FOURTH_OPTION -> {
            annoyingCustomsDialogue(it)
        }

    }
}

suspend fun askForEmploymentDialogue(it: QueueTask) {
    it.chatPlayer("Could you offer me employment on your plantation?")
    it.chatNpc(*"Yes, I can sort something out. There's a crate ready to be loaded onto the ship.".splitForDialogue())
    it.chatNpc(*("You wouldn't believe the demand for bananas from Wydin's shop over in Port Sarim. I think this is " +
        "the third crate I've shipped him this month..").splitForDialogue())
    it.chatNpc(*"If you could fill it up with bananas, I'll pay you 30 gold.".splitForDialogue())
    it.player.attr[EMPLOYED_ATTR] = true
}

suspend fun annoyingCustomsDialogue(it: QueueTask) {
    it.chatPlayer("That customs officer is annoying isn't she?")
    it.chatNpc(*"Well I know her pretty well. She doesn't cause me any trouble any more.".splitForDialogue())
    it.chatNpc(*"She doesn't even search my export crates any more. She knows they only contain bananas.".splitForDialogue())
    it.chatPlayer(*"Really? How interesting. Whereabouts do you send those to?".splitForDialogue())
    it.chatNpc(*"There is a little shop over in Port Sarim that buys them up by the crate. I believe it is run by a man called Wydin.".splitForDialogue())
}
