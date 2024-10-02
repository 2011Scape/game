package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

val RING_OF_DUELLING =
    intArrayOf(
        Items.RING_OF_DUELLING_8,
        Items.RING_OF_DUELLING_7,
        Items.RING_OF_DUELLING_6,
        Items.RING_OF_DUELLING_5,
        Items.RING_OF_DUELLING_4,
        Items.RING_OF_DUELLING_3,
        Items.RING_OF_DUELLING_2,
        Items.RING_OF_DUELLING_1,
    )

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1

private val LOCATIONS =
    mapOf(
        "Duel Arena" to Tile(3308, 3234, 0),
        "Castle Wars" to Tile(2440, 3089, 0),
        "Mobilising Armies" to Tile(2412, 2849, 0),
        "Fist of Guthix" to Tile(1703, 5599, 0),
    )

RING_OF_DUELLING.forEach { duel ->
    on_item_option(item = duel, option = "rub") {
        player.queue {
            when (
                options(
                    "Al Kharid Duel Arena.",
                    "Castle Wars Arena.",
                    "Mobilising Armies Command Centre.",
                    "Fist of Guthix.",
                    "Nowhere.",
                )
            ) {
                1 -> player.teleportWithRing(LOCATIONS["Duel Arena"]!!, isEquipped = false)
                2 -> player.teleportWithRing(LOCATIONS["Castle Wars"]!!, isEquipped = false)
                3 -> player.teleportWithRing(LOCATIONS["Mobilising Armies"]!!, isEquipped = false)
                4 -> player.teleportWithRing(LOCATIONS["Fist of Guthix"]!!, isEquipped = false)
            }
        }
    }
}

RING_OF_DUELLING.forEach { duel ->
    LOCATIONS.forEach { (location, endTile) ->
        on_equipment_option(duel, option = location) {
            player.queue(TaskPriority.STRONG) {
                player.teleportWithRing(endTile, isEquipped = true)
            }
        }
    }
}

/**
 * Teleports the player using a ring of duelling and updates the ring's charges.
 *
 * @param endTile The destination tile for the player after teleportation.
 * @param isEquipped Indicates if the ring is equipped on the player (true) or in the inventory (false).
 */
fun Player.teleportWithRing(
    endTile: Tile,
    isEquipped: Boolean,
) {
    // Check if the player can teleport using items
    if (canTeleport(TeleportType.JEWELRY)) {
        // Play a sound effect in the area around the player
        world.spawn(AreaSound(tile, SOUNDAREA_ID, SOUNDAREA_RADIUS, SOUNDAREA_VOLUME))

        // Get the replacement ring with updated charges
        val replacement = ringReplacement(getInteractingItemId())

        if (isEquipped) {
            // Update the equipped ring, or remove it if there are no charges left
            equipment[EquipmentType.RING.id] = if (replacement > -1) Item(replacement) else null
        } else {
            // Remove the ring from the inventory and add the replacement if there are charges left
            inventory.remove(getInteractingItem())
            if (replacement > -1) {
                inventory.add(replacement)
            }
        }

        // Teleport the player to the destination tile
        teleport(endTile, TeleportType.JEWELRY)

        // Send a message to the player about the remaining ring charges
        message(ringChargeMessage(getInteractingItemId()))
    }
}

/**
 * Returns the replacement ring with updated charges.
 *
 * @param original The original ring ID.
 * @return The replacement ring ID, or -1 if the original ring is out of charges.
 */
fun ringReplacement(original: Int): Int {
    return when (original) {
        in Items.RING_OF_DUELLING_8..Items.RING_OF_DUELLING_2 -> original + 2
        else -> -1
    }
}

/**
 * Returns a message with the remaining charges for the specified ring.
 *
 * @param original The original ring ID.
 * @return A message with the remaining charges, or a message indicating the ring has crumbled to dust.
 */
fun ringChargeMessage(original: Int): String {
    return when (original) {
        Items.RING_OF_DUELLING_8 -> "Your ring of duelling has seven uses left."
        Items.RING_OF_DUELLING_7 -> "Your ring of duelling has six uses left."
        Items.RING_OF_DUELLING_6 -> "Your ring of duelling has five uses left."
        Items.RING_OF_DUELLING_5 -> "Your ring of duelling has four uses left."
        Items.RING_OF_DUELLING_4 -> "Your ring of duelling has three uses left."
        Items.RING_OF_DUELLING_3 -> "Your ring of duelling has two uses left."
        Items.RING_OF_DUELLING_2 -> "Your ring of duelling has one use left."
        else -> "Your ring of duelling crumbles to dust."
    }
}
