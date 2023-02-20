package gg.rsmod.plugins.content.quests

import gg.rsmod.game.model.quest.Quest.Companion.getQuest
import gg.rsmod.game.model.quest.Quest.Companion.quests

on_login {
    player.setVarp(MAX_QUEST_POINT_VARP, quests.sumOf { it.pointReward })
    player.setInterfaceEvents(interfaceId = 190, component = 18, from = 0, to = 300, setting = 300)
}

on_button(interfaceId = 190, 18) {
    val quest = getQuest(player.getInteractingSlot()) ?: return@on_button
    if(!player.startedQuest(quest)) {
        player.buildQuestOverview(quest)
        return@on_button
    }
    player.buildQuestStages(quest)
}

on_button(interfaceId = 178, component = 57) {
    player.setComponentHidden(interfaceId = 178, component = 56, hidden = true)
    player.setComponentHidden(interfaceId = 178, component = 58, hidden = false)
}

on_button(interfaceId = 178, component = 30) {
    player.setComponentHidden(interfaceId = 178, component = 64, hidden = true)
    player.setComponentHidden(interfaceId = 178, component = 65, hidden = false)
}