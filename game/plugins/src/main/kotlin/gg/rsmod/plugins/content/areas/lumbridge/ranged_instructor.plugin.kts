package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.RANGED_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Hey there adventurer, I am the Ranged combat tutor. Is", "there anything you would like to know?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("How can I train my Ranged?", "How do I create a bow and arrows?", "I'd like a training bow and arrows.", "Goodbye."
    )) {
        1 -> {
            if (it.player.getSkills().getCurrentLevel(Skills.RANGED) >= 30){
                it.chatNpc("You should know the basics of ranged combat by now, ", "but if you are looking for somewhere to test your ", "skills and make some money, you should ", "try hunting Moss giants in Varrock sewers.")
                it.chatNpc("If you're lucky you might snag some goodies ", "that you can sell for a decent profit.")
                it.chatNpc("If you are looking to do something different, visit ", "the ranging guild over near Hemenster, there you can ", "practice your archery and earn tickets for it at the same time.")
                it.chatNpc("You can then hand those tickets in for a variety ", "of goods you can use to train your Ranged skill.")
                it.chatNpc("If you are ever in the market for a new bow or some ", "arrows, you should head on over to ", "Lowe's Archery Emporium in Varrock.")
                it.terminateAction
                it.player.queue { mainChat(this) }
            } else {
                it.chatNpc("To start with you'll need a bow and arrows, you were ", "given a Shortbow and some arrows when you arrived here.") //TODO: append "from Tutorial Island" when it is added.
                it.chatNpc("Alternatively, you can claim a ", "training bow and some arrows from me.")
                it.chatNpc("Mikasi, the Magic Combat tutor and I both give out ", "items every 30 minutes, however you must choose whether ", "you want runes or ranged equipment.")
                it.chatNpc("To claim the Training bow and arrows, right-click on ", "me and choose Claim, to claim runes right-click ", "on the Magic Combat tutor and select Claim.")
                it.player.runClientScript(115, 2)
                it.chatNpc("Not all bows can use every type of arrow, most bows ", "have a limit. You can find out your bows ", "limit by checking the Ranged skill guide.")
                it.chatNpc("If you do decide to use the Training bow, you will ", "only be able to use the Training arrows with it. ", "Remember to pick up your arrows, ", "re-use them and come back when you need more.")
                it.chatNpc("Once you have your bow and arrows, equip them by ", "selecting their Wield option in your inventory.")
                it.player.runClientScript(115, 0)
                it.chatNpc("You can change the way you attack by going to the ", "combat options tab. There are three attack styles for ", "bows. Those styles are Accurate, Rapid and Longrange.")
                it.chatNpc("Accurate increases your bows attack accuracy. Rapid ", "will increase your attack speed with the bow. ", "Longrange will let you attack your ", "enemies from a greater distance.")
                it.terminateAction
                it.player.queue { mainChat(this) }
            }

        }

        2 -> {

        }

        3 -> {

        }

        4 -> {

        }
    }
}