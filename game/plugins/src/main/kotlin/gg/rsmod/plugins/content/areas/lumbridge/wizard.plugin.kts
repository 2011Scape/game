package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val lostCity = LostCity

on_npc_option(npc = Npcs.WIZARD_652, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(lostCity)) {
            LostCity.NOT_STARTED -> beforeLostCity(this)
            LostCity.FINDING_SHAMUS -> findingShamus(this)
            LostCity.FOUND_SHAMUS -> afterLostCity(this)
            LostCity.ENTRANA_DUNGEON, LostCity.CUT_DRAMEN_TREE, LostCity.CREATE_DRAMEN_BRANCH -> afterLostCity(this)
            LostCity.QUEST_COMPLETE -> afterLostCity(this)
        }
    }
}

suspend fun beforeLostCity(it: QueueTask) {
    it.chatPlayer("Why are all of you standing around here?")
    it.chatNpc(
        "Hahaha, you dare talk to a mighty wizard such as myself?",
        "I bet you can't even cast Air Strike yet, amateur!",
    )
    it.chatPlayer("... You're an idiot.")
}

suspend fun findingShamus(it: QueueTask) {
    it.chatPlayer(
        "Found that leprechaun yet?",
    )
    it.chatNpc(
        "Hahaha! Go away, amateur! You're not worthy",
        "of joining our great group!",
    )
    it.chatPlayer(
        "... Right.",
    )
}

suspend fun afterLostCity(it: QueueTask) {
    it.chatNpc(
        "Hahaha, you're such an amateur!",
    )
    it.chatNpc(
        "Go and play with some cabbage, amateur!",
    )
    it.chatPlayer("... Right.")
}
