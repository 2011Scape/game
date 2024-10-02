package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Zeke's Superior Scimitars",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.BRONZE_SCIMITAR, amount = 5)
    items[1] = ShopItem(Items.IRON_SCIMITAR, amount = 3)
    items[2] = ShopItem(Items.STEEL_SCIMITAR, amount = 2)
    items[3] = ShopItem(Items.MITHRIL_SCIMITAR, amount = 1)
}

on_npc_option(Npcs.ZEKE, option = "trade") {
    openShop(player)
}

on_npc_option(Npcs.ZEKE, option = "talk-to") {
    player.queue { chat(this) }
}

fun openShop(player: Player) {
    player.openShop("Zeke's Superior Scimitars")
}

on_item_on_npc(Items.AL_KHARID_FLYER, Npcs.ZEKE) {
    player.queue { flyerChat(this) }
}

suspend fun flyerChat(it: QueueTask) {
    it.chatPlayer("Hi. I have this money off voucher.", facialExpression = FacialExpression.HAPPY_TALKING)
    it.chatNpc(
        *"So I see. Unfortunately, it seems to have expired... yesterday. Never mind.".splitForDialogue(),
        facialExpression = FacialExpression.CONFUSED,
    )
    it.chatPlayer("But I only just got it!", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("I'm sorry. There's nothing I can do. Goodbye.", facialExpression = FacialExpression.WORRIED)
}

suspend fun chat(it: QueueTask) {
    // TODO toggle check if player has completed the Monkey Madness quest
    val completedMonkeyMadness = false // it.player.finishedQuest(MonkeyMadness)
    val male = it.player.appearance.gender == Gender.MALE
    it.chatNpc(
        "A thousand greetings, ${if (male) "sir" else "madam"}.",
        facialExpression = FacialExpression.HAPPY_TALKING,
    )
    when (it.options("Do you want to trade?", "Nice cloak.", "Could you sell me a dragon scimitar?")) {
        1 -> {
            it.chatPlayer("Do you want to trade?", facialExpression = FacialExpression.HAPPY_TALKING)
            it.chatNpc("Yes certainly. I deal in scimitars.", facialExpression = FacialExpression.TALKING)
            openShop(it.player)
        }

        2 -> {
            it.chatPlayer("Nice clock.", facialExpression = FacialExpression.HAPPY_TALKING)
            it.chatNpc("Thank you.", facialExpression = FacialExpression.CONFUSED)
        }

        3 -> {
            it.chatPlayer("Could you sell me a dragon scimitar?", facialExpression = FacialExpression.HAPPY_TALKING)
            it.chatNpc("A dragon scimitar? A DRAGON scimitar?", facialExpression = FacialExpression.UPSET)
            it.chatNpc("No way, ${if (male) "man" else "lady"}!", facialExpression = FacialExpression.ANGRY)
            it.chatNpc(
                *"The banana-brained nitwits who make them would never dream of selling any to me.".splitForDialogue(),
                facialExpression = FacialExpression.TALKING_ALOT,
            )
            it.chatNpc(
                *"Seriously, you'll be a monkey's uncle before you'll ever hold a dragon scimitar.".splitForDialogue(),
                facialExpression = FacialExpression.TALKING_ALOT,
            )
            if (completedMonkeyMadness) {
                it.chatPlayer(
                    "Hmmm, funny you should say that...",
                    facialExpression = FacialExpression.SECRETLY_TALKING,
                )
            }
            it.chatNpc(
                "Perhaps you'd like to take a look at my stock?",
                facialExpression = FacialExpression.HAPPY_TALKING,
            )
            when (it.options("Yes please, Zeke.", "Not today, thank you.")) {
                1 -> openShop(it.player)
                2 -> it.chatPlayer("Not today, thank you.", facialExpression = FacialExpression.ANNOYED)
            }
        }
    }
}
