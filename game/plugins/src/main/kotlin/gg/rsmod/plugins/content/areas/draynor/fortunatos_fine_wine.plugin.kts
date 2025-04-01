package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer
import gg.rsmod.plugins.content.quests.startedQuest

create_shop(
    "Fortunato's Fine Wine",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.JUG_OF_WINE, 10)
    items[1] = ShopItem(Items.BOTTLE_OF_WINE, 10)
    items[2] = ShopItem(Items.JUG, 10)
}

on_npc_option(Npcs.FORTUNATO, "trade") {
    openShop(player)
}

on_npc_option(Npcs.FORTUNATO, "talk-to") {
    player.queue {
        chatNpc("Can I help you at all?", facialExpression = FacialExpression.CONFUSED, wrap = true)
        if (player.startedQuest(VampyreSlayer) || player.finishedQuest(VampyreSlayer)) {
            when (options(
                "Yes, what are you selling?",
                "Talk about Vampyre Slayer.",
                "Not at the moment."
            )) {
                FIRST_OPTION -> yesDialogue(this)
                SECOND_OPTION -> vampyreSlayerDialogue(this)
                THIRD_OPTION -> noDialogue(this)
            }
        }
        else {
            when (options(
                "Yes, what are you selling?",
                "Not at the moment."
            )) {
                FIRST_OPTION -> yesDialogue(this)
                SECOND_OPTION -> noDialogue(this)
            }
        }
    }
}

suspend fun yesDialogue(it: QueueTask) {
    it.chatPlayer("Yes, what are you selling?", facialExpression = FacialExpression.NORMAL, wrap = true)
    openShop(it.player)
}

suspend fun noDialogue(it: QueueTask) {
    it.chatPlayer("Not at the moment.", facialExpression = FacialExpression.CALM_TALK, wrap = true)
    it.chatNpc("Then move along, you filthy ragamuffin, I have customers to serve!")

}

suspend fun vampyreSlayerDialogue(it: QueueTask) {
    if (it.player.finishedQuest(VampyreSlayer)) {
        it.chatNpc("Well done killing that vampyre. Business has certainly improved since the attacks have stoppped.",
            facialExpression = FacialExpression.NORMAL,
            wrap = true)
        it.chatPlayer("Happy to help.", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
    }
    else {
        it.chatPlayer("Can you tell me anything about vampyres? I told Morgan that I would slay the one in " +
            "Draynor Manor.",
            facialExpression = FacialExpression.CONFUSED,
            wrap = true)
        it.chatNpc("So, it is a vampyre is it? Very interesting.",
            facialExpression = FacialExpression.CALM_TALK,
            wrap = true)
        it.chatNpc("I'm afraid I can't tell you anything of use, except to be careful.",
            facialExpression = FacialExpression.CALM_TALK,
            wrap = true)
        it.chatPlayer("That's not very helpful.", facialExpression = FacialExpression.ANGRY)
    }
}

fun openShop(player: Player) {
    player.openShop("Fortunato's Fine Wine")
}
