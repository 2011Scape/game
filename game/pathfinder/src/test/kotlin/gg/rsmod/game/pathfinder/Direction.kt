package gg.rsmod.game.pathfinder

enum class Direction(val offX: Int, val offZ: Int) {
    South(0, -1),
    North(0, 1),
    West(-1, 0),
    East(1, 0),
    SouthWest(-1, -1),
    NorthWest(-1, 1),
    SouthEast(1, -1),
    NorthEast(1, 1);

    companion object {

        val values = enumValues<Direction>()

        val cardinal = listOf(South, North, West, East)
    }
}
