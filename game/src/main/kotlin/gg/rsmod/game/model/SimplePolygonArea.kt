package gg.rsmod.game.model

/**
 * Represents a simple polygon area in the world. Simple polygons are polygons which have no overlapping sides and no
 * inner gaps.
 *
 * @author Ilwyd <https://github.com/ilwyd>
 */
data class SimplePolygonArea(
    var vertices: Array<Tile>,
) {
    val associatedRegionIds: Array<Int> = determineRegions()

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

    /**
     * Determines which regions contain tiles of this polygon area. Currently, this works by getting the highest and
     * lowest x and z tile values in the vertices and getting a rectangle of regions. This is not the most efficient
     * way to go about this, but is fast enough for our purposes at the moment.
     */
    private fun determineRegions(): Array<Int> {
        // If we only have 2 vertices, we need to turn it into a box
        if (vertices.size == 2) {
            val vertList = vertices.toMutableList()
            vertList.add(1, Tile(vertices[0].x, vertices[1].z))
            vertList.add(3, Tile(vertices[1].x, vertices[0].z))

            vertices = vertList.toTypedArray()
        }

        val maxX = vertices.maxOf { it.x }
        val minX = vertices.minOf { it.x }
        val maxZ = vertices.maxOf { it.z }
        val minZ = vertices.minOf { it.z }

        val topRight = Tile(maxX, maxZ)
        val bottomLeft = Tile(minX, minZ)

        val topRightRegion = topRight.regionId
        val bottomLeftRegion = bottomLeft.regionId

        if (topRightRegion == bottomLeftRegion) return arrayOf(topRightRegion)

        val regionDiffZ = (topRightRegion - bottomLeftRegion) % 256
        val regionDiffX = (topRightRegion - bottomLeftRegion - regionDiffZ) / 256

        val output = mutableListOf<Int>()

        for (z in 0..regionDiffZ) {
            for (x in 0..regionDiffX) {
                output.add(bottomLeftRegion + z + (x * 256))
            }
        }

        return output.toTypedArray()
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
