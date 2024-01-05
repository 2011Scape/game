package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.attr.gaveHarlowBeer
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * @authors: Tank <https://github.com/reeeccoo>,
 *           Harley <https://github.com/HarleyGilpin>
 */
object VampyreSlayer : Quest(
    name = "Vampyre Slayer",
    startPoint = "Speak to Moran in Draynor Village",
    requirements = emptyList(),
    requiredItems = "Beer (or 2 Coins to purchase one).",
    combat = "Count Draynor (Level 34).",
    rewards = "3 Quest Points & 4825 Attack XP.",
    pointReward = 3,
    questId = 6914,
    spriteId = 4532,
    slot = 16,
    stages = 3,
    usesVarbits = true
) {
    init {
        addQuest(this)
    }

    override fun getObjective(player: Player, stage: Int): QuestStage = when (stage) {
        1 -> QuestStage (
            objectives = listOf(
                striked("I talked to Morgan in Draynor Village. He told me that the"),
                striked("Locals are being attacked by a terrifying vampyre."),
                "",
                "I need to speak to ${red("Dr Harlow")}, who can normally be found in",
                "the ${red("Blue Moon Inn")} in ${red("Varrock")}.",
                "",
                "Morgan said I could reach the ${red("Blue Moon Inn")} by leaving",
                "Draynor Village and heading towards Lumbridge. I need to",
                "cross the River Lum and head north to Varrock.",
                "If I enter Varrock from the south, the ${red("Blue Moon Inn")} will be",
                "on my right."
            )
        )

        2 -> {
            if (player.attr.has(gaveHarlowBeer)) {
                QuestStage(
                    objectives = listOf(
                        striked("I talked to Morgan in Draynor Village. He told me that the"),
                        striked("Locals are being attacked by a terrifying vampyre."),
                        "",
                        striked("I need to speak to ${red("Dr Harlow")}, who can normally be found in"),
                        striked("the ${red("Blue Moon Inn")} in ${red("Varrock")}."),
                        "",
                        striked("Morgan said I could reach the ${red("Blue Moon Inn")} by leaving"),
                        striked("Draynor Village and heading towards Lumbridge. I need to"),
                        striked("cross the River Lum and head north to Varrock."),
                        striked("If I enter Varrock from the south, the ${red("Blue Moon Inn")} will be"),
                        striked("on my right."),
                        "",
                        striked("I've spoken to Dr Harlow. He seemed terribly drunk,"),
                        striked("and he kept asking for drinks."),
                        striked("I need to buy ${red("Dr Harlow")} a drink and ask him"),
                        striked("how to kill the vampyre."),
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
            } else {
                QuestStage(
                    objectives = listOf(
                        striked("I've spoken to Dr Harlow. He seemed terribly drunk, and he kept asking for"),
                        striked("drinks."),
                        "I need to buy ${red("Dr Harlow")} a drink and ask him",
                        "how to kill the vampyre.",
                    )
                )
            }
        }

        3 -> QuestStage(
            objectives = listOf(
                striked("Locals are being attacked by a terrifying vampyre."),
                "",
                "I need to speak to ${red("Dr Harlow")}, who can normally be found in",
                "the ${red("Blue Moon Inn")} in ${red("Varrock")}.",
                "",
                "Morgan said I could reach the ${red("Blue Moon Inn")} by leaving",
                "Draynor Village and heading towards Lumbridge. I need to",
                "cross the River Lum and head north to Varrock.",
                "If I enter Varrock from the south, the ${red("Blue Moon Inn")} will be",
                "on my right.",
                questCompleteText
            )
        )

        else -> TODO("Not yet implemented")
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.addXp(Skills.ATTACK, 4825.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(this,
            item = Items.STAKE,
            rewards = arrayOf(
                "3 Quest Points",
                "4825 Attack XP"
            )
        )
    }
}