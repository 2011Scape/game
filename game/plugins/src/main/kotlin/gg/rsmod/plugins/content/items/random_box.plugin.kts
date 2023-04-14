package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.Rare
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.RANDOM_EVENT_GIFT, option = "open") {
    if(player.inventory.remove(player.getInteractingItem(), beginSlot = player.getInteractingItemSlot()).hasSucceeded()) {
        val drop = DropTableFactory.createDropInventory(player, Items.RANDOM_EVENT_GIFT, DropTableType.STALL) ?: return@on_item_option
        val item = drop[0]
        val def = world.definitions.get(ItemDef::class.java, item.id)
        val extraString = when(def.cost * item.amount) {
            in 0..100 -> "Better luck next time!"
            in 101..500 -> "Well, it could have been worse."
            else -> "Excellent!"
        }
        player.message("Inside the box you find ${Misc.formatWithIndefiniteArticle(def.name.lowercase(), item.amount)}! $extraString")
    }
}

val randomBox = DropTableFactory.build {
    main {
        total(13)
        obj(Items.CABBAGE, slots = 1)
        obj(Items.DIAMOND, slots = 1)
        obj(Items.BUCKET, slots = 1)
        obj(Items.COINS_995, quantity = 500, slots = 1)
        obj(Items.FLIER, slots = 1)
        obj(Items.OLD_BOOT, slots = 1)
        obj(Items.BODY_RUNE, slots = 1)
        obj(Items.ONION, slots = 1)
        obj(Items.MITHRIL_SCIMITAR, slots = 1)
        obj(Items.CASKET, slots = 1)
        obj(Items.STEEL_PLATEBODY, slots = 1)
        obj(Items.NATURE_RUNE, quantity = 20, slots = 1)
        table(Rare.rareTable, slots = 1)
    }
    table("Camo") {
        total(3)
        nothing(0)

        // Check if the player has all camo items
        val hasAllCamoItems = player.hasItem(Items.CAMO_TOP) && player.hasItem(Items.CAMO_BOTTOMS) && player.hasItem(Items.CAMO_HELMET)

        if (!player.hasItem(Items.CAMO_TOP) && !hasAllCamoItems) {
            obj(Items.CAMO_TOP, slots = 1)
        } else {
            obj(Items.COINS, quantity = 500, slots = 1)
        }

        if (!player.hasItem(Items.CAMO_BOTTOMS) && !hasAllCamoItems) {
            obj(Items.CAMO_BOTTOMS, slots = 1)
        } else {
            obj(Items.COINS, quantity = 500, slots = 1)
        }

        if (!player.hasItem(Items.CAMO_HELMET) && !hasAllCamoItems) {
            obj(Items.CAMO_HELMET, slots = 1)
        } else {
            obj(Items.COINS, quantity = 500, slots = 1)
        }
    }
}

DropTableFactory.register(randomBox, Items.RANDOM_EVENT_GIFT, type = DropTableType.STALL)