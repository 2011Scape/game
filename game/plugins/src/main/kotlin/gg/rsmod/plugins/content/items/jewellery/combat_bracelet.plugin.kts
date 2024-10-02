package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.game.model.Tile
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1

val COMBAT_BRACELET =
    intArrayOf(
        Items.COMBAT_BRACELET_4,
        Items.COMBAT_BRACELET_3,
        Items.COMBAT_BRACELET_2,
        Items.COMBAT_BRACELET_1,
    )

private val LOCATIONS =
    mapOf(
        "Warriors' Guild" to Tile(2881, 3542, 0),
        "Champions' Guild" to Tile(3191, 3367, 0),
        "Monastery" to Tile(3052, 3490, 0),
        "Ranging Guild" to Tile(2654, 3441, 0),
    )

on_equipment_option(item = Items.COMBAT_BRACELET, option = "Rub") {
    player.message(
        "You will need to recharge your combat bracelet before you can use it again.",
        type = ChatMessageType.GAME_MESSAGE,
    )
}
on_item_option(item = Items.COMBAT_BRACELET, option = "Rub") {
    player.message("The bracelet has lost its charge.", type = ChatMessageType.GAME_MESSAGE)
    player.message("It will need to be recharged before you can use it again.", type = ChatMessageType.GAME_MESSAGE)
}

COMBAT_BRACELET.forEach { item ->
    on_item_option(item = item, option = "Rub") {
        player.queue {
            when (options("Warriors' Guild.", "Champions' Guild.", "Monastery.", "Ranging Guild.", "Nowhere.")) {
                1 -> player.teleport(LOCATIONS["Warriors' Guild"]!!, isEquipped = false)
                2 -> player.teleport(LOCATIONS["Champions' Guild"]!!, isEquipped = false)
                3 -> player.teleport(LOCATIONS["Monastery"]!!, isEquipped = false)
                4 -> player.teleport(LOCATIONS["Ranging Guild"]!!, isEquipped = false)
            }
        }
    }
}

COMBAT_BRACELET.forEach { item ->
    LOCATIONS.forEach { (location, endTile) ->
        on_equipment_option(item, option = location) {
            player.queue(TaskPriority.STRONG) {
                player.teleport(endTile, isEquipped = true)
            }
        }
    }
}

/**
 * Teleports the player using jewellery and updates the item's charges.
 *
 * @param endTile The destination tile for the player after teleportation.
 * @param isEquipped Indicates if the item is equipped on the player (true) or in the inventory (false).
 */
fun Player.teleport(
    endTile: Tile,
    isEquipped: Boolean,
) {
// Check if the player can teleport using items
    if (canTeleport(TeleportType.JEWELRY)) {
// Play a sound effect in the area around the player
        world.spawn(AreaSound(tile, SOUNDAREA_ID, SOUNDAREA_RADIUS, SOUNDAREA_VOLUME))

// Get the replacement bracelet with updated charges
        val replacement = replacement(getInteractingItemId())

        if (isEquipped) {
// Update the equipped item, or remove it if there are no charges left
            equipment[EquipmentType.GLOVES.id] = if (replacement > -1) Item(replacement) else null
        } else {
// Remove the item from the inventory and add the replacement if there are charges left
            inventory.remove(getInteractingItem())
            if (replacement > -1) {
                inventory.add(replacement)
            }
        }

// Teleport the player to the destination tile
        this@teleport.teleport(endTile, TeleportType.JEWELRY)

// Send a message to the player about the remaining bracelet charges
        message(message(getInteractingItemId()))
    }
}

/**
 * Returns the replacement item with updated charges.
 *
 * @param original The original item ID.
 * @return The replacement item ID, or -1 if the original item is out of charges.
 */
fun replacement(original: Int): Int {
    return when (original) {
        in Items.COMBAT_BRACELET_4..Items.COMBAT_BRACELET_2 -> original + 2
        else -> -1
    }
}

/**
 * Returns a message with the remaining charges for the specified item.
 *
 * @param original The original item ID.
 * @return A message with the remaining charges, or a message indicating the item has crumbled to dust.
 */
fun message(original: Int): String {
    return when (original) {
        Items.COMBAT_BRACELET_4 -> "Your combat bracelet has three charges left."
        Items.COMBAT_BRACELET_3 -> "Your combat bracelet has two charges left."
        Items.COMBAT_BRACELET_2 -> "Your combat bracelet has one charge left."
        else -> "You use your combat bracelet's last charge."
    }
}
