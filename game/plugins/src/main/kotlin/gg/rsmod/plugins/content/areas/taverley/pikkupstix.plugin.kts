package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.WolfWhistle

val wolfWhistle = WolfWhistle

on_npc_option(Npcs.PIKKUPSTIX_6971, option = "Talk-to") { //handles the talk to option when clicking on npc
    player.queue {
        when(player.getCurrentStage(wolfWhistle)) {
            0 -> preQuest(this)
            2 -> afterWolpertinger(this)
            3 -> wolfBones(this)
            4 -> pouchesAndScrolls(this)
        }
    }
}

suspend fun preQuest(it: QueueTask) {
    var gender = "boy"
    if (!it.player.appearance.gender.isMale()) {
        gender = "girl"
    }

    when(it.options("Talk about Wolf Whistle quest.", "Talk about Pikkenmix.")) {
        1 -> {
            it.chatNpc(
                "You there! What are you doing here, as if I didn't have",
                "enough troubles?",
                facialExpression = FacialExpression.THINK
            )
            it.chatPlayer("I'm just a passing adventurer. Who are you?")
            it.chatNpc(
                "Who am I? My dear ${gender}, I just do happen to be one",
                "of the most important druids in this village!"
            )
            it.chatPlayer("Wow, I don't meet that many celebrities.", facialExpression = FacialExpression.DISBELIEF)
            it.chatPlayer("What do you do here?")
            it.chatNpc("Well, firstly, I-")
            it.doubleMessageBox(
                "There is a loud crash from upstairs, followed by the tinkling of",
                "broken glass."
            )
            it.chatNpc("Confound you, lapine menace!")
            it.chatPlayer("What was that?")
            it.chatNpc(
                "That was the sound of my carefully arranged room",
                "being destoryed by a fluffy typhoon of wickedness!"
            )
            it.chatPlayer("What?")
            it.chatPlayer(
                "Wait, I'm good with monsters. Do you want me to go",
                "kill it for you?"
            )
            it.chatNpc("Well, yes, but it's a little more complicated than that.")
            it.chatPlayer("How complicated can it be?")
            it.messageBox("A resounding crash comes from upstairs.")
            it.chatNpc("Complicated enough for me to waste time explaining it", "to you.")
            it.chatNpc("Tell me, have you ever heard of Summoning?")
            it.chatPlayer("Not really.")
            it.chatNpc(
                "Well, some of the concepts I am about to discuss might",
                "go over your head, so I'll stick to the basics."
            )
            it.messageBox("There is a series of loud crunches from upstairs.")
            it.chatNpc("Gah! That better not be my warbrode!")
            it.chatNpc(
                "Summoners are able to call upon animal familiars for a",
                "number of uses. These familiars can help the summoner",
                "practice their skills, fight on the battlefield, or offer any",
                "number of other benefits."
            )
            it.chatNpc(
                "Beneath this house is a monument - an obelisk - that",
                "allows us druids to tap into the Summoning power."
            )
            it.messageBox("A loud gnawing begins upstairs.")
            it.chatNpc(
                "The downside to these obelisks is that rogue familiars,",
                "like the one upstairs, get into this world and wreak",
                "havoc."
            )
            it.chatNpc(
                "I chased it upstairs, but I didn't have the necessary",
                "Summoning pouch to banish it."
            )
            it.chatNpc(
                "If I leave this house, it will come down here, potentially",
                "even getting to the obelisk. If that happens, it could call",
                "another familiar through and multiply!"
            )
            it.chatNpc(
                "I sent my assistant, Stikklebrix, out to bring me what I",
                "needed, but he has not returned."
            )
            it.messageBox("The gnawing stops. There is a crash.")
            it.chatPlayer("How fast can it multiply?")
            it.chatNpc("Like a rabbit, because it is-")
            it.chatPlayer("A rabbit!")
            it.chatNpc("Well yes, rabbit-like, but-")
            it.chatPlayer(
                "Oh, don't worry, I'll deal with this myself. One rabbit",
                "stew coming right up."
            )
            it.chatNpc(
                "Very well, young fool. You go see how well you do",
                "against it."
            )
            it.player.advanceToNextStage(wolfWhistle)
        }
        2 -> {
            pikkenmix(it)
        }
    }
}

