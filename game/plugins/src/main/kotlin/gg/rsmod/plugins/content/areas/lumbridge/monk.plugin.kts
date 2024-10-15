package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val lostCity = LostCity

on_npc_option(npc = Npcs.MONK_651, option = "talk-to") {
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
    it.chatNpc("None of your business. Get lost.")
}

suspend fun findingShamus(it: QueueTask) {
    it.chatPlayer(
        "Have you found the tree with the leprechaun",
        "yet?",
    )
    it.chatNpc(
        "No, we've looked for ages but haven't.... hey!",
        "Wait a minute! How did you know about that?",
    )
    it.chatPlayer(
        "Thanks for the information!",
    )
    it.chatNpc(
        "... You tricked me. I'm not talking to you anymore.",
    )
}

suspend fun afterLostCity(it: QueueTask) {
    it.chatNpc(
        "I already told you. I'm not talking to you anymore.",
    )
}
