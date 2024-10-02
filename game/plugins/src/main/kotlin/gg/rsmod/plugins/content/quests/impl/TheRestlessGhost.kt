package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * @author Tank <Tank#4733>
 */

object TheRestlessGhost : Quest( // Adds Quest Info
    name = "The Restless Ghost",
    startPoint = "Speak to Father Aereck in the chapel south-east of Lumbridge Castle",
    requirements = emptyList(),
    requiredItems = "Armour to survive a level 11 skeleton warlock attack.",
    combat = "Face a level 11 skeleton warlock.",
    rewards = "1 Quest Point, 125 Prayer XP and five ancient bones that each give 200 Prayer XP.",
    pointReward = 1,
    questId = 107,
    spriteId = 2381,
    slot = 11,
    stages = 5,
) {
    init { // inits the quest to the server
        addQuest(this)
    }

    override fun getObjective(
        player: Player,
        stage: Int,
    ): QuestStage =
        when (stage) {
            1 ->
                QuestStage( // Quest Journal Stage 1
                    objectives =
                        listOf(
                            striked("I've not started this quest yet."),
                            striked("I can start this quest by speaking to Father Aereck in the"),
                            striked("church east of Lumbridge castle, in the centre of"),
                            striked("Lumbridge."),
                            "",
                            "I should find ${red("Father Urhney")}, who is an expert on ${red("ghosts.")}",
                            "He lives in a ${red("shack")}, in the south of ${red("Lumbridge Swamp")}, near",
                            "the coastline.",
                        ),
                )

            2 ->
                QuestStage(
                    objectives =
                        listOf(
                            striked("I should find Father Urhney, who is an expert on ghosts."),
                            striked("He lives in a shack in the south of Lumbridge Swamp, near"),
                            striked("the coastline."),
                            "",
                            "I should talk to the ${red("ghost")} in the crypt south of Lumbridge",
                            "church to find out why it is haunting the ${red("graveyard.")} I must",
                            "make sure to wear my ghostspeak amulet when doing so.",
                        ),
                )

            3 ->
                QuestStage(
                    objectives =
                        listOf(
                            striked("I should talk to the ghost in the crypt south of"),
                            striked("Lumbridge church to find out why it is haunting the"),
                            striked("graveyard. I must make sure to wear my ghostspeak"),
                            striked("amulet when doing so."),
                            "",
                            "I should go and search the ${red("Mining Spot")} on the coast ${red("south")}",
                            "${red("of Lumbridge")} for the ${red("ghost's skull.")}",
                        ),
                )

            4 ->
                QuestStage(
                    objectives =
                        listOf(
                            striked("I should talk to the ghost in the crypt south of"),
                            striked("Lumbridge church to find out why it is haunting the"),
                            striked("graveyard. I must make sure to wear my ghostspeak"),
                            striked("amulet when doing so."),
                            "",
                            if (player.inventory.contains(Items.MUDDY_SKULL)) {
                                "I should take the ${red("skull")} back to the ${red("ghost")} in the"
                            } else {
                                "I should go and search the ${red("Mining Spot")} on the coast ${red("south")}"
                            },
                            if (player.inventory.contains(Items.MUDDY_SKULL)) {
                                "graveyard so it can rest in peace."
                            } else {
                                "${red("of Lumbridge")} for the ${red("ghost's skull.")}"
                            },
                        ),
                )

            5 ->
                QuestStage(
                    objectives =
                        listOf(
                            striked("I should take the skull back to the ghost in the"),
                            striked("graveyard so it can rest in peace."),
                            questCompleteText,
                        ),
                )

            else -> TODO()
        }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.addXp(Skills.PRAYER, 125.0)
        player.inventory.add(Items.ANCIENT_BONES, 5)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.SKULL,
            rewards = arrayOf("1 Quest Point", "125 Prayer XP and five", "ancient bones"),
        )
    }
}
