package gg.rsmod.plugins.content.areas.wilderness

import gg.rsmod.game.model.attr.prayedAtStatue
import gg.rsmod.game.model.attr.receivedStaff
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency


val chamberGuardian = Npcs.CHAMBER_GUARDIAN

create_shop(
    "Mage Arena Staves",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.SARADOMIN_STAFF, amount = 10, sellPrice = 80000, buyPrice = 40000)
    items[1] = ShopItem(Items.GUTHIX_STAFF, amount = 10, sellPrice = 80000, buyPrice = 40000)
    items[2] = ShopItem(Items.ZAMORAK_STAFF, amount = 10, sellPrice = 80000, buyPrice = 40000)
}

on_npc_option(chamberGuardian, option = "talk-to") {
    player.queue {
        if (player.attr.has(prayedAtStatue)) {
            if (player.attr.has(receivedStaff)) {
                afterClaimingFreeStaffDialogue(this)
            } else {
                afterPrayingAtStatueDialogue(this)
            }
        } else {
            beforePrayingAtStatueDialogue(this)
        }
    }
}

on_npc_option(chamberGuardian, option = "trade") {
    player.openShop("Mage Arena Staves")
}

suspend fun beforePrayingAtStatueDialogue(it: QueueTask) {
    it.chatPlayer("Hello my friend, Kolodion sent me down.")
    it.chatNpc(
        "Sssshhh... the gods are talking. I can hear their whispers.",
        "Can you hear them adventurer, they're calling you.",
    )
    it.chatPlayer("Erm... ok!")
    it.chatNpc(
        "Go chant at the statue of the god you most wish to",
        "represent in this world, you will be rewarded. Once",
        "you are done, come back to me. I shall supply you",
        "with a mage staff ready for battle.",
    )
}

suspend fun afterPrayingAtStatueDialogue(it: QueueTask) {
    it.chatPlayer("Hi.")
    it.chatNpc("Hello adventurer, have you made your choice?")
    it.chatPlayer("I have.")
    it.chatNpc(
        "Good, good, I hope you have chosen well. I will now",
        "present you with a magic staff. This, along with the",
        "cape awarded to you by your chosen god, are all the",
        "weapons and armour you will need here.",
    )
    if (it.player.hasItem(Items.ZAMORAK_CAPE)) {
        it.player.inventory.add(Items.ZAMORAK_STAFF)
    }
    if (it.player.hasItem(Items.GUTHIX_CAPE)) {
        it.player.inventory.add(Items.GUTHIX_STAFF)
    }
    if (it.player.hasItem(Items.SARADOMIN_CAPE)) {
        it.player.inventory.add(Items.SARADOMIN_STAFF)
    }
    it.player.attr.put(receivedStaff, true)
}

suspend fun afterClaimingFreeStaffDialogue(it: QueueTask) {
    it.chatPlayer("Hello again.")
    it.chatNpc("Hello adventurer, are you looking for another staff?")
    val option = it.options("What do you have to offer?", "No thanks.")
    when (option) {
        1 -> {
            it.player.openShop("Mage Arena Staves")
        }
        2 -> {
            it.chatPlayer("No thanks.")
            it.chatNpc("Well let me know if you need one.")
        }
    }
}
