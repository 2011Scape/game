package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.game.Server
import gg.rsmod.game.model.Gender
import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.plugin.KotlinPlugin
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.plugins.api.cfg.*
import gg.rsmod.plugins.api.ext.*

class BankGuard(r: PluginRepository, world: World, server: Server) : KotlinPlugin(r, world, server) {

    companion object {
        val TALKED_ABOUT_WALL = AttributeKey<Boolean>(persistenceKey = "talked_about_wall")
        val SHOWN_BANK_TAPE = AttributeKey<Boolean>(persistenceKey = "shown_bank_tape")
    }

    init {
        on_npc_option(Npcs.BANK_GUARD, "Talk-to") {
            val talkedBefore = player.attr.has(TALKED_ABOUT_WALL)
            player.queue {
                chatNpc("Yes?")
                if (talkedBefore) {
                    if (!player.attr.has(SHOWN_BANK_TAPE)) {
                        chatPlayer("Do you have any idea who robbed the bank yet?", facialExpression =
                            FacialExpression.CONFUSED, wrap = true)
                        fairlyCertainDialogue(this)
                    }
                    else {
                        afterViewingTape(this)
                    }
                }
                else {
                    when(options(
                        "Can I deposit my stuff here?",
                        "That wall doesn't look very good.",
                        "Sorry, I don't want anything."
                    )) {
                        FIRST_OPTION -> {
                            chatPlayer("Hello. Can I deposit my stuff here?")
                            chatNpc("No. I am a security guard, not a bank clerk.")
                            when (options(
                                "That wall doesn't look very good.",
                                "Alright, I'll stop bothering you now."
                            )) {
                                FIRST_OPTION -> wallNoGood(this)
                                SECOND_OPTION -> stopBothering(this)
                            }
                        }
                        SECOND_OPTION -> wallNoGood(this)
                        THIRD_OPTION -> wantNothing(this)
                    }
                }
            }
        }
    }
}

