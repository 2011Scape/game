package gg.rsmod.plugins.content.objs.furnaces

import gg.rsmod.plugins.content.skills.smithing.Smelting
import kotlin.math.min

/**
 * The set of 'standard' furnaces
 */
val standardFurnaces =
    setOf(
        Objs.FURNACE_4304,
        Objs.FURNACE_6189,
        Objs.LAVA_FORGE,
        Objs.FURNACE_11010,
        Objs.FURNACE_11666,
        Objs.FURNACE_12809,
        Objs.SMALL_FURNACE_14921,
        Objs.FURNACE_24720,
        Objs.FURNACE_24887,
        Objs.FURNACE_26814,
        Objs.FURNACE_30510,
        Objs.FURNACE_36956,
        Objs.FURNACE_37651,
        Objs.FURNACE_45310,
        Objs.FURNACE_52574,
    )

val moltenGlassItems =
    setOf(
        Items.BUCKET_OF_SAND,
        Items.SODA_ASH,
    )

val moulds =
    listOf(
        Items.AMULET_MOULD,
        Items.BRACELET_MOULD,
        Items.HOLY_MOULD,
        Items.NECKLACE_MOULD,
        Items.RING_MOULD,
        Items.TIARA_MOULD,
        Items.SICKLE_MOULD,
        Items.UNHOLY_MOULD,
    )

/**
 * Makes a list of all the ores to use as smelting
 */
val oresList = mutableListOf<Int>()
Smelting.standardOreIds.forEach { if (!oresList.contains(it)) oresList.add(it) }

/**
 * Handle smelting at any 'standard' furnace
 */
standardFurnaces.forEach { furnace ->
    on_obj_option(obj = furnace, option = "smelt") {

        /**
         * Opens the smelting interface if ores are present in inventory
         */

        oresList.forEach { ore ->
            if (player.inventory.contains(ore)) {
                Smelting.smeltStandard(player)
            }
        }

        /**
         * Checks if there is a mould in inventory. If there is a mould from the list and a silver bar,
         * it will open the Silver crafting interface, and it will open the jewelry crafting interface if
         * there is a gold bar and a mould in the inventory.
         **/

        for (mould in moulds) {
            if (player.inventory.contains(mould) && player.inventory.contains(Items.SILVER_BAR)) {
                player.openSilverCraftingInterface()
                return@on_obj_option
            }
            if (player.inventory.contains(mould) && player.inventory.contains(Items.GOLD_BAR)) {
                player.openJewelleryCraftingInterface()
                return@on_obj_option
            }
        }
    }

    /**
     * If any molten glass components are used on a furnace, send the [produceItemBox] to make it.
     */
    moltenGlassItems.forEach {
        on_item_on_obj(obj = furnace, item = it) {
            val inventory = player.inventory
            if (!inventory.contains(Items.BUCKET_OF_SAND) || !inventory.contains(Items.SODA_ASH)) {
                player.queue {
                    doubleItemMessageBox(
                        "You need soda ash and buckets of sand to make molten glass.",
                        item1 = Items.SODA_ASH,
                        item2 = Items.BUCKET_OF_SAND,
                    )
                }
                return@on_item_on_obj
            }
            if (inventory.getItemCount(Items.BUCKET_OF_SAND) == 1 || inventory.getItemCount(Items.SODA_ASH) == 1) {
                handleMoltenGlass(player, Items.MOLTEN_GLASS, 1)
                return@on_item_on_obj
            }
            player.queue {
                produceItemBox(
                    Items.MOLTEN_GLASS,
                    maxItems =
                        min(
                            inventory.getItemCount(Items.SODA_ASH),
                            inventory.getItemCount(Items.BUCKET_OF_SAND),
                        ),
                    logic = ::handleMoltenGlass,
                )
            }
        }
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
     * Firstly, Opens the smelting interface if ores are present in inventory
     */
    oresList.forEach { ore ->
        if (player.inventory.contains(ore)) {
            Smelting.smeltStandard(player)
        }
    }

    /**
     * Next, Checks if gold bars are present in inventory and open Jewellery crafting in inventory
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
 * If ores are used on the furnace, sends the ore smelting menu
 */
oresList.forEach { on_item_on_obj(obj = 21303, item = it) { Smelting.smeltStandard(player) } }

fun handleMoltenGlass(
    player: Player,
    item: Int,
    amount: Int,
) {
    val inventory = player.inventory
    player.queue(TaskPriority.WEAK) {
        wait(2)
        repeat(amount) {
            if (!inventory.contains(Items.SODA_ASH) || !inventory.contains(Items.BUCKET_OF_SAND)) {
                return@queue
            }
            if (inventory.remove(Items.SODA_ASH, assureFullRemoval = true).hasSucceeded() &&
                inventory.remove(Items.BUCKET_OF_SAND, assureFullRemoval = true).hasSucceeded()
            ) {
                player.animate(id = 899)
                player.playSound(Sfx.FURNACE)
                inventory.add(Items.BUCKET, assureFullInsertion = true)
                inventory.add(item = item, assureFullInsertion = true)
                player.filterableMessage("You heat the sand and soda ash in the furnace to make glass.")
                player.addXp(Skills.CRAFTING, xp = 20.0)
                wait(3)
            }
        }
    }
}
