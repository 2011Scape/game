package gg.rsmod.plugins.content.objs.furnaces

import gg.rsmod.plugins.content.skills.smithing.Smelting

/**
 * The set of 'standard' furnaces
 */
val standardFurnaces = setOf(
    Objs.FURNACE_45310, Objs.FURNACE_11666
)

/**
 * Makes a list of all the ores to use as smelting
 */
val oresList = mutableListOf<Int>()
Smelting.standardOreIds.forEach {if (!oresList.contains(it)) oresList.add(it) }

/**
 * Handle smelting at any 'standard' furnace
 */
standardFurnaces.forEach { furnace ->

    on_obj_option(obj = furnace, option = "smelt") {
        /**
         * Checks first if ores are within the inventory, then send the smelting interface
         */
        oresList.forEach { ore ->
            if (player.inventory.contains(ore)) {
                Smelting.smeltStandard(player)
                return@on_obj_option
            }
        }
        /**
         * If no ores present, sends the Jewellery crafting interface if gold ore is in the interface
         */
        if (player.inventory.contains(Items.GOLD_BAR))
            player.openJewelleryCraftingInterface()
    }

    /**
     * If gold bar is used on furnace, sends the Jewellery crafting interface
     */
    on_item_on_obj(obj = furnace, item = Items.GOLD_BAR) {
        player.openJewelleryCraftingInterface()
    }

    /**
     * Lastly, if ores are used on the furnace, sends the ore smelting menu
     */
    oresList.forEach { on_item_on_obj(obj = furnace, item = it) { Smelting.smeltStandard(player) } }

}