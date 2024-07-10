package gg.rsmod.game.model.instance

import gg.rsmod.game.model.Area
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.region.Chunk

/**
 * A system responsible for allocating and de-allocating [InstancedMap]s.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class InstancedMapAllocator {
    /**
     * A list of active [InstancedMap]s.
     */
    private val maps = mutableListOf<InstancedMap>()

    /**
     * The current cycles that keep track of how long before our allocated
     * should scan for 'inactive' [InstancedMap]s.
     */
    private var deallocationScanCycle = 0

    private fun deallocate(
        world: World,
        map: InstancedMap,
    ) {
        if (maps.remove(map)) {
            removeCollision(world, map)
            world.removeAll(map.area)

            /**
             * If the map is de-allocated, we want to move any players in the
             * instance to the [InstancedMap.exitTile].
             */
            world.players.forEach { player ->
                if (map.area.contains(player.tile)) {
                    player.moveTo(map.exitTile)
                }
            }
        }
    }

    internal val activeMapCount: Int get() = maps.size

    internal fun logout(player: Player) {
        val world = player.world

        getMap(player.tile)?.let { map ->
            player.moveTo(map.exitTile)

            if (map.attr.contains(InstancedMapAttribute.DEALLOCATE_ON_LOGOUT)) {
                val mapOwner = map.owner!! // If map has this attribute, they should also set an owner.
                if (player.uid == mapOwner) {
                    deallocate(world, map)
                }
            }
        }
    }

    internal fun death(player: Player) {
        val world = player.world

        getMap(player.tile)?.let { map ->
            if (map.attr.contains(InstancedMapAttribute.DEALLOCATE_ON_DEATH)) {
                val mapOwner = map.owner!! // If map has this attribute, they should also set an owner.
                if (player.uid == mapOwner) {
                    deallocate(world, map)
                }
            }
        }
    }

    internal fun cycle(world: World) {
        if (deallocationScanCycle++ == SCAN_MAPS_CYCLES) {
            for (i in 0 until maps.size) {
                val map = maps[i]

                /*
                 * If there's no players in the [map] area, we can de-allocate
                 * the map.
                 */
                if (world.players.none { map.area.contains(it.tile) }) {
                    deallocate(world, map)
                }
            }

            deallocationScanCycle = 0
        }
    }

    /**
     * @return
     * An [InstancedMap] who's area contains [tile], or null if no map is found in said tile.
     */
    fun getMap(tile: Tile): InstancedMap? = maps.find { it.area.contains(tile) }

    private fun removeCollision(
        world: World,
        map: InstancedMap,
    ) {
        val regionCount = map.chunks.regionSize
        val chunks = world.chunks

        for (i in 0 until regionCount) {
            val tile = map.area.bottomLeft.transform(i * Chunk.REGION_SIZE, i * Chunk.REGION_SIZE)
            chunks.remove(tile.chunkCoords)
        }
    }

    companion object {
        /**
         * 07 identifies instanced maps by having an X-axis of 6400 or above. They
         * use this for some client scripts, such as a Theatre of Blood cs2 that
         * will change depending on if you're inside the Theatre (in an instance)
         * or in the lobby.
         *
         * Current stats (as of 17/03/2018):
         * Area size: 3200x6400
         * Instance support: 5000 [50 in x-axis * 100 in z-axis]
         *
         * [Instance support]: the amount of instances the world can support at a time,
         * assuming every map is 64x64 in size, which isn't always the case.
         */
        private val VALID_AREA = Area(bottomLeftX = 6400, bottomLeftZ = 0, topRightX = 9600, topRightZ = 6400)

        /**
         * The amount of game cycles that must go by before scanning the active
         * [maps] for any [InstancedMap] eligible to be de-allocated.
         */
        private const val SCAN_MAPS_CYCLES = 25
    }
}
