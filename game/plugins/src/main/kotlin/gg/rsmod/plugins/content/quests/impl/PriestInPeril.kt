package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.getVarp
import gg.rsmod.plugins.api.ext.setVarp
import gg.rsmod.plugins.content.quests.*

/**
 * @author Harley <https://github.com/HarleyGilpin>
 * Start Date: 5/19/2023
 */

object PriestInPeril : Quest(
    name = "Priest in Peril",
    startPoint = "Speak to King Roald in the Varrock Palace.",
    requirements = emptyList(),
    requiredItems = "50 rune essence or 25 pure essence (the essence must be un-noted), and a bucket",
    combat = "Temple guardian dog (level 30) You cannot use Magic.",
    rewards = "1 Quest Point, Access to Morytania, 1,406 Prayer XP, The Wolfbane Dagger",
    pointReward = 1,
    varbit = 302,
    spriteId = 4213,
    slot = 84,
    stages = 6
) {

    init {
        addQuest(this)
    }

    override fun getObjective(player: Player, stage: Int): QuestStage = when (stage) {
        1 -> QuestStage(
            objectives = listOf(
                "<str>I spoke to King Roald who asked me to investigate why his",
                "<str>friend Priest Drezel has stopped communicating with him.",
                "",
                "<",
                "",
                "${red("Drezel")} lives in a ${red("temple")} to the ${red("east")} of Varrock Palace. I.",
                "should head there and ${red("investigate")} what's happened to him"
            )
        )

        2 -> QuestStage(
            objectives = listOf(
                "<str>I spoke to King Roald who asked me to investigate why his",
                "<str>friend Priest Drezel has stopped communicating with him.",
                "",
                "<",
                "",
                "I headed to the temple where ${red("Drezel")} lives, but it was all",
                "locked shut. I spoke through the locked door to ${red("Drezel")}.",
                "He told me that there was an annoying ${red("dog")} below the",
                "temple, and asked me to ${red("kill")} it."
            )
        )

        3 -> QuestStage(
            objectives = listOf(
                "<str>I spoke to King Roald who asked me to investigate why his",
                "<str>friend Priest Drezel has stopped communicating with him.",
                "<str>I headed to the temple where Drezel lives, but it was all",
                "<str>locked shut. I spoke through the locked door to Drezel.",
                "<str>He told me that there was an annoying dog below the",
                "<str>temple, and asked me to kill it, which I did easily",
                "",
                "I should tell ${red("King Roald")} everything's fine with ${red("Drezel")} now I",
                "have killed that ${red("dog")} for him, and claim my ${red("reward")}"
            )
        )

        4 -> QuestStage(
            objectives = listOf(
                "<str>I spoke to King Roald who asked me to investigate why his",
                "<str>friend Priest Drezel has stopped communicating with him.",
                "<str>I headed to the temple where Drezel lives, but it was all",
                "<str>locked shut. I spoke through the locked door to Drezel.",
                "<str>He told me that there was an annoying dog below the",
                "<str>temple, and asked me to kill it, which I did easily",
                "<str>When I told Roald what I had done, he was furious. The",
                "<str>person who told me to kill the dog wasn't Drezel at all!",
                "",
                "I must return to the ${red("temple")} and find out what happened to",
                "the real ${red("Drezel")}, or the King will have me executed!"
            )
        )

        else -> TODO("Not yet implemented")
    }

    override fun finishQuest(player: Player) {
        player.advanceToNextStage(this)
        player.addXp(Skills.PRAYER, 1406.0)
        player.setVarp(QUEST_POINT_VARP, player.getVarp(QUEST_POINT_VARP).plus(pointReward))
        player.buildQuestFinish(
            this,
            item = Items.WOLFBANE,
            rewards = arrayOf(
                "1 Quest Point",
                "Access to Morytania",
                "1,406 Prayer XP",
                "The Wolfbane Dagger"
            )
        )
    }
}