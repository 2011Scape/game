package gg.rsmod.plugins.content.areas.canifis

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

on_npc_option(npc = Npcs.ROAVAR, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

on_npc_option(npc = Npcs.ROAVAR, option = "trade") {
    player.openShop("Hair of the Dog tavern")
}

create_shop(
    "Hair of the Dog tavern",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_NONE,
) {
    items[0] = ShopItem(Items.MOONLIGHT_MEAD, 10)
    items[1] = ShopItem(Items.PICKLED_BRAIN, sellPrice = 50, amount = 10)
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    val chat = it

    chat.chatNpc(
        "Greetings traveller.",
        "Welcome to 'The Hair Of The Dog' Tavern.",
        "What can I do for you?",
    )

    when (
        chat.options(
            "Can I buy a beer?",
            "Can I hear some gossip?",
            "Can I hear a story?",
            "Nothing thanks.",
        )
    ) {
        FIRST_OPTION -> buyBeer(chat, player)
        SECOND_OPTION -> gossip(chat)
        THIRD_OPTION -> story(chat)
        FOURTH_OPTION -> {
            chat.chatPlayer("Nothing thanks.")
            chat.chatNpc(
                "...I don't know why you talked to me if you don't",
                "want anything then...",
            )
        }
    }
}

suspend fun story(chat: QueueTask) {
    chat.chatPlayer("Can I hear a story?")
    chat.chatNpc(
        "A story??? Heh, well the only one I can think of right",
        "now is the one my dear old mammy told me as a pup.",
    )
    chat.chatNpc("Now how did it go... Ah yes!")

    chat.chatNpc(
        "Once upon a time a brave young wolf was walking through",
        "a forest, when he came upon a human dressed all in red.",
        "'Aha!' he thought to himself 'Here's a nice easy meal!'",
    )
    chat.chatNpc(
        "But the human talked to him, and he was always taught",
        "to be a polite wolf, so he spoke back to it.",
    )
    chat.chatNpc(
        "Well, this cunning human told him that there was a better",
        "meal that would not run away in the house in the woods.",
    )
    chat.chatNpc(
        "As the brave young wolf could see that this human was",
        " not fully grown, he figured maybe he'd get a better meal",
        "at this house, so he ran as fast as his paws could carry",
        "him to the house the human told him about...",
    )
    chat.chatNpc(
        "Inside he found an old human lying in bed, and",
        "although she was a little tough, as the human was",
        "older than he thought, he had a good meal and decided",
        "to sleep it off in the house for a while.",
    )
    chat.chatNpc(
        "He had not been asleep long though, when he was woken",
        "by a knocking at the door. The human in red had followed",
        "him to the house!",
    )
    chat.chatNpc(
        "Suspecting a human trap, the brave young wolf put",
        "on the old human's clothes and jumped into the bed, so that",
        "he could pretend to be human and escape from",
        "this terrible trap!",
    )
    chat.chatNpc(
        "Well now, this human dressed in red came into the house,",
        "and pretended to believe the brave wolf was really a human,",
        "and began to talk to him.",
    )
    chat.chatNpc(
        "But then the human started asking strange questions",
        "because the human knew that it was not a human at all!",
    )
    chat.chatNpc(
        "The brave young wolf decided to try and escape,",
        "for it was only a young human, and not very strong, so",
        "the brave young wolf said he would eat the human if",
        "they did not let him escape!",
    )
    chat.chatNpc(
        "As he said this however, the human in red shouted at him:",
        "'Aha! You are a wolf!' and as the human shouted this",
        "another bigger human ran into the room from outside!",
    )
    chat.chatNpc(
        "This bigger human was much stronger and was carrying",
        "an axe, and the poor young wolf died in this terrible",
        "trap made for him by humans.",
    )
    chat.chatNpc("And do you know what the moral of this story is?")

    chat.chatPlayer("Um... no, not really.")

    chat.chatNpc(
        "It's 'Never trust humans' of course! My dear old",
        "my old mammy told me that story when I was a pup,",
        "and I'm still alive and well to tell it you today!",
    )
    chat.chatNpc("Pretty good story huh?")

    chat.chatPlayer("Um... yeah. It was great. Really.")
}

suspend fun buyBeer(
    it: QueueTask,
    player: Player,
) {
    val chat = it

    chat.chatPlayer("Can I buy a beer?")
    chat.chatNpc(
        "Well that's my specialty!",
        "The local brew's named 'Moonlight Mead'",
        "and will set you back 5 gold. Whaddya say? Fancy a pint?",
    )
    when (chat.options("Yes please.", "Actually, no thanks.")) {
        FIRST_OPTION -> {
            chat.chatPlayer("Yes please.")
            if (player.hasItem(Items.COINS_995, 5) && player.inventory.hasSpace) {
                player.inventory.remove(Items.COINS_995, 5)
                player.inventory.add(Items.MOONLIGHT_MEAD, 1)
                chat.chatNpc("Here ya go pal. Enjoy!")
                if (!player.inventory.hasSpace) {
                    it.messageBox("You don't have enough inventory space to buy a beer.")
                }
            } else {
                chat.chatPlayer(
                    "I don't have the money on me right now though...",
                    "can I start a tab?",
                )
                chat.chatNpc("You see that sign behind me there?")
                chat.chatPlayer(
                    "The one that says;",
                    "'Please Do Not Ask For Credit As Being Attacked By",
                    "A Large Angry Werewolf Inn Keeper Often Offends'?",
                )
                chat.chatNpc("Bingo")
            }
        }
        SECOND_OPTION -> {
            chat.chatPlayer("Actually, no thanks.")
            chat.chatNpc("Eh, suit yourself. You're missing out on a genuine taste experience.")
        }
    }
}

suspend fun gossip(chat: QueueTask) {
    chat.chatPlayer("Can I hear some gossip?")
    chat.chatNpc(
        "Well, I dunno...",
        "The village is kind of on the fringe out here,",
        "I dunno how up to date the stuff I heat about is...",
    )

    when (
        chat.options(
            "Tell me about this village.",
            "Tell me about the land of Morytania.",
            "Tell me about the shopkeepers here.",
            "Tell me about the temple to the West.",
        )
    ) {
        FIRST_OPTION -> chatAboutVillage(chat)
        SECOND_OPTION -> chatAboutMorytania(chat)
        THIRD_OPTION -> chatAboutShopkeepers(chat)
        FOURTH_OPTION -> chatAboutTemple(chat)
    }
}

suspend fun chatAboutShopkeepers(chat: QueueTask) {
    chat.chatPlayer("Tell me about the shopkeepers here.")
    chat.chatNpc("Hmmm? Why, who did you want to hear the gossip about?")

    when (
        chat.options(
            "Sbott the Tanner",
            "Rufus the Food Seller",
            "Barker the Clothes Seller",
            "Fidelio the general store owner",
        )
    ) {
        FIRST_OPTION -> chatAboutSbott(chat)
        SECOND_OPTION -> chatAboutRufus(chat)
        THIRD_OPTION -> chatAboutBarker(chat)
        FOURTH_OPTION -> chatAboutFidelio(chat)
    }
}

suspend fun chatAboutTemple(chat: QueueTask) {
    chat.chatPlayer("Tell me about the temple to the West.")
    chat.chatNpc(
        "Well, I'm not old enough to remember the full story",
        "behind it, but it was a terrible day for our kingdom",
        "when it was built there. Apparently Morytania had a once",
        "strong kingdom, with lands spreading",
    )
    chat.chatNpc(
        "far further west than they do today, and south into",
        "the desert until the day when a sneak attack by the hated",
        "human forces who worship",
    )
    chat.chatNpc(
        "Saradomin burned our villages and slaughtered our peoples",
        "in mass[sic]. Then they cursed the river so that it would",
        "burn our kind should we touch it! Can you imagine?",
        "To make an entire river poisonous to us?",
    )
    chat.chatNpc(
        "Luckily they seem to have calmed down somewhat recently,",
        "and tend to stay on their side of the river...",
    )
    chat.chatNpc(
        "the wrong they have done to my people will never",
        "be forgotten, and will never be forgiven. What is",
        "worse is that they then had the nerve to build that",
        "gigantic statue on the river mouth to",
    )
    chat.chatNpc(
        "mock the slaughter of our people and",
        "the poisoning of our river!",
    )
    chat.chatNpc(
        "I can understand Lord Drakan's hatred for them even if",
        "I do not share it to his extent!",
    )
    chat.chatPlayer("So you hate humans too?")
    chat.chatNpc(
        "Absolutely! If I ever met one I would gobble him up",
        "where he stands! And then chew the bones for dessert!",
    )
    chat.chatPlayer("Um, okay, thanks, bye.")
}

suspend fun chatAboutSbott(chat: QueueTask) {
    chat.chatPlayer("Tell me what you know about Sbott the Tanner.")
    chat.chatNpc(
        "Hey, I won't hear a word said bad about that guy!",
        "He's an honest and hard-working wolf if I ever met one!",
        "He charges a lot for his job, but he's one of the best tanners",
        "I've ever seen, and I'm over four hundred years old!",
    )
    chat.chatNpc("You need stuff tanning, I recommend him!")
}

suspend fun chatAboutRufus(chat: QueueTask) {
    chat.chatPlayer("Know anything about Rufus the Food Seller?")
    chat.chatNpc(
        "Ah yeah, good old Rufus... He's kind of getting on in years",
        "and doesn't like to come out of his wolf form so often,",
        "but let me tell you this: that guy is a hunter through and through.",
    )
    chat.chatNpc(
        "You seen how much food he catches in a day? That takes real dedication!",
        "Some of the young pups think that just because a wolf has because a",
        "wolf has a bit of grey in his fur then he's past it, but I've seen him ",
        "put those pups to shame in a hunt! He's a real inspiration to us all!",
    )
}

suspend fun chatAboutBarker(chat: QueueTask) {
    chat.chatPlayer("Got anything to share about Barker the Clothes Seller?")
    chat.chatNpc(
        "Eh, I don't like the guy much, but you can't knock",
        "the quality of his stock. They're[sic] some fine",
        "quality threads that you can get yourself there.",
    )
    chat.chatPlayer("What's wrong with him?")
    chat.chatNpc(
        "Eh, I can't really tell you to be honest.",
        "Something about the guy just gets my hackles up everytime",
        "he opens his yap, know what I mean?",
    )
    chat.chatPlayer("No, not really.")
    chat.chatNpc(
        "Lucky for you, I can't really explain it,",
        "I just don't like the guy. Does really great",
        "clothes though, you got to give him that.",
    )
}

suspend fun chatAboutFidelio(chat: QueueTask) {
    chat.chatPlayer(
        "I bet you have some juicy gossip about Fidelio",
        "the general store owner.",
    )
    chat.chatNpc(
        "That nut job? Oh sure, we all know about him.",
        "He was a real firebrand daredevil when he was much",
        "younger, always taking risks to make himself",
        "a quick buck...",
    )
    chat.chatNpc(
        "he got himself caught up in the smuggling trade,",
        "sneaking over the border into Misthalin and pretending",
        "to be a human so he could buy exotic items to sell.",
    )
    chat.chatPlayer("Exotic items? From Misthalin? Like what?")
    chat.chatNpc(
        "Oh man, you name it!",
        "Flour... buckets... tinderboxes... chisels...",
        "he could get the lot!",
    )
    chat.chatNpc(
        "He's been doing it so long now though, his nerves are shot.",
        "Nowadays he's a disgrace, he can barely string a sentence",
        "together and jumps at his own shadow.",
        "Makes you feel ashamed to be a werewolf.",
    )
}

suspend fun chatAboutVillage(chat: QueueTask) {
    chat.chatPlayer("Tell me about this village.")
    chat.chatNpc(
        "You want to know about Canifis? I dunno why,",
        "not a lot happens here. We're just your typical",
        "everyday down to earth werewolf folk, after all...",
    )

    chat.chatPlayer("So... everyone here is a werewolf?")
    chat.chatNpc("Yep. We are as Zamorak made us!")

    chat.chatPlayer("You mentioned Zamorak...")
    chat.chatNpc(
        "Yeah, the great god Zamorak! That is another crime",
        "to add to Saradomin's slate. Not long ago, Saradomin defeated",
        "Zamorak in a great battle near a place called Lumbridge.",
    )
    chat.chatNpc(
        "First he poisons our river, then he banishes our god!",
        "He really is an evil god!",
    )
    chat.chatNpc(
        "And he has all these crazy followers who say things like",
        "we shouldn't kill people and eat them!",
        "What's up with that?",
    )
    chat.chatNpc(
        "Anyway, every year we hold a big festival to give thanks to",
        "Zamorak for keeping us well fed and happy here.",
    )

    chat.chatPlayer("...So when is this festival?")
    chat.chatNpc(
        "Aaaah, not for a good few months yet.",
        "Come and ask me nearer to time, I'll keep some extra",
        "meat and mead from the rest for ya pal.",
    )
}

suspend fun chatAboutMorytania(chat: QueueTask) {
    chat.chatPlayer("Tell me about the land of Morytania.")
    chat.chatNpc(
        "Well, I don't know what to tell you really...",
        "This village is called Canifis and lies on the border",
        "between Morytania and Misthalin...",
    )
    chat.chatNpc(
        "so we're kind of on the front line if those Saradominists",
        "to the west ever decide to attack us... South East ",
        "of here is the castle of Lord Drakan, our master.",
    )

    chat.chatPlayer("Lord Drakan? Who's that?")
    chat.chatNpc(
        "Ahhh... you must be new to these parts if you",
        "haven't heard of Lord Drakan! He's the lord of the land,",
        "and we all pledge allegiance to him.",
    )
    chat.chatNpc(
        "In return for our allegiance, and the tithe of course,",
        "he keeps our land safe from any invaders",
        "and the Saradominists who want to kill us all.",
    )

    chat.chatPlayer("Tithe? What do you mean?")
    chat.chatNpc(
        "Ah, well, in return for his protection, we have to give",
        "Lord Drakan a share of blood every week. If we don't have any",
        "to spare from our hunts, then we need to pick a member",
        "of the village to give their life in return",
    )
    chat.chatNpc("for the blood so that the tithe is fulfilled.")

    chat.chatPlayer(
        "You mean you kill someone you know",
        "in order to meet the tithe??",
    )
    chat.chatNpc(
        "That's right, but only if we haven't managed to get enough",
        "spare blood from our hunts. It's kind of severe if you look",
        "at it that way, but frankly, I think the price is fair in return",
        "for his protection and his tolerance of our village.",
    )
    chat.chatNpc(
        "could probably kill us all if he wanted us gone, so keeping",
        "on his good side is worth the sacrifice to us.",
        "Lucky we're not human really!",
    )
    // Add the conditional dialogue based on Vampyre Slayer quest completion
}
