package gg.rsmod.plugins.content.npcs

import gg.rsmod.plugins.content.items.books.RulesOf2011Scape
import gg.rsmod.plugins.content.items.books.openBook
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer
import gg.rsmod.plugins.content.quests.startedQuest

val crierTips = listOf(
    "Be careful when fighting wizards! Wearing heavy armour can lower your Magic resistance!",
    "Beware of players trying to lure you into the wilderness. Your items cannot be returned if you lose them!",
    "Did you know having a bank pin can help you secure your valuable items?",
    "Did you know most skills have right click 'Make-X' options to help you train faster?",
    "Did you know that at high levels of Runecrafting you get more than one rune per essence?",
    "Did you know that mithril equipment is very light?",
    "Did you know that you can wear a shield with a crossbow?",
    "Did you know you burn food less often than the range in Lumbridge castle than other ranges?",
    "Did you know? Superheat Item means you never fail to smelt ore!",
    "Did you know? You burn food less often on a range than on a fire!",
    "Don't use your RuneScape password on other sites. Keep your account safe!",
    "Feeling harassed? Don't forget your ignore list can be especially useful if a player seems to be picking on you!",
    "If a player isn't sure of the rules, send them to me! I'll be happy to remind them!",
    "If the chat window is moving too quickly to report a player accurately, run to a quiet spot and review the chat " +
        "at your leisure!",
    "If you see someone breaking the rules, report them!",
    "If you think someone knows your password - change it!",
    "If you're lost have no idea where to go, use the Home Teleport spell for free!",
    "Jagex will never email you asking for your log-in details.",
    "Make your recovery questions and answers hard to guess but easy to remember.",
    "Melee armour actually gives you disadvantages when using magic attacks. It may be better to take off your armour" +
        " entirely!",
    "Never let anyone else use your account.",
    "Never question a penguin.",
    "Never tell your password to anyone, not even your best friend!",
    "Players can not trim armour. Don't fall for this popular scam!",
    "The squirrels! The squirrels are coming! Noooo, get them out of my head!",
    "Take time to check the second trade window carefully. Don't be scammed!",
    "There are no cheats in 2011Scape! Never visit websites promising otherwise!"
)

val crierIds = listOf(Npcs.TOWN_CRIER_6136, Npcs.TOWN_CRIER_6137, Npcs.TOWN_CRIER_6138, Npcs.TOWN_CRIER_6139)

crierIds.forEach { id ->
    on_npc_option(id, "Talk-to") {
        when (id){
            Npcs.TOWN_CRIER_6136 -> {
                player.queue {
                    vampyreSlayerDialogue(this)
                }
            }
            else -> {
                player.queue {
                    normalDialogue(this, id == Npcs.TOWN_CRIER_6138)
                }
            }
        }
    }
}


suspend fun vampyreSlayerDialogue(it: QueueTask) {
    if (it.player.startedQuest(VampyreSlayer)) {
        when (it.options(
            "Vampyre Slayer.",
            "Something else."
        )) {
            FIRST_OPTION -> {
                if (it.player.finishedQuest(VampyreSlayer)) {
                    finishedVampyreSlayer(it)
                }
                else {
                    startedVampyreSlayer(it)
                }
            }
            SECOND_OPTION -> normalDialogue(it)
        }
    }
    else {
        it.chatNpc("BEWARE THE VAMPYRE, EVERYONE IN BY", "NIGHTFALL...", facialExpression = FacialExpression.WORRIED)
        it.chatNpc("Oh, sorry for shouting right at you.", facialExpression = FacialExpression.DISDAIN)
        when (it.options(
            "Vampyre? What vampyre?",
            "Talk about something else."
        )) {
            FIRST_OPTION -> {
                it.chatNpc("Hello citizen. What can I do for you?")
                it.chatPlayer("What did you mean about 'Beware the vampyre'?")
                it.chatNpc("Our village is plagued by a vampyre that lives in the manor to the north. One of the " +
                    "citizens - Morgan I think - is trying to find a vampyre slayer to rid us of him.",
                    facialExpression = FacialExpression.SAD, wrap = true)
                it.chatPlayer("Hmm, maybe I should speak to him.", facialExpression = FacialExpression.THINK)
            }
            SECOND_OPTION -> normalDialogue(it)
        }
    }
}

