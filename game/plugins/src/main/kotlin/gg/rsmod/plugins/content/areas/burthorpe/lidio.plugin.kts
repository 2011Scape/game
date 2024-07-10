package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 40..80

on_npc_spawn(npc = Npcs.LIDIO) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when (world.random(3)) {
        0 -> npc.forceChat("Come try my lovely pizza or maybe some fish!")
        1 -> npc.forceChat("Stew to fill the belly, on sale here!")
        2 -> npc.forceChat("Potatoes are filling and healthy too!")
        3 -> npc.forceChat("Potatoes are filling and healthy too!")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop(
    "Warrior Guild Food Shop",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.TROUT, 10)
    items[1] = ShopItem(Items.BASS, 10)
    items[2] = ShopItem(Items.PLAIN_PIZZA, 5)
    items[3] = ShopItem(Items.POTATO_WITH_CHEESE, 10)
    items[4] = ShopItem(Items.STEW, 10)
}

on_npc_option(Npcs.LIDIO, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.LIDIO, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Warrior Guild Food Shop")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Greetings, warrior, how can I fill your stomach today?",
        facialExpression = FacialExpression.HAPPY_TALKING,
    )
    it.chatPlayer(
        "With food preferably.",
        facialExpression = FacialExpression.CALM_TALK,
    )
    sendShop(it.player)
}
