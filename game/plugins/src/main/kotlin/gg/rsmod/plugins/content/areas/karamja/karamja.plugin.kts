package gg.rsmod.plugins.content.areas.karamja

/**
 * The region ids of karamja (also a part of mudskipper point)
 */
val karamjaRegionIds = arrayOf(
    10802, 11058,
    10801, 11057, 11313, 11569, 11825,
    11056, 11312, 11568,
    11055, 11311, 11567, 11823,
    11054, 11310, 11566, 11822,
    11053, 11309, 11565, 11821
    )

karamjaRegionIds.forEach {
    on_exit_region(it) {
        // If player entered a new region is not in Karamja
        if (!karamjaRegionIds.contains(player.tile.regionId)) {
            onLeaveKaramja(player)
        }
    }
}

fun onLeaveKaramja(player: Player) {
    if (player.inventory.contains(Items.KARAMJAN_RUM)) {
        player.inventory.remove(Items.KARAMJAN_RUM, 28)
        player.message("Your Karamjan rum gets broken and spilled.")
    }
}