suspend fun wallNoGood(it: QueueTask) {
    it.chatPlayer("That wall doesn't look very good.")
    it.chatNpc("No, it doesn't.")
    when (it.options(
        "Are you going to tell me what happened?",
        "Alright, I'll stop bothering you now."
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("Are you going to tell me what happened?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("I could do.")
            it.chatPlayer("Okay, go on!", facialExpression = FacialExpression.LAUGH_EXCITED)
            it.chatNpc("Someone smashed the wall when they were robbing the bank.", facialExpression =
                FacialExpression.SAD, wrap = true)
            it.player.attr[BankGuard.TALKED_ABOUT_WALL] = true
            it.chatPlayer("Someone's robbed the bank?")
            it.chatNpc("Yes.")
            it.chatPlayer("But... was anyone hurt?", facialExpression = FacialExpression.CONFUSED)
            it.chatPlayer("Did they get anything valuable?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("Yes, but we were able to get more staff and mend the wall easily enough.", wrap = true)
            it.chatNpc("The Bank has already replaced all the stolen items that belonged to customers.", wrap = true)
            it.chatPlayer("Oh, good... but the bank staff got hurt?")
            it.chatNpc("Yes, but the new ones are just as good.")
            it.chatPlayer("You're not very nice, are you?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("No-one's expecting me to be nice.")
            it.chatPlayer("Anyway... So, someone's robbed the bank?", facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            it.chatNpc("Yes.")
            it.chatPlayer("Do you know who did it?")
            fairlyCertainDialogue(it)
        }
        SECOND_OPTION -> stopBothering(it)
    }
}

suspend fun wantNothing(it: QueueTask) {
    it.chatPlayer("Sorry, I don't want anything.")
    it.chatNpc("Ok.")
}

suspend fun stopBothering(it: QueueTask) {
    it.chatPlayer("Alright, I'll stop bothering you now.")
    val sirOrMadam = if (it.player.appearance.gender == Gender.MALE) "sir" else "madam"
    it.chatNpc("Good day, $sirOrMadam.")
}

suspend fun fairlyCertainDialogue(it: QueueTask) {
    it.chatNpc("We are fairly sure we know who the robber was. The security recording was damaged in the " +
        "attack, but it still shows his face clearly enough.", wrap = true)
    it.chatPlayer("You've got a security recording?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("Yes. Our insurers insisted that we install a magical scrying orb.", wrap = true)
    when (it.options(
        "Can I see the recording?",
        "So who was the robber?"
    )) {
        FIRST_OPTION -> seeRecording(it)
        SECOND_OPTION -> {
            it.chatPlayer("So who was the robber?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("I can't disclose that information.")
            when (it.options(
                "Can I see the recording?",
                "Alright, I'll stop bothering you now."
            )) {
                FIRST_OPTION -> seeRecording(it)
                SECOND_OPTION -> stopBothering(it)
            }
        }
    }
}

suspend fun seeRecording(it: QueueTask) {
    it.chatPlayer("Can I see the recording?")
    it.chatNpc("I suppose so. But it's quite long.")
    when (it.options(
        "That's okay, show me the recording.",
        "Thanks, maybe another day.",
        title = "Would you like to see the<br>recording?",
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("That's okay, show me the recording.")
            it.chatNpc("Alright... The bank's magical playback device will feed the recorded images into you mind. " +
                "Just shut your eyes.", wrap = true)
            showBankRobberyCutscene(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("Thanks, maybe another day.")
            it.chatNpc("Ok.")
        }
    }
}

suspend fun seeRecordingAgain(it: QueueTask) {
    it.chatPlayer("Can I see that recording again, please?")
    it.chatNpc("I'd like you to pay me 50 gp first.")
    if (it.player.inventory.getItemCount(Items.COINS_995) >= 50) {
        when (it.options(
            "Okay, here's 50 gp.",
            "Thanks, maybe another day.",
            title = "Pay 50 gp to see the recording<br>again?",
        )) {
            FIRST_OPTION -> {
                it.chatPlayer("Okay, here's 50 gp.")
                it.player.inventory.remove(Items.COINS_995, 50)
                showBankRobberyCutscene(it)
            }
            SECOND_OPTION -> {
                it.chatPlayer("Thanks, maybe another day.")
                it.chatNpc("Ok.")
            }
        }
    }
    else {
        it.chatPlayer("I'm not carrying that much.")
        if (it.player.bank.getItemCount(Items.COINS_995) >= 50) {
            it.chatNpc("As a bank employee, I suppose I could take the money directly from your bank account.",
                wrap = true)
            when (it.options(
                "Okay, you can take 50 gp from my bank account.",
                "Thanks, maybe another day."
            )) {
                FIRST_OPTION -> {
                    it.chatPlayer("Okay, you can take 50 gp from my bank account.")
                    it.player.bank.remove(Items.COINS_995, 50)
                    showBankRobberyCutscene(it)
                }
                SECOND_OPTION -> {
                    it.chatPlayer("Thanks, maybe another day.")
                    it.chatNpc("Ok.")
                }
            }
        }
        else {
            it.chatNpc("Oh well, maybe another day.")
        }
    }
}

suspend fun afterViewingTape(it: QueueTask) {
    when (it.options(
        "The robber in the recording looked familiar.",
        "Can I see that recording again, please?",
        "Sorry, I don't want anything."
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("The robber in the recording looked familiar.")
            it.chatNpc("Oh, you recognised him too?", facialExpression = FacialExpression.CONFUSED)
            it.chatPlayer("Yes, and I can tell you where he lives!", facialExpression = FacialExpression.WORRIED)
            it.chatNpc("Thanks, but we already know where to find our suspect.")
            it.chatPlayer("So are you going to have him arrested?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("We're certainly keeping an eye on him. I'm afraid I can't give you any more details about the" +
                " case.", wrap = true)
            it.chatPlayer("Fair enough.")
        }
        SECOND_OPTION -> seeRecordingAgain(it)
        THIRD_OPTION -> wantNothing(it)
    }
}

suspend fun showBankRobberyCutscene(it: QueueTask) {
    if (!it.player.attr.has(BankGuard.SHOWN_BANK_TAPE)) {
        it.player.attr[BankGuard.SHOWN_BANK_TAPE] = true
    }
    it.messageBox("You close your eyes and watch the recording....")

    //TODO insert bank robbery cutscene here

    it.messageBox("End of recording.")
}
