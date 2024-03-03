package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

object WolfWhistle : Quest( //sets up the quest info.
    name = "Wolf Whistle",
    startPoint = "I can begin this quest by talking to Pikkupstix, who lives in Taverley",
    requirements = emptyList(),
    requiredItems = "2 wolf bones",
    combat = "None.",
    rewards = "Something",
    pointReward = 4,
    questId = 4302,
    spriteId = 4432,
    slot = 131,
    stages = 110,
    usesVarbits = true
) {

    init { //inits the quest in the server
        addQuest(this)
    }

    override fun getObjective(player: Player, stage: Int): QuestStage = when (stage) {
        1 -> QuestStage( //quest journal stage 1
            objectives = listOf(
                "I started the quest"
            )
        )

        else -> TODO()
    }

    override fun finishQuest(player: Player) { //what happens when you finish the quest
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward)) //adds quest points
        player.addXp(Skills.HERBLORE, 250.0) //adds xp for herblore
        player.buildQuestFinish(
            this,
            item = Items.CLEAN_GUAM,
            rewards = arrayOf("4 Quest Points", "250 Herblore XP", "Access to Herblore skill")
        ) //shows the finish quest dialogue.
    }
}