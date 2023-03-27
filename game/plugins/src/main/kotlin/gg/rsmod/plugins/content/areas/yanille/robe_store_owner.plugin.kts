package gg.rsmod.plugins.content.areas.yanille

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.skills.Skillcapes

create_shop(
    "Magic Guild Store - Mystic Robes",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false
)
{
    items[0] = ShopItem(Items.MYSTIC_HAT, 30)
    items[1] = ShopItem(Items.MYSTIC_ROBE_TOP, 10)
    items[2] = ShopItem(Items.MYSTIC_ROBE_BOTTOM, 10)
    items[3] = ShopItem(Items.MYSTIC_GLOVES, 30)
    items[4] = ShopItem(Items.MYSTIC_BOOTS, 10)
}

on_npc_option(Npcs.ROBE_STORE_OWNER, "trade") {
    player.openShop("Magic Guild Store - Mystic Robes")
}

on_npc_option(npc = Npcs.ROBE_STORE_OWNER, option = "talk-to") {
    player.queue {
        if (player.getSkills().getCurrentLevel(Skills.MAGIC) >= 99) {
            mainChatWith99 (this, player)
        }else{
            mainChat (this, player)
        }

    }
}

suspend fun mainChat(it: QueueTask, player: Player) {
    it.chatNpc(
        "Welcome to the Magic Guild Store. Would you like to",
        "buy some magic supplies?")
    when (it.options(
        "Yes please",
        "No thank you."
    )) {
        FIRST_OPTION -> {
            player.openShop("Magic Guild Store - Mystic Robes")
        }
        SECOND_OPTION -> {
            //nothing, so close
        }
    }
}

suspend fun mainChatWith99(it: QueueTask, player: Player) {
    it.chatNpc(
        "Welcome to the Magic Guild Store. Would you like to",
        "buy some magic supplies, or perhaps a Skillcape",
        "of Magic, seeing as you've masted the art of Magic?")
    when (it.options(
        "Ask about the Skillcape",
        "What do you have for sale?",
        "No thank you"
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("Can I buy a Skillcape of Magic?")
            it.chatNpc("Sure, it will cost you 99000 gold.")
            when (it.options(
                "99000 gold? Are you mad?",
                "I think I have the money right here, actually.",
            )) {
                FIRST_OPTION -> {
                    it.chatPlayer("99000 gold? Are you mad?")
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.")
                }
                SECOND_OPTION -> {
                    it.chatPlayer("I think I have the money right here, actually.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Magic."
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.MAGIC)
                        it.chatNpc("Excellent! Wear that cape with pride my friend.")
                    } else{
                        it.chatPlayer("But, unfortunately, I was mistaken.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
        SECOND_OPTION -> {
            player.openShop("Magic Guild Store - Mystic Robes")
        }
        THIRD_OPTION -> {
            //nothing, so close
        }
    }
}