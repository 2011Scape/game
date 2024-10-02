package gg.rsmod.plugins.content.items

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

/**
 * @author Alycia <https://github.com/alycii>
 */

on_item_on_player(item = Items.CHRISTMAS_CRACKER) {
    val target = player.getInteractingPlayer()
    if (target.isDead() || target.isLocked() || target.interfaces.currentModal != -1) {
        player.message("The other player is currently busy.")
        return@on_item_on_player
    }
    if (!target.inventory.hasSpace) {
        player.message("The other player doesn't have enough inventory space to do this.")
        return@on_item_on_player
    }
    if (player.inventory.remove(Items.CHRISTMAS_CRACKER).hasSucceeded()) {
        player.animate(151)
        player.graphic(176)
        val winner = world.random(2) == 1
        if (winner) {
            player.forceChat("Hey! I got the cracker!")
        } else {
            target.forceChat("Hey! I got the cracker!")
        }
        DropTableFactory.createDropInventory(
            if (winner) player else target,
            Items.CHRISTMAS_CRACKER,
            DropTableType.CHEST,
        )
        DropTableFactory.createDropInventory(
            if (winner) target else player,
            Items.CHRISTMAS_CRACKER_NOTED,
            DropTableType.CHEST,
        )
    }
}

val table = DropTableFactory
val partyHats =
    table.build {
        main {
            total(128)
            obj(Items.RED_PARTYHAT, slots = 32)
            obj(Items.YELLOW_PARTYHAT, slots = 28)
            obj(Items.WHITE_PARTYHAT, slots = 23)
            obj(Items.GREEN_PARTYHAT, slots = 20)
            obj(Items.BLUE_PARTYHAT, slots = 15)
            obj(Items.PURPLE_PARTYHAT, slots = 10)
        }
    }

val commonItems =
    table.build {
        main {
            total(128)
            obj(Items.CHOCOLATE_BAR, slots = 24)
            obj(Items.CHOCOLATE_CAKE, slots = 16)
            obj(Items.GOLD_RING, slots = 10)
            obj(Items.IRON_ORE_NOTED, quantity = 5, slots = 14)
            obj(Items.BLACK_DAGGER, slots = 6)
            obj(Items.LAW_RUNE, slots = 4)
            obj(Items.SPINACH_ROLL, slots = 16)
            obj(Items.SILK, slots = 10)
            obj(Items.HOLY_SYMBOL, slots = 10)
            obj(Items.SILVER_BAR, slots = 18)
        }
    }

table.register(partyHats, Items.CHRISTMAS_CRACKER, type = DropTableType.CHEST)
table.register(commonItems, Items.CHRISTMAS_CRACKER_NOTED, type = DropTableType.CHEST)
