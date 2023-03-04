package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.RuneMysteries

on_npc_option(npc = Npcs.MAGIC_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Hello there adventurer, I am the Magic combat tutor.", "Would you like to learn about magic combat, or perhaps", "how to make runes?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("Tell me about magic combat please.", "How do I make runes?", "I'd like some air and mind runes.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("Tell me about magic combat please.")
            it.chatNpc("Of course ${it.player.username}! As a rule of thumb, if you cast the", "highest spell of which you're capable, you'll get the best ", "experience possible.")
            it.chatNpc("Wearing metal armour and ranged armour can", "seriously impair your magical abilities. Make sure you", "wear some robes to maximise your capabilities.")
            it.chatNpc("Superheat Item and the Alchemy spells are good ways", "to level magic if you are not interested in the combat", "aspect of magic.")
            it.chatNpc("There's always the Magic Training Arena. You can", "find it north of the Duel Arena in Al Kharid. You will", "be able to earn some special rewards there by practicing", "your magic there.")
//            if (it.player.hasAncientMagicks) {
//                it.chatNpc("I see you already have access to the ancient magicks.", "Well done, these will aid you greatly.")
//            }
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        2 -> {
            it.chatPlayer("How do I make runes?")
            it.chatNpc("There are a couple of things you will need to make", "runes, rune essence and a talisman to enter the temple", "ruins.")
            if (it.player.getSkills().getCurrentXp(Skills.RUNECRAFTING) > 0 && it.player.getCurrentStage(RuneMysteries) > 1){
                it.chatNpc("To get rune essence you will need to gather them in", "the essence mine. You can get to the mine by talking", "to Aubury who owns the runes shop in south east", "Varrock.")
                it.chatNpc("I see you have some experience already in", "Runecrafting. Perhaps you should try crafting some", "runes which you can then use in magic.")
                it.player.runClientScript(115, 2)
                it.chatNpc("Check the skill guide to see which runes you can craft.")
            }else{
                it.chatNpc("To get rune essence you will need to gather them", "somehow. You should talk to the Duke of Lumbridge, he", "may be able to help you with that. Alternatively, other", "players may sell you the essence.")
                it.chatNpc("As you're fairly new to runecrafting you should start", "with air runes and mind runes.")
            }
            it.chatNpc("You will need a talisman for the rune you would like to", "create. You can right-click on it and select the Locate", "option. This will tell you the rough location of the altar.")
            it.chatNpc("When you find the ruined altar, use the talisman on it", "to be transported to a temple where you can craft your", "runes.")
            it.chatNpc("Clicking on the temple's altar will imbue your rune", "essence with the altar's magical property.")
            it.chatNpc("If you want to save yourself an inventory space, you", "could always try binding the talisman to a tiara.")
            it.chatNpc("To make one, take a tiara and talisman to the ruins", "and use the tiara on the temple altar. This will bind the", "talisman to your tiara.")
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        3 -> {
            it.chatPlayer("I'd like some air and mind runes.")
            it.terminateAction
            it.player.queue { claimRunes(this) }
        }
        4 -> {
            it.chatPlayer("Goodbye.")
        }
    }
}

suspend fun claimRunes(it: QueueTask) {
    val tutorConsumablesTimer = TimerKey(persistenceKey = "tutor_consumables_timer", tickOffline = true)
    val tutorConsumablesDelay = 3000
    if (it.player.timers.has(tutorConsumablesTimer)) {
        it.chatNpc("I work with the Ranged tutor to give out consumable ", "items that you may need for combat such as arrows ", "and runes. However we have had some cheeky people ", "try to take both!")
        it.chatNpc("So, every half an hour, you may come back and claim ", "either arrows OR runes, but not both. Come back in a ", "while for runes, or simply make your own.")
    } else {
        if(it.player.hasItem(Items.AIR_RUNE) && it.player.hasItem(Items.MIND_RUNE)) {
            it.chatNpc("You already have mind and air runes.")
        }else if(it.player.inventory.freeSlotCount >= 2) {
            it.doubleItemMessageBox("The instructor gives you 30 mind runes and air runes.", item1 = Items.MIND_RUNE, item2 = Items.AIR_RUNE)
            it.player.inventory.add(Items.MIND_RUNE, amount = 30)
            it.player.inventory.add(Items.AIR_RUNE, amount = 30)
            it.player.timers[tutorConsumablesTimer] = tutorConsumablesDelay
            it.chatNpc("There you go, use them well.")
        }else{
            it.chatNpc("You don't have enough space for me to give you", "any runes.")
        }
    }
}