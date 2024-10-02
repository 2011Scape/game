package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

object DruidicRitual : Quest( // sets up the quest info.
    name = "Druidic Ritual",
    startPoint = "Speak to Kaqemeex in the stone circle north of Taverley",
    requirements = emptyList(),
    requiredItems = "Raw bear meat, raw rat meat, raw beef, raw chicken.",
    combat = "None.",
    rewards = "4 Quest Points, introduction to the Herblore skill and 250 Herblore XP.",
    pointReward = 4,
    questId = 80,
    spriteId = 4432,
    slot = 33,
    stages = 4,
) {
    init { // inits the quest in the server
        addQuest(this)
    }

    override fun getObjective(
        player: Player,
        stage: Int,
    ): QuestStage =
        when (stage) {
            1 ->
                QuestStage( // quest journal stage 1
                    objectives =
                        listOf(
                            "I told ${red("Kaqemeex")} I would help them prepare their ceremony.",
                            "I should speak to ${red("Sanfew")} in the village to the south.",
                        ),
                )

            2 ->
                QuestStage( // quest journal stage 2
                    objectives =
                        listOf(
                            "I told ${red("Kaqemeex")} I would help them prepare their ceremony.",
                            "${red("Sanfew")} told me for the ritual they would need me to place",
                            "raw bear meat, raw chicken, raw rat meat, and raw beef in",
                            "the ${red("Cauldron of Thunder")} in the dungeon South of ${red("Taverley")}.",
                        ),
                )

            3 ->
                QuestStage( // quest journal stage 3
                    objectives =
                        listOf(
                            "I told ${red("Kaqemeex")} I would help them prepare their ceremony.",
                            "The ceremony required various meats being placed in the",
                            "${red("Cauldron of Thunder")}. I did this and gave them to ${red("Sanfew")}.",
                            "I should speak to ${red("Kaqemeex")} again and claim my reward.",
                        ),
                )

            4 ->
                QuestStage(
                    objectives =
                        listOf( // finished quest text
                            striked(text = "I told ${red("Kaqemeex")} I would help them prepare their ceremony."),
                            striked("The ceremony required various meats being placed in the"),
                            striked("${red("Cauldron of Thunder")}. I did this and gave them to ${red("Sanfew")}."),
                            striked("${red("Kaqemeex")} then taught me the basics of the skill Herblore."),
                            "",
                            questCompleteText,
                        ),
                )

            else -> TODO()
        }

    override fun finishQuest(player: Player) { // what happens when you finish the quest
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward)) // adds quest points
        player.addXp(Skills.HERBLORE, 250.0) // adds xp for herblore
        player.buildQuestFinish(
            this,
            item = Items.CLEAN_GUAM,
            rewards = arrayOf("4 Quest Points", "250 Herblore XP", "Access to Herblore skill"),
        ) // shows the finish quest dialogue.
    }
}
