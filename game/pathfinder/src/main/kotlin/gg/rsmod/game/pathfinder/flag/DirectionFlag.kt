package gg.rsmod.game.pathfinder.flag

object DirectionFlag {

    const val NORTH: Int = 0x1
    const val EAST: Int = 0x2
    const val SOUTH: Int = 0x4
    const val WEST: Int = 0x8

    const val SOUTH_WEST: Int = WEST or SOUTH
    const val NORTH_WEST: Int = WEST or NORTH
    const val SOUTH_EAST: Int = EAST or SOUTH
    const val NORTH_EAST: Int = EAST or NORTH
}
