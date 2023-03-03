package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.RANGED_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Hey there adventurer, I am the Ranged combat tutor. ", "Is there anything you would like to know?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("How can I train my Ranged?", "How do I create a bow and arrows?", "I'd like some arrows and a training bow.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("How can I train my Ranged?")
            it.chatNpc("To start with you'll need a bow and arrows, you were ", "given a Shortbow and some arrows when you arrived here.") //from Tutorial island.")
            it.chatNpc("Alternatively, you can claim a training bow and some ", "arrows from me.")
            it.chatNpc("The Magic Combat tutor and I both give out ", "items every 30 minutes, however you must choose ", "whether you want runes or ranged equipment.")
            it.player.runClientScript(115, 2)
            it.chatNpc("Not all bows can use every type of arrow, most bows ", "have a limit. You can find out your bows limit by ", "checking the Ranged skill guide.")
            it.chatNpc("If you do decide to use the Training bow, you will only ", "be able to use the Training arrows with it. Remember", "to pick up your arrows, re-use them and come back", "when you need more.")
            it.player.runClientScript(115, 4)
            it.chatNpc("Once you have your bow and arrows, equip them by ", "selecting their Wield option in your inventory.")
            it.player.runClientScript(115, 0)
            it.chatNpc("You can change the way you attack by going to the ", "combat options tab. There are three attack styles for ", "bows. Those styles are Accurate, Rapid and Longrange.")
            it.chatNpc("Accurate increases your bows attack accuracy. Rapid ", "will increase your attack speed with the bow. Longrange ", "will let you attack your enemies from a greater ", "distance.")
            it.chatNpc("If you are ever in the market for a new bow or some ", "arrows, you should head on over to Lowe's Archery ", "Emporium in Varrock.")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        2 -> {
            it.chatPlayer("How do I create a bow and arrows?")
            it.chatNpc("Ahh the art of fletching. Fletching is used to create ", "your own bow and arrows.")
            it.chatNpc("It's quite simple really. You'll need an axe to cut some ", "logs from trees and a knife. Knives can be found in ", "and around the Lumbridge castle and in the Varrock", "General store upstairs.")
            it.chatNpc("Use your knife on the logs. This will bring up a menu ", "listing items you can fletch.")
            it.chatNpc("For arrows you will need to smith some arrow heads ", "and kill some chickens for feathers.")
            it.chatNpc("Add the feathers to your Arrow shafts to make Headless ", "arrows, then use your chosen arrow heads on the ", "Headless arrows to make your arrows.")
            it.chatNpc("Now for making bows. When accessing the fletching ", "menu, instead of choosing Arrows shafts, you can make ", "an unstrung bow instead.")
            it.chatNpc("To complete the bow you will need to get your hands ", "on a Bow string.")
            it.chatNpc("First you will need to get some flax from a flax field. ", "There's one south of Seers' Village. Gather flax, then ", "spin it on a spinning wheel, there's one in Seers' Village ", "too.")
            it.chatNpc("This makes bow strings which you can then use on the ", "unstrung bows to make a working bow!")
            it.chatPlayer("Brilliant. If I forget anything I'll come talk to you ", "again.")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        3 -> {
            it.chatPlayer("I'd like some arrows and a training bow.")
            it.terminateAction
            it.player.queue { claimBow(this) }
        }
        4 -> {
            it.chatPlayer("Goodbye.")
        }
    }
}

suspend fun claimBow(it: QueueTask) {
    val tutorConsumablesTimer = TimerKey(persistenceKey = "tutor_consumables_timer", tickOffline = true)
    val tutorConsumablesDelay = 100
    if (it.player.timers.has(tutorConsumablesTimer)) {
        it.chatNpc("I work with the Magic tutor to give out consumable ", "items that you may need for combat such as arrows ", "and runes. However we have had some cheeky people ", "try to take both!")
        it.chatNpc("So, every half an hour, you may come back and claim ", "either arrows OR runes, but not both. Come back in a ", "while for arrows, or simply make your own.")
    } else {
        if(it.player.hasItem(Items.TRAINING_BOW) && it.player.hasItem(Items.TRAINING_ARROWS)) {
            it.chatNpc("You already have a training bow and some training arrows.")
        }else if(it.player.inventory.freeSlotCount >= 2) {
            it.doubleItemMessageBox("The instructor gives you a Training bow and arrows.", item1 = Items.TRAINING_BOW, item2 = Items.TRAINING_ARROWS)
            it.player.inventory.add(Items.TRAINING_BOW)
            it.player.inventory.add(Items.TRAINING_ARROWS, amount = 25)
            it.player.timers[tutorConsumablesTimer] = tutorConsumablesDelay
            it.chatNpc("There you go, use them well.")
        }else{
            it.chatNpc("You don't have enough space for me to give you a", "training bow, nor arrows.")
        }
    }
}