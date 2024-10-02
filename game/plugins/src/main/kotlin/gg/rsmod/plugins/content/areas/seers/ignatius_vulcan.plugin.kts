package gg.rsmod.plugins.content.areas.seers
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.IGNATIUS_VULCAN, option = "talk-to") {
    player.queue {
        mainChat(this, player)
    }
}

create_shop(
    "Ignatius' Hot Deals",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.TINDERBOX_590, amount = 60000, sellPrice = 1)
    items[1] = ShopItem(Items.UNLIT_TORCH, amount = 10)
    items[2] = ShopItem(Items.BRONZE_HATCHET, amount = 10)
}

on_npc_option(npc = Npcs.IGNATIUS_VULCAN, option = "trade") {
    player.openShop("Ignatius' Hot Deals")
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Can I help you at all?",
    )
    when (
        it.options(
            "Who are you?",
            "Do you have anything for trade?",
            "Can I buy a Skillcape of Firemaking?",
            "No, thanks.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Who are you?")
            it.chatNpc(
                "My name is Ignatius Vulcan. Once I was - like you - an",
                "adventurer, but that was before I realised the beauty",
                "and power of flame! Just look at this...",
            )
            it.chatNpc(
                "Stare into the flame and witness the purity and power",
                "of fire! As my attraction to flame grew, so did my",
                "skills at firelighting. I began to neglect my combat",
                "skills, my Mining skills and my questing. ",
            )
            it.chatNpc(
                "Who needs such mundane skills when one can harness the",
                "power of fire? After years of practice I am now the",
                "acknowledged Master of Flame!",
                "Everything must be purified by fire",
            )
            it.chatPlayer("Okaaaay! Err, I'll be going now. Umm, get better soon.")
        }

        SECOND_OPTION -> {
            it.chatPlayer("Do you have anything for trade?")
            player.openShop("Ignatius' Hot Deals")
        }

        THIRD_OPTION -> {
            it.chatPlayer("Can I buy a Skillcape of Firemaking?")
            if (player.skills.getCurrentLevel(Skills.FIREMAKING) >= 99) {
                it.chatNpc(
                    "Of course you can. You are already a master of fire,",
                    "you just need the cape to prove it. That'll be 99000",
                    "coins, please.",
                )
                when (
                    it.options(
                        "No thanks, that's too much.",
                        "Okay, that seems reasonable.",
                    )
                ) {
                    FIRST_OPTION -> {
                        it.chatPlayer("No thanks, that's too much.")
                        it.chatNpc(
                            "Not at all; there are many other adventurers who",
                            "would love the opportunity to purchase such a",
                            "prestigious item! You can find me here if you change",
                            "your mind.",
                        )
                    }
                    SECOND_OPTION -> {
                        it.chatPlayer("Okay, that seems reasonable.")
                        if (it.player.inventory.freeSlotCount < 2) {
                            it.chatNpc(
                                "You don't have enough free space in your inventory ",
                                "for me to sell you a Skillcape of Firemaking.",
                            )
                            it.chatNpc("Come back to me when you've cleared up some space.")
                            return
                        }
                        if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                            Skills.purchaseSkillcape(it.player, data = Skillcapes.FIREMAKING)
                            it.chatNpc("I'm sure you'll look hot in that cape.")
                        } else {
                            it.chatPlayer("But, unfortunately, I don't have enough gold.")
                            it.chatNpc("Well, come back and see me when you do.")
                        }
                    }
                }
            } else {
                it.chatNpc(
                    "Unfortunately, I cannot sell you the Skillcape of",
                    "Firemaking. Come back when you have reached 99",
                    "in the skill.",
                )
            }
        }
        FOURTH_OPTION -> {
            it.chatPlayer("No, thanks.")
            it.chatNpc("Okay, keep the fire burning.")
        }
    }
}
