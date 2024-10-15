package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

object ImpCatcher : Quest(
    name = "Imp Catcher",
    startPoint = "Talk to Wizard Mizgog in the Wizards' Tower.",
    requirements = emptyList(),
    requiredItems = "None.",
    combat = "Multiple imps (level 2).",
    rewards = "1 Quest Point, 875 Magic XP and an Amulet of Accuracy.",
    pointReward = 1,
    questId = 160,
    spriteId = 4427,
    slot = 7,
    stages = 2,
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
                            striked("I have spoken to Wizard Mizgog."),
                            "I need to collect some items by killing ${red("Imps")},",
                            "I can find Imps all over the kingdom.",
                            if (player.inventory.contains(
                                    Items.BLACK_BEAD,
                                )
                            ) {
                                striked("1 Black Bead.")
                            } else {
                                red("1 Black Bead.")
                            },
                            if (player.inventory.contains(
                                    Items.RED_BEAD,
                                )
                            ) {
                                striked("1 Red Bead.")
                            } else {
                                red("1 Red Bead.")
                            },
                            if (player.inventory.contains(
                                    Items.WHITE_BEAD,
                                )
                            ) {
                                striked("1 White Bead.")
                            } else {
                                red("1 White Bead.")
                            },
                            if (player.inventory.contains(
                                    Items.YELLOW_BEAD,
                                )
                            ) {
                                striked("1 Yellow Bead")
                            } else {
                                red("1 Yellow Bead.")
                            },
                            if (obtainedAllBeads(
                                    player,
                                )
                            ) {
                                "I have collected all the missing beads and need to"
                            } else {
                                null
                            },
                            if (obtainedAllBeads(player)) "return them to ${red("Wizard Mizgog")}." else null,
                        ),
                )
            2 ->
                QuestStage(
                    objectives =
                        listOf(
                            striked("I have spoken to Wizard Mizgog."),
                            striked("I have collected all the beads."),
                            striked("Wizard Mizgog thanked me for finding his beads and gave"),
                            striked("me and Amulet of Accuracy."),
                            "",
                            questCompleteText,
                        ),
                )
            else -> TODO("Not yet implemented")
        }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        exchangeBeads(player)
        player.addXp(Skills.MAGIC, 875.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.AMULET_OF_ACCURACY,
            rewards = arrayOf("1 Quest Point", "875 Magic XP", "An Amulet of Accuracy"),
        )
    }

    fun beadsCount(player: Player) = beads.count(player.inventory::contains)

    private fun obtainedAllBeads(player: Player) = player.inventory.hasItems(beads.toIntArray())

    fun exchangeBeads(player: Player) {
        beads.forEach(player.inventory::remove)
        player.inventory.add(Items.AMULET_OF_ACCURACY)
    }

    private val beads = listOf(Items.BLACK_BEAD, Items.RED_BEAD, Items.WHITE_BEAD, Items.YELLOW_BEAD)
}
