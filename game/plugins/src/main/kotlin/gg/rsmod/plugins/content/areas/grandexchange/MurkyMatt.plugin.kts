package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.MURKY_MATT_RUNES) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("Sure be a busy place, today.")
        1 -> npc.forceChat("Yarrr! I'm gonna be rich, I tell ye!")
        2 -> npc.forceChat("I'm lovin' this Grand Exchange! Arrr!")
        3 -> npc.forceChat("Arrr! Another gold sale!")
        4 -> npc.forceChat("No! Me prices, they be goin' down!")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_npc_option(npc = Npcs.MURKY_MATT_RUNES, option = "info-runes") {
    //todo: open runes ge prices interface.
}

on_npc_option(npc = Npcs.MURKY_MATT_RUNES, "talk-to") {
    player.queue {
        chatPlayer("A pirate!")
        chatNpc("Arrrr, How'd ye be guessing that?", wrap = true)
        chatPlayer("You're kidding, right?")
        chatNpc("Nay! Now, what is it ye be wantin? I can tell ye all about the prices of runes, or perhaps I could combine the charges on yer teleport jewellery.", wrap = true)
        when(options("What's a pirate doing here?", "Tell me about the prices of runes.", "I got to go, erm, swab some decks! Yarr!", title = "Select an Option")) {
            1 -> {
                chatPlayer("What's a pirate doing here?", wrap = true)
                chatNpc("By my sea-blistered skin, I could ask the same of you!", wrap = true)
                chatPlayer("But... I'm not a pirate?")
                chatNpc("No? Then what's that smell? The smell o' someone spent too long at sea without a bath!", wrap = true)
                chatPlayer("I think that's probably you.", wrap = true)
                chatNpc("Har har har! We've got a stern landlubber 'ere! Well, let me tell ye, I'm here for the Grand Exchange! Gonna cash in me loot!", wrap = true)
                chatPlayer("Don't you just want to sell it in a shop or trade it to someone specific?", wrap = true)
                chatNpc("By my wave-battered bones! Not when I can sell to the whole world from this very spot!", wrap = true)
                chatPlayer("You pirates are nothing but trouble! Why, once I travelled to Lunar Isle with a bunch of your type, and spent days sailing around in circles!", wrap = true)
                chatNpc("Then ye must know me brother! Murky Pat!")
                chatPlayer("Hmmm. Not so sure I remember him.", wrap = true)
                chatNpc("Well, 'e be on that ship for sure. And I remember 'im tellin' me about some guy like ye, getting all mixed up with curses and cabin boys.", wrap = true)
                chatPlayer("Yes! That was me!", wrap = true)
                chatNpc("Ye sure be a different character.")
                when(options("Tell me about the prices of runes.", "I got to go, erm, swab some decks! Yarr!", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Tell me about the prices of runes.", wrap = true)
                        //todo: open runes ge prices interface.
                    }
                    2 -> {
                        chatPlayer("I got to go, erm, swab some decks! Yarr!")
                        chatNpc("Defer your speech right there! Quit this derogatory and somewhat narrow-minded allusion that all folks of sea voyage are only concerned with washing the decks, looking after parrots and drinking rum. I'll have ye know there is much more to a pirate than meets the eye.", wrap = true)
                        chatPlayer("Aye-aye, captain!")
                        chatNpc("...", wrap = true)
                        chatPlayer("Oh, come on! Lighten up!")
                    }
                }
            }
            2 -> {
                chatPlayer("Tell me about the prices of runes.", wrap = true)
                //todo: open runes ge prices interface.
            }
            3 -> {
                chatPlayer("I got to go, erm, swab some decks! Yarr!")
                chatNpc("Defer your speech right there! Quit this derogatory and somewhat narrow-minded allusion that all folks of sea voyage are only concerned with washing the decks, looking after parrots and drinking rum.", wrap = true)
                chatNpc("I'll have ye know there is much more to a pirate than meets the eye.", wrap = true)
                chatPlayer("Aye-aye, captain!")
                chatNpc("...", wrap = true)
                chatPlayer("Oh, come on! Lighten up!")
            }
        }
    }
}
