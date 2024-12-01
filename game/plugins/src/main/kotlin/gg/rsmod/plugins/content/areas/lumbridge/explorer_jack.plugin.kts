// https://www.youtube.com/watch?v=8QSnnjeUUtY
// https://www.youtube.com/watch?v=52dPJix0f-o
// https://www.youtube.com/watch?v=uTdraZGILso
// https://www.youtube.com/watch?v=CdkqY3qMsZQ

on_npc_option(Npcs.EXPLORER_JACK, option = "talk-to") { // handles the talk to option when clicking on npc

    player.queue {
        chatNpc("What, ho! Where did you come from?")
        chatPlayer("Oh, sorry. I was just looking around.", facialExpression = FacialExpression.SHOCK)
        chatNpc("Oh, that's perfectly alright. Mi Casa and all that, what!")
        chatPlayer("Uh...and all what?", facialExpression = FacialExpression.THINK)
        chatNpc(
            "Splendid! I love a person with a sense of humor. I bet",
            "you're from Ardougne eh? Ha!"
        )
        chatNpc(
            "Now, then. It looks like you've made your first few",
            "steps into the world of exploration."
        )
        chatPlayer("I have?", facialExpression = FacialExpression.THINK)
        chatNpc(
            "Yes indeed. My name is Explorer Jack, by the way.",
            "Mustn't forget the pleasantries, or where would we be,",
            "eh?"
        )
        chatPlayer("I'm not sure.", facialExpression = FacialExpression.THINK)
        chatNpc(
            "Don't worry about that, chum. When I was your age,",
            "I was just like you: young, eager, full of excitement",
            "about the big world."
        )
        chatPlayer("Ok. So, what are you doing here in Lumbridge?", facialExpression = FacialExpression.THINK)
        chatNpc(
            "Alas, even intrepid explorers like myself must come,",
            "home eventually! Since you're here I can tell you",
            "about your Achievement Diary."
        )
        chatPlayer("What is the Achievement Diary?", facialExpression = FacialExpression.THINK)
        chatNpc(
            "Ah, well, it's a diary that helps you keep track of",
            "particular achievements you've made in the world of",
            "Runescape. In Lumbridge and Draynor it can help you",
            "discover some very useful things indeed.",
            facialExpression = FacialExpression.TALKING
        )
        chatNpc(
            "Eventually, with enough exploration, you will be",
            "rewarded for your explorative efforts."
        )
        chatNpc(
            "You can find your Achievement Diary by clicking on",
            "the green star icon.",
            facialExpression = FacialExpression.TALKING
        )
        // The part where an arrow is supposed to appear.
        chatNpc(
            "You should see the icon flashing now. Go ahead and",
            "click on it to find your Achievement Diary. If you have",
            "any questions, feel free to speak to me again.",
            facialExpression = FacialExpression.TALKING
        )
    }
}
