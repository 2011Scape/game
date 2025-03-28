package gg.rsmod.plugins.content.items.jewellery

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

val RING_OF_SLAYING =
    intArrayOf(
        Items.RING_OF_SLAYING_8,
        Items.RING_OF_SLAYING_7,
        Items.RING_OF_SLAYING_6,
        Items.RING_OF_SLAYING_5,
        Items.RING_OF_SLAYING_4,
        Items.RING_OF_SLAYING_3,
        Items.RING_OF_SLAYING_2,
        Items.RING_OF_SLAYING_1,
    )

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1

private val LOCATIONS =
    mapOf(
        "Sumona" to Tile(3359, 2993, 0),
        "Slayer Tower" to Tile(3428, 3535, 0),
        "Fremennik Slayer Dungeon" to Tile(2791, 3615, 0),
        "Tarn's Lair" to Tile(3187, 4601, 0),
    )

RING_OF_SLAYING.forEach { ring ->
    on_item_option(item = ring, option = "rub") {
        teleportInterface(player, false)
    }

    on_equipment_option(item = ring, option = "rub") {
        teleportInterface(player, true)
    }

    on_item_option(item = ring, option = "kills-left") {
        player.getSlayerKillsRemaining()
    }

    on_equipment_option(item = ring, option = "kills-left") {
        player.getSlayerKillsRemaining()
    }
}

fun teleportInterface(
    player: Player,
    isEquipped: Boolean,
) {
    player.queue {
        when (
            options(
                "Sumona.",
                "Slayer Tower.",
                "Fremennik Slayer Dungeon.",
                "Tarn's Lair.",
                "Nowhere. Give me Slayer options.", // TODO: Confirm this text. Taken from RS3.
            )
        ) {
            1 -> player.teleportWithRing(LOCATIONS["Sumona"]!!, isEquipped)
            2 -> player.teleportWithRing(LOCATIONS["Slayer Tower"]!!, isEquipped)
            3 -> player.teleportWithRing(LOCATIONS["Fremennik Slayer Dungeon"]!!, isEquipped)
            4 -> player.teleportWithRing(LOCATIONS["Tarn's Lair"]!!, isEquipped)
            5 -> player.contactSlayerMaster(fromRing = true)
        }
    }
}

/**
 * Teleports the player using a ring of slaying and updates the ring's charges.
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
        in Items.RING_OF_SLAYING_8..Items.RING_OF_SLAYING_2 -> original + 1
        else -> Items.ENCHANTED_GEM
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
        Items.RING_OF_SLAYING_8 -> "Your ring of slaying has seven uses left."
        Items.RING_OF_SLAYING_7 -> "Your ring of slaying has six uses left."
        Items.RING_OF_SLAYING_6 -> "Your ring of slaying has five uses left."
        Items.RING_OF_SLAYING_5 -> "Your ring of slaying has four uses left."
        Items.RING_OF_SLAYING_4 -> "Your ring of slaying has three uses left."
        Items.RING_OF_SLAYING_3 -> "Your ring of slaying has two uses left."
        Items.RING_OF_SLAYING_2 -> "Your ring of slaying has one use left."
        else -> "The ring collapses into a Slayer gem, which you stow in your pack."
    }
}
