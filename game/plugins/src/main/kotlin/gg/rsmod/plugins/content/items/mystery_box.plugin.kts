package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.plugins.content.drops.global.Rare
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.MYSTERY_BOX, option = "open") {
    if (player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    ) {
        val drop =
            DropTableFactory.createDropInventory(player, Items.MYSTERY_BOX, DropTableType.BOX) ?: return@on_item_option
        if (drop.isEmpty()) {
            player.message("Inside the box you find nothing! Better luck next time!")
            return@on_item_option
        }
        val item = drop[0]
        val def = world.definitions.get(ItemDef::class.java, item.id)
        val extraString =
            when (def.cost * item.amount) {
                in 0..100 -> "Better luck next time!"
                in 101..500 -> "Well, it could have been worse."
                else -> "Excellent!"
            }
        player.message(
            "Inside the box you find ${Misc.formatWithIndefiniteArticle(
                def.name.lowercase(),
                item.amount,
            )}! $extraString",
        )
    }
}

val mysteryBox =
    DropTableFactory.build {
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
/*    table("Holiday") {
        total(10)
        nothing(9)
        if(!player.hasItem(Items.SANTA_HAT)) {
            obj(Items.SANTA_HAT, slots = 1)
        } else {
            nothing(1)
        }
    }*/
    }

DropTableFactory.register(mysteryBox, Items.MYSTERY_BOX, type = DropTableType.BOX)
