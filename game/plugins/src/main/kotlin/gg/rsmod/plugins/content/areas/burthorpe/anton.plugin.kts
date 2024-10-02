package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.ANTON) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when (world.random(4)) {
        0 -> npc.forceChat("A fine selection of blades for you to peruse, come take a look!")
        1 -> npc.forceChat("Armours and axes to suit your needs.")
        2 -> npc.forceChat("Imported weapons from the finest smithys around the lands!")
        3 -> npc.forceChat("Ow my toe! That armour is heavy.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop(
    "Warrior Guild Armoury",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.IRON_BATTLEAXE, 5)
    items[1] = ShopItem(Items.STEEL_BATTLEAXE, 2)
    items[2] = ShopItem(Items.MITHRIL_BATTLEAXE, 1)
    items[3] = ShopItem(Items.BRONZE_SWORD, 5)
    items[4] = ShopItem(Items.IRON_SWORD, 4)
    items[5] = ShopItem(Items.STEEL_SWORD, 4)
    items[6] = ShopItem(Items.BLACK_SWORD, 3)
    items[7] = ShopItem(Items.MITHRIL_SWORD, 3)
    items[8] = ShopItem(Items.ADAMANT_SWORD, 2)
    items[9] = ShopItem(Items.BRONZE_LONGSWORD, 4)
    items[10] = ShopItem(Items.IRON_LONGSWORD, 3)
    items[11] = ShopItem(Items.STEEL_LONGSWORD, 3)
    items[12] = ShopItem(Items.BLACK_LONGSWORD, 2)
    items[13] = ShopItem(Items.MITHRIL_LONGSWORD, 2)
    items[14] = ShopItem(Items.ADAMANT_LONGSWORD, 1)
    items[15] = ShopItem(Items.BRONZE_DAGGER, 10)
    items[16] = ShopItem(Items.IRON_DAGGER, 6)
    items[17] = ShopItem(Items.STEEL_DAGGER, 5)
    items[18] = ShopItem(Items.BLACK_DAGGER, 4)
    items[19] = ShopItem(Items.MITHRIL_DAGGER, 3)
    items[20] = ShopItem(Items.ADAMANT_DAGGER, 2)
    items[21] = ShopItem(Items.BRONZE_MACE, 5)
    items[22] = ShopItem(Items.IRON_MACE, 4)
    items[23] = ShopItem(Items.STEEL_MACE, 4)
    items[24] = ShopItem(Items.MITHRIL_MACE, 3)
    items[25] = ShopItem(Items.ADAMANT_MACE, 2)
    items[26] = ShopItem(Items.BRONZE_2H_SWORD, 4)
    items[27] = ShopItem(Items.IRON_2H_SWORD, 3)
    items[28] = ShopItem(Items.STEEL_2H_SWORD, 2)
    items[29] = ShopItem(Items.BLACK_2H_SWORD, 1)
    items[30] = ShopItem(Items.MITHRIL_2H_SWORD, 1)
    items[31] = ShopItem(Items.ADAMANT_2H_SWORD, 1)
    items[32] = ShopItem(Items.BRONZE_CHAINBODY, 5)
    items[33] = ShopItem(Items.IRON_CHAINBODY, 3)
    items[34] = ShopItem(Items.STEEL_CHAINBODY, 3)
    items[35] = ShopItem(Items.BRONZE_MED_HELM, 5)
    items[36] = ShopItem(Items.IRON_MED_HELM, 3)
    items[37] = ShopItem(Items.STEEL_MED_HELM, 3)
}

on_npc_option(Npcs.ANTON, option = "trade") {
    sendShop(player)
}

on_npc_option(Npcs.ANTON, option = "talk-to") {
    player.queue { chat(this) }
}

fun sendShop(player: Player) {
    player.openShop("Warrior Guild Armoury")
}

suspend fun chat(it: QueueTask) {
    it.chatNpc(
        "Ahhh, hello there. How can I help?",
        facialExpression = FacialExpression.HAPPY_TALKING,
    )
    it.chatPlayer(
        "Looks like you have a good selection of weapons around",
        "here...",
        facialExpression = FacialExpression.CALM_TALK,
    )
    it.chatNpc(
        "Indeed so, specially imported from the finest smiths",
        "around the lands, take a look at my wares.",
        facialExpression = FacialExpression.CALM_TALK,
    )
    sendShop(it.player)
}
