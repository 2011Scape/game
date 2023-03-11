package gg.rsmod.plugins.content.objs.furnaces

import gg.rsmod.plugins.content.skills.smithing.Smelting

/**
 * The set of 'standard' furnaces
 */
val standardFurnaces = setOf(
    Objs.FURNACE_45310, Objs.FURNACE_11666
)

/**
 * Handle smelting at any 'standard' furnace
 */
standardFurnaces.forEach { furnace ->

    on_obj_option(obj = furnace, option = "smelt") { Smelting.smeltStandard(player) }

    on_item_on_obj(obj = furnace, item = Items.GOLD_BAR) {
        player.openJewelleryCraftingInterface()
    }

    val oresList = mutableListOf<Int>()
    Smelting.standardOreIds.forEach {if (!oresList.contains(it)) oresList.add(it) }
    oresList.forEach { on_item_on_obj(obj = furnace, item = it) { Smelting.smeltStandard(player) } }

}