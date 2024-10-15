package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 30..80

on_npc_spawn(npc = Npcs.LILLY) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when (world.random(5)) {
        0 -> npc.forceChat("Pink bananas... mmmmmm.")
        1 -> npc.forceChat("Demons are a Ghoul's best Friend.")
        2 -> npc.forceChat("It's not an optical illusion, it just looks like one.")
        3 -> npc.forceChat("Don't look back, they might be gaining on you.")
        4 -> npc.forceChat("If you don't care where you are, then you ain't lost.")
        5 -> npc.forceChat("Madness takes its toll. Please have exact change.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop(
    "Warrior Guild Potion Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.STRENGTH_POTION_3, 10)
    items[1] = ShopItem(Items.ATTACK_POTION_3, 10)
    items[2] = ShopItem(Items.DEFENCE_POTION_3, 10)
}

on_npc_option(Npcs.LILLY, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.LILLY, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Warrior Guild Potion Shop")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Uh..... hi... didn't see you there. Can.... I help?",
        facialExpression = FacialExpression.THINK,
    )

    it.chatPlayer(
        "Umm... do you sell potions?",
        facialExpression = FacialExpression.CONFUSED,
    )

    it.chatNpc(
        "Erm... yes. When I'm not drinking them.",
        facialExpression = FacialExpression.THINK,
    )

    when (it.options("I'd like to see what you have for sale.", "That's a pretty wall hanging.", "Bye!")) {
        1 -> {
            it.chatPlayer(
                "I'd like to see what you have for sale.",
                facialExpression = FacialExpression.TALKING,
            )
            sendShop(it.player)
        }

        2 -> {
            it.chatPlayer(
                "That's a pretty wall hanging.",
                facialExpression = FacialExpression.CONFUSED,
            )
            it.chatNpc(
                "Do you think so? I made it myself.",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            it.chatPlayer(
                "Really? Is that why there's all this cloth and dye around?",
                facialExpression = FacialExpression.CONFUSED,
            )
            it.chatNpc(
                "Yes, it's a hobby of mine when I'm.... relaxing.",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
        }

        3 -> {
            it.chatPlayer(
                "Bye!",
                facialExpression = FacialExpression.CALM_TALK,
            )
            it.chatNpc(
                "Have fun and come back soon!",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            // TODO missing waving hand anim when have fun and come back soon
        }
    }
}
