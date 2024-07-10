package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType
import gg.rsmod.util.Misc.formatWithIndefiniteArticle

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_option(item = Items.CASKET, option = "open") {
    if (player.inventory
            .remove(
                player.getInteractingItem(),
                beginSlot = player.getInteractingItemSlot(),
            ).hasSucceeded()
    ) {
        val drop =
            DropTableFactory.createDropInventory(player, Items.CASKET, DropTableType.CHEST) ?: return@on_item_option
        val item = drop[0]
        player.queue {
            val name =
                world.definitions
                    .get(ItemDef::class.java, item.id)
                    .name
                    .lowercase()
            itemMessageBox(
                "You open the casket. Inside you find ${formatWithIndefiniteArticle(name)}.",
                item = item.id,
                amountOrZoom = item.amount,
            )
        }
    }
}

val table = DropTableFactory
val casketRewards =
    table.build {
        main {
            total(128)
            obj(Items.COINS_995, quantity = 20, slots = 10)
            obj(Items.COINS_995, quantity = 40, slots = 10)
            obj(Items.COINS_995, quantity = 80, slots = 10)
            obj(Items.COINS_995, quantity = 160, slots = 10)
            obj(Items.COINS_995, quantity = 320, slots = 10)
            obj(Items.COINS_995, quantity = 640, slots = 10)

            obj(Items.UNCUT_SAPPHIRE, slots = 32)
            obj(Items.UNCUT_EMERALD, slots = 16)
            obj(Items.UNCUT_RUBY, slots = 8)
            obj(Items.UNCUT_DIAMOND, slots = 2)

            obj(Items.COSMIC_TALISMAN, slots = 8)
            obj(Items.LOOP_HALF_OF_A_KEY, slots = 1)
            obj(Items.TOOTH_HALF_OF_A_KEY, slots = 1)
        }
    }

table.register(casketRewards, Items.CASKET, type = DropTableType.CHEST)
