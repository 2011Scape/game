package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity

val lostCity = LostCity

on_npc_option(npc = Npcs.ARCHER_649, option = "talk-to") {
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
    it.chatPlayer("Why are you guys standing around here?")
    it.chatNpc("(ahem)... 'Guys'?")
    it.chatPlayer(
        "Um... yeah, sorry about that.",
        "Why are you all standing around out here?",
    )
    it.chatNpc("Well, that's really none of your business.")
}

suspend fun findingShamus(it: QueueTask) {
    it.chatPlayer(
        "So I hear there's a leprechaun around here who can show",
        "me the way to Zanaris?",
    )
    it.chatNpc(
        "... W-what? How did you...?",
        "Wait a minute! How did you know about that?",
    )
    it.chatNpc(
        "No. You're wrong. Now go away.",
    )
}

suspend fun afterLostCity(it: QueueTask) {
    it.chatPlayer(
        "So you didn't find the entrance to Zanaris yet, huh?",
    )
    it.chatNpc(
        "Don't tell me a novice like YOU has found it!",
    )
    it.chatPlayer(
        "Yep. Found it REALLY easily too.",
    )
    it.chatNpc(
        "... I Cannot believe that someone like you could find",
        "the portal when experienced adventurers such as",
        "ourselves could not.",
    )
    it.chatPlayer("Believe what you want. Enjoy your little camp fire.")
}