suspend fun afterWolpertinger(it: QueueTask) {
    when(it.options("Talk about Wolf Whistle quest.", "Talk about Pikkenmix.")) {
        1 -> {
            it.chatPlayer("The teeth!")
            it.chatNpc(
                "So, you finally saw what you're up against, eh? Not as",
                "harmless as you assumed, I take it?"
            )
            it.chatPlayer("Horns! Teeth!")
            it.chatNpc(
                "Are you prepared to treat this situation with the gravity",
                "it deserves, now?"
            )
            it.chatPlayer("What IS it?")
            it.chatNpc("A wolpertinger, and a pretty big one at that.")
            it.chatNpc(
                "They are spirits that tend to be on the energetic and",
                "destructive side when they manifest here, but are a little",
                "less violent on the spirit plane."
            )
            it.chatPlayer("So, what can be done about it? Can't you banish it?")
            it.chatNpc(
                "Well, I could, but in order to do so, I'd need a spirit",
                "wolf pouch."
            )
            it.chatNpc(
                "Wolpertingers are generally afraid of wolves; that's the",
                "rabbity-side of them. A spirit wolf will scare it and banish it."
            )
            it.chatNpc(
                "The problem is that I don't have any of the necessary",
                "spirit wolf pouches, and the only thing keeping the giant",
                "wolpertinger upstairs is the my presence. If I were to leave,",
                "it would amble downstairs to bring more of its kind"
            )
            it.chatNpc("through, as I said before.")
            it.chatPlayer(
                "Well, what if I were to bring you the elements you",
                "needed? Would that work?"
            )
            it.chatNpc(
                "Not really, I would need to go down to the obelisk in",
                "my cellar and use the necessary ingredients to make a",
                "spirit wolf pouch and some Howl scrolls, but it may slip",
                "out the front door."
            )
            it.chatNpc(
                "You could infuse the pouch yourself, though. Would",
                "you like the learn the secrets of Summoning? I can",
                "sense the spark within you, urging you to master the",
                "art."
            )
            it.chatPlayer("Certainly!")
            it.chatPlayer("What do you need me to bring?")
            it.chatNpc("That's wonderful")
            it.chatNpc(
                "You need to bring two lots of wolf bones. I can",
                "provide the other items you will need."
            )
            when(it.options("I'll get right on it.", "Wait. Why do you need those things?")) {
                1 -> {
                    it.chatPlayer("I'll get right on it.")
                    it.player.advanceToNextStage(wolfWhistle)
                }
                2 -> {
                    // TODO: Add dialogue
                }
            }
        }

        2 -> {
            pikkenmix(it)
        }
    }
}

