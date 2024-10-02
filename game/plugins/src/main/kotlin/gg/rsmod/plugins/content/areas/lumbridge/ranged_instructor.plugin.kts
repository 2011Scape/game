package gg.rsmod.plugins.content.areas.lumbridge

on_npc_option(npc = Npcs.RANGED_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc(
            "Hey there adventurer, I am the Ranged combat tutor. ",
            "Is there anything you would like to know?",
        )
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("How can I train my Ranged?", "How do I create a bow and arrows?", "Goodbye.")) {
        FIRST_OPTION -> {
            it.chatPlayer("How can I train my Ranged?")
            it.chatNpc(
                "To start with you'll need a bow and arrows, you were ",
                "given a Shortbow and some arrows when you arrived here.",
            ) // from Tutorial island.")
            it.chatNpc(
                "Alternatively, you can buy a training bow and some ",
                "arrows from Beefy Bill, just north of the mill.",
            )
            it.player.focusTab(Tabs.SKILLS)
            it.chatNpc(
                "Not all bows can use every type of arrow, most bows ",
                "have a limit. You can find out your bows limit by ",
                "checking the Ranged skill guide.",
            )
            it.chatNpc(
                "If you do decide to use the Training bow, you will only ",
                "be able to use the Training arrows with it. Remember",
                "to pick up your arrows, re-use them and come back",
                "when you need more.",
            )
            it.player.focusTab(Tabs.INVENTORY)
            it.chatNpc(
                "Once you have your bow and arrows, equip them by ",
                "selecting their Wield option in your inventory.",
            )
            it.player.focusTab(Tabs.COMBAT_STYLES)
            it.chatNpc(
                "You can change the way you attack by going to the ",
                "combat options tab. There are three attack styles for ",
                "bows. Those styles are Accurate, Rapid and Longrange.",
            )
            it.chatNpc(
                "Accurate increases your bows attack accuracy. Rapid ",
                "will increase your attack speed with the bow. Longrange ",
                "will let you attack your enemies from a greater ",
                "distance.",
            )
            it.chatNpc(
                "If you are ever in the market for a new bow or some ",
                "arrows, you should head on over to Lowe's Archery ",
                "Emporium in Varrock.",
            )
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        SECOND_OPTION -> {
            it.chatPlayer("How do I create a bow and arrows?")
            it.chatNpc("Ahh the art of fletching. Fletching is used to create ", "your own bow and arrows.")
            it.chatNpc(
                "It's quite simple really. You'll need an axe to cut some ",
                "logs from trees and a knife. Knives can be found in ",
                "and around the Lumbridge castle and in the Varrock",
                "General store upstairs.",
            )
            it.chatNpc("Use your knife on the logs. This will bring up a menu ", "listing items you can fletch.")
            it.chatNpc("For arrows you will need to smith some arrow heads ", "and kill some chickens for feathers.")
            it.chatNpc(
                "Add the feathers to your Arrow shafts to make Headless ",
                "arrows, then use your chosen arrow heads on the ",
                "Headless arrows to make your arrows.",
            )
            it.chatNpc(
                "Now for making bows. When accessing the fletching ",
                "menu, instead of choosing Arrows shafts, you can make ",
                "an unstrung bow instead.",
            )
            it.chatNpc("To complete the bow you will need to get your hands ", "on a Bow string.")
            it.chatNpc(
                "First you will need to get some flax from a flax field. ",
                "There's one south of Seers' Village. Gather flax, then ",
                "spin it on a spinning wheel, there's one in Seers' Village ",
                "too.",
            )
            it.chatNpc("This makes bow strings which you can then use on the ", "unstrung bows to make a working bow!")
            it.chatPlayer("Brilliant. If I forget anything I'll come talk to you ", "again.")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        THIRD_OPTION -> {
            it.chatPlayer("Goodbye.")
        }
    }
}
