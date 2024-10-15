package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Requirement
import gg.rsmod.plugins.api.cfg.SkillRequirement
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * Contains the requirements needed to interact with Ava, as well as her shop inventory and dialogue options.
 *
 * @author Alycia <https://github.com/alycii>
 */
val requirements =
    listOf<Requirement>(
        SkillRequirement(skill = Skills.RANGED, level = 30),
        SkillRequirement(skill = Skills.WOODCUTTING, level = 35),
        SkillRequirement(skill = Skills.CRAFTING, level = 19),
        SkillRequirement(skill = Skills.SLAYER, level = 18),
    )

/**
 * Creates Ava's shop inventory.
 *
 * @param currency The currency used by the shop.
 * @param purchasePolicy The shop's purchase policy.
 * @param containsSamples Whether the shop's inventory contains sample items.
 */
create_shop(
    "Ava's Odds and Ends",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_NONE,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.FEATHER, 1000)
    items[1] = ShopItem(Items.IRON_ARROW, 1000)
    items[2] = ShopItem(Items.STEEL_ARROW, 1000)
    items[3] = ShopItem(Items.IRON_ARROWTIPS, 1000)
    items[4] = ShopItem(Items.STEEL_ARROWTIPS, 1000)
}

/**
 * Defines the behavior of the "trade" option for Ava.
 *
 * @param npc The NPC that was clicked.
 * @param option The option that was selected.
 */
on_npc_option(npc = Npcs.AVA, option = "trade") {
    if (!checkRequirements(player)) {
        return@on_npc_option
    }
    player.openShop("Ava's Odds and Ends")
}

/**
 * Defines the behavior of the "talk-to" option for Ava.
 *
 * @param npc The NPC that was clicked.
 * @param option The option that was selected.
 */
on_npc_option(npc = Npcs.AVA, option = "talk-to") {
    if (!checkRequirements(player)) {
        return@on_npc_option
    }
    player.queue {
        chatNpc(
            *"Hello there and welcome to my humble abode. It's sadly rather more humble than I'd like, to be honest, although perhaps you can help with that?"
                .splitForDialogue(),
        )
        chatPlayer(
            *"I would be happy to make your home a better place."
                .splitForDialogue(),
        )
        chatNpc(
            *"Yay, I didn't even have to talk about a reward; you're more gullible than most adventurers, that's for sure."
                .splitForDialogue(),
        )
        chatPlayer(
            *"Err, well when you put it that way.. I think I'd rather just buy a device from you."
                .splitForDialogue(),
        )
        purchaseDialogue(this)
    }
}

/**
 * Defines the behavior of the "buy device" option for Ava.
 *
 * @param npc The NPC that was clicked.
 * @param option The option that was selected.
 */
on_npc_option(npc = Npcs.AVA, option = "buy device") {
    if (!checkRequirements(player)) {
        return@on_npc_option
    }
    player.queue {
        purchaseDialogue(this)
    }
}

/**
 * Checks if the player meets the requirements to purchase an item from the shop.
 *
 * @param player The player whose requirements need to be checked.
 * @return {@code true} if the player meets all the requirements, {@code false} otherwise.
 */
fun checkRequirements(player: Player): Boolean {
    val meetsRequirement = requirements.all { it.hasRequirement(player) }
    if (!meetsRequirement) {
        player.message("She doesn't seem interested in talking to you.")
        player.message("You'll need 30 Ranged, 35 Woodcutting, 19 Crafting and 18 Slayer to get her attention.")
        return false
    }
    return true
}

/**
 * Handles the purchase dialogue for the shop.
 *
 * @param it The QueueTask object representing the player's interaction with the shop.
 */
suspend fun purchaseDialogue(it: QueueTask) {
    if (!it.player.inventory.hasSpace) {
        it.chatNpc(*"You'll need some inventory space before I can sell you anything.".splitForDialogue())
        return
    }
    when (it.options("The attractor", "The accumulator", title = "I would like to buy:")) {
        FIRST_OPTION -> {
            if (it.player.inventory
                    .remove(Items.COINS_995, amount = 999)
                    .hasSucceeded()
            ) {
                it.player.inventory.add(Items.AVAS_ATTRACTOR)
                it.messageBox("You buy a new attractor for 999 coins.")
            } else {
                it.chatNpc("I'll need 999 coins from you for the attractor.")
            }
        }

        SECOND_OPTION -> {
            if (it.player.inventory.getItemCount(Items.COINS_995) < 999 ||
                it.player.inventory.getItemCount(Items.STEEL_ARROW) < 75
            ) {
                it.chatNpc(*"I'll need 999 coins, and 75 steel arrows from you for the accumulator.".splitForDialogue())
                return
            }
            if (it.player.skills.getCurrentLevel(Skills.RANGED) < 50) {
                it.chatNpc(
                    *"I'm afraid you aren't yet skilled enough for the upgraded version. You need a Ranged level of 50 or greater."
                        .splitForDialogue(),
                )
                return
            }
            if (it.player.inventory
                    .remove(Items.COINS_995, amount = 999)
                    .hasSucceeded() &&
                it.player.inventory
                    .remove(
                        Items.STEEL_ARROW,
                        75,
                    ).hasSucceeded()
            ) {
                it.player.inventory.add(Items.AVAS_ACCUMULATOR)
                it.messageBox("You buy a new accumulator for 999 coins and 75 arrows.")
            } else {
                it.chatNpc(*"I'll need 999 coins, and 75 steel arrows from you for the accumulator.".splitForDialogue())
            }
        }
    }
}
