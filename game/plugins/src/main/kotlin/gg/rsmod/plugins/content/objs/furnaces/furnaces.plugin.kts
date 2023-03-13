package gg.rsmod.plugins.content.objs.furnaces

import gg.rsmod.plugins.content.skills.smithing.Smelting

/**
 * The set of 'standard' furnaces
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
         * Checks first if gold bars are present in inventory and open Jewellery crafting in inventory
         * if that's the case
         */
        if (player.inventory.contains(Items.GOLD_BAR)) {
            player.openJewelleryCraftingInterface()
            return@on_obj_option
        }

        /**
         * If no gold bars present, check and send the silver crafting interface if silver bars are present.
         */
        if (player.inventory.contains(Items.SILVER_BAR)) {
            player.openSilverCraftingInterface()
            return@on_obj_option
        }

        /**
         * Lastly, Opens the smelting interface if gold or silver bars aren't present in inventory.
         */
        Smelting.smeltStandard(player)
    }

    /**
     * If gold bar is used on furnace, sends the Jewellery crafting interface
     */
    on_item_on_obj(obj = furnace, item = Items.GOLD_BAR) {
        player.openJewelleryCraftingInterface()
    }

    /**
     * If silver bar is used on furnace, sends the Silver crafting interface
     */
    on_item_on_obj(obj = furnace, item = Items.SILVER_BAR) {
        player.openSilverCraftingInterface()
    }

    /**
     * Lastly, if ores are used on the furnace, sends the ore smelting menu
     */
    oresList.forEach { on_item_on_obj(obj = furnace, item = it) { Smelting.smeltStandard(player) } }

}

on_obj_option(obj = 21303, option = "smelt-ore") {
    /**
     * Checks first if gold bars are present in inventory and open Jewellery crafting in inventory
     * if that's the case
     */
    if (player.inventory.contains(Items.GOLD_BAR)) {
        player.openJewelleryCraftingInterface()
        return@on_obj_option
    }

    /**
     * If no gold bars present, check and send the silver crafting interface if silver bars are present.
     */
    if (player.inventory.contains(Items.SILVER_BAR)) {
        player.openSilverCraftingInterface()
        return@on_obj_option
    }

    /**
     * Lastly, Opens the smelting interface if gold or silver bars aren't present in inventory.
     */
    Smelting.smeltStandard(player)
}

/**
 * If gold bar is used on furnace, sends the Jewellery crafting interface
 */
on_item_on_obj(obj = 21303, item = Items.GOLD_BAR) {
    player.openJewelleryCraftingInterface()
}

/**
 * If silver bar is used on furnace, sends the Silver crafting interface
 */
on_item_on_obj(obj = 21303, item = Items.SILVER_BAR) {
    player.openSilverCraftingInterface()
}

/**
 * Lastly, if ores are used on the furnace, sends the ore smelting menu
 */
oresList.forEach { on_item_on_obj(obj = 21303, item = it) { Smelting.smeltStandard(player) } }