suspend fun finishedVampyreSlayer(it: QueueTask) {
    it.chatNpc("Hello citizen. What can I do for you?")
    it.chatPlayer("I did it! I killed the vampyre. Your village is safe again.", facialExpression = FacialExpression
        .HAPPY_TALKING, wrap = true)
    it.chatNpc("That is great news!", facialExpression = FacialExpression.NORMAL, wrap = true)
}

suspend fun startedVampyreSlayer(it: QueueTask) {
    it.chatNpc("Hello citizen. What can I do for you?")
    it.chatPlayer("What can you tell me about the vampyre attacks on the village?", facialExpression =
        FacialExpression.CONFUSED, wrap = true)
    it.chatNpc("A sad business, that. It used to be a great tourist attraction, having a haunted house next to the " +
        "village. Then these attacks began, and the town has been hit hard financially. It's hard to keep morale up " +
        "when", facialExpression = FacialExpression.SAD, wrap = true)
    it.chatNpc("it's not safe to be outside after nightfall.", facialExpression = FacialExpression.SAD, wrap =
        true)
}

suspend fun normalDialogue(it: QueueTask, tasksDialogueAdded: Boolean = false) {
    it.chatNpc("Hear ye! Hear ye! Player Moderators massive help to 2011Sc-", facialExpression = FacialExpression
        .CALM_TALK, wrap = true)
    it.chatNpc("Oh, hello citizen. Are you here to find out about Player Moderators? Or perhaps would you like to " +
        "know about the laws of the land?", facialExpression = FacialExpression.CALM_TALK, wrap = true)
    if (tasksDialogueAdded) {
        somethingElseWithArdyTasks(it)
    }
    else {
        somethingElse(it)
    }
}

