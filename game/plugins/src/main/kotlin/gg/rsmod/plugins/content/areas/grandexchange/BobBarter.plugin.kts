package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.BOB_BARTER_HERBS) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("please, please, please work.")
        1 -> npc.forceChat("I'm in the money!")
        2 -> npc.forceChat("I could have sworn that would have worked.")
        3 -> npc.forceChat("Now what should I buy?")
        4 -> npc.forceChat("Hope this item sells well.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_npc_option(npc = Npcs.BOB_BARTER_HERBS, option = "info-herbs") {
    ///todo: add herb ge prices interface.
}

on_npc_option(npc = Npcs.BOB_BARTER_HERBS, "talk-to") {
    player.queue {
        chatNpc("Hello, chum, fancy buyin' some designer jewellry? They've come all the way from Ardougne! Most pukka!", wrap = true)
        chatPlayer("Erm, no. I'm all set, thanks.")
        chatNpc("Okay, chum, would you like to show you the very latest potion prices?", wrap = true)
        when(options("Who are you?", "Can you show me the prices for potions?", "I'll leave you to it.", title = "Select an Option")) {
            1 -> {
                chatPlayer("Who are you?", wrap = true)
                chatNpc("Why, I'm Bob! Your friendly seller of smashin' goods!", wrap = true)
                chatPlayer("So what do you have to sell?")
                chatNpc("Oh, not much at the moment. Cuz, ya know, business being so well and cushie.", wrap = true)
                chatPlayer("You don't really look like you're being so successful.", wrap = true)
                chatNpc("You plonka! It's all a show, innit! If I let people knows I'm in good business they'll want a share of the moolah!", wrap = true)
                chatPlayer("You conveniently have a response for everything.", wrap = true)
                chatNpc("That's the Ardougne way, my son.")
                when(options("Can you show me the prices for potions?", "I'll leave you to it.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Can you show me the prices for potions?", wrap = true)
                        ///todo: add herb ge prices interface.
                    }
                    2 -> {
                        chatPlayer("I'll leave you to it.")
                    }
                }
            }
            2 -> {
                chatPlayer("Can you show me the prices for potions?", wrap = true)
                ///todo: add herb ge prices interface.
            }
            3 -> {
                chatPlayer("I'll leave you to it.")
            }
        }
    }
}
