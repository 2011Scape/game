package gg.rsmod.game.pathfinder

data class RayCast(
    val coordinates: List<RouteCoordinates>,
    val alternative: Boolean,
    val success: Boolean
) : List<RouteCoordinates> by coordinates {

    companion object {

        val FAILED: RayCast = RayCast(
            coordinates = emptyList(),
            alternative = false,
            success = false
        )

        val EMPTY_SUCCESS: RayCast = RayCast(
            coordinates = emptyList(),
            alternative = false,
            success = true
        )
    }
}
