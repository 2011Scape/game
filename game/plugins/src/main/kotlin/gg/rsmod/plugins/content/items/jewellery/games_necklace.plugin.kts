package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

val GAMES_NECKLACE =
    intArrayOf(
        Items.GAMES_NECKLACE_8,
        Items.GAMES_NECKLACE_7,
        Items.GAMES_NECKLACE_6,
        Items.GAMES_NECKLACE_5,
        Items.GAMES_NECKLACE_4,
        Items.GAMES_NECKLACE_3,
        Items.GAMES_NECKLACE_2,
        Items.GAMES_NECKLACE_1,
    )

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1

private val LOCATIONS =
    mapOf(
        "Burthorpe" to Tile(2899, 3546, 0),
        "Barbarian Outpost" to Tile(2520, 3571, 0),
        "Gamers' Grotto" to Tile(2970, 9673, 0),
        "Corporeal Beast" to Tile(2885, 4372, 2),
    )

GAMES_NECKLACE.forEach { item ->
    on_item_option(item = item, option = "rub") {
        player.queue {
            when (options("Burthorpe.", "Barbarian Outpost.", "Gamers' Grotto.", "Corporeal Beast.", "Nowhere.")) {
                1 -> player.teleport(LOCATIONS["Burthorpe"]!!, isEquipped = false)
                2 -> player.teleport(LOCATIONS["Barbarian Outpost"]!!, isEquipped = false)
                3 -> player.teleport(LOCATIONS["Gamers' Grotto"]!!, isEquipped = false)
                4 -> player.teleport(LOCATIONS["Corporeal Beast"]!!, isEquipped = false)
            }
        }
    }
}

GAMES_NECKLACE.forEach { item ->
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

        // Get the replacement ring with updated charges
        val replacement = replacement(getInteractingItemId())

        if (isEquipped) {
            // Update the equipped item, or remove it if there are no charges left
            equipment[EquipmentType.AMULET.id] = if (replacement > -1) Item(replacement) else null
        } else {
            // Remove the item from the inventory and add the replacement if there are charges left
            inventory.remove(getInteractingItem())
            if (replacement > -1) {
                inventory.add(replacement)
            }
        }

        // Teleport the player to the destination tile
        this@teleport.teleport(endTile, TeleportType.JEWELRY)

        // Send a message to the player about the remaining ring charges
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
        in Items.GAMES_NECKLACE_8..Items.GAMES_NECKLACE_2 -> original + 2
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
        Items.GAMES_NECKLACE_8 -> "Your games necklace has seven uses left."
        Items.GAMES_NECKLACE_7 -> "Your games necklace has six uses left."
        Items.GAMES_NECKLACE_6 -> "Your games necklace has five uses left."
        Items.GAMES_NECKLACE_5 -> "Your games necklace has four uses left."
        Items.GAMES_NECKLACE_4 -> "Your games necklace has three uses left."
        Items.GAMES_NECKLACE_3 -> "Your games necklace has two uses left."
        Items.GAMES_NECKLACE_2 -> "Your games necklace has one use left."
        else -> "Your games necklace crumbles to dust."
    }
}
