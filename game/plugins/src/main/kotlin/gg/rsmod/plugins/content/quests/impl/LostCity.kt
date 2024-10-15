package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.SkillRequirement
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

object LostCity : Quest(
    name = "Lost City",
    startPoint = "Talk to warrior adventurer in the Lumbridge Swamp.",
    requirements =
        listOf(
            SkillRequirement(Skills.CRAFTING, level = 31),
            SkillRequirement(Skills.WOODCUTTING, level = 36),
        ),
    requiredItems = "None.",
    combat = "Must be able to kill a level 63 Tree spirit.",
    rewards =
        "Access to Zanaris, Ability to wield dragon longswords and dragon daggers" +
            "<br>Ability to craft cosmic runes, Access to Chaeldar, the slayer master (requires level 75 combat)",
    pointReward = 3,
    questId = 147,
    spriteId = -1,
    slot = 68,
    stages = 6,
) {
    init {
        addQuest(this)
    }

    // QuestStages
    const val NOT_STARTED: Int = 0
    const val FINDING_SHAMUS: Int = 1
    const val FOUND_SHAMUS: Int = 2
    const val ENTRANA_DUNGEON: Int = 3
    const val CUT_DRAMEN_TREE: Int = 4
    const val CREATE_DRAMEN_BRANCH: Int = 5
    const val QUEST_COMPLETE: Int = 6

    override fun getObjective(
        player: Player,
        stage: Int,
    ): QuestStage =
        when (stage) {
            1 -> {
                QuestStage(
                    objectives =
                        listOf(
                            "According to one of the adventurers in Lumbridge Swamp",
                            "the entrance to Zanaris is somewhere around there.",
                            "",
                        ),
                )
            }

            2 -> {
                QuestStage(
                    objectives =
                        listOf(
                            striked("According to one of the adventurers in Lumbridge Swamp"),
                            striked("the entrance to Zanaris is somewhere around there."),
                            "",
                            "I found a Leprechaun hiding in a nearby tree.",
                            "He told me that the entrance to Zanaris is in the shed in",
                            "Lumbridge swamp but only if I am carrying a Dramen Staff.",
                        ),
                )
            }

            3 -> {
                QuestStage(
                    objectives =
                        listOf(
                            striked("According to one of the adventurers in Lumbridge Swamp"),
                            striked("the entrance to Zanaris is somewhere around there."),
                            "",
                            striked("I found a Leprechaun hiding in a nearby tree."),
                            striked("He told me that the entrance to Zanaris is in the shed in"),
                            striked("Lumbridge swamp but only if I am carrying a Dramen Staff."),
                            "",
                            "The Dramen Tree was guarded by a powerful Tree Spirit.", // TODO: Fix this one too
                        ),
                )
            }
            4 -> {
                QuestStage(
                    objectives =
                        listOf(
                            striked("According to one of the adventurers in Lumbridge Swamp"),
                            striked("the entrance to Zanaris is somewhere around there."),
                            "",
                            striked("I found a Leprechaun hiding in a nearby tree."),
                            striked("He told me that the entrance to Zanaris is in the shed in"),
                            striked("Lumbridge swamp but only if I am carrying a Dramen Staff."),
                            "",
                            "The Dramen Tree was guarded by a powerful Tree Spirit.", // TODO: Fix this stage's text
                            "I cut a branch from the tree and crafted a Dramen Staff.",
                        ),
                )
            }
            5 -> {
                QuestStage(
                    objectives =
                        listOf(
                            striked("According to one of the adventurers in Lumbridge Swamp"),
                            striked("the entrance to Zanaris is somewhere around there."),
                            "",
                            striked("I found a Leprechaun hiding in a nearby tree."),
                            striked("He told me that the entrance to Zanaris is in the shed in"),
                            striked("Lumbridge swamp but only if I am carrying a Dramen Staff."),
                            "",
                            striked("The Dramen Tree was guarded by a powerful Tree Spirit."),
                            striked("I cut a branch from the tree and crafted a Dramen Staff."),
                            "",
                            "I should enter Zanaris by going to the shed in Lumbridge",
                            "Swamp while keeping the Dramen staff with me.",
                        ),
                )
            }
            6 -> {
                QuestStage(
                    objectives =
                        listOf(
                            striked("According to one of the adventurers in Lumbridge Swamp"),
                            striked("the entrance to Zanaris is somewhere around there."),
                            "",
                            striked("I found a Leprechaun hiding in a nearby tree."),
                            striked("He told me that the entrance to Zanaris is in the shed in"),
                            striked("Lumbridge swamp but only if I am carrying a Dramen Staff."),
                            "",
                            striked("The Dramen Tree was guarded by a powerful Tree Spirit."),
                            striked("I cut a branch from the tree and crafted a Dramen Staff."),
                            striked("With the mystical Dramen Staff in my possession I was"),
                            striked("able to enter ${red("Zanaris")} through the shed in ${red("Lumbridge")}"),
                            striked("${red("swamp")}."),
                            "",
                            "<col=ff0000>    QUEST COMPLETE!    ",
                        ),
                )
            }
            else -> {
                QuestStage(
                    objectives =
                        listOf(
                            "Legends tell of a magical lost city hidden in the swamps.",
                            "Many adventurers have tried to find this city, but it is",
                            "proving difficult. Can you unlock the secrets of the city",
                            "of Zanaris?",
                            "",
                            "I can start this quest by talking to a warrior in Lumbridge Swamp.",
                            "You will need to puzzle through the dialogues to start the quest.",
                            "Come back here to see when to look for the dramen branches.",
                            "",
                            "~~Requirements~~",
                            "31 Crafting",
                            "36 Woodcutting",
                            "",
                        ),
                )
            }
        }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.DRAMEN_STAFF,
            rewards =
                arrayOf(
                    "3 Quest Points",
                    "Access to Zanaris",
                ),
        )
    }
}
