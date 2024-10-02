package gg.rsmod.plugins.content.areas.falador

/**
 * @author Alycia <https://github.com/alycii>
 */

val CRUMBLING_WALL_LEVEL = 5
val CRUMBLING_WALL_ANIMATION = 839
val AGILITY_EXPERIENCE = 0.5

/**
 * Climb over a crumbling wall object.
 */
on_obj_option(obj = Objs.CRUMBLING_WALL_11844, option = "climb-over") {
    // Check Agility level requirement
    if (player.skills.getCurrentLevel(Skills.AGILITY) < CRUMBLING_WALL_LEVEL) {
        player.message("You need an Agility level of $CRUMBLING_WALL_LEVEL to climb over this wall.")
        return@on_obj_option
    }

    // The current tile
    val currentTile = player.tile

    // Calculate next tile based on player's current tile
    val nextTile =
        if (player.tile.x >= 2936) {
            player.tile.transform(x = -2, z = 0)
        } else {
            player.tile.transform(x = 2, z = 0)
        }

    // Create forced movement
    val move =
        ForcedMovement.of(
            currentTile,
            nextTile,
            clientDuration1 = 33,
            clientDuration2 = 60,
            directionAngle = Direction.between(currentTile, nextTile).ordinal,
            lockState = LockState.FULL,
        )

    // Perform climb animation and forced movement
    player.queue {
        player.stopMovement()
        wait(2)
        player.animate(CRUMBLING_WALL_ANIMATION)
        player.forceMove(this, move)
        player.addXp(skill = Skills.AGILITY, xp = AGILITY_EXPERIENCE)
    }
}
