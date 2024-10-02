package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.game.model.attr.BAR_TYPE
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.DoricsQuest
import gg.rsmod.plugins.content.skills.smithing.data.BarProducts
import gg.rsmod.plugins.content.skills.smithing.data.BarType
import gg.rsmod.plugins.content.skills.smithing.data.SmithingType


val SMITHING_INTERFACE = 300

val smithingAction = SmithingAction(world.definitions)

on_button(interfaceId = SMITHING_INTERFACE, component = SmithingType.retrieveAllButtons()) {
    val product =
        BarProducts.getProductId(
            player.getInteractingButton(),
            BarType.values.first {
                player.attr[BAR_TYPE] ==
                    it.item
            },
        )
    val bar = BarProducts.forId(product)
    var amount = SmithingType.forButton(player, bar, player.getInteractingButton(), bar!!.barType.item)
    if (amount == -1) {
        player.queue(TaskPriority.WEAK) {
            amount = inputInt("How many would you like to smith?")
            if (amount > 0) {
                player.closeInterface(interfaceId = SMITHING_INTERFACE)
                player.queue {
                    smithingAction.smith(this, bar, amount)
                }
            }
        }
    } else {
        player.queue(TaskPriority.STRONG) {
            smithingAction.smith(this, bar, amount)
        }
    }
}

val DORICS_ANVIL = Objs.ANVIL

val anvils = arrayOf(DORICS_ANVIL, Objs.ANVIL_12692, Objs.ANVIL_2783, Objs.ANVIL_24744, Objs.BARBARIAN_ANVIL)

anvils.forEach { anvil ->
    BarType.values.forEach { bar ->
        on_item_on_obj(anvil, item = bar.item) {
            if (anvil == DORICS_ANVIL) {
                if (!checkDoricsQuest(player)) {
                    player.interruptQueues()
                    return@on_item_on_obj
                }
            }
            if (!player.inventory.contains(Items.HAMMER)) {
                player.queue {
                    messageBox("You need a hammer to work the metal with.")
                }
                return@on_item_on_obj
            }
            if (player.skills.getCurrentLevel(Skills.MINING) < bar.levelRequired) {
                player.queue {
                    messageBox(
                        "You need a Smithing level of at least ${bar.levelRequired} to work ${bar.name.lowercase()} bars.",
                    )
                }
                return@on_item_on_obj
            }
            player.buildSmithingInterface(bar)
        }
    }

    on_obj_option(anvil, option = "Smith") {
        if (anvil == DORICS_ANVIL) {
            if (!checkDoricsQuest(player)) {
                player.interruptQueues()
                return@on_obj_option
            }
        }
        if (!player.inventory.contains(Items.HAMMER)) {
            player.queue {
                messageBox("You need a hammer to work the metal with.")
            }
            return@on_obj_option
        }

        val bar =
            BarType.values.reversed().firstOrNull { bar ->
                player.inventory.filterNotNull().any { item -> item.id == bar.item } &&
                    player.skills.getCurrentLevel(Skills.SMITHING) >= bar.levelRequired
            }
        if (bar == null) {
            player.queue {
                doubleMessageBox("You should select an item from your inventory and use it on the", "anvil.")
            }
            return@on_obj_option
        }
        player.buildSmithingInterface(bar)
    }
}

fun checkDoricsQuest(player: Player): Boolean {
    if (!player.finishedQuest(DoricsQuest)) {
        player.queue {
            chatNpc(
                "Hey, who said you could use that? My anvils get",
                "enough work with my own use. I make pickaxes, and it",
                "takes a lot of hard work.",
                npc = Npcs.DORIC,
            )
            when (options("Sorry, would it be OK if I used your anvils?", "I didn't want to use your anvils anyway.")) {
                1 -> {
                    chatPlayer("Sorry, would it be OK if I used your anvils?")
                    chatNpc(
                        "If you could get me some more materials then I could",
                        "let you use them.",
                        npc = Npcs.DORIC,
                    )
                }
                2 -> {
                    chatPlayer("I didn't want to use your anvils anyway.")
                    chatNpc("That is your choice.", npc = Npcs.DORIC)
                }
            }
        }
        return false
    }
    return true
}
