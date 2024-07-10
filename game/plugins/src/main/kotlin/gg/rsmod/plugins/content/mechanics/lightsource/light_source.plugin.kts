package gg.rsmod.plugins.content.mechanics.lightsource

import gg.rsmod.game.model.timer.DARK_ZONE_TIMER

/**
 * This code contains logic for handling light sources in a game world, such as
 * dropping, extinguishing, and lighting actions. It is designed for
 * use within the Lumbridge Swamp Caves region.
 *
 * @author Alycia <https://github.com/alycii>
 */

// The region ID for the Lumbridge Swamp Caves.
val lumbridgeSwampCavesRegionId = 12693

// Obtain an array of light source product IDs.
val lightSource = LightSource.values().map { source -> source.product }.toIntArray()

// Obtain an array of light source raw IDs.
val lightSourceRaw = LightSource.values().map { source -> source.raw }.toIntArray()

// Define the logic for dropping light sources.
lightSource.forEach { source ->
    can_drop_item(item = source) {
        // Prevent dropping a light source within the Lumbridge Swamp Caves.
        if (player.tile.regionId == lumbridgeSwampCavesRegionId) {
            player.message("Dropping that would leave you without a light source.")
            return@can_drop_item false
        } else {
            return@can_drop_item true
        }
    }

    // Define the logic for extinguishing light sources.
    on_item_option(item = source, option = "extinguish") {
        val name = getName(source)
        // Prevent extinguishing a light source within the Lumbridge Swamp Caves.
        if (player.tile.regionId == lumbridgeSwampCavesRegionId) {
            player.message("Extinguishing the $name would leave you without a light source.")
            return@on_item_option
        }
        val item = player.getInteractingItem()
        val raw = LightSource.values().firstOrNull { item.id == it.product }?.raw ?: return@on_item_option
        val slot = player.getInteractingSlot()

        // Extinguish the source
        if (player.inventory.remove(item, beginSlot = slot).hasSucceeded()) {
            player.inventory.add(raw, beginSlot = slot)
            player.filterableMessage("You extinguish the $name.")
        }
    }
}

// Define the logic for lighting raw light sources.
lightSourceRaw.forEach { raw ->
    on_item_on_item(item1 = raw, item2 = Items.TINDERBOX_590) {
        val lightSource = LightSource.values().firstOrNull { it.raw == raw } ?: return@on_item_on_item
        val name = getName(raw)
        val slot = player.getInteractingSlot()
        // Check if the player has the required Firemaking level.
        if (player.skills.getCurrentLevel(Skills.FIREMAKING) < lightSource.levelRequired) {
            player.message("You need a Firemaking level of ${lightSource.levelRequired} to light the $name.")
            return@on_item_on_item
        }

        // Light the source
        if (player.inventory.remove(raw, beginSlot = slot).hasSucceeded()) {
            player.inventory.add(lightSource.product, beginSlot = slot)
            if (player.timers.has(DARK_ZONE_TIMER)) {
                val interfaceId = LightSource.getActiveLightSource(player)?.interfaceId ?: return@on_item_on_item
                player.openInterface(dest = InterfaceDestination.MAIN_SCREEN_OVERLAY, interfaceId = interfaceId)
            }
        }
    }
}

/**
 * Returns the name of a light source based on its item ID.
 *
 * @param itemId The item ID of the light source.
 * @return The name of the light source: "candle" or "lantern".
 */
fun getName(itemId: Int): String {
    return if (world.definitions
            .get(ItemDef::class.java, itemId)
            .name
            .contains("candle")
    ) {
        "candle"
    } else {
        "lantern"
    }
}
