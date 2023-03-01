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

object ImpCatcher : Quest(
    name = "Imp Catcher",
    startPoint = "I can start this quest by talking to Wizard Mizgog in the Wizards' Tower.",
    requirements = emptyList(),
    requiredItems = "None.",
    combat = "Multiple imps (level 2).",
    rewards = "1 Quest Point, 875 Magic XP and an Amulet of Accuracy.",
    pointReward = 1,
    varbit = 160,
    spriteId = 2388,
    slot = 7,
    stages = listOf(
        QuestStage(
            objectives = listOfNotNull(
                striked("I have spoken to Wizard Mizgog."),
                "${blue("I need to collect some items by killing")} ${red("Imps")},",
                "I can find Imps all over the kingdom.",
                red("1 Black Bead."),
                red("1 Red Bead."),
                red("1 White Bead."),
                red("1 Yellow Bead."),
                // TODO: strike when obtained
                // TODO: if all obtained:
//                "I have collected all the missing beads and need to",
//                "return them to ${red("Wizard Mizgog")}."
            ),
            value = 1
        ),
        QuestStage(
            objectives = listOf(
                striked("I have spoken to Wizard Mizgog."),
                striked("I have collected all the beads."),
                striked("Wizard Mizgog thanked me for finding his beads and gave"),
                striked("me and Amulet of Accuracy."),
                questCompleteText
            ),
            value = 2
        )
    ),
) {

    init {
        addQuest(this)
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        exchangeBeads(player)
        player.addXp(Skills.MAGIC, 875.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(this, item = Items.AMULET_OF_ACCURACY, rewards = arrayOf("1 Quest Point", "875 Magic XP", "An Amulet of Accuracy"))
    }

    fun beadsCount(player: Player) = beads.count(player.inventory::contains)

    fun exchangeBeads(player: Player) {
        beads.forEach(player.inventory::remove)
        player.inventory.add(Items.AMULET_OF_ACCURACY)
    }

    private val beads = listOf(Items.BLACK_BEAD, Items.RED_BEAD, Items.WHITE_BEAD, Items.YELLOW_BEAD)
}