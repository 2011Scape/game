package gg.rsmod.game.model

/**
 * Represents a simple polygon area in the world. Simple polygons are polygons which have no overlapping sides and no
 * inner gaps.
 *
 * @author Ilwyd
 */
data class SimplePolygonArea(
    val vertices: Array<Tile>,
    val associatedRegionIds: Array<Int> = vertices.map { it.regionId }.distinct().toTypedArray(),
) {
    /**
     * Determines whether a [Tile] is within the [SimplePolygonArea] or not. Because this is a simple polygon, we can
     * use the Even-Odd Rule (https://en.wikipedia.org/wiki/Even%E2%80%93odd_rule) to determine if the tile is in the
     * polygon.
     */
    fun containsTile(tile: Tile): Boolean {
        var inPolygon = false
        for (i in vertices.indices) {
            val a = vertices[i]
            val b = vertices[(i - 1 + vertices.size) % vertices.size]

            // Tile is on a corner
            if (tile.x == a.x && tile.z == a.z) return true

            if ((a.z > tile.z) != (b.z > tile.z)) {
                val slope = (tile.x - a.x) * (b.z - a.z) - (b.x - a.x) * (tile.z - a.z)
                // Tile is on boundary
                if (slope == 0) return true
                if ((slope < 0) != (b.z < a.z)) inPolygon = !inPolygon
            }
        }
        return inPolygon
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimplePolygonArea

        if (!vertices.contentEquals(other.vertices)) return false
        if (!associatedRegionIds.contentEquals(other.associatedRegionIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vertices.contentHashCode()
        result = 31 * result + associatedRegionIds.contentHashCode()
        return result
    }
}
