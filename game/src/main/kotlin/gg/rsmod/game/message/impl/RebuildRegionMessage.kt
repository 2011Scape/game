package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.service.xtea.XteaKeyService

/**
 * Represents a message responsible for rebuilding a specific region in the game
 * based on the provided coordinates and parameters.
 *
 * @property x The x-coordinate of the region.
 * @property z The z-coordinate of the region.
 * @property forceLoad A flag indicating whether the region should be forcefully loaded.
 * @property coordinates An array consisting of coordinates related to the region.
 * @property buildArea The identifier of the build area for the region.
 * @property areaMode The mode applied to the area during the rebuild process. The value of
 * `areaMode` determines specific behavior based on the client constants:
 *   - `DEFAULT (-1)`: Default behavior, applies no special rules.
 *   - `STATIC_AREA (0)`: Indicates the area is static and unchanging.
 *   - `CLEAR_LOCAL_NPCS (1)`: Clears all local NPCs (Non-Player Characters) within the area.
 *   - `ALLOW_OUT_OF_BOUNDS (3)`: Allows building operations outside the region’s defined boundaries.
 *   - `RETAIN_OUT_OF_BOUNDS (4)`: Retains items or configurations beyond the region’s normal boundaries.
 * @property xteaKeyService The service responsible for providing XTEA keys required
 * for map decryption within the region. These keys ensure secure and correct decryption
 * of game map data.
 *
 * This message is critical in managing game regions dynamically, enabling
 * functionality like forced loading, boundary adjustments, and NPC management.
 */

data class RebuildRegionMessage(
    val x: Int,
    val z: Int,
    val forceLoad: Int,
    val coordinates: IntArray,
    val buildArea: Int,
    val areaMode: Int,
    val xteaKeyService: XteaKeyService?,
) : Message {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RebuildRegionMessage

        if (x != other.x) return false
        if (z != other.z) return false
        if (forceLoad != other.forceLoad) return false
        if (buildArea != other.buildArea) return false
        if (areaMode != other.areaMode) return false
        if (!coordinates.contentEquals(other.coordinates)) return false
        if (xteaKeyService != other.xteaKeyService) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + z
        result = 31 * result + forceLoad
        result = 31 * result + buildArea
        result = 31 * result + areaMode
        result = 31 * result + coordinates.contentHashCode()
        result = 31 * result + (xteaKeyService?.hashCode() ?: 0)
        return result
    }
}
