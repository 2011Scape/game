package gg.rsmod.plugins.content.skills.smithing

import gg.rsmod.game.model.attr.BAR_TYPE
import gg.rsmod.plugins.content.skills.smithing.data.BarType
import gg.rsmod.plugins.content.skills.smithing.data.BarProducts
import gg.rsmod.plugins.content.skills.smithing.data.SmithingType


val SMITHING_INTERFACE = 300

val smithingAction = SmithingAction(world.definitions)

on_button(interfaceId = SMITHING_INTERFACE, component = SmithingType.retrieveAllButtons()) {
    val product = BarProducts.getProductId(player.getInteractingButton(), BarType.values.first { player.attr[BAR_TYPE] == it.item })
    val bar = BarProducts.forId(product)
    val amount = SmithingType.forButton(player, bar, player.getInteractingButton(), bar!!.barType.item)
    player.closeInterface(interfaceId = SMITHING_INTERFACE)
    player.queue {
        smithingAction.smith(this, bar, amount)
    }
}

val anvils = arrayOf(Objs.ANVIL_12692, Objs.ANVIL_2783)

anvils.forEach { anvil ->
    BarType.values.forEach { bar ->
        on_item_on_obj(anvil, item = bar.item) {
            if(!player.inventory.contains(Items.HAMMER)) {
                player.queue {
                    messageBox("You need a hammer to work the metal with.")
                }
                return@on_item_on_obj
            }
            if(player.getSkills().getCurrentLevel(Skills.MINING) < bar.levelRequired) {
                player.queue {
                    messageBox("You need a Smithing level of at least ${bar.levelRequired} to work ${bar.name.lowercase()} bars.")
                }
                return@on_item_on_obj
            }
            player.buildSmithingInterface(bar)
        }
    }

    on_obj_option(anvil, option = "Smith") {

        if(!player.inventory.contains(Items.HAMMER)) {
            player.queue {
                messageBox("You need a hammer to work the metal with.")
            }
            return@on_obj_option
        }

        val bar = BarType.values.reversed().firstOrNull { bar ->
            player.inventory.filterNotNull().any { item -> item.id == bar.item } &&
                    player.getSkills().getCurrentLevel(Skills.SMITHING) >= bar.levelRequired
        }
        if(bar == null) {
            player.queue {
                messageBox("You should select an item from your inventory and use it on the<br><br>anvil.")
            }
            return@on_obj_option
        }
        player.buildSmithingInterface(bar)
    }
}