suspend fun wolfBones(it: QueueTask) {
    var gender = "boy"
    if (!it.player.appearance.gender.isMale()) {
        gender = "girl"
    }

    when(it.options("Talk about Wolf Whistle quest.", "Talk about Pikkenmix.")) {
        1 -> {
            if (it.player.inventory.getItemCount(Items.WOLF_BONES) >= 2) {
                it.chatPlayer("I have the bones right here.")
                it.chatNpc("Spenldid, dear ${gender}.")
                if (it.player.inventory.freeSlotCount < 4) {
                    it.chatNpc( //TODO: Find actual dialogue from 2011
                        "I have some items for you. You'll need to clear some",
                        "space in your backpack."
                    )
                }
                else {
                    it.player.advanceToNextStage(wolfWhistle)
                    it.player.inventory.add(Items.GOLD_CHARM, 2) // This is supposed to give GOLD_CHARM_12527, but they don't update the pouch interface
                    it.player.inventory.add(Items.POUCH, 2) // Quest give fake pouches, but can't find the ID
                    it.player.inventory.add(Items.TRAPDOOR_KEY)
                    it.player.inventory.add(Items.SPIRIT_SHARDS, 14)
                    it.chatNpc(
                        "Here are the pouches, spirit shards and charms you will",
                        "need to make the spirit wolf pouch and Howl scrolls.",
                        "This key is to the trapdoor over there, which leads to the obelisk."
                    )
                    it.chatPlayer("What are these things?")
                    it.chatNpc(
                        "Well, the wolf bones, spirit shards and gold charms are",
                        "all ingredients to go into the pouch."
                    )
                    it.chatNpc(
                        "The wolf bones give a solidity to the spirit, the charm",
                        "attracts the type of familiar that you desire - in this",
                        "case a gold charm - and the shards are the spirit's",
                        "focus."
                    )
                    it.chatNpc(
                        "You will first need to make two Summoning pouches:",
                        "one that can be used to summon the spirit wolf, and",
                        "another to tear open at the obelisk, creating some Howl",
                        "scrolls. These Howl scrolls will help make the spirit wolf"
                    )
                    it.chatNpc("perform a fear-inducing Howl special move.")
                    it.chatPlayer(
                        "Okay, I think I understood that. So, how do I get the",
                        "obelisk to work?"
                    )
                    it.chatNpc(
                        "Well, you stand before the obelisk with the charms.",
                        "pouches, shards and bones necessary to complete two",
                        "Summoning pouches. You should the 'use' your empty",
                        "pouch on the obelisk. Your ingredients will be added to"
                    )
                    it.chatNpc(
                        "the pouch, mixing with the spirit of a familiar to create a",
                        "spirit wolf pouch."
                    )
                    it.chatNpc(
                        "Creating a scroll is a similar process. By using a",
                        "Summoning pouch on an obelisk and breaking it open,",
                        "you are allowing the spirit energy to transform into a",
                        "different form - some scrolls."
                    )
                    it.chatNpc(
                        "Once you have a spirit wolf pouch and some Howl",
                        "scrolls, you can come upstairs to see me, to find out",
                        "what must be done with the giant wolpertinger."
                    )
                    it.chatPlayer("It all sounds simple enough.")
                }
            }
            else {
                // TODO: Add dialogue for not having bones
            }
        }

        2 -> {
            pikkenmix(it)
        }
    }
}

suspend fun pouchesAndScrolls(it: QueueTask) {
    when(it.options("Talk about Wolf Whistle quest.", "Talk about Pikkenmix.")) {
        1 -> {
            if (it.player.hasItem(Items.SPIRIT_WOLF_POUCH) && it.player.hasItem(Items.HOWL_SCROLL)) {
                it.chatPlayer("Here is the pouch and scroll.")
                it.chatNpc(
                    "Wonderful! Now, all you have to do is go upstairs and",
                    "summon the spirit wolf, the the Howl effect."
                )
                it.chatPlayer("That's it?")
                it.chatNpc(
                    "It's that simple. The spirit wolf will, when you use the",
                    "Howl scroll, chase away the giant wolpertinger and then",
                    "disappear. Under normal circumstances, the spirit wolf",
                    "would follow you around, defend you in combat and"
                )
                it.chatNpc("often lend you its powers.")
                it.chatPlayer("What sort of powers?")
                it.chatNpc(
                    "Well for a start, it has the ability to perform a",
                    "Summoning scroll that forces opponents to flee. That is",
                    "what it will use on the giant wolpertinger, with the",
                    "difference being that the giant wolpertinger will be so"
                )
                it.chatNpc(
                    "scared that it will retreat to its spirit plane, rather than",
                    "face the spirit wolf."
                )
                it.chatPlayer(
                    "Great! So, how will I make it perform the Summoning",
                    "scroll?"
                )
                it.chatNpc(
                    "All you need to do is use the special move button in",
                    "your follower details and aim it at the giant",
                    "wolpertinger, once the spirit wolf has been summoned.",
                    "The spirit wolf will then perform its special move."
                )
                it.doubleMessageBox(
                    "To access your follower details, right-click the Summoning icon near",
                    "your minimap and select 'Follower Details'."
                )
                it.doubleMessageBox(
                    "You can also access your familiar's spell and various other functions",
                    "from this menu."
                )
                it.doubleMessageBox(
                    "Bear in mind that the icon will only function so long as you have a",
                    "Summoning familiar present."
                )
                it.chatPlayer("What a relief!")
                it.chatNpc(
                    "Well, I think that I have talked enough. Now you have",
                    "to put it into practice."
                )
                it.chatPlayer("Wish me luck!")
                it.player.advanceToNextStage(wolfWhistle)
            }
            else {
                // TODO: Add dialogue for not having scroll / pouch
            }
        }
        2 -> {
            pikkenmix(it)
        }
    }
}

suspend fun pikkenmix(it: QueueTask) {
    // TODO: Add Dialogue
}