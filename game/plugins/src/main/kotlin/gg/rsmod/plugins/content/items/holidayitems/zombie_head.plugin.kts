package gg.rsmod.plugins.content.items.holidayitems

/**
 * @author Tank <https://github.com/reeeccoo>
 */

on_item_option(Items.ZOMBIE_HEAD, option = "talk-at") {
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.animate(2840)
        player.forceChat("Alas!")
    }
}

on_item_option(Items.ZOMBIE_HEAD, option = "display") {
    player.lockingQueue(lockState = LockState.DELAY_ACTIONS) {
        player.animate(2844)
        player.forceChat("Mwahahaha!")
    }
}

on_item_option(Items.ZOMBIE_HEAD, option = "question") {
    player.queue {
        when (
            options(
                "How did you die?",
                "What is your name?",
                "Can you do any tricks?",
                "Want a new hat?",
                "Want to go scare some people?",
            )
        ) {
            1 -> { // How did you die?
                chatPlayer("Hey, Head?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("What?", npc = Npcs.ZOMBIE_HEAD, facialExpression = FacialExpression.CONFUSED)
                chatPlayer("How did you die?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "I stuck my neck out for an old friend.",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.REALLY_SAD,
                )
                chatPlayer("You shouldn't get so cut up about it.", facialExpression = FacialExpression.DISDAIN)
                chatNpc(
                    "Well if I keep it all bottled up I'll turn into a total head case.",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.REALLY_SAD,
                    wrap = true,
                )
            }
            2 -> { // What is your name?
                chatPlayer("Hey, Head?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("What?", npc = Npcs.ZOMBIE_HEAD, facialExpression = FacialExpression.CONFUSED)
                chatPlayer("What is your name?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("Mumblemumblemumble...", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("What was that?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("My name is Edward Cranium.", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("Edd Cranium?")
                chatPlayer("Hahahahahahahahahahaha!")
                chatNpc("Har har...", npc = Npcs.ZOMBIE_HEAD)
            }
            3 -> { // Can you do any tricks?
                chatPlayer("Hey, Head?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("What now?", npc = Npcs.ZOMBIE_HEAD, facialExpression = FacialExpression.ANNOYED)
                chatPlayer("Can you do any tricks?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("Not any more.", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("How come?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "Because I used to be able to do a handstand for over an hour while juggling cannon balls with my feet...",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.REALLY_SAD,
                    wrap = true,
                )
                chatPlayer("Wow, you were quite the entertainer.")
                chatNpc("Yep. Now I can barely roll my eyes...", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("I know what you can do!")
                chatNpc("What?", npc = Npcs.ZOMBIE_HEAD, facialExpression = FacialExpression.CONFUSED)
                chatPlayer("Vent...")
                chatNpc("Don't even suggest it!", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("Ok.")
            }
            4 -> { // Want a new hat?
                chatPlayer("Hey, Head?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("Can't I rest in peace?", npc = Npcs.ZOMBIE_HEAD, facialExpression = FacialExpression.ANNOYED)
                chatPlayer("No!", facialExpression = FacialExpression.CHEERFUL)
                chatPlayer("Would you like a new hat?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("No, but could you screw a handle into the top of my head?", npc = Npcs.ZOMBIE_HEAD)
                chatPlayer("A handle? Why?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "Because currently you wave me about by my hair, and it hurts.",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.REALLY_SAD,
                )
            }
            5 -> { // Want to go scare some people?
                chatPlayer("Hey, Head?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "Will you ever leave me alone?",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.MEAN_FACE,
                )
                chatPlayer("No!", facialExpression = FacialExpression.CHEERFUL)
                chatPlayer("Want to go scare some people?", facialExpression = FacialExpression.CONFUSED)
                chatNpc(
                    "Let's leave it for now.",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.REALLY_SAD,
                )
                chatPlayer("All right...", facialExpression = FacialExpression.REALLY_SAD)
                chatPlayer("We'll quit while we're ahead!", facialExpression = FacialExpression.LAUGHING)
                chatNpc(
                    "If I try really hard I might be able to will myself",
                    "deader...",
                    npc = Npcs.ZOMBIE_HEAD,
                    facialExpression = FacialExpression.WAILING,
                )
            }
        }
    }
}
