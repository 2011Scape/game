package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.game.model.Tile
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1

private val LOCATIONS =
    mapOf(
        "Fishing Guild" to Tile(2614, 3383, 0),
        "Mining Guild" to Tile(3017, 3339, 0),
        "Crafting Guild" to Tile(2933, 3294, 0),
        "Cooking Guild" to Tile(3143, 3441, 0),
    )

val SKILLS_NECKLACE =
    intArrayOf(
        Items.SKILLS_NECKLACE_4,
        Items.SKILLS_NECKLACE_3,
        Items.SKILLS_NECKLACE_2,
        Items.SKILLS_NECKLACE_1,
    )

on_equipment_option(item = Items.SKILLS_NECKLACE, option = "Rub") {
    player.message(
        "You will need to recharge your skills necklace before you can use it again.",
        type = ChatMessageType.GAME_MESSAGE,
    )
}
on_item_option(item = Items.SKILLS_NECKLACE, option = "Rub") {
    player.message("The necklace has lost its charge.", type = ChatMessageType.GAME_MESSAGE)
    player.message("It will need to be recharged before you can use it again.", type = ChatMessageType.GAME_MESSAGE)
}

SKILLS_NECKLACE.forEach { item ->
    on_item_option(item = item, option = "Rub") {
        player.queue {
            when (options("Fishing Guild.", "Mining Guild.", "Crafting Guild.", "Cooking Guild.", "Nowhere.")) {
                1 -> player.teleport(LOCATIONS["Fishing Guild"]!!, isEquipped = false)
                2 -> player.teleport(LOCATIONS["Mining Guild"]!!, isEquipped = false)
                3 -> player.teleport(LOCATIONS["Crafting Guild"]!!, isEquipped = false)
                4 -> player.teleport(LOCATIONS["Cooking Guild"]!!, isEquipped = false)
            }
        }
    }
}

SKILLS_NECKLACE.forEach { item ->
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
    return original + 2
}

/**
 * Returns a message with the remaining charges for the specified item.
 *
 * @param original The original item ID.
 * @return A message with the remaining charges, or a message indicating the item has crumbled to dust.
 */
fun message(original: Int): String {
    return when (original) {
        Items.SKILLS_NECKLACE_4 -> "Your skills necklace has three charges left."
        Items.SKILLS_NECKLACE_3 -> "Your skills necklace has two charges left."
        Items.SKILLS_NECKLACE_2 -> "Your skills necklace has one charge left."
        else -> "You use your skills necklace's last charge."
    }
}
