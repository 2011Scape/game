package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.quest.Quest
import gg.rsmod.game.model.quest.QuestStage
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarbit
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.QUEST_POINT_VARP
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.buildQuestFinish

/**
 * @author Alycia <https://github.com/alycii>
 */
object DoricsQuest : Quest(
    name = "Doric's Quest",
    startPoint = "Talk to Doric at his home north of Falador.",
    requirements = emptyList(),
    requiredItems = "None, but a Mining level of 15 would be useful.",
    combat = "None.",
    rewards = "1 Quest Point, 1,300 Mining XP, 180 Coins, use of Doric's anvils.",
    pointReward = 1,
    varbit = 31,
    spriteId = 4431,
    slot = 3,
    stages = listOf(
        QuestStage(
            objectives = listOf(
                "I have spoken to ${red("Doric")}",
                "I need to collect some items and bring them to ${red("Doric")}",
                "",
                red("6 Clay"),
                red("4 Copper Ore"),
                red("2 Iron Ore"),
            ),
            value = 1
        ),
        QuestStage(
            objectives = listOf(
                striked(text = "I have spoken to Doric"),
                striked(text = "I have collected some Clay, Copper Ore, and Iron Ore"),
                striked(text = "Doric rewarded me for all my hard work"),
                striked(text = "I can now use Doric's Anvils whenever I want"),
                questCompleteText
            ),
            value = 100
        )
    ),
) {

    init {
        addQuest(this)
    }

    override fun finishQuest(player: Player) {
        player.setVarp(varbit, 100)
        player.inventory.remove(Items.CLAY, 6)
        player.inventory.remove(Items.COPPER_ORE, 4)
        player.inventory.remove(Items.IRON_ORE, 2)
        player.inventory.add(Items.COINS_995, 180)
        player.addXp(Skills.MINING, 1300.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(this, item = Items.STEEL_PICKAXE, rewards = arrayOf("1 Quest Point", "1300 Mining XP", "180 Coins", "Use of Doric's Anvils"))
    }
}