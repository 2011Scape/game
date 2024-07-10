package gg.rsmod.plugins.api

import kotlin.math.max

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class ProjectileType(
    val startHeight: Int,
    val endHeight: Int,
    val delay: Int,
    val angle: Int,
    val steepness: Int,
) {
    BOLT(startHeight = 38, endHeight = 36, delay = 41, angle = 5, steepness = 11),
    ARROW(startHeight = 40, endHeight = 36, delay = 41, angle = 15, steepness = 11),
    JAVELIN(startHeight = 38, endHeight = 36, delay = 42, angle = 1, steepness = 120),
    THROWN(startHeight = 40, endHeight = 36, delay = 32, angle = 15, steepness = 11),
    CHINCHOMPA(startHeight = 40, endHeight = 36, delay = 21, angle = 15, steepness = 11),
    TELEKINETIC_GRAB(startHeight = 38, endHeight = 0, delay = 55, angle = 0, steepness = 124),
    MAGIC(startHeight = 38, endHeight = 32, delay = 48, angle = 0, steepness = 124),
    FIERY_BREATH(startHeight = 38, endHeight = 16, delay = 48, angle = 16, steepness = 0),
    ;

    fun calculateLife(distance: Int): Int =
        when (this) {
            THROWN -> distance * 5
            CHINCHOMPA -> distance * 5
            ARROW, BOLT -> max(10, distance * 5)
            JAVELIN -> (distance * 3) + 2
        /*
         * Handled in [gg.rsmod.plugins.content.combat.Combat.getProjectileLifespan].
         */
            MAGIC, FIERY_BREATH, TELEKINETIC_GRAB -> -1
        }
}
