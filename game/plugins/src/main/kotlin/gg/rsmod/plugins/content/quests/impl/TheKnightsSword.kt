package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.SkillRequirement
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * The Knight's Sword quest
 * @author Kevin Senez <ksenez94@gmail.com>
 */
object TheKnightsSword : Quest(
    name = "The Knight's Sword",
    startPoint = "I can start this quest by speaking to the Squire in the courtyard of the White Knights' Castle in southern Falador.",
    requirements = listOf(SkillRequirement(Skills.MINING, level = 10)),
    requiredItems = "1 Redberry pie, 2 Iron bars and a Pickaxe.",
    combat = "Ability to evade level 54 Ice Warriors and level 53 Ice Giants.",
    rewards = "1 Quest Point and 12,725 Smithing XP.",
    pointReward = 1,
    questId = 122,
    spriteId = 4434,
    slot = 8,
    stages = 7,
) {
    init {
        addQuest(this)
    }

    override fun getObjective(
        player: Player,
        stage: Int,
    ): QuestStage =
        when (stage) {
            1 ->
                QuestStage(
                    objectives =
                        listOfNotNull(
                            striked("I told the Squire I would help him to replace the sword he had lost."),
                            striked("It could only be made by an Imcando Dwarf."),
                            "The squire suggests I speak to ${red("Reldo")} in the ${red("Varrock Palace")}",
                            "${red("Library")} for information about the ${red("Imcando Dwarves.")}",
                        ),
                )

            2 ->
                QuestStage(
                    objectives =
                        listOfNotNull(
                            striked("I told the Squire I would help him to replace the sword he"),
                            striked("had lost. It could only be made by an Imcando Dwarf."),
                            "Reldo couldn't give me much information about the",
                            "${red("Imcando")} except a few live on the ${red("southern peninsula of")}",
                            "${red("Asgarnia")}, they dislike strangers, and LOVE ${red("redberry pies.")}",
                        ),
                )

            3, 4, 5, 6 ->
                QuestStage(
                    objectives =
                        listOfNotNull(
                            striked("I told the Squire I would help him to replace the sword he had lost."),
                            striked("It could only be made by an Imcando Dwarf."),
                            striked("I found an Imcando Dwarf named Thurgo thanks to information"),
                            striked("provided by Reldo. He wasn't very talkative until I gave"),
                            striked("him a Redberry pie, which he gobbled up."),
                        ) + getModifiedStrings(player, stage),
                )

            7 ->
                QuestStage(
                    objectives =
                        listOfNotNull(
                            striked("Thurgo needed a picture of the sword before he could"),
                            striked("start work on a replacement. I took him a portrait of it."),
                            striked("After bringing Thurgo two iron bars and some blurite ore"),
                            striked("he made me a fine replica of Sir Vyvin's Sword, which I"),
                            striked("returned to the Squire for a reward."),
                            "",
                            questCompleteText,
                        ),
                )

            else -> throw Exception("Unhandled Quest Stage: $stage for quest: The Knight's Sword")
        }

    private fun getModifiedStrings(
        player: Player,
        stage: Int,
    ): List<String> {
        val strings = mutableListOf<String>()
        when (stage) {
            3 -> {
                strings.addAll(
                    listOf(
                        "He will help me now I have gained his trust through ${red("pie")}.",
                        "I need to ask him about the sword.",
                    ),
                )
            }

            4 -> {
                strings.addAll(
                    listOf(
                        "${red("Thurgo")} needs a ${red("picture of the sword")} before he can help.",
                        "I should probably ask the ${red("Squire")} about obtaining one.",
                    ),
                )
            }

            5 -> {
                if (!player.inventory.contains(Items.PORTRAIT)) {
                    strings.addAll(
                        listOf(
                            "${red("Thurgo")} needs a ${red("picture of the sword")} before he can help.",
                            "The Squire told me about a ${red("portrait")} of Sir Vyvin's father which",
                            "has a ${red("picture of the sword")} in ${red("Sir Vyvin's room")}.",
                        ),
                    )
                } else {
                    strings.addAll(
                        listOf(
                            "${red("Thurgo")} needs a ${red("picture of the sword")} before he can help.",
                            "I now have a picture of the ${red("Knight's Sword")}. I should take it",
                            "to ${red("Thurgo")} so that he can duplicate it.",
                        ),
                    )
                }
            }

            6 -> {
                if (!player.inventory.contains(Items.BLURITE_SWORD)) {
                    strings.addAll(
                        listOf(
                            "According to ${red("Thurgo")}, to make a ${red("replica sword")} he",
                            "will need ${red("two Iron bars")} and some ${red("Blurite Ore.")}.",
                            "${red("Blurite Ore")} can only be found ${red("deep in the caves below")}",
                            "${red("Thurgo's house")}.",
                        ),
                    )
                } else {
                    strings.addAll(
                        listOf(
                            "Thurgo has now smithed me a ${red("replica of Sir Vyvin's sword")}.",
                            "I should return it to the ${red("Squire")} for my ${red("reward")}.",
                        ),
                    )
                }
            }
        }

        return strings
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.addXp(skill = Skills.SMITHING, xp = 12725.0)
        player.inventory.remove(Items.BLURITE_SWORD)
        player.buildQuestFinish(
            this,
            item = Items.BLURITE_SWORD,
            rewards = arrayOf("1 Quest Point", "12,725 Smithing XP"),
        )
    }
}
