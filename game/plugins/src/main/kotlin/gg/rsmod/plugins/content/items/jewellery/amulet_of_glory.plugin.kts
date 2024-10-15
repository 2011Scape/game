package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.game.model.Tile
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 20

private val LOCATIONS =
    mapOf(
        "Edgeville" to Tile(3086, 3503, 0),
        "Karamja" to Tile(2917, 3175, 0),
        "Draynor Village" to Tile(3104, 3249, 0),
        "Al Kharid" to Tile(3293, 3162, 0),
    )

val AMULET_OF_GLORY =
    intArrayOf(
        Items.AMULET_OF_GLORY_4,
        Items.AMULET_OF_GLORY_3,
        Items.AMULET_OF_GLORY_2,
        Items.AMULET_OF_GLORY_1,
        Items.AMULET_OF_GLORY_T4,
        Items.AMULET_OF_GLORY_T3,
        Items.AMULET_OF_GLORY_T2,
        Items.AMULET_OF_GLORY_T1,
    )

val EMPTY_GLORY =
    intArrayOf(
        Items.AMULET_OF_GLORY,
        Items.AMULET_OF_GLORY_T,
    )

EMPTY_GLORY.forEach { item ->
    on_equipment_option(item = item, option = "Rub") {
        player.message(
            "You will need to recharge your amulet of glory before you can use it again.",
            type = ChatMessageType.GAME_MESSAGE,
        )
    }
    on_item_option(item = item, option = "Rub") {
        player.message("The amulet has lost its charge.", type = ChatMessageType.GAME_MESSAGE)
        player.message("It will need to be recharged before you can use it again.", type = ChatMessageType.GAME_MESSAGE)
    }
}

AMULET_OF_GLORY.forEach { item ->
    on_item_option(item = item, option = "Rub") {
        player.queue {
            when (options("Edgeville.", "Karamja.", "Draynor Village.", "Al Kharid.", "Nowhere.")) {
                1 -> player.teleport(LOCATIONS["Edgeville"]!!, isEquipped = false)
                2 -> player.teleport(LOCATIONS["Karamja"]!!, isEquipped = false)
                3 -> player.teleport(LOCATIONS["Draynor Village"]!!, isEquipped = false)
                4 -> player.teleport(LOCATIONS["Al Kharid"]!!, isEquipped = false)
            }
        }
    }
}

AMULET_OF_GLORY.forEach { item ->
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

        // Get the replacement amulet with updated charges
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

        // Send a message to the player about the remaining amulet charges
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
        in Items.AMULET_OF_GLORY_T4..Items.AMULET_OF_GLORY_T1 -> original + 2
        else -> original - 2
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
        Items.AMULET_OF_GLORY_4, Items.AMULET_OF_GLORY_T4 -> "Your amulet of glory has three charges left."
        Items.AMULET_OF_GLORY_3, Items.AMULET_OF_GLORY_T3 -> "Your amulet of glory has two charges left."
        Items.AMULET_OF_GLORY_2, Items.AMULET_OF_GLORY_T2 -> "Your amulet of glory has one charge left."
        else -> "You use your amulet of glory's last charge."
    }
}
