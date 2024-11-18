package gg.rsmod.plugins.content.items

val NEWCOMER_MAP_POSITION_VARP = 106

on_item_option(Items.NEWCOMER_MAP, "read") {
    player.setVarp(NEWCOMER_MAP_POSITION_VARP, getNewcomerMapPosition(player))
    player.openInterface(270, InterfaceDestination.MAIN_SCREEN)
}

/**
 * Gets the player's position in the world and converts it to a value used by the Newcomer Map.
 *
 * Varp 106 can be set when the player opens the map and can use a value of 1-41 to show an X (-1 if the player is
 * out of the f2p zone). Varp values go from 1 in the bottom left corner of the map, to 41 in the top right.
 *
 * @param player: The player
 *
 * @return: The player's position converted to a value from 1-41 (or -1 if the player is out of f2p areas)
 */
fun getNewcomerMapPosition(player: Player): Int {
    // TODO: Implement

    return -1
}
