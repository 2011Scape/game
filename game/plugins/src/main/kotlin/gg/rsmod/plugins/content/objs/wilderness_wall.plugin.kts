package gg.rsmod.plugins.content.objs

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids =
    arrayOf(
        Objs.WILDERNESS_WALL,
        Objs.WILDERNESS_WALL_1441,
        Objs.WILDERNESS_WALL_1442,
        Objs.WILDERNESS_WALL_1443,
        Objs.WILDERNESS_WALL_1444,
    )
ids.forEach {
    on_obj_option(it, "cross") {
        val wall = player.getInteractingGameObj()
        val sideCross = player.tile.z == wall.tile.z

        val (direction, steps) =
            when {
                sideCross && player.tile.x < wall.tile.x -> Direction.EAST to 3
                sideCross -> Direction.WEST to 3
                player.tile.z < wall.tile.z -> Direction.NORTH to 3
                else -> Direction.SOUTH to 3
            }
        val endTile = player.tile.step(direction, steps)
        val directionAngle = direction.ordinal

        val movement =
            ForcedMovement.of(
                player.tile,
                endTile,
                clientDuration1 = 33,
                clientDuration2 = 60,
                directionAngle = directionAngle,
                lockState = LockState.FULL,
            )
        player.faceTile(endTile)
        player.crossDitch(movement)
    }
}

fun Player.crossDitch(movement: ForcedMovement) {
    queue {
        player.stopMovement()
        wait(2)
        playSound(Sfx.CLIMB_UNDER)
        animate(6132)
        forceMove(this, movement)
    }
}
