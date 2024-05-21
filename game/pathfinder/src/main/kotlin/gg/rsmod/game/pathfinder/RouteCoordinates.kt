package gg.rsmod.game.pathfinder

@JvmInline
value class RouteCoordinates(val packed: Int) {

    val x: Int get() = (packed shr 14) and 0x3FFF

    val z: Int get() = packed and 0x3FFF

    val level: Int get() = (packed shr 28) and 0x3

    constructor(x: Int, z: Int, level: Int = 0) : this(
        (z and 0x3FFF) or ((x and 0x3FFF) shl 14) or ((level and 0x3) shl 28)
    )

    fun translate(xOffset: Int, zOffset: Int, levelOffset: Int = 0): RouteCoordinates = RouteCoordinates(
        x = x + xOffset,
        z = z + zOffset,
        level = level + levelOffset
    )

    operator fun component1(): Int = x

    operator fun component2(): Int = z

    operator fun component3(): Int = level

    override fun toString(): String {
        return "RouteCoordinates(x=$x, z=$z, level=$level)"
    }
}
