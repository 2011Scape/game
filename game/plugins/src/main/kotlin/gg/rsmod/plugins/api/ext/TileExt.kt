package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World

fun Tile.isMulti(world: World): Boolean {
    val region = regionId
    val chunk = chunkCoords.hashCode()
    return world.getMultiCombatChunks().contains(chunk) || world.getMultiCombatRegions().contains(region)
}

fun Tile.getWildernessLevel(): Int {
    /** Note from Ally: taken directly from cs2 script 54 */
    val undergroundLevel = (z - 9920) / 8 + 1;
    val level = (z - 3520) / 8 + 1;
    return if(z > 6400) undergroundLevel else level
}