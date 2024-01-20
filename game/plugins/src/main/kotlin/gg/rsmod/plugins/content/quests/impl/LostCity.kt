package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.attr.gaveHarlowBeer
import gg.rsmod.game.model.attr.killedCountDraynor
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
    requirements = listOf(
            SkillRequirement(Skills.CRAFTING, level = 31),
            SkillRequirement(Skills.WOODCUTTING, level = 36),
            ),
    requiredItems = "None.",
    combat = "Must be able to kill a level 63 Tree spirit.",
    rewards = "Access to Zanaris<br>Ability to wield dragon longswords and dragon daggers" +
            "<br>Ability to craft cosmic runes<br>Access to Chaeldar, the slayer master (requires level 75 combat)",
    pointReward = 3,
    questId = 147,
    spriteId = 0,
    slot = 68,
    stages = 6
) {
    init {
        addQuest(this)
    }

    //QuestStages
    val NOT_STARTED: Int = 0
    val FINDING_SHAMUS: Int = 1
    val ENTRANA_ARRIVAL: Int = 2
    val CHOP_DRAMEN_BRANCH: Int = 3
    val CREATE_DRAMEN_STAFF: Int = 4
    val ENTER_ZANARIS: Int = 5
    val QUEST_COMPLETE: Int = 6


    override fun getObjective(player: Player, stage: Int): QuestStage = when (stage) {
        1 -> QuestStage (
            objectives = listOf(
                "The warrior in Lumbridge Swamp said I am to find a",
                "leprechaun in the area by trying to chop down trees?",
                "",
            )
        )

        2 -> {
            if (player.attr.has(gaveHarlowBeer) && !player.attr.has(killedCountDraynor)) {
                QuestStage(
                    objectives = listOf(
                        striked("I talked to Morgan in Draynor Village. He told me that the"),
                        striked("Locals are being attacked by a terrifying vampyre."),
                        striked("I have spoken to Dr Harlow. He seemed terribly drunk, and"),
                        striked("he kept asking for drinks."),
                        "",
                        "${red("Dr Harlow")} told me that, to succeed, I'll need the following:",
                        striked("A stake to kill the vampyre once I've finished fighting it."),
                        striked("A stake hammer to drive the stake deep into the"),
                        striked("vampyre's chest."),
                        "Some ${red("garlic")} to weaken the ${red("vampire")}",
                        "",
                        "When I'm ready, I should go to ${red("Draynor Manor")}, north of",
                        "Draynor, to kill the ${red("vampyre")} that's living in the basement.",
                        "Dr Harlow reminded me again and again to be prepared for",
                        "a tough fight."
                    )
                )
            }
            if (player.attr.has(gaveHarlowBeer) && player.attr.has(killedCountDraynor)) {
                QuestStage(
                    objectives = listOf(
                        striked("I talked to Morgan in Draynor Village. He told me that the"),
                        striked("Locals are being attacked by a terrifying vampyre."),
                        striked("I have spoken to Dr Harlow. He seemed terribly drunk, and"),
                        striked("he kept asking for drinks."),
                        "",
                        "I killed the vampyre! I should tell ${red("Morgan")} that his village is",
                        "safe once again.",
                    )
                )
            } else {
                QuestStage(
                    objectives = listOf(
                        striked("I talked to Morgan in Draynor Village. He told me that the"),
                        striked("Locals are being attacked by a terrifying vampyre."),
                        striked("I have spoken to Dr Harlow. He seemed terribly drunk, and"),
                        striked("he kept asking for drinks."),
                        "",
                        "I need to buy ${red("Dr Harlow")} a drink and ask him",
                        "how to kill the vampyre.",
                    )
                )
            }
        }

        else -> QuestStage(
            objectives = listOf(
                striked("I talked to Morgan in Draynor Village. He told me that the"),
                striked("Locals are being attacked by a terrifying vampyre."),
                striked("I have spoken to Dr Harlow. He seemed terribly drunk, and"),
                striked("he kept asking for drinks."),
                "",
                "I have killed the vampyre, Count Draynor.",
                "Draynor Village is now safe!",
                questCompleteText
            )
        )
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(this,
            item = Items.DRAMEN_STAFF,
            rewards = arrayOf(
                "3 Quest Points",
                "Access to Zanaris",
                    "The ability to wield a dragon longsword or dagger with level 60 attack."
            )
        )
    }
}