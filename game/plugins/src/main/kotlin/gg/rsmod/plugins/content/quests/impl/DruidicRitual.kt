package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.quest.Quest
import gg.rsmod.game.model.quest.QuestStage
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.QUEST_POINT_VARP
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.buildQuestFinish

object DruidicRitual : Quest(
    name = "Druidic Ritual",
    startPoint = "Speak to Kaqemeex in the stone circle north of Taverley",
    requirements = emptyList(),
    requiredItems = "Raw bear meat, raw rat meat, raw beef, raw chicken.",
    combat = "None.",
    rewards = "4 Quest Points, introduction to the Herblore skill and 250 Herblore XP.",
    pointReward = 4,
    varbit = 80,
    spriteId = 4432,
    slot = 33,
    stages = listOf(
        QuestStage(
            objectives = listOf(
                "I told Kaqemeex I would help them prepare their ceremony.",
                "I should speak to Sanfew in the village to the south."
            ),
            value = 1
        ),
        QuestStage(
            objectives = listOf(
                "I told Kaqemeex I would help them prepare their ceremony.",
                "Sanfew told me for the ritual they would need me to place",
                "raw bear meat, raw chicken, raw rat meat, and raw beef in",
                "the Cauldron of Thunder in the dungeon South of Taverley"
            ),
            value = 2
        ),
        QuestStage(
            objectives = listOf(
                "I told Kaqemeex I would help them prepare their ceremony.",
                "The ceremony required various meats being placed in the",
                "Cauldron of Thunder. I did this and gave them to Sanfew.",
                "I should speak to Kaqemeex again and claim my reward."
            ),
            value = 3
        ),
        QuestStage(
            objectives = listOf(
                "I told Kaqemeex I would help them prepare their ceremony.",
                "The ceremony required various meats being placed in the",
                "Cauldron of Thunder. I did this and gave them to Sanfew.",
                "Kaqemeex then taught me the basics of the skill Herblore.",
                "QUEST COMPLETE!"
            ),
            value = 4
        ),
    ),
) {

    init {
        addQuest(this)
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.addXp(Skills.HERBLORE, 250.0)
        player.buildQuestFinish(this, item = Items.CLEAN_GUAM, rewards = arrayOf("4 Quest Points", "250 Herblore XP", "Access to Herblore skill"))
    }
}