package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.quests.Quest
import gg.rsmod.plugins.content.quests.QuestStage

object PiratesTreasure : Quest(
    name = "Pirate's Treasure",
    startPoint = "Talk to Redbeard Frank just south of The Rusty Anchor pub in Port Sarim.",
    requirements = emptyList(),
    requiredItems = "White apron, 60 coins (or an activated ring of Charos and 30 coins)",
    combat = "Optionally defeat a level 4 gardener",
    rewards =
        "2 Quest Points, " +
        "One-Eyed Hector's treasure casket (containing 450 coins, an emerald and a gold ring)",
    pointReward = 1,
    questId = 71,
    spriteId = 4430,
    slot = 9,
    stages = 4
) {
    init {
        addQuest(this)
    }

    override fun getObjective(player: Player, stage: Int): QuestStage =
        when (stage) {
            1 -> QuestStage(objectives = listOf(
                "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the location",
                "of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                "I need to go to ${red("Karamja")} and buy some ${red("rum")}. I hope it is",
                "not too expensive."
            ))

            else -> QuestStage(objectives = listOf(
                ""
            ))
        }

    override fun finishQuest(player: Player) {
        TODO("Not yet implemented")
    }

    fun red(text: String) = "<col=8A0808>$text</col>"
}
