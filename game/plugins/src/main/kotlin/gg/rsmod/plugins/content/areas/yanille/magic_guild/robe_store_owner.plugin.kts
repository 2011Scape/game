package gg.rsmod.plugins.content.areas.yanille.magic_guild
/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.skills.Skillcapes

create_shop(
    "Magic Guild Store - Mystic Robes",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    var index = 0
    items[index++] = ShopItem(Items.MYSTIC_HAT, 1, false, 15000, 3000)
    items[index++] = ShopItem(Items.MYSTIC_ROBE_TOP, 1, false, 120000, 4500)
    items[index++] = ShopItem(Items.MYSTIC_ROBE_BOTTOM, 1, false, 80000, 3000)
    items[index++] = ShopItem(Items.MYSTIC_GLOVES, 1, false, 10000, 1500)
    items[index] = ShopItem(Items.MYSTIC_BOOTS, 1, false, 10000, 1500)
}

on_npc_option(Npcs.ROBE_STORE_OWNER, "trade") {
    player.openShop("Magic Guild Store - Mystic Robes")
}

on_npc_option(npc = Npcs.ROBE_STORE_OWNER, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.MAGIC) >= 99) {
            mainChatWith99(this, player)
        } else {
            mainChat(this, player)
        }
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
            player.openShop("Magic Guild Store - Mystic Robes")
        }
        SECOND_OPTION -> {
            it.chatPlayer("No thank you.")
        }
    }
}

suspend fun mainChatWith99(
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
            "Can I buy a skillcape of Magic?",
            "No thank you.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Yes please.")
            player.openShop("Magic Guild Store - Mystic Robes")
        }
        SECOND_OPTION -> {
            it.chatPlayer("Can I buy a Skillcape of Magic?")
            it.chatNpc("Sure, it will cost you 99000 gold.")
            when (
                it.options(
                    "99000 gold? Are you mad?",
                    "I think I have the money right here, actually.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("99000 gold? Are you mad?")
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    it.chatPlayer("I think I have the money right here, actually.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Magic.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.MAGIC)
                        it.chatNpc("Excellent! Wear that cape with pride my friend.")
                    } else {
                        it.chatPlayer("But, unfortunately, I was mistaken.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
        THIRD_OPTION -> {
            it.chatPlayer("No thank you.")
        }
    }
}
