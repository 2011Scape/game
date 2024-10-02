package gg.rsmod.plugins.content.areas.lumbridge

val CUSTOMISATION_INTERFACE = 1122

/**
 * Varbits for customization
 */

val CAPE_COLOR_VARBIT = 9671
val CAPE_COLOR_1_VARBIT = 9672
val CAPE_COLOR_2_VARBIT = 9673
val BOTTOM_TEXTURE_VARBIT = 9674
val TOP_TEXTURE_VARBIT = 9675
// TODO: find the varbit/info for the city logo/texture

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 40..80

on_npc_spawn(npc = Npcs.HERALD_OF_LUMBRIDGE) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(8)) {
        0 -> npc.forceChat("Give me an L!")
        1 -> npc.forceChat("Give me a U!")
        2 -> npc.forceChat("Give me a Lumbridge herald cape!")
        3 -> npc.forceChat("Lumbridge; the friendliest place to be")
        4 -> npc.forceChat("Wear a Lumbridge herald cape with pride!")
        5 -> npc.forceChat("It's Lumbridge!")
        6 -> npc.forceChat("The eye of the fun hurricane")
        7 -> npc.forceChat("Whoo! This is tiring work.")
        8 -> npc.forceChat("Have yourself a snazzy Lumbridge day!")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_npc_option(npc = Npcs.HERALD_OF_LUMBRIDGE, option = "talk-to") {
    player.queue { mainChat(this) }
}

on_npc_option(npc = Npcs.HERALD_OF_LUMBRIDGE, option = "customise") {
    if(player.hasItem(Items.HERALD_CAPE_21435)) {
        player.queue { customiseChat(this) }
    } else {
        player.message("You need to have a herald cape to perform this action.")
    }
}

suspend fun customiseChat(it: QueueTask) {
    it.chatNpc("Wonderful! Let's take a look at the customisations we can", "do...")
    it.player.openInterface(interfaceId = CUSTOMISATION_INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc("Hello there, deary! How can I make your Lumbridge", "day even more wonderful?")
    it.chatPlayer("Hello back at you!", "What do you do here?")
    it.chatNpc("I'm the Lumbridge herald, my dear! It's my job to", "make Lumbridge a lovely place to be in.")
    it.chatNpc("You need a parade? I'll arrange it! You need a fancy", "banner? I can do that too! You need a herald cape...")
    if(it.player.hasItem(Items.HERALD_CAPE_21435)) {
        it.chatPlayer("Oh, I already have one of those.")
        it.chatNpc("Yes, I noticed! It looks very dashing on you. Would", "you like to customise it some more, perhaps?")
        when (it.options("Yes", "No", title = "Customise your Lumbridge herald cape?")) {
            1 -> it.player.openInterface(interfaceId = CUSTOMISATION_INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
            2 -> it.terminateAction
        }
    } else {
        it.chatPlayer("Herald cape? What's that?")
        it.chatNpc("Why, it's the latest and greatest thing in Lumbridge", "attire. We provide you with a lovely Lumbridge cape to", "wear around, for nothing!")
        it.chatNpc("And if you've been particularly handy around the area,", "we'll show you how to customise it into something more", "fabulous!")
        it.chatNpc("In fact, I hear that other cities, like Varrock and Falador", "are also offering herald capes with their own special", "designs!")
        it.chatNpc("Naturally, our Lumbridge capes are much nicer.")
        it.chatNpc("Can I interest you in taking a cape?")
        when (it.options("Yes", "No", title = "Accept this Lumbridge herald cape?")) {
            1 -> {
                it.chatPlayer("Sure, I'll take one!")
                it.player.inventory.add(Item(Items.HERALD_CAPE_21435))
                it.chatNpc("Wonderful! Let's take a look at the customisations we can", "do...")
                it.player.openInterface(interfaceId = CUSTOMISATION_INTERFACE, dest = InterfaceDestination.MAIN_SCREEN)
            }
            2 -> {
                it.chatPlayer("No thanks.")
                it.chatNpc("Oh, gosh. Well, drop by again if you change your mind,", "sweetheart.")
            }
        }
    }
}