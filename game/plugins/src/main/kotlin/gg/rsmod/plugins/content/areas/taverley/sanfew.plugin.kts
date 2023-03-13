package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.DruidicRitual
import gg.rsmod.plugins.content.quests.impl.RuneMysteries
import gg.rsmod.plugins.content.quests.startQuest

val druidicRitual = DruidicRitual

on_npc_option(Npcs.SANFEW, option = "talk-to") {
    player.queue {
        when(player.getCurrentStage(druidicRitual)) {
            1 -> duringDruidicRitual(this)
            2 -> ingredientsDialogue(this)
            else -> postQuest(this)
        }
    }
}
suspend fun duringDruidicRitual(it: QueueTask) {
    it.chatNpc("What can I do for you young 'un?")
    it.chatPlayer(
        "I've been sent to assist you with the ritual to",
        "purify the Varrockian stone circle.")
    it.chatNpc(
        "Well, what I'm struggling with right now is the meats",
        "needed for the potion to honour Guthix. I need the raw",
        "meats of four different animals for it, but not just",
        "any old meats will do.")
    it.chatNpc(
        "Each meat has to be dipped individually into the",
        "Cauldron of Thunder for it to work correctly.")
    it.chatPlayer(
        "Where can I find this cauldron?")
    it.chatNpc(
        "It is located somewhere in the mysterious underground",
        "halls which are located somewhere in the woods just",
        "South of here. They are too dangerous",
        "for me to go myself however.")
    it.chatPlayer(
        "Ok, I'll go do that then.")
    it.player.advanceToNextStage(DruidicRitual)
}

suspend fun ingredientsDialogue(it: QueueTask) {
    it.chatNpc(
        "Did you bring me the required",
        "ingredients for the potion?")
    if(
        it.player.inventory.contains(Items.ENCHANTED_BEEF) || it.player.inventory.contains(Items.ENCHANTED_BEAR_MEAT) || it.player.inventory.contains(Items.ENCHANTED_RAT_MEAT) || it.player.inventory.contains(Items.ENCHANTED_CHICKEN)) {
        it.chatPlayer("Yes, I have all four now!")
        it.player.inventory.remove(item = Item(Items.ENCHANTED_BEEF, amount = 1), assureFullRemoval = true)
        it.player.inventory.remove(item = Item(Items.ENCHANTED_BEAR_MEAT, amount = 1), assureFullRemoval = true)
        it.player.inventory.remove(item = Item(Items.ENCHANTED_RAT_MEAT, amount = 1), assureFullRemoval = true)
        it.player.inventory.remove(item = Item(Items.ENCHANTED_CHICKEN, amount = 1), assureFullRemoval = true)
        it.chatNpc(
            "Well hand 'em over then lad! Thank you so much",
            "adventurer! These meats will allow our potion to honour",
            "Guthix to be completed, and bring one step closer to",
            "reclaiming our stone circle! Now go and talk to")
        it.chatNpc(
            "Kaqemeex and he will introduce you to the wonderful",
            "world of herblore and potion making!")
        it.player.advanceToNextStage(DruidicRitual)
    }
    else {
        it.chatPlayer("No. not yet...")
        it.chatNpc("Well, let me know when you do young 'un.")
    }
}

suspend fun postQuest(it: QueueTask) {
    when(it.options("Option 1", "Option 2", "Option 3", "Option 4")) {
        1 -> {
        }
        2 -> {
        }
        3 -> {
        }
        4 -> {
        }
    }
}