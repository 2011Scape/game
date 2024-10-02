package gg.rsmod.plugins.content.areas.yanille.magic_guild
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Magic Guild Store - Runes and Staves",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    var index = 0
    items[index++] = ShopItem(Items.AIR_RUNE, 1000, false, 22, 5)
    items[index++] = ShopItem(Items.WATER_RUNE, 1000, false, 22, 5)
    items[index++] = ShopItem(Items.EARTH_RUNE, 1000, false, 22, 5)
    items[index++] = ShopItem(Items.FIRE_RUNE, 1000, false, 22, 5)
    items[index++] = ShopItem(Items.MIND_RUNE, 1000, false, 22, 5)
    items[index++] = ShopItem(Items.BODY_RUNE, 1000, false, 20, 4)
    items[index++] = ShopItem(Items.CHAOS_RUNE, 300, false, 182, 42)
    items[index++] = ShopItem(Items.NATURE_RUNE, 300, false, 563, 111)
    items[index++] = ShopItem(Items.DEATH_RUNE, 300, false, 403, 93)
    items[index++] = ShopItem(Items.LAW_RUNE, 100, false, 504, 113)
    items[index++] = ShopItem(Items.BLOOD_RUNE, 100, false, 733, 165)
    items[index++] = ShopItem(Items.SOUL_RUNE, 100, false, 546, 123)
    items[index++] = ShopItem(Items.STAFF_OF_AIR, 10, false, 1950, 450)
    items[index++] = ShopItem(Items.STAFF_OF_WATER, 10, false, 1950, 450)
    items[index++] = ShopItem(Items.STAFF_OF_EARTH, 10, false, 1950, 450)
    items[index] = ShopItem(Items.STAFF_OF_FIRE, 10, false, 1950, 450)
}

on_npc_option(Npcs.MAGIC_STORE_OWNER, "trade") {
    player.openShop("Magic Guild Store - Runes and Staves")
}

on_npc_option(npc = Npcs.MAGIC_STORE_OWNER, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Welcome to the Magic Guild store.",
        "Would you like to buy some magic supplies?",
    )
    when (
        it.options(
            "Yes please.",
            "No thank you.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Yes please.")
            player.openShop("Magic Guild Store - Runes and Staves")
        }
        SECOND_OPTION -> {
            it.chatPlayer("No thank you.")
        }
    }
}
