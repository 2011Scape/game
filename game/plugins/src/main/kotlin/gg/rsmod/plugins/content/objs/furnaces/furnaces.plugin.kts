package gg.rsmod.plugins.content.objs.furnaces

import gg.rsmod.plugins.content.skills.smithing.Smelting

/**
 * The set of 'standard' furnaces
 * 3492, 4304, 6189, 9390, 11010, 11496, 11666, 12809, 14921, 24720, 24887, 26814, 30510, 36956, 37651, 45310, 50191, 50192, 50193, 52574, 54884, 55812, 56031, 56032, 56033, 56034, 56035, 56036, 61330
 */
val standardFurnaces = setOf(
    Objs.FURNACE_4304, Objs.FURNACE_6189, Objs.LAVA_FORGE, Objs.FURNACE_11010, Objs.FURNACE_11666, Objs.FURNACE_12809, Objs.SMALL_FURNACE_14921, Objs.FURNACE_24720, Objs.FURNACE_24887, Objs.FURNACE_26814, Objs.FURNACE_30510, Objs.FURNACE_36956, Objs.FURNACE_37651, Objs.FURNACE_45310, Objs.FURNACE_52574
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

on_obj_option(obj = 21303, option = "smelt-ore") {
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
on_item_on_obj(obj = 21303, item = Items.GOLD_BAR) {
    player.openJewelleryCraftingInterface()
}

/**
 * Lastly, if ores are used on the furnace, sends the ore smelting menu
 */
oresList.forEach { on_item_on_obj(obj = 21303, item = it) { Smelting.smeltStandard(player) } }