suspend fun somethingElse(it: QueueTask) {
    when (it.options(
        "Tell me about Player Moderators.",
        "Tell me about the Rules of 2011Scape",
        "Can you give me a handy tip please?"
    )) {
        FIRST_OPTION -> {
            talkAboutPMods(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("Tell me about the Rules of 2011Scape", facialExpression = FacialExpression.NORMAL, wrap =
                true)
            it.chatNpc("At once. Take a look at my book here.", facialExpression = FacialExpression.NORMAL, wrap =
                true)
            it.player.closeInterface(InterfaceDestination.MAIN_SCREEN)
            it.player.openBook(RulesOf2011Scape.book)
        }
        THIRD_OPTION -> {
            it.chatPlayer("Can you give me a handy tip please?")
            it.chatNpc(crierTips.random(), facialExpression = FacialExpression.NORMAL, wrap = true)
            anythingElsePMods(it, false)
        }
    }
}

suspend fun talkAboutPMods(it: QueueTask, showInitialDialogue: Boolean = true) {
    if (showInitialDialogue) {
        it.chatPlayer("Tell me about Player Moderators.", facialExpression = FacialExpression.CONFUSED, wrap = true)
        it.chatNpc("Of course. What would you like to know?", facialExpression = FacialExpression.NORMAL, wrap =
            true)
    }
    when(it.options(
        "What is a Player Moderator?",
        "What can Player Moderators do?",
        "How do I become a Player Moderator?",
        "What can Player Moderators not do?",
        "Something else."
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("What is a Player Moderator?", facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            it.chatNpc("Player Moderators are normal players of the game, just like you. However since they " +
                "have shown themselves to be trustworthy and active reporters, they have been invited by " +
                "Jagex", facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("to monitor the game and take appropriate action when they see rule breaking. You can " +
                "spot a Player Moderator in game by looking at the chat screen - when a Player Moderator speaks,",
                facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("a silver crown appears to the left of their name. Remember, if there's no silver " +
                "crown there, they are not a Player Moderator! You can check out the website if you'd like " +
                "more information.", facialExpression = FacialExpression.NORMAL, wrap = true)
            anythingElsePMods(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("What can Player Moderators do?", facialExpression = FacialExpression.CONFUSED, wrap = true)
            it.chatNpc("Player Moderators, or 'P-mods', have the ability to mute rule breakers and Jagex view their " +
                "reports as a priority so that action is taken as quickly as possible. P-Mods also have access to the" +
                " Player Moderator Centre.", facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("Within the Centre are tools to help them Moderate 2011Scape. These tools include dedicated " +
                "forums, the Player Moderator Guidelines and the Player Moderator Code of Conduct.", facialExpression
            = FacialExpression.NORMAL, wrap = true)
            anythingElsePMods(it)
        }
        THIRD_OPTION -> {
            it.chatPlayer("How do I become a Player Moderator?", facialExpression = FacialExpression.CONFUSED, wrap = true)
            it.chatNpc("Jagex picks players who spend their time and effort to help better the 2011Scape community. " +
                "To increase your chances of becoming a Player Moderator:", facialExpression = FacialExpression
                    .NORMAL, wrap = true)
            it.chatNpc("Keep your account secure! This is very important, as a player with poor security will never " +
                "be a P-Mod. Read our Security Tips for more information.", facialExpression = FacialExpression
                    .NORMAL, wrap = true)
            it.chatNpc("Play by the rules! The rules of 2011Scape are enforced for a reason, to make the game a fair " +
                "and enjoyable environment for all.", facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("Report accurately! When Jagex consider an account for review they look for quality, not " +
                "quantity. Ensure your reports are of a high quality by following the report guidelines.",
                facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("Be excellent to each other! Treat others as you would want to be treated yourself. Respect " +
                "your fellow player. More information can be found on the website.", facialExpression =
                FacialExpression.NORMAL, wrap = true)
            anythingElsePMods(it)
        }
        FOURTH_OPTION -> {
            it.chatPlayer("What can Player Moderators not do?", facialExpression = FacialExpression.CONFUSED, wrap =
                true)
            it.chatNpc("P-Mods cannot ban your account - the can only report offences. Jagex then take action based " +
                "on the evidence received. If you lose your password or get scammed by another player, P-Mods cannot " +
                "help", facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("you get your account back. All they can do is recommend you to go to Player Support. They " +
                "cannot retrieve any items you may have lost and they certainly do not receive any free items from " +
                "Jagex for moderating the game.", facialExpression = FacialExpression.NORMAL,
                wrap = true)
            it.chatNpc("They are players who give their all to help the community, out of the goodness of their " +
                "hearts! P-mods do not work for Jagex and so cannot make you a Moderator, or recommend other accounts" +
                " to become Moderators.", facialExpression = FacialExpression.NORMAL, wrap = true)
            it.chatNpc("If you wish to become a Moderator, feel free to ask me!")
            anythingElsePMods(it)

        }
        FIFTH_OPTION -> somethingElse(it)
    }
}

suspend fun anythingElsePMods(it: QueueTask, sayThanks: Boolean = true) {
    if (sayThanks) {
        it.chatPlayer("Thanks!", facialExpression = FacialExpression.HAPPY_TALKING)

    }
    it.chatNpc("Is there anything else you'd like to know?", facialExpression = FacialExpression
        .THINK, wrap = true)
    when (it.options(
        "Tell me about Player Moderators.",
        "Tell me about something else.",
        "No thanks."
    )) {
        FIRST_OPTION -> talkAboutPMods(it, false)
        SECOND_OPTION -> somethingElse(it)
        THIRD_OPTION -> it.chatPlayer("No thanks.", facialExpression = FacialExpression.NORMAL)
    }
}

suspend fun somethingElseWithArdyTasks(it: QueueTask) {
    when (it.options(
        "Tell me about Player Moderators.",
        "Tell me about the Rules of 2011Scape",
        "Can you give me a handy tip please?",
        "About the Task System..."
    )) {
        FIRST_OPTION -> {
            talkAboutPMods(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("Tell me about the Rules of 2011Scape", facialExpression = FacialExpression.NORMAL, wrap =
                true)
            it.chatNpc("At once. Take a look at my book here.", facialExpression = FacialExpression.NORMAL, wrap =
                true)
            it.player.closeInterface(InterfaceDestination.MAIN_SCREEN)
            it.player.openBook(RulesOf2011Scape.book)
        }
        THIRD_OPTION -> {
            it.chatPlayer("Can you give me a handy tip please?")
            it.chatNpc(crierTips.random(), facialExpression = FacialExpression.NORMAL, wrap = true)
            anythingElsePMods(it, false)
        }
        FOURTH_OPTION -> {
            // TODO Change once task system is implemented
            it.chatNpc(
                "There are no tasks for you to do right now. Check back again later.",
                facialExpression = FacialExpression.NORMAL,
                wrap = true)
        }
    }
}
