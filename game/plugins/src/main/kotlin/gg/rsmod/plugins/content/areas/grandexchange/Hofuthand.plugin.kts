package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("Wow, that's cheap.")
        1 -> npc.forceChat("Oh. That didn't sell so well.")
        2 -> npc.forceChat("Hmmm. If I spend twenty thousand on that, then...")
        3 -> npc.forceChat("Jackpot! I'm in the money now!")
        4 -> npc.forceChat("Hahaha! Trading the likes of which I have never seen.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_npc_option(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS, option = "info-combat") {
    //todo: add armour and weapons ge prices interface.
}


on_npc_option(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS, "talk-to") {
    player.queue {
        chatPlayer("Hello!")
        chatNpc("What? Oh, hello. I was deep in thought. Did you want me to show you the prices of weapons and armour?", wrap = true)
        when(options("You seem a bit flustered.", "Yes, show me the prices of weapons and armour.", "I'll leave you alone.", title = "Select an Option")) {
            1 -> {
                chatPlayer("You seem a bit flustered.", wrap = true)
                chatNpc("Sorry, I'm just deep in thought. I'm waiting for many deals to complete today.", wrap = true)
                chatPlayer("What sort of things are you selling?")
                chatNpc(" Good old weapons and armour! My people - dwarves, you understand - are hoping I can trade with humans.", wrap = true)
                chatPlayer("It looks like you've come to the right place for that.", wrap = true)
                chatNpc("I have indeed, my friend. Now, can I help you?", wrap = true)
                when(options("Yes, show me the prices of weapons and armour.", "I'll leave you alone.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Yes, show me the prices of weapons and armour.", wrap = true)
                        //todo: add armour and weapons ge prices interface.
                    }
                    2 -> {
                        chatPlayer("I'll leave you alone.")
                        chatNpc("Thank you, I have much on my mind.")
                    }
                }
            }
            2 -> {
                chatPlayer("Yes, show me the prices of weapons and armour.", wrap = true)
                //todo: add armour and weapons ge prices interface.
            }
            3 -> {
                chatPlayer("I'll leave you alone.")
                chatNpc("Thank you, I have much on my mind.")
            }
        }
    }
}
