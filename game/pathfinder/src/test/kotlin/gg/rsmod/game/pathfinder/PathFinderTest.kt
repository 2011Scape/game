package gg.rsmod.game.pathfinder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.EnumSource
import gg.rsmod.game.pathfinder.collision.buildCollisionMap
import gg.rsmod.game.pathfinder.collision.flag
import gg.rsmod.game.pathfinder.flag.BlockAccessFlag.BLOCK_EAST
import gg.rsmod.game.pathfinder.flag.BlockAccessFlag.BLOCK_NORTH
import gg.rsmod.game.pathfinder.flag.BlockAccessFlag.BLOCK_SOUTH
import gg.rsmod.game.pathfinder.flag.BlockAccessFlag.BLOCK_WEST
import gg.rsmod.game.pathfinder.flag.CollisionFlag
import gg.rsmod.game.pathfinder.flag.CollisionFlag.WALL_EAST
import gg.rsmod.game.pathfinder.flag.CollisionFlag.WALL_NORTH
import gg.rsmod.game.pathfinder.flag.CollisionFlag.WALL_SOUTH
import gg.rsmod.game.pathfinder.flag.CollisionFlag.WALL_WEST

class PathFinderTest {

    @Test
    fun testRouteCoordinatesMatchLevelInput() {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = 3201 to 3200
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ).let { route ->
            check(route.success)
            assertTrue(route.all { it.level == 0 })
        }
        pathFinder.findPath(level = 1, srcX, srcZ, destX, destZ).let { route ->
            check(route.success)
            assertTrue(route.all { it.level == 1 })
        }
        pathFinder.findPath(level = 2, srcX, srcZ, destX, destZ).let { route ->
            check(route.success)
            assertTrue(route.all { it.level == 2 })
        }
        pathFinder.findPath(level = 3, srcX, srcZ, destX, destZ).let { route ->
            check(route.success)
            assertTrue(route.all { it.level == 3 })
        }
    }

    @Test
    fun testSurroundedByObjectsAllowMoveNear() {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = 3205 to 3200
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
            .flag(srcX - 1, srcZ - 1, width = 3, height = 3, CollisionFlag.OBJECT)
        map[srcX, srcZ, 0] = 0 // Remove collision flag from source tile
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ).let { route ->
            assertTrue(route.alternative)
            assertTrue(route.isEmpty())
        }
    }

    @Test
    fun testSurroundedByObjectsNoMoveNear() {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = 3205 to 3200
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
            .flag(srcX - 1, srcZ - 1, width = 3, height = 3, CollisionFlag.OBJECT)
        map[srcX, srcZ, 0] = 0 // Remove collision flag from source tile
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ, moveNear = false).let { route ->
            assertTrue(route.failed)
            assertTrue(route.isEmpty())
        }
    }

    @Test
    fun testSurroundedByObjectsSingleExitPoint() {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = 3200 to 3205
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
            .flag(srcX - 1, srcZ - 1, width = 3, height = 3, CollisionFlag.OBJECT)
        map[srcX, srcZ, 0] = 0 // Remove collision flag from source tile
        map[srcX, srcZ - 1, 0] = 0 // Remove collision flag from tile south of source tile.
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ).let { route ->
            assertTrue(route.success)
            assertEquals(4, route.waypoints.size)
            assertEquals(RouteCoordinates(3200, 3198), route.waypoints[0])
            assertEquals(RouteCoordinates(3198, 3198), route.waypoints[1])
            assertEquals(RouteCoordinates(3198, 3203), route.waypoints[2])
            assertEquals(RouteCoordinates(destX, destZ), route.waypoints.last())
        }
    }

    @Test
    fun testStandingOnClosestApproachPoint() {
        val (srcX, srcZ) = 3200 to 3200
        val (objX, objZ) = 3200 to 3201
        val map = buildCollisionMap(srcX, srcZ, objX, objZ)
        val pathFinder = PathFinder(map)
        map[objX, objZ, 0] = WALL_NORTH or WALL_SOUTH or WALL_WEST or WALL_EAST
        pathFinder.findPath(level = 0, srcX, srcZ, objX, objZ).let { route ->
            assertTrue(route.success)
            assertTrue(route.alternative)
            assertTrue(route.isEmpty())
        }
    }

    @ParameterizedTest
    @EnumSource(Direction::class)
    fun testValidDirectionalPath(dir: Direction) {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = (srcX + dir.offX) to (srcZ + dir.offZ)
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ).let { route ->
            assertEquals(1, route.size)
            assertEquals(destX, route.last().x)
            assertEquals(destZ, route.last().z)
        }
        pathFinder.findPath(level = 1, srcX, srcZ, destX, destZ).let { route ->
            assertEquals(1, route.size)
            assertEquals(destX, route.last().x)
            assertEquals(destZ, route.last().z)
        }
        pathFinder.findPath(level = 2, srcX, srcZ, destX, destZ).let { route ->
            assertEquals(1, route.size)
            assertEquals(destX, route.last().x)
            assertEquals(destZ, route.last().z)
        }
        pathFinder.findPath(level = 3, srcX, srcZ, destX, destZ).let { route ->
            assertEquals(1, route.size)
            assertEquals(destX, route.last().x)
            assertEquals(destZ, route.last().z)
        }
    }

    @ParameterizedTest
    @EnumSource(Direction::class)
    fun testInvalidDirectionalPath(dir: Direction) {
        val (srcX, srcZ) = 3200 to 3200
        val (destX, destZ) = (srcX + dir.offX) to (srcZ + dir.offZ)
        val map = buildCollisionMap(srcX, srcZ, destX, destZ)
            .flag(destX, destZ, width = 1, height = 1, mask = CollisionFlag.FLOOR)
        val pathFinder = PathFinder(map)
        pathFinder.findPath(level = 0, srcX, srcZ, destX, destZ, moveNear = false).let { route ->
            assertTrue(route.failed)
            assertTrue(route.isEmpty())
            assertFalse(route.alternative)
        }
        pathFinder.findPath(level = 1, srcX, srcZ, destX, destZ, moveNear = false).let { route ->
            assertTrue(route.failed)
            assertTrue(route.isEmpty())
            assertFalse(route.alternative)
        }
        pathFinder.findPath(level = 2, srcX, srcZ, destX, destZ, moveNear = false).let { route ->
            assertTrue(route.failed)
            assertTrue(route.isEmpty())
            assertFalse(route.alternative)
        }
        pathFinder.findPath(level = 3, srcX, srcZ, destX, destZ, moveNear = false).let { route ->
            assertTrue(route.failed)
            assertTrue(route.isEmpty())
            assertFalse(route.alternative)
        }
    }

    /**
     * Test that object rotations are taken into account within [PathFinder.findPath]
     * and do not rely on external modifications. For example, given the parameters of
     * an object in coordinates (3203, 3203) with a dimension of 3 x 1 (width x height),
     * the following test should pass:
     *
     * Object rotation of [0] or [2]. (normal)
     * ```
     * --------
     * --------
     * --------
     * ---ooo--
     * --o   o-
     * ---ooo--
     * --------
     * --------
     * ```
     * Where:
     * - Area starts from bottom-left and makes its way to top-right. (3200,3200 - 3207,3207)
     * - ' ' (whitespace) are the tiles occupied by the rotated object.
     * - 'o' are the valid tiles that the tail of [Route.waypoints] may return.
     * - '-' represents every other tile in the area. (in this case a zone, or 8x8 tile area)
     */
    @ParameterizedTest
    @ArgumentsSource(RotatedObjectProvider::class)
    fun testRotatedObjectPathNormal(objX: Int, objZ: Int, dimension: Dimension) {
        val (width, height) = dimension
        val (minX, minZ) = RouteCoordinates(objX - 16, objZ - 16)
        val (maxX, maxZ) = RouteCoordinates(objX + 16, objZ + 16)
        val map = buildCollisionMap(minX, minZ, maxX, maxZ)
            .flag(objX, objZ, width = width, height = height, mask = CollisionFlag.OBJECT)
        val pathFinder = PathFinder(map)
        fun route(srcX: Int, srcZ: Int, rot: Int, blockAccessFlags: Int = 0): Route {
            return pathFinder.findPath(
                level = 0,
                srcX = srcX,
                srcZ = srcZ,
                destX = objX,
                destZ = objZ,
                destWidth = width,
                destHeight = height,
                objRot = rot,
                objShape = -2, //  Use rectangular exclusive strategy
                blockAccessFlags = blockAccessFlags
            )
        }
        for (x in 0 until width) {
            // Test coming from south tiles.
            route(srcX = objX + x, srcZ = objZ - 3, rot = 0).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ - 1), route.last())
            }
            route(srcX = objX + x, srcZ = objZ - 3, rot = 2).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ - 1), route.last())
            }
            // Test coming from north tiles.
            route(srcX = objX + x, srcZ = objZ + height + 3, rot = 0).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ + height), route.last())
            }
            route(srcX = objX + x, srcZ = objZ + height + 3, rot = 2).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ + height), route.last())
            }
            // Test coming from south tiles with access blocked.
            route(srcX = objX + x, srcZ = objZ - 3, rot = 0, blockAccessFlags = BLOCK_SOUTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ - 1, route.last().z)
            }
            route(srcX = objX + x, srcZ = objZ - 3, rot = 2, blockAccessFlags = BLOCK_NORTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ - 1, route.last().z)
            }
            // Test coming from north tiles with access blocked.
            route(srcX = objX + x, srcZ = objZ + height + 3, rot = 0, blockAccessFlags = BLOCK_NORTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ + height, route.last().z)
            }
            route(srcX = objX + x, srcZ = objZ + height + 3, rot = 2, blockAccessFlags = BLOCK_SOUTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ + height, route.last().z)
            }
        }
        for (z in 0 until height) {
            // Test coming from west tiles.
            route(srcX = objX - 3, srcZ = objZ + z, rot = 0).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX - 1, objZ + z), route.last())
            }
            route(srcX = objX - 3, srcZ = objZ + z, rot = 2).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX - 1, objZ + z), route.last())
            }
            // Test coming from east tiles.
            route(srcX = objX + width + 3, srcZ = objZ + z, rot = 0).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + width, objZ + z), route.last())
            }
            route(srcX = objX + width + 3, srcZ = objZ + z, rot = 2).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + width, objZ + z), route.last())
            }
            // Test coming from west tiles with access blocked.
            route(srcX = objX - 3, srcZ = objZ + z, rot = 0, blockAccessFlags = BLOCK_WEST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX - 1, route.last().x)
            }
            route(srcX = objX - 3, srcZ = objZ + z, rot = 2, blockAccessFlags = BLOCK_EAST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX - 1, route.last().x)
            }
            // Test coming from east tiles with access blocked.
            route(srcX = objX + width + 3, srcZ = objZ + z, rot = 0, blockAccessFlags = BLOCK_EAST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX + width, route.last().x)
            }
            route(srcX = objX + width + 3, srcZ = objZ + z, rot = 2, blockAccessFlags = BLOCK_WEST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX + width, route.last().x)
            }
        }
    }

    /**
     * Test that object rotations are taken into account within [PathFinder.findPath]
     * and do not rely on external modifications. For example, given the parameters of
     * an object in coordinates (3203, 3203) with a dimension of 3 x 1 (width x height),
     * the following test should pass:
     *
     * Object rotation of [1] or [3]. (flipped)
     * ```
     * --------
     * ---o----
     * --o o---
     * --o o---
     * --o o---
     * ---o----
     * --------
     * --------
     * ```
     * Where:
     * - Area starts from bottom-left and makes its way to top-right. (3200,3200 - 3207,3207)
     * - ' ' (whitespace) are the tiles occupied by the rotated object.
     * - 'o' are the valid tiles that the tail of [Route.waypoints] may return.
     * - '-' represents every other tile in the area. (in this case a zone, or 8x8 tile area)
     */
    @ParameterizedTest
    @ArgumentsSource(RotatedObjectProvider::class)
    fun testRotatedObjectPathFlipped(objX: Int, objZ: Int, dimension: Dimension) {
        val (width, height) = dimension
        val (minX, minZ) = RouteCoordinates(objX - 16, objZ - 16)
        val (maxX, maxZ) = RouteCoordinates(objX + 16, objZ + 16)
        val map = buildCollisionMap(minX, minZ, maxX, maxZ)
            .flag(objX, objZ, width = height, height = width, mask = CollisionFlag.OBJECT)
        val pathFinder = PathFinder(map)
        fun route(srcX: Int, srcZ: Int, rot: Int, blockAccessFlags: Int = 0): Route {
            return pathFinder.findPath(
                level = 0,
                srcX = srcX,
                srcZ = srcZ,
                destX = objX,
                destZ = objZ,
                destWidth = width,
                destHeight = height,
                objRot = rot,
                objShape = -2, //  Use rectangular exclusive strategy
                blockAccessFlags = blockAccessFlags
            )
        }
        for (x in 0 until height) {
            // Test coming from south tiles.
            route(srcX = objX + x, srcZ = objZ - 3, rot = 1).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ - 1), route.last())
            }
            route(srcX = objX + x, srcZ = objZ - 3, rot = 3).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ - 1), route.last())
            }
            // Test coming from north tiles.
            route(srcX = objX + x, srcZ = objZ + width + 3, rot = 1).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ + width), route.last())
            }
            route(srcX = objX + x, srcZ = objZ + width + 3, rot = 3).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + x, objZ + width), route.last())
            }
            // Test coming from south tiles with access blocked.
            route(srcX = objX + x, srcZ = objZ - 3, rot = 1, blockAccessFlags = BLOCK_EAST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ - 1, route.last().z)
            }
            route(srcX = objX + x, srcZ = objZ - 3, rot = 3, blockAccessFlags = BLOCK_WEST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ - 1, route.last().z)
            }
            // Test coming from north tiles with access blocked.
            route(srcX = objX + x, srcZ = objZ + width + 3, rot = 1, blockAccessFlags = BLOCK_WEST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ + width, route.last().z)
            }
            route(srcX = objX + x, srcZ = objZ + width + 3, rot = 3, blockAccessFlags = BLOCK_EAST).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objZ + width, route.last().z)
            }
        }
        for (z in 0 until width) {
            // Test coming from west tiles.
            route(srcX = objX - 3, srcZ = objZ + z, rot = 1).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX - 1, objZ + z), route.last())
            }
            route(srcX = objX - 3, srcZ = objZ + z, rot = 3).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX - 1, objZ + z), route.last())
            }
            // Test coming from east tiles.
            route(srcX = objX + height + 3, srcZ = objZ + z, rot = 1).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + height, objZ + z), route.last())
            }
            route(srcX = objX + height + 3, srcZ = objZ + z, rot = 3).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertEquals(RouteCoordinates(objX + height, objZ + z), route.last())
            }
            // Test coming from west tiles with access blocked.
            route(srcX = objX - 3, srcZ = objZ + z, rot = 1, blockAccessFlags = BLOCK_SOUTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX - 1, route.last().x)
            }
            route(srcX = objX - 3, srcZ = objZ + z, rot = 3, blockAccessFlags = BLOCK_NORTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX - 1, route.last().x)
            }
            // Test coming from east tiles with access blocked.
            route(srcX = objX + height + 3, srcZ = objZ + z, rot = 1, blockAccessFlags = BLOCK_NORTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX + height, route.last().x)
            }
            route(srcX = objX + height + 3, srcZ = objZ + z, rot = 3, blockAccessFlags = BLOCK_SOUTH).let { route ->
                assertTrue(route.success)
                assertFalse(route.alternative)
                assertNotEquals(objX + height, route.last().x)
            }
        }
    }
}
