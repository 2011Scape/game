package gg.rsmod.game.pathfinder

data class Route(
    val waypoints: List<RouteCoordinates>,
    val alternative: Boolean,
    val success: Boolean
) : List<RouteCoordinates> by waypoints {

    val failed: Boolean get() = !success

    companion object {

        val FAILED: Route = Route(emptyList(), alternative = false, success = false)
    }
}
