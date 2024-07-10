package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * @author Alycia <https://github.com/alycii>
 */

object RuneMysteries : Quest(
    name = "Rune Mysteries",
    startPoint = "Speak to Duke Horacio, upstairs in Lumbridge Castle.",
    requirements = emptyList(),
    requiredItems = "None.",
    combat = "None.",
    rewards = "1 Quest Point, introduction to the Runecrafting skill and an air talisman.",
    pointReward = 1,
    questId = 63,
    spriteId = 2378,
    slot = 13,
    stages = 6,
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
                        listOf(
                            "I spoke to <col=8A0808>Duke Horacio</col> in <col=8A0808>Lumbridge Castle</col>. He told me",
                            "that he'd found a <col=8A0808>Strange Talisman</col> in the <col=8A0808>Castle</col> which",
                            "might be of use to the <col=8A0808>Order of Wizards</col> at the <col=8A0808>Wizards'</col>",
                            "<col=8A0808>Tower</col>. He asked me to take it there and give it to a wizard",
                            "called <col=8A0808>Sedridor</col>. I can find the <col=8A0808>Wizards' Tower</col> south west of",
                            "<col=8A0808>Lumbridge</col>, across the bridge from <col=8A0808>Draynor Village.</col>",
                        ),
                )
            3 ->
                QuestStage(
                    objectives =
                        listOf(
                            "I delivered the <col=8A0808>Strange Talisman</col> to <col=8A0808>Sedridor</col> in the",
                            "basement of the <col=8A0808>Wizards' Tower</col>. He believes it might be",
                            "key to discovering a <col=8A0808>Teleportation Incantation</col> to the lost",
                            "<col=8A0808>Rune Essence Mine</col>. He asked me to help confirm this by",
                            "delivering a <col=8A0808>Package</col> to <col=8A0808>Aubury</col>, an expert on",
                            "<col=8A0808>Runecrafting</col>. I can find him in his <col=8A0808>Rune Shop</col> in south east",
                            "<col=8A0808>Varrock</col>.",
                        ),
                )
            5 ->
                QuestStage(
                    objectives =
                        listOf(
                            "I delivered the <col=8A0808>Package</col> to <col=8A0808>Aubury</col> at his <col=8A0808>Rune Shop</col> in",
                            "south east <col=8A0808>Varrock</col>. He confirmed <col=8A0808>Sedridor's</col> suspicions",
                            "and asked me to take some <col=8A0808>Research Notes</col> back to him. I",
                            "can find <col=8A0808>Sedridor</col> in the basement of the <col=8A0808>Wizards' Tower</col>.",
                        ),
                )
            6 ->
                QuestStage(
                    objectives =
                        listOf(
                            "<str>I spoke to Duke Horacio and he showed me a strange",
                            "<str>talisman that had been found by one of his subjects.",
                            "<str>I agreed to take it to the Wizards' Tower, South West of",
                            "<str>Lumbridge for further examination by the wizards.",
                            "<str>I gave the Talisman to the Head of the Tower and",
                            "<str>agreed to help him with his research into rune stones.",
                            "<str>I took the research package to Varrock and delivered it.",
                            "<str>Aubury was interested in the research package and gave",
                            "<str>me his own research notes to deliver to Sedridor.",
                            "<str>I brought Sedridor the research notes that Aubury had",
                            "<str>compiled so that he could compare their research. They",
                            "<str>They discovered that it was now possible to create new rune",
                            "<str>stones, a skill that had been thought lost forever.",
                            "<str>In return for all of my help they taught me how to do this,",
                            "<str>and will teleport me to mine blank runes anytime.",
                            "",
                            questCompleteText,
                        ),
                )
            else -> QuestStage(objectives = emptyList())
        }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.inventory.remove(Items.RESEARCH_NOTES)
        player.inventory.add(Items.AIR_TALISMAN, 1)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.AIR_TALISMAN,
            rewards =
                arrayOf(
                    "1 Quest Point",
                    "Introduction to the",
                    "Runecrafting skill and rune",
                    "essence mine",
                    "Air talisman",
                ),
        )
    }
}
