package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.FARID_MORRISANE_ORES) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("My father shall be so pleased.")
        1 -> npc.forceChat("Hmm. If divide by 20 and take off 50%...")
        2 -> npc.forceChat("Woo hoo! What a sale!")
        3 -> npc.forceChat("What shall I trade next...")
        4 -> npc.forceChat("I can make so much money here!")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_npc_option(npc = Npcs.FARID_MORRISANE_ORES, option = "info-ores") {
    //todo: add ore ge prices interface.
}

on_npc_option(npc = Npcs.FARID_MORRISANE_ORES, "talk-to") {
    player.queue {
        chatPlayer("Hello, little boy.")
        chatNpc("I would prefer it if you didn't speak to me in such a manner. I'll have you know I'm an accomplished merchant.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
        when(options("Calm down, junior.", "Can you show me the prices of ores and bars?", "I best go and speak with someone more my height.", title = "Select an Option")) {
            1 -> {
                chatPlayer("Calm down, junior.", wrap = true)
                chatNpc("Don't tell me to calm down! And don't call me 'junior'.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
                chatNpc("I'll have you know I am Farid Morrisane, son of Ali Morrisane, the world's greatest merchant!", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
                chatPlayer("Then why are you here and not him?")
                chatNpc("My dad has given me the responsibility of expanding our business here.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
                chatPlayer("And you're up to the task? What a grown up boy you are! Mummy and daddy must be very pleased!", wrap = true)
                chatNpc(" Look, mate - I may be young, I may be short, but I'm a respected merchant around here and don't have time to deal with simpletons like you.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
                when(options("Can you show me the prices of ores and bars?", "I best go and speak with someone more my height.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Can you show me the prices of ores and bars?", wrap = true)
                        //todo: add ore ge prices interface.
                    }
                    2 -> {
                        chatPlayer("I best go and speak with someone more my height.")
                        chatNpc("Then I shall not stop you. I've too much work to do.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
                    }
                }
            }
            2 -> {
                chatPlayer("Can you show me the prices of ores and bars?", wrap = true)
                //todo: add ore ge prices interface.
            }
            3 -> {
                chatPlayer("I best go and speak with someone more my height.")
                chatNpc("Then I shall not stop you. I've too much work to do.", wrap = true, facialExpression = FacialExpression.CHILD_NORMAL)
            }
        }
    }
}
