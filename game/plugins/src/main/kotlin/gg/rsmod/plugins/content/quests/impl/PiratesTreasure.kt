package gg.rsmod.plugins.content.quests.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.areas.karamja.Luthas
import gg.rsmod.plugins.content.areas.portsarim.Wydin
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
            1 -> {
                val employedByWydin = player.attr.has(Wydin.EMPLOYED_ATTR) && player.attr[Wydin.EMPLOYED_ATTR]!!
                val employedByLuthas = player.attr.has(Luthas.EMPLOYED_ATTR) && player.attr[Luthas.EMPLOYED_ATTR]!!
                val stashedAtWydin = player.attr.has(Wydin.RUM_STASHED_ATTR) && player.attr[Wydin.RUM_STASHED_ATTR]!!
                val stashedAtLuthas = player.attr.has(Luthas.RUM_STASHED_ATTR) && player.attr[Luthas.RUM_STASHED_ATTR]!!
                if (employedByWydin) {
                    if (stashedAtWydin) {
                        QuestStage(objectives = listOf(
                            "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                            "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                            str("I have taken employment on the banana plantation, as"),
                            str("the Customs Officers might not notice the rum if it"),
                            str("is covered in bananas."),
                            str("I have hidden my rum in the crate. I should fill it"),
                            str("with bananas and speak to Luthas to have it shipped over."),
                            str("I have spoken to Luthas, and the crate has been shipped"),
                            str("to Wydin's store in Port Sarim. Now all I have to do is"),
                            str("get to it..."),
                            "I have taken a job at ${red("Wydin's store")}. I now have access to the back",
                            "room of his shop where the ${red("rum")} is."
                        ))
                    }
                    else {
                        if (player.inventory.contains(Items.KARAMJAN_RUM)) {
                            QuestStage(objectives = listOf(
                                "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                "I have the ${red("Karamjan Rum")}. I should take it to",
                                "${red("Redbeard Frank")}."
                            ))
                        }
                        else {
                            QuestStage(objectives = listOf(
                                "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                "I had some ${red("rum")} but I seem to have lost it. I will",
                                "need to smuggle some more off ${red("Karamja")}."
                            ))
                        }
                    }
                }
                else {
                    if (stashedAtWydin) {
                        QuestStage(objectives = listOf(
                            "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                            "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                            str("I have taken employment on the banana plantation, as the Customs"),
                            str("Officers might not notice the rum if it is covered in bananas."),
                            str("I have hidden my rum in the crate and filled it with bananas."),
                            str("I should speak to Luthas and have it shipped over."),
                            "I have spoken to ${red("Luthas")}, and the crate has been shipped to ${red("Wydin's")}",
                            "${red("store")} in ${red("Port Sarim")}. Now all I have to do is get to it..."
                        ))
                    }
                    else {
                        if (stashedAtLuthas) {
                            val cratedBananas = player.attr.getOrDefault(Luthas.CRATED_BANANAS_ATTR, 0).toInt()
                            if (cratedBananas == 10) {
                                QuestStage(objectives = listOf(
                                    "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                    "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                    "I have taken employment on the ${red("banana plantation")}, as the ${red("Customs")}",
                                    "${red("Officers")} might not notice the ${red("rum")} if it is covered in ${red
                                        ("bananas")}.",
                                    "I have hidden my ${red("rum")} in the crate and filled it with",
                                    "${red("bananas")}. I should speak to ${red("Luthas")} and have it",
                                    "shipped over."
                                ))
                            }
                            else {
                                QuestStage(objectives = listOf(
                                    "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                    "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                    "I have taken employment on the ${red("banana plantation")}, as the ${red("Customs")}",
                                    "${red("Officers")} might not notice the ${red("rum")} if it is covered in ${red
                                        ("bananas")}.",
                                    "I have hidden my ${red("rum")} in the crate but still need to fill it",
                                    "with ${red("bananas")} before I speak to ${red("Luthas")} and have it",
                                    "shipped over."
                                ))
                            }
                        }
                        else {
                            if (employedByLuthas) {
                                if (player.inventory.contains(Items.KARAMJAN_RUM)) {
                                    QuestStage(objectives = listOf(
                                        "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                        "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                        "I have taken employment on the ${red("banana plantation")}, as the ${red("Customs")}",
                                        "${red("Officers")} might not notice the ${red("rum")} if it is covered in ${red
                                            ("bananas")}.",
                                        "I'm sure I will be able to hide my ${red("rum")} in the next crate destined",
                                        "for ${red("Wydin's store")}."
                                    ))
                                }
                                else {
                                    QuestStage(objectives = listOf(
                                        "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                        "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                        "I have taken employment on the ${red("banana plantation")}, as the ${red("Customs")}",
                                        "${red("Officers")} might not notice the ${red("rum")} if it is covered in ${red
                                            ("bananas")}.",
                                        "Now all I need is some ${red("rum")} to hide in the next crate destined",
                                        "for ${red("Wydin's store")}..."
                                    ))
                                }
                            }
                            else {
                                if (player.inventory.contains(Items.KARAMJAN_RUM)) {
                                    QuestStage(objectives = listOf(
                                        "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                        "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                        "I have the ${red("rum")}, and now I need to find a way to get the rum",
                                        "off ${red("Karamja")}. This might be tricky, as the ${red("Customs Officers")}" +
                                            " are",
                                        "searching people for it."
                                    ))
                                }
                                else {
                                    QuestStage(objectives = listOf(
                                        "I have spoken to ${red("Redbeard Frank")}. He has agreed to tell me the",
                                        "location of some ${red("treasure")} for some ${red("Karamjan Rum")}.",
                                        "I need to go to ${red("Karamja")} and buy some ${red("rum")}. I hope it is",
                                        "not too expensive."
                                    ))
                                }
                            }
                        }
                    }

                }
            }
            2 -> {
                if (player.inventory.contains(Items.CHEST_KEY)) {
                    QuestStage(objectives = listOf(
                        str("I have spoken to Redbeard Frank. He has agreed to tell me the"),
                        str("location of some treasure for some Karamjan Rum."),
                        str("I have smuggled some rum off Karamja, and retrieved it from"),
                        str("the back room of Wydin's shop."),
                        "I have given the rum to ${red("Redbeard Frank")}. He has told me that the",
                        "${red("treasure")} is hidden in the chest in the upstairs room of the",
                        "${red("Blue Moon Inn")} in ${red("Varrock")}.",
                        "I have a ${red("key")} that can be used to unlock the chest that",
                        "holds the treasure."
                    ))
                }
                else {
                    QuestStage(objectives = listOf(
                        str("I have spoken to Redbeard Frank. He has agreed to tell me the"),
                        str("location of some treasure for some Karamjan Rum."),
                        str("I have smuggled some rum off Karamja, and retrieved it from"),
                        str("the back room of Wydin's shop."),
                        "I have given the rum to ${red("Redbeard Frank")}. He has told me that the",
                        "${red("treasure")} is hidden in the chest in the upstairs room of the",
                        "${red("Blue Moon Inn")} in ${red("Varrock")}.",
                        "I have lost the ${red("key")} that ${red("Redbeard Frank")} gave me. I should see",
                        "if he has another."
                    ))
                }
            }
            else -> QuestStage(objectives = listOf(
                ""
            ))
        }

    override fun finishQuest(player: Player) {
        TODO("Not yet implemented")
    }

    fun red(text: String) = "<col=8A0808>$text</col>"

    fun str(text: String) = "<str>$text</str>"
}
