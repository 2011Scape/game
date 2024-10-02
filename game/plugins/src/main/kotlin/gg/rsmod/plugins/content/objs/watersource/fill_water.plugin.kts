package gg.rsmod.plugins.content.objs.watersource

/**
 * Handles any option relating to water sources & containers.
 * @author Kevin Senez <ksenez94@gmail.com>
 */

val ids =
    setOf(
        Objs.SINK_873,
        Objs.SINK_874,
        Objs.SINK_4063,
        Objs.SINK_6151,
        Objs.SINK_8699,
        Objs.SINK_9143,
        Objs.SINK_9684,
        Objs.SINK_10175,
        Objs.SINK_13563,
        Objs.SINK_13564,
        Objs.SINK_14917,
        Objs.SINK_16704,
        Objs.SINK_16705,
        Objs.SINK_22715,
        Objs.SINK_24112,
        Objs.SINK_25729,
        Objs.SINK_25929,
        Objs.SINK_26966,
        Objs.SINK_29105,
        Objs.SINK_33458,
        Objs.SINK_34082,
        Objs.SINK_34496,
        Objs.SINK_34547,
        Objs.SINK_35762,
        Objs.SINK_36971,
        Objs.SINK_37154,
        Objs.SINK_37155,
        Objs.SINK_40063,
        Objs.SINK_43896,
        Objs.SINK_45299,
        Objs.SINK_52815,
        Objs.FOUNTAIN,
        Objs.FOUNTAIN_879,
        Objs.FOUNTAIN_880,
        Objs.FOUNTAIN_2864,
        Objs.FOUNTAIN_6232,
        Objs.FOUNTAIN_10436,
        Objs.FOUNTAIN_10437,
        Objs.FOUNTAIN_11007,
        Objs.FOUNTAIN_11759,
        Objs.FOUNTAIN_21764,
        Objs.FOUNTAIN_22973,
        Objs.FOUNTAIN_24161,
        Objs.FOUNTAIN_24214,
        Objs.FOUNTAIN_24265,
        Objs.FOUNTAIN_28662,
        Objs.FOUNTAIN_30820,
        Objs.FOUNTAIN_33364,
        Objs.FOUNTAIN_33374,
        Objs.FOUNTAIN_34579,
        Objs.FOUNTAIN_36781,
        Objs.FOUNTAIN_41448,
        Objs.FOUNTAIN_47150,
        Objs.FOUNTAIN_47747,
        Objs.FOUNTAIN_49354,
        Objs.FOUNTAIN_49355,
        Objs.FOUNTAIN_49356,
        Objs.FOUNTAIN_49357,
        Objs.FOUNTAIN_49358,
        Objs.FOUNTAIN_49359,
        Objs.FOUNTAIN_52568,
        Objs.FOUNTAIN_54410,
        Objs.FOUNTAIN_54411,
        Objs.FOUNTAIN_59987,
        Objs.FOUNTAIN_59988,
        Objs.FOUNTAIN_59989,
        Objs.FOUNTAIN_59990,
        Objs.WATER_PUMP,
        Objs.WATER_PUMP_15937,
        Objs.WATER_PUMP_15938,
        Objs.WATER_PUMP_61331,
        Objs.WATERPUMP,
        Objs.WATERPUMP_6827,
        Objs.WATERPUMP_34577,
        Objs.WATERPUMP_11661,
        Objs.WATER_BARREL_14353,
        Objs.WELL_884,
        Objs.WELL_5086,
        Objs.WELL_8747,
        Objs.WELL_26945,
        Objs.WELL_43094,
        Objs.WELL_43095,
        Objs.WELL_43096,
        Objs.WELL_43097,
        Objs.WELL_43098,
        Objs.WELL_43099,
        Objs.LARGE_GEYSER_26414,
        Objs.LARGE_GEYSER,
    )

/**
 * Handles using empty water containers on water sources to fill them up.
 */
ids.forEach { obj ->
    WaterContainerData.values.forEach { data ->
        on_item_on_obj(obj = obj, item = data.startItem) {
            val name =
                player.world.definitions
                    .get(
                        ItemDef::class.java,
                        data.startItem,
                    ).name
                    .lowercase()
                    .replace("empty ", "")
            player.queue {
                repeat(player.inventory.getItemCount(data.startItem)) {
                    if (player.inventory.remove(data.startItem, assureFullRemoval = true).hasSucceeded()) {
                        player.animate(id = 832)
                        player.playSound(Sfx.FIRE_DOOR_PASS)
                        player.inventory.add(data.resultItem, assureFullInsertion = true)
                        player.filterableMessage("You fill the $name with water.")
                        wait(2)
                    }
                }
            }
        }
    }
}

/**
 * Handles the 'empty' option on water containers.
 */
WaterContainerData.values.forEach { data ->
    if (world.definitions
            .get(ItemDef::class.java, data.resultItem)
            .inventoryMenu
            .contains("empty")
    ) {
        on_item_option(data.resultItem, "empty") {
            val slot = player.getInteractingSlot()
            if (player.inventory.remove(data.resultItem, assureFullRemoval = true, beginSlot = slot).hasSucceeded()) {
                val name =
                    player.world.definitions
                        .get(ItemDef::class.java, data.resultItem)
                        .name
                        .lowercase()
                player.inventory.add(data.startItem, assureFullInsertion = true, beginSlot = slot)
                player.filterableMessage("You empty the $name.")
            }
        }
    }
}
