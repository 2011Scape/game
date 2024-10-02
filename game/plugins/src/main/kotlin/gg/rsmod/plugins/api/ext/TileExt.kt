package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World

fun Tile.isMulti(world: World): Boolean {
    val region = regionId
    val chunk = chunkCoords.hashCode()
    return world.getMultiCombatChunks().contains(chunk) || world.getMultiCombatRegions().contains(region)
}

val wildernessRegionIds =
    listOf(
        11831,
        11832,
        11833,
        11834,
        11835,
        11836,
        11837,
        12087,
        12088,
        12089,
        12090,
        12091,
        12092,
        12093,
        12343,
        12344,
        12345,
        12346,
        12347,
        12348,
        12349,
        12599,
        12600,
        12601,
        12602,
        12603,
        12604,
        12605,
        12855,
        12856,
        12857,
        12858,
        12859,
        12860,
        12861,
        13111,
        13112,
        13113,
        13114,
        13115,
        13116,
        13117,
        13367,
        13368,
        13369,
        13370,
        13371,
        13372,
        13373,
    )

fun Tile.getWildernessLevel(): Int {
    if (!wildernessRegionIds.contains(regionId) || z <= 3524) {
        return 0
    }

    /** Note from Ally: taken directly from cs2 script 54 */
    val undergroundLevel = ((z - 9920) / 8 + 1)
    val level = ((z - 3520) / 8 + 1)
    return if (z > 6400) undergroundLevel else level